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

        fun isInNotchedPosition() : Boolean = position.character in notchPositions.map { it.character }
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
        val characterIndex = characterSet.indexOf(character)
        val characterIndexWithOffset = shiftIndex(characterIndex, shiftBy = offset())
        val characterWithOffset = characterSet[characterIndexWithOffset]
        Logger.add(Log.RotorShift.create(character, characterWithOffset, rotor = this))

        // Step 2 - Simulate wiring to substitute given character with new character. Allow bidirectional substitutions.
        val substituteCharacter = cipherSetMap.encipher(characterWithOffset, reverse)
        Logger.add(Log.RotorSubstitute.create(characterWithOffset, substituteCharacter, reverse, rotor = this))

        // Step 3 - Revert offset (applied in step 1) to substitute character's index.
        val substituteCharacterIndex = characterSet.indexOf(substituteCharacter)
        val finalCharacterIndex = shiftIndex(substituteCharacterIndex, shiftBy = offset() * -1)
        val finalCharacter = characterSet[finalCharacterIndex]
        Logger.add(Log.RotorDeshift.create(substituteCharacter, finalCharacter, rotor = this))

        return finalCharacter
    }

    fun isCompatible(enigmaType: EnigmaType) : Boolean = enigmaType in compatibility

    fun offset() : Int = position.index - ringSetting.index

    fun resetPosition() {
        position = Letter.A
    }

    fun getCipherSetMaps() : Pair<String,String> = cipherSetMap.characterSet to cipherSetMap.cipherSet

    protected fun shiftIndex(index: Int, shiftBy: Int) : Int {
        val shiftedIndex = index + shiftBy % characterSet.length

        return when {
            shiftedIndex >= characterSet.length -> shiftedIndex - characterSet.length
            shiftedIndex < 0                    -> shiftedIndex + characterSet.length
            else                                -> shiftedIndex
        }
    }
}
