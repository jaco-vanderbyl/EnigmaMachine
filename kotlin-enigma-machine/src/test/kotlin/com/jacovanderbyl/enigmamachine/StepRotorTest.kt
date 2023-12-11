package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class StepRotorTest {
    private val cipherSet = "EKMFLGDQVZNTOWYHXUSPAIBRCJ"
    private val notchCharacters = setOf(Position('C'), Position('J'), Position('W'))

    private fun createRotor() : StepRotor = StepRotor(
        type = RotorType.ROTOR_I,
        cipherSetMap = CipherSetMap(cipherSet),
        notch = Notch(*notchCharacters.toTypedArray()),
        compatibility = setOf(EnigmaType.ENIGMA_I),
        ringSetting = RingSetting(),
        position = Position()
    )

    @Test
    fun `ensure step moves rotor forward one position`() {
        val rotor = createRotor()

        Enigma.CHARACTER_SET.repeat(2).forEach { char ->
            assertEquals(
                expected = char,
                actual = rotor.position.character,
                message = "Failed to ensure step moves rotor forward one position."
            )
            rotor.step()
        }
    }

    @Test
    fun `ensure getting whether the rotor is in notched position works correctly`() {
        val rotor = createRotor()

        do {
            when (rotor.position.character) {
                in notchCharacters.map { it.character } -> assertTrue(
                    actual = rotor.isInNotchedPosition(),
                    message = "Failed to confirm rotor is in notched position."
                )
                else -> assertFalse(
                    actual = rotor.isInNotchedPosition(),
                    message = "Failed to confirm rotor is not in notched position."
                )
            }

            rotor.step()
        } while (rotor.position.character != 'Z')
    }
}
