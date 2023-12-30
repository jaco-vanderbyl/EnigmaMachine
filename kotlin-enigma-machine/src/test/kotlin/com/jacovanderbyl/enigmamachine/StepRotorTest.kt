package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class StepRotorTest {
    private val cipherSet = "EKMFLGDQVZNTOWYHXUSPAIBRCJ"
    private val notchPositions = setOf(Letter.C, Letter.J, Letter.W)

    private fun createRotor() : StepRotor = StepRotor(
        type = RotorType.ROTOR_I,
        cipherSetMap = CipherSetMap(cipherSet),
        notch = Notch(notchPositions),
        compatibility = setOf(EnigmaType.ENIGMA_I),
        ringSetting = Ring.SETTING_1,
        position = Letter.A
    )

    @Test
    fun `ensure step moves rotor forward one position`() {
        val rotor = createRotor()

        Letter.characterSet().repeat(2).forEach { char ->
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
                in notchPositions.map { it.character } -> assertTrue(
                    actual = rotor.isInNotchedPosition(),
                    message = "Failed to confirm rotor is in notched position."
                )
                else -> assertFalse(
                    actual = rotor.isInNotchedPosition(),
                    message = "Failed to confirm rotor is not in notched position."
                )
            }

            rotor.step()
        } while (rotor.position.character != Letter.Z.character)
    }
}
