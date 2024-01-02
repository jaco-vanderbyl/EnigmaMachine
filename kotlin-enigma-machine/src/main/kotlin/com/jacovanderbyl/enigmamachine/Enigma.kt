package com.jacovanderbyl.enigmamachine

/**
 * Represents an Enigma Machine with a plugboard and a rotor unit (which contains a reflector and a set of rotors).
 *
 * Class simulates three behaviours:
 *     - Enciphering:
 *           Taking 'plaintext' input and outputting letter-substitution 'ciphertext'.
 *     - Setting Rotor Positions:
 *           Changing the starting position for each rotor (part of machine setup).
 *     - Adding Plugboard Connectors:
 *           Connecting letters on the plugboard (part of machine setup).
 *
 * Learn more about Enigma Machine:
 *     - https://en.wikipedia.org/wiki/Enigma_machine
 *     - https://www.cryptomuseum.com/crypto/enigma/
 */

class Enigma(
    val type: EnigmaType,
    private val rotorUnit: RotorUnit,
    private val plugboard: Plugboard
) : CanEncipher {
    /**
     * Simulate the enciphering of a single character.
     *
     * The input for this function is like a key-press on the Enigma Machine,
     * and the output is like a letter display-lamp lighting up.
     *
     * Each key-press on an Enigma Machine causes the rotors to step (turn), so this function does that first.
     *
     * Next, it simulates the letter substitution journey:
     *     1) Letter is 'sent' to the plugboard for substitution.
     *     2) Substituted letter is 'sent' to the rotor unit for substitution.
     *     3) Substituted letter is 'sent' back to the plugboard for final substitution.
     */
    override fun encipher(character: Char) : Char {
        rotorUnit.stepRotors()
        return plugboard.encipher(rotorUnit.encipher(plugboard.encipher(character)))
    }

    fun encipher(plaintext: String) : String = plaintext.map { encipher(it) }.joinToString("")

    fun setPositions(vararg positions: Letter) {
        require(positions.size == rotorUnit.rotors.size) {
            "Invalid position count. The number of rotor positions must equal the number of rotors " +
                    "in the unit: '${rotorUnit.rotors.size}'. Given: '${positions.size}'."
        }

        positions.forEachIndexed { rotorIndex, position -> rotorUnit.setPosition(rotorIndex, position) }
    }

    fun resetPositions() {
        rotorUnit.resetPositions()
    }

    fun addConnectors(vararg connectors: Connector) {
        plugboard.addConnectors(*connectors)
    }

    fun replaceConnectors(vararg connectors: Connector) {
        resetConnectors()
        plugboard.addConnectors(*connectors)
    }

    fun resetConnectors() {
        plugboard.reset()
    }

    fun getRotors(): Set<Rotor> = rotorUnit.rotors
}
