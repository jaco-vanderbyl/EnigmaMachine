package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RotorTest {
    private val cipherSet = "EKMFLGDQVZNTOWYHXUSPAIBRCJ"

    private fun createFixedRotor() : Rotor = Rotor.FixedRotor(
        type = RotorType.ROTOR_I,
        cipherSetMap = CipherSetMap(cipherSet),
        compatibility = setOf(EnigmaType.ENIGMA_I),
        position = Letter.A,
        ringSetting = Rotor.Ring.SETTING_1
    )

    @Test
    fun `ensure rotor position can be reset to default`() {
        val rotor = createFixedRotor()
        rotor.position = Letter.G
        rotor.resetPosition()

        assertEquals(
            expected = Letter.A.character,
            actual = rotor.position.character,
            message = "Failed to ensure rotor position can be reset to default."
        )
    }

    @Test
    fun `ensure rotor compatibility works correctly`() {
        val rotor = createFixedRotor()

        assertTrue(
            message = "Failed to ensure rotor compatibility works correctly.",
            actual = rotor.isCompatible(EnigmaType.ENIGMA_I)
        )
        assertFalse(
            message = "Failed to ensure reflector compatibility works correctly.",
            actual = rotor.isCompatible(EnigmaType.ENIGMA_M3)
        )
    }

    @TestFactory
    fun `ensure rotor enciphers with default position and ring setting`() = mapOf(
        'A' to 'E',
        'B' to 'K',
        'K' to 'N',
        'O' to 'Y',
        'Y' to 'C',
        'Z' to 'J',
    ).map {
        DynamicTest.dynamicTest("Rotor should encipher '${it.key}' to '${it.value}}'.") {
            val rotor = createFixedRotor()

            assertEquals(
                expected = it.value,
                actual = rotor.encipher(it.key),
                message = "Failed to ensure rotor enciphers with default position and ring setting."
            )
            assertEquals(
                expected = it.key,
                actual = rotor.encipher(it.value, reverse = true),
                message = "Failed to ensure rotor enciphers in reverse with default position and ring setting."
            )
        }
    }

    @TestFactory
    fun `ensure rotor enciphers with set position and default ring setting`() = mapOf(
        'A' to 'J',
        'B' to 'L',
        'K' to 'S',
        'O' to 'G',
        'Y' to 'I',
        'Z' to 'D',
    ).map {
        DynamicTest.dynamicTest("Rotor should encipher '${it.key}' to '${it.value}}'.") {
            val rotor = createFixedRotor()
            rotor.position = Letter.B

            assertEquals(
                expected = it.value,
                actual = rotor.encipher(it.key),
                message = "Failed to ensure rotor enciphers with set position and default ring setting."
            )
            assertEquals(
                expected = it.key,
                actual = rotor.encipher(it.value, reverse = true),
                message = "Failed to ensure rotor enciphers in reverse with set position and default ring setting."
            )
        }
    }

    @TestFactory
    fun `ensure rotor enciphers with set position and set ring setting`() = mapOf(
        'A' to 'K',
        'B' to 'T',
        'K' to 'O',
        'O' to 'Y',
        'Y' to 'A',
        'Z' to 'I',
    ).map {
        DynamicTest.dynamicTest("Rotor should encipher '${it.key}' to '${it.value}}'.") {
            val rotor = createFixedRotor()
            rotor.position = Letter.G
            rotor.ringSetting = Rotor.Ring.SETTING_19

            assertEquals(
                expected = it.value,
                actual = rotor.encipher(it.key),
                message = "Failed to ensure rotor enciphers with set position and set ring setting."
            )
            assertEquals(
                expected = it.key,
                actual = rotor.encipher(it.value, reverse = true),
                message = "Failed to ensure rotor enciphers in reverse with set position and set ring setting."
            )
        }
    }

    private val notchPositions = setOf(Letter.C, Letter.J, Letter.W)

    private fun createStepRotor() = Rotor.StepRotor(
        type = RotorType.ROTOR_I,
        cipherSetMap = CipherSetMap(cipherSet),
        notchPositions = notchPositions,
        compatibility = setOf(EnigmaType.ENIGMA_I),
        ringSetting = Rotor.Ring.SETTING_1,
        position = Letter.A
    )

    @Test
    fun `ensure step moves rotor forward one position`() {
        val rotor = createStepRotor()

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
        val rotor = createStepRotor()

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
