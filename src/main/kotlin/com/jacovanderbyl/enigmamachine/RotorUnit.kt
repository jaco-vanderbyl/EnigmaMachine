package com.jacovanderbyl.enigmamachine

/**
 * Represents a rotor unit, consisting of a reflector and a set of rotors.
 *
 * This class simulates two behaviours:
 *     - Stepping:
 *           When and how the rotors turn, which happens after each key-press on the Enigma Machine.
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
class RotorUnit(val reflector: Reflector, val rotors: Set<Rotor>) : CanEncipher {
    init {
        require(rotors.none { rotor -> rotors.count { it.type == rotor.type } > 1 }) {
            "Duplicate rotor types are not allowed. Given: ${rotors.map { it.type }}."
        }
    }

    /**
     * Simulate rotor stepping.
     *
     * To understand how the stepping mechanism works, it's required to know the following:
     *     - There are different types of rotors.
     *     - Every rotor is a disc with 26 positions, each indicated by a letter of the alphabet.
     *     - Every rotor has at least one notch cut out at a specific letter position.
     *       E.g. RotorI has notch at 'Q', RotorII at 'E', and RotorIII at 'V'. RotorVI has two notches, at 'M' and 'Z'.
     *     - The different types of rotors fit together, and they can be arranged in any order. The Enigma I, the most
     *       common Enigma Machine model, accepted a unit of 3 rotors, but other models could accept up to 8.
     *       E.g. A rotor unit could be: RotorI-RotorII-RotorIII or RotorV-RotorI-RotorIV, etc.
     *     - Once in the machine, each rotor can be set independently to a specific starting letter-position.
     *       E.g. A-A-A, or R-T-B, etc.
     *     - The rotor notches, together with physical key-presses, determine when and how the rotors in the unit turn.
     *
     * Stepping rules:
     *     1) The right-most rotor always steps when a key is pressed.
     *     2) Any rotor that steps out of its notch position will cause the rotor to its left to step also.
     *        I.e. when a rotor is in its notched position and then turns, it will turn the rotor to the left of it.
     *     3) When the right-most rotor steps, it will also step the rotor immediately next to it if that adjacent
     *        rotor happens to be in its notched position. Subsequently, this will cause the next-left rotor to step
     *        as well (by way of rule 2). This is the so-called 'double stepping' mechanism.
     *
     * Example with following setup: RotorI-RotorII-RotorIII, which have notches Q-E-V, with starting positions A-A-T.
     *     A-A-T
     *     A-A-U  (Rule1: T→U)
     *     A-A-V  (Rule1: U→V) — Right-most rotor is now in its notch position: V
     *     A-B-W  (Rule1: V→W steps out of notch) ⇒ (Rule2: A→B)
     *     A-B-X  (Rule1: W→X)
     *     .....  Stepping continues
     *     A-D-U  (Rule1: T→U)
     *     A-D-V  (Rule1: U→V) — Right-most rotor is now in its notch position: V
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
     * In this function the stepping rules are applied as follows: it loops over the rotors in the unit, starting with
     * the right-most rotor (last item in the rotor set), and then, to determine if the current rotor in the loop
     * should step, one of three conditions must be true:
     *     a) the rotor _is_ the right-most rotor, or
     *     b) the previous rotor stepped out of its notched position, or
     *     c) the rotor is in its notched position, and it's adjacent to the right-most rotor.
     */
    fun stepRotors() {
        var previousSteppedOutOfNotch = false

        rotors.reversed().forEachIndexed { index, rotor ->
            val isRightMost = index == 0
            val isNextToRightMost = index == 1
            val isNotchedAndNextToRightMost = rotor.isInNotchedPosition() && isNextToRightMost

            if (isRightMost || previousSteppedOutOfNotch || isNotchedAndNextToRightMost) {
                previousSteppedOutOfNotch = rotor.isInNotchedPosition()
                rotor.step()
            }
        }
    }

    /**
     * Simulate enciphering of rotor unit.
     *
     * There is a sequence of letter substitutions that happens within the rotor unit. It starts with the right-most
     * rotor, which does a substitution, then the next rotor takes the substituted letter and substitutes it with yet
     * another letter. This continues until the left-most rotor passes a letter to the reflector. The reflector does
     * its own substitution, and then sends (reflects) a letter back to the left-most rotor. Substitutions happen again
     * for each rotor back to the right-most rotor, which does the last substitution for the unit.
     *
     * I.e. A rotor unit with 3 rotors and a reflector will perform 7 letter substitutions.
     *
     * The reflector is also what enables an Enigma Machine to decipher the ciphertext produced by another machine
     * with the same configuration and settings.
     */
    override fun encipher(character: Char) : Char {
        // Simulate character substitutions for each rotor, moving from last (right-most) to first (left-most) rotor.
        var substituteCharacter = character
        for (rotor in rotors.reversed()) {
            substituteCharacter = rotor.encipher(substituteCharacter)
        }

        // Simulate character substitution of reflector
        substituteCharacter = reflector.encipher(substituteCharacter)

        // Simulate character substitution for each rotor, moving from first (left-most) to last (right-most) rotor.
        for (rotor in rotors) {
            substituteCharacter = rotor.encipher(substituteCharacter, reverse = true)
        }

        return substituteCharacter
    }

    fun setRotorPosition(rotorIndex: Int, position: Position) {
        rotors.elementAt(rotorIndex).position = position
    }

    fun resetRotorPositions() {
        for (rotor in rotors) rotor.resetPosition()
    }
}
