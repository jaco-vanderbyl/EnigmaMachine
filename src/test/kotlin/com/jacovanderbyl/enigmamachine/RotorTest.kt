package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RotorTest {
    private val cipherSet = "EKMFLGDQVZNTOWYHXUSPAIBRCJ"
    private val notchCharacters = setOf('C', 'J', 'W')

    private fun createRotorFake() : Rotor = Rotor(
        type = RotorType.I,
        cipherSetMap = CipherSetMap(cipherSet),
        notch = Notch(notchCharacters),
        compatibility = setOf(EnigmaType.ENIGMA_I),
        position = Position(),
        ringSetting = RingSetting()
    )

    @Test
    fun `ensure step moves rotor forward one position`() {
        val rotor = createRotorFake()

        Enigma.CHARACTER_SET.repeat(2).forEach { character ->
            assertEquals(
                expected = character,
                actual = rotor.position.character,
                message = "Failed to ensure step moves rotor forward one position."
            )
            rotor.step()
        }
    }

    @Test
    fun `ensure rotor position can be reset to default`() {
        val rotor = createRotorFake()
        rotor.position = Position('G')
        rotor.resetPosition()

        assertEquals(
            expected = 'A',
            actual = rotor.position.character,
            message = "Failed to ensure rotor position can be reset to default."
        )
    }

    @Test
    fun `ensure getting whether the rotor is in notched position works correctly`() {
        val rotor = createRotorFake()

        do {
            when (rotor.position.character) {
                in notchCharacters -> assertTrue(
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

    @Test
    fun `ensure rotor compatibility works correctly`() {
        val rotor = createRotorFake()

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
            val rotor = createRotorFake()

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
            val rotor = createRotorFake()
            rotor.position = Position('B')

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
            val rotor = createRotorFake()
            rotor.position = Position('G')
            rotor.ringSetting = RingSetting(19)

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
}
