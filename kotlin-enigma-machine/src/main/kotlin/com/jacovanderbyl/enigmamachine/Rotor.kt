package com.jacovanderbyl.enigmamachine

import com.jacovanderbyl.enigmamachine.log.Log
import com.jacovanderbyl.enigmamachine.log.Logger

/**
 * Represents a rotor, used to substitute one letter with another.
 */
sealed class Rotor(
    val type: RotorType,
    private val cipherSetMap: CipherSetMap,
    private val compatibility: Set<EnigmaType>,
    var position: Letter,
    var ringSetting: Ring,
) {
    protected val characterSet: String = cipherSetMap.characterSet

    /**
     * Represents a rotor that does not step (turn). E.g., Emigma M4's BETA and GAMMA rotors.
     */
    class FixedRotor(
        type: RotorType,
        cipherSetMap: CipherSetMap,
        compatibility: Set<EnigmaType>,
        position: Letter,
        ringSetting: Ring
    ) : Rotor(type, cipherSetMap, compatibility, position, ringSetting)

    /**
     * Represents a rotor that can step (turn one position at a time).
     *
     * A single rotor performs a simple letter-substitution, but multiple rotors are mounted side by side on a spindle in
     * the rotor unit, and the rotors turn with every letter substitution. From wikipedia: Enigma's security comes
     * from using several rotors in series (usually three or four) and the regular stepping movement of the rotors,
     * thus implementing a polyalphabetic substitution cipher.
     */
    class StepRotor(
        type: RotorType,
        cipherSetMap: CipherSetMap,
        val notchPositions: Set<Letter>,
        compatibility: Set<EnigmaType>,
        position: Letter,
        ringSetting: Ring
    ) : Rotor(type, cipherSetMap, compatibility, position, ringSetting) {
        fun step() {
            val stepToCharacter = characterSet[shiftIndex(position.index, shiftBy = 1)]
            position = Letter.valueOf(stepToCharacter.toString())
        }

        fun isInNotchedPosition() : Boolean = position in notchPositions
    }

    /**
     * Represents the ring setting on a rotor.
     *
     * From Wikipedia: The ring settings, or Ringstellung, are used to change the position of the alphabet ring relative
     * to the internal wiring. Notch and alphabet ring are fixed together. Changing the ring setting will therefore change
     * the positions of the wiring, relative to the turnover-point and start position.
     */
    enum class Ring(val value: Int, val index: Int) {
        SETTING_1(1, 0),
        SETTING_2(2, 1),
        SETTING_3(3, 2),
        SETTING_4(4, 3),
        SETTING_5(5, 4),
        SETTING_6(6, 5),
        SETTING_7(7, 6),
        SETTING_8(8, 7),
        SETTING_9(9, 8),
        SETTING_10(10, 9),
        SETTING_11(11, 10),
        SETTING_12(12, 11),
        SETTING_13(13, 12),
        SETTING_14(14, 13),
        SETTING_15(15, 14),
        SETTING_16(16, 15),
        SETTING_17(17, 16),
        SETTING_18(18, 17),
        SETTING_19(19, 18),
        SETTING_20(20, 19),
        SETTING_21(21, 20),
        SETTING_22(22, 21),
        SETTING_23(23, 22),
        SETTING_24(24, 23),
        SETTING_25(25, 24),
        SETTING_26(26, 25);

        companion object {
            fun list() = Ring.entries
        }
    }

    /**
     * Substitute one character for another, simulating rotor wiring, position, and ring setting.
     *
     * To better understand the steps, see:
     *     https://crypto.stackexchange.com/a/71233
     *     https://crypto.stackexchange.com/a/81585
     */
    fun encipher(character: Char, reverse: Boolean = false) : Char {
        require(character in characterSet) {
            "Invalid character. Valid: '${characterSet}'. Given: '${character}'."
        }

        // Step 1 - Apply offset to given character's index, to accommodate rotor's current position and ring setting.
        val charIndex = characterSet.indexOf(character)
        val charIndexWithOffset = shiftIndex(charIndex, shiftBy = offset())
        val charWithOffset = characterSet[charIndexWithOffset]
        Logger.get()?.write(Log.RotorShift.create(character, charWithOffset, rotor = this))

        // Step 2 - Simulate wiring to substitute given character with new character. Allow bidirectional substitutions.
        val substituteChar = cipherSetMap.encipher(charWithOffset, reverse)
        Logger.get()?.write(Log.RotorSubstitute.create(charWithOffset, substituteChar, reverse, rotor = this))

        // Step 3 - Revert offset (applied in step 1) to substitute character's index.
        val substituteCharIndex = characterSet.indexOf(substituteChar)
        val finalCharIndex = shiftIndex(substituteCharIndex, shiftBy = offset() * -1)
        val finalChar = characterSet[finalCharIndex]
        Logger.get()?.write(Log.RotorDeshift.create(substituteChar, finalChar, rotor = this))

        return finalChar
    }

    fun isCompatible(enigmaType: EnigmaType) : Boolean = enigmaType in compatibility

    fun offset() : Int = position.index - ringSetting.index

    fun resetPosition() {
        position = Letter.A
    }

    protected fun shiftIndex(index: Int, shiftBy: Int) : Int {
        val shiftedIndex = index + shiftBy % characterSet.length

        return when {
            shiftedIndex >= characterSet.length -> shiftedIndex - characterSet.length
            shiftedIndex < 0                    -> shiftedIndex + characterSet.length
            else                                -> shiftedIndex
        }
    }

    // For logging.
    val cipherSetMaps: Pair<String,String> = cipherSetMap.characterSet to cipherSetMap.cipherSet
}
