package com.jacovanderbyl.enigmamachine

import com.jacovanderbyl.enigmamachine.log.Log
import com.jacovanderbyl.enigmamachine.log.Logger

/**
 * Represents a rotor unit, consisting of a reflector and a set of rotors.
 *
 * This class simulates two behaviours:
 *     - Stepping:
 *           When and how the step rotors turn, which happens after each key-press on the Enigma Machine.
 *     - Enciphering:
 *           Taking a 'plaintext' input letter and substituting it with a 'ciphertext' letter.
 *
 * STEPPING allows for greater cryptographical complexity. It's what ensures that repeated letters in the input
 * plaintext are not just substituted with repeated letters in the ciphertext.
 *     I.e. it avoids 'AAAAA' being enciphered to 'BBBBB'. Instead, the ciphertext will be something like: 'BDZGO'.
 *
 * ENCIPHERING depends on the type of reflector, the number and type of rotors, the rotors' ring setting, and
 * the rotors' position.
 */
class RotorUnit(val reflector: Reflector, val rotors: Set<Rotor>) {
    init {
        require(rotors.none { rotor -> rotors.count { it.type == rotor.type } > 1 }) {
            "Duplicate rotor types are not allowed. Given: ${rotors.map { it.type }}."
        }
    }

    /**
     * Simulate rotor stepping.
     *
     * To understand how the stepping mechanism works, it's required to know the following:
     *     - There are different types of step rotors.
     *     - Every step rotor is a disc with 26 positions, each indicated by a letter of the alphabet.
     *     - Every step rotor has at least one notch cut out at a specific letter position.
     *       E.g. RotorI has notch at 'Q', RotorII at 'E', and RotorIII at 'V'. RotorVI has two notches, at 'M' and 'Z'.
     *     - The different types of rotors fit together, and they can be arranged in any order. The Enigma I, the most
     *       common Enigma Machine model, accepted a unit of 3 rotors, but other models could accept up to 8.
     *       E.g. A rotor unit could be: RotorI-RotorII-RotorIII or RotorV-RotorI-RotorIV, etc.
     *     - Once in the machine, each rotor can be set independently to a specific starting letter-position.
     *       E.g. A-A-A, or R-T-B, etc.
     *     - The rotor notches, together with physical key-presses, determine when and how the rotors in the unit turn.
     *
     * Stepping rules:
     *     1) The entry rotor (right-most) always steps when a key is pressed.
     *     2) Any rotor that steps out of its notch position will cause the rotor to its left to step also.
     *        I.e. when a rotor is in its notched position and then turns, it will turn the rotor to the left of it.
     *     3) When the entry rotor steps, it will also step the rotor immediately next to it if that adjacent
     *        rotor happens to be in its notched position. Subsequently, this will cause the next-left rotor to step
     *        as well (by way of rule 2). This is the so-called 'double stepping' mechanism.
     *
     * Example with following setup: RotorI-RotorII-RotorIII, which have notches Q-E-V, with starting positions A-A-T.
     *     A-A-T
     *     A-A-U  (Rule1: T→U)
     *     A-A-V  (Rule1: U→V) — Entry rotor is now in its notch position: V
     *     A-B-W  (Rule1: V→W steps out of notch) ⇒ (Rule2: A→B)
     *     A-B-X  (Rule1: W→X)
     *     .....  Stepping continues
     *     A-D-U  (Rule1: T→U)
     *     A-D-V  (Rule1: U→V) — Entry rotor is now in its notch position: V
     *     A-E-W  (Rule1: V→W steps out of notch) ⇒ (Rule2: D→E) — Middle rotor is now in its notch position: E
     *     B-F-X  (Rule1: W→X) ⇒ (Rule3: E→F steps out of notch) ⇒ (Rule2: A→B) — Double stepping complete
     *     B-F-Y  (Rule1: X→Y)
     *     .....  Stepping continues
     *     Q-D-U  Left-most rotor is in its notch position: Q
     *     Q-D-V
     *     Q-E-W  Middle rotor is now in its notch position: E
     *     R-F-X  (Rule1: W→X) ⇒ (Rule3: E→F steps out of notch) ⇒ (Rule2: Q→R steps out of notch) ⇒ no further effect
     *            because there is not another rotor to the left
     *
     * In this function the stepping rules are applied as follows: it loops over the step rotors in the unit, starting
     * with the entry rotor (last item in the rotor set), and then, to determine if the current rotor in the loop
     * should step, one of three conditions must be true:
     *     a) the rotor _is_ the entry rotor, or
     *     b) the previous rotor stepped out of its notched position, or
     *     c) the rotor is in its notched position, and it's adjacent to the entry rotor.
     */
    fun stepRotors() {
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

        Logger.add(Log.RotorUnitStep.create(rotorsBeforeStep, rotorUnit = this))
    }

    /**
     * Simulate enciphering of rotor unit.
     *
     * There is a sequence of letter substitutions that happens within the rotor unit:
     *     - It starts with the entry rotor (right-most), which does a substitution.
     *     - Then, the left-next rotor takes the substituted letter and substitutes it with yet another letter.
     *     - This is repeated for every rotor, until the left-most rotor passes a letter to the reflector.
     *     - The reflector does its own substitution, and then sends (reflects) the letter back to the left-most rotor.
     *     - Substitutions happen again for each rotor, in reverse, back to the entry rotor, which does the
     *       final substitution for the unit.
     *
     * I.e. A rotor unit with 3 rotors and a reflector will perform 7 letter substitutions.
     *
     * The reflector is also what enables an Enigma Machine to decipher the ciphertext produced by another machine
     * with the same configuration and settings.
     */
    fun encipher(character: Char) : Char = rotors.runningFold(
        initial = reflector.encipher(
            rotors.reversed().runningFold(initial = character) { char, rotor -> rotor.encipher(char) }.last()
        )
    ) { char, rotor -> rotor.encipher(char, reverse = true) }.last()

    fun setPosition(rotorIndex: Int, position: Letter) {
        rotors.elementAt(rotorIndex).position = position
    }

    fun resetPositions() {
        rotors.forEach { it.resetPosition() }
    }
}
