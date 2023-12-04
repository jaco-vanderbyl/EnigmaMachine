package com.jacovanderbyl.enigmamachine

/**
 * Represents an Enigma Machine with a plugboard and a rotor unit (which contains a reflector and a set of rotors).
 *
 * This class simulates three behaviours:
 *     - Enciphering: taking a 'plaintext' input letter and substituting it with a 'ciphertext' letter.
 *     - Setting Rotor Positions: changing the starting position for each rotor (part of setting up the machine).
 *     - Connecting Plugboard Connector Cables: connecting letters with one another (part of setting up the machine).
 */
class Enigma(
    val type: EnigmaFactory,
    private val rotorUnit: RotorUnit,
    private val plugboard: Plugboard
) : CanEncipher {
    /**
     * Simulate an Enigma Machine's enciphering of a single character.
     *
     * The input of this function is like a key-press on the Enigma Machine and the output is like a letter
     * display-lamp lighting up.
     *
     * Each key-press on an Enigma Machine cause the rotors to step, so this function does that first.
     *
     * Next, it simulates the letter substitution journey: first the letter is sent to the plugboard for
     * substitution, then it's sent to the rotor unit, and finally, it's sent back to the plugboard, which
     * does the final substitution.
     */
    override fun encipher(character: Char) : Char {
        require(character in Keys.CHARACTER_SET) {
            "Invalid character. Valid: '${Keys.CHARACTER_SET}'. Given: '${character}'."
        }

        rotorUnit.stepRotors()

        var substituteCharacter = character
        substituteCharacter = plugboard.encipher(substituteCharacter)
        substituteCharacter = rotorUnit.encipher(substituteCharacter)
        substituteCharacter = plugboard.encipher(substituteCharacter)

        return substituteCharacter
    }

    fun encipher(plaintext: String) : String = plaintext.map { encipher(it) }.joinToString("")

    fun setRotorPositions(vararg positions: Position) {
        require(positions.size == rotorUnit.rotors.size) {
            "The number of rotor positions must equal the number of rotors in the unit: '${rotorUnit.rotors.size}'. " +
                    "Given: '${positions.size}'."
        }

        positions.forEachIndexed { index, position ->
            rotorUnit.setRotorPosition(index, position)
        }
    }

    fun getRotorPositions() : List<Position> {
        return rotorUnit.rotors.map { it.position }
    }

    fun resetRotorPositions() {
        rotorUnit.resetRotorPositions()
    }

    fun addPlugboardConnectors(vararg connectors: Connector) {
        plugboard.connectPlugs(*connectors)
    }

    fun replacePlugboardConnectors(vararg connectors: Connector) {
        resetPlugboard()
        plugboard.connectPlugs(*connectors)
    }

    fun resetPlugboard() {
        plugboard.reset()
    }
}
