package com.jacovanderbyl.enigmamachine

import com.jacovanderbyl.enigmamachine.log.Logger
import com.jacovanderbyl.enigmamachine.log.RotorLogDeshift
import com.jacovanderbyl.enigmamachine.log.RotorLogShift
import com.jacovanderbyl.enigmamachine.log.RotorLogSubstitute

/**
 * Represents a rotor, used to substitute one letter with another.
 */
open class Rotor(
    val type: RotorType,
    protected val cipherSetMap: CipherSetMap,
    private val compatibility: Set<EnigmaType>,
    var position: Position,
    var ringSetting: RingSetting,
) : CanEncipherBidirectionally, HasCompatibility {
    val characterSet: String = cipherSetMap.characterSet
    val cipherSet: String = cipherSetMap.cipherSet
    var logger: Logger? = null

    override fun isCompatible(enigmaType: EnigmaType) : Boolean = enigmaType in compatibility

    fun resetPosition() {
        position = Position()
    }

    /**
     * Substitute one character for another, simulating rotor wiring, position, and ring setting.
     *
     * To better understand steps, see:
     *     https://crypto.stackexchange.com/a/71233
     *     https://crypto.stackexchange.com/a/81585
     */
    override fun encipher(character: Char, reverse: Boolean) : Char {
        require(character in characterSet) {
            "Invalid character. Valid: '${characterSet}'. Given: '${character}'."
        }

        // Step 1 - Apply offset to given character's index, to accommodate rotor's current position and ring setting.
        val characterIndex = characterSet.indexOf(character)
        val characterIndexWithOffset = shiftIndex(characterIndex, shiftBy = offset())
        val characterWithOffset = characterSet[characterIndexWithOffset]
        logger?.add(RotorLogShift.fromRotor(character, characterWithOffset, rotor = this))

        // Step 2 - Simulate wiring to substitute given character with new character. Allow bidirectional substitutions.
        val substituteCharacter = cipherSetMap.encipher(characterWithOffset, reverse)
        logger?.add(RotorLogSubstitute.fromRotor(characterWithOffset, substituteCharacter, reverse, rotor = this))

        // Step 3 - Revert offset (applied in step 1) to substitute character's index.
        val substituteCharacterIndex = characterSet.indexOf(substituteCharacter)
        val finalCharacterIndex = shiftIndex(substituteCharacterIndex, shiftBy = offset() * -1)
        val finalCharacter = characterSet[finalCharacterIndex]
        logger?.add(RotorLogDeshift.fromRotor(substituteCharacter, finalCharacter, rotor = this))

        return finalCharacter
    }

    fun offset() : Int = position.index - ringSetting.index

    protected fun shiftIndex(index: Int, shiftBy: Int) : Int {
        val shiftedIndex = index + shiftBy % characterSet.length

        return when {
            shiftedIndex >= characterSet.length -> shiftedIndex - characterSet.length
            shiftedIndex < 0                    -> shiftedIndex + characterSet.length
            else                                -> shiftedIndex
        }
    }
}
