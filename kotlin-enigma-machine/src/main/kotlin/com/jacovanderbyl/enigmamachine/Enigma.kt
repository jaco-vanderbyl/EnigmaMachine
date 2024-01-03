package com.jacovanderbyl.enigmamachine

import com.jacovanderbyl.enigmamachine.log.Log
import com.jacovanderbyl.enigmamachine.log.Logger

/**
 * Represents an Enigma Machine with a Reflector, Rotors, and a Plugboard.
 *
 * This class simulates four behaviours:
 *     - Stepping:
 *           How the Step Rotors turn, which happens after each key-press.
 *     - Enciphering:
 *           Taking 'plaintext' input and outputting letter-substitution 'ciphertext'.
 *     - Setting Rotor Positions:
 *           Changing the starting position for each Rotor (part of machine setup).
 *     - Adding Plugboard Connectors:
 *           Connecting letters on the Plugboard (part of machine setup).
 *
 * STEPPING allows for greater cryptographical complexity.
 * It ensures that repeated letters in the input plaintext
 * are not just substituted with repeated letters in the ciphertext.
 *     I.e., it avoids 'AAAAA' being enciphered to something like 'BBBBB'.
 *
 * ENCIPHERING depends on the type of Reflector,
 * the number and type of Rotors together with their ring settings and positions,
 * and the Plugboard configuration.
 *
 * Learn more about Enigma Machine:
 *     - https://en.wikipedia.org/wiki/Enigma_machine
 *     - https://www.cryptomuseum.com/crypto/enigma/
 */
class Enigma(
    val type: EnigmaType,
    private val reflector: Reflector,
    private val rotors: Set<Rotor>,
    private val plugboard: Plugboard
) {
    /**
     * Simulate the enciphering of a single character.
     *
     * The input for this function is like a key-press on the Enigma Machine,
     * and the output is like a letter display-lamp lighting up.
     *
     * Each key-press on an Enigma Machine causes the rotors to step (turn),
     * so the function does that first.
     *
     * Next, the letter undergoes multiple substitutions (up to nine on a three-rotor machine).
     *
     * The letter substitution journey:
     *     1) PLUGBOARD              — may substitute, depends on Connector configuration.
     *     2) ROTORS (right to left) — each will substitute, starting with Entry Rotor (right-most).
     *     3) REFLECTOR              — will substitute and 'reflect' back to Rotors.
     *     4) ROTORS (left to right) — each will substitute, ending with Entry Rotor.
     *     5) PLUGBOARD              — may substitute
     */
    fun encipher(character: Char) : Char {
        stepRotors()

        val first = plugboard.encipher(character)
        val second = rotors.reversed().runningFold(first) { char, rotor -> rotor.encipher(char) }.last()
        val third = reflector.encipher(second)
        val fourth = rotors.runningFold(third) { char, rotor -> rotor.encipher(char, reverse = true) }.last()

        return plugboard.encipher(fourth)
    }

    fun encipher(plaintext: String) : String = plaintext.map { encipher(it) }.joinToString("")

    fun setPositions(vararg positions: Letter) {
        require(positions.size == rotors.size) {
            "Invalid position count. The number of rotor positions must equal the number of rotors " +
                    "in the unit: '${rotors.size}'. Given: '${positions.size}'."
        }

        positions.forEachIndexed { index, position -> rotors.elementAt(index).position = position }
    }

    fun resetPositions() {
        rotors.forEach { it.resetPosition() }
    }

    fun addConnectors(vararg connectors: Plugboard.Connector) {
        plugboard.addConnectors(*connectors)
    }

    fun replaceConnectors(vararg connectors: Plugboard.Connector) {
        resetConnectors()
        plugboard.addConnectors(*connectors)
    }

    fun resetConnectors() {
        plugboard.reset()
    }

    /**
     * Simulate rotor stepping.
     *
     * To understand how the stepping mechanism works, it's required to know the following:
     *     - There are different types of Step Rotors.
     *     - Each Step Rotor is a disc with 26 positions, indicated by a letter.
     *     - Each Step Rotor has at least one notch cut out at a specific letter position.
     *           RotorI has notch at 'Q', RotorII at 'E', and RotorIII at 'V'.
     *           RotorVI has two notches, at 'M' and 'Z'.
     *     - The different types of Rotors fit together, and they can be arranged in any order.
     *           RotorI-RotorII-RotorIII or RotorV-RotorI-RotorIV, etc.
     *           The Enigma I, the most common model, accepted three Rotors,
     *           but other models could accept up to eight.
     *     - Once in the machine, each Rotor can be set to a specific starting letter-position.
     *           A-A-A, or R-T-B, etc.
     *     - The Rotor notches, together with physical key-presses,
     *       determine when and how the rotors in the unit turn.
     *
     * Stepping rules:
     *     1) The Entry Rotor (right-most) always steps when a key is pressed.
     *     2) Any Rotor that steps out of its notch position
     *        will cause the Rotor to its left to step also.
     *     3) When the Entry Rotor steps, it will also step the Rotor immediately next to it
     *        if that adjacent Rotor is in its notched position.
     *        Subsequently, this will cause the next-left Rotor to step as well (rule 2).
     *        This is the so-called 'double stepping' mechanism.
     *
     * Example of stepping sequences with the following Rotor setup:
     *     RotorI-RotorII-RotorIII (has notches Q-E-V) with starting positions A-A-T.
     *
     *     A-A-T
     *     A-A-U  (Rule1: T→U)
     *     A-A-V  (Rule1: U→V) — Entry Rotor is now in notch position: V
     *     A-B-W  (Rule1: V→W steps out of notch) ⇒ (Rule2: A→B)
     *     A-B-X  (Rule1: W→X)
     *     .....  Stepping continues
     *     A-D-U  (Rule1: T→U)
     *     A-D-V  (Rule1: U→V) — Entry Rotor is now in notch position: V
     *     A-E-W  (Rule1: V→W steps out of notch) ⇒ (Rule2: D→E) — Middle is now in notch position: E
     *     B-F-X  (Rule1: W→X) ⇒ (Rule3: E→F steps out of notch) ⇒ (Rule2: A→B) — Double stepping
     *     B-F-Y  (Rule1: X→Y)
     *     .....  Stepping continues
     *     Q-D-U  Left-most is in notch position: Q
     *     Q-D-V
     *     Q-E-W  Middle is now in notch position: E
     *     R-F-X  (Rule1: W→X) ⇒ (Rule3: E→F steps out of notch) ⇒ (Rule2: Q→R steps out of notch) ⇒
     *            no further effect because there is not another Rotor to the left
     *
     * In this function, the stepping rules are applied as follows:
     * It loops over the Step Rotors, starting with the Entry Rotor (last item in the set),
     * and then, to determine if the current Rotor in the loop should step,
     * one of three conditions must be true:
     *     a) The current Rotor _is_ the Entry Rotor, or
     *     b) the previous Rotor stepped out of its notched position, or
     *     c) the current Rotor is in its notched position, and it's adjacent to the entry rotor.
     */
    private fun stepRotors() {
        // For logging.
        val rotorsBeforeStep = rotors.map { it }

        var previousSteppedOutOfNotch = false
        rotors.filterIsInstance<Rotor.StepRotor>().reversed().forEachIndexed { index, rotor ->
            val isEntryRotor = index == 0
            val isNextToEntryRotor = index == 1
            val isNotchedAndNextToEntryRotor = rotor.isInNotchedPosition() && isNextToEntryRotor

            if (isEntryRotor || previousSteppedOutOfNotch || isNotchedAndNextToEntryRotor) {
                previousSteppedOutOfNotch = rotor.isInNotchedPosition()
                rotor.step()
            }
        }

        Logger.get()?.write(Log.EnigmaStep.create(rotorsBeforeStep, rotors.map { it }, enigma = this))
    }
}
