package com.jacovanderbyl.enigmamachine

/**
 * Represents a rotor, used to substitute one letter with another.
 *
 * A single rotor performs a simple letter-substitution, but multiple rotors are mounted side by side on a spindle in
 * the rotor unit, and the rotors turn with every letter substitution. From wikipedia: Enigma's security comes
 * from using several rotors in series (usually three or four) and the regular stepping movement of the rotors,
 * thus implementing a polyalphabetic substitution cipher.
 *
 * This class simulates two behaviours:
 *     - Stepping:
 *           Turning the rotor one position. E.g. turing from position 'A' to 'B'.
 *     - Enciphering:
 *           Substituting one letter for another, given a cipher set map (representing the internal wiring).
 */
class Rotor(
    val type: RotorType,
    private val cipherSetMap: CipherSetMap,
    private val notch: Notch,
    private val compatibility: Set<EnigmaType>,
    var position: Position,
    var ringSetting: RingSetting
) : CanEncipherBidirectionally, HasCompatibility {
    private val characterSet: String = cipherSetMap.characterSet

    fun step() {
        position = Position(characterSet[shiftIndex(position.index, shiftBy = 1)])
    }

    fun resetPosition() {
        position = Position()
    }

    fun isInNotchedPosition() : Boolean = position.character in notch.characters

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

        // Step 2 - Simulate wiring to substitute given character with new character. Allow bidirectional substitutions.
        val substituteCharacter = cipherSetMap.encipher(characterWithOffset, reverse)

        // Step 3 - Revert offset (applied in step 1) to substitute character's index.
        val substituteCharacterIndex = characterSet.indexOf(substituteCharacter)
        val finalCharacterIndex = shiftIndex(substituteCharacterIndex, shiftBy = offset() * -1)

        return characterSet[finalCharacterIndex]
    }

    override fun isCompatible(enigmaType: EnigmaType) : Boolean = enigmaType in compatibility

    private fun offset() : Int = position.index - ringSetting.index

    private fun shiftIndex(index: Int, shiftBy: Int) : Int {
        val shiftedIndex = index + shiftBy % characterSet.length

        return when {
            shiftedIndex >= characterSet.length -> shiftedIndex - characterSet.length
            shiftedIndex < 0                    -> shiftedIndex + characterSet.length
            else                                -> shiftedIndex
        }
    }
}
