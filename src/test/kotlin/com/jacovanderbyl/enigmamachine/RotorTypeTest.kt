package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals

class RotorTypeTest {
    private fun expectedCipherSets(rotorType: RotorType) = when (rotorType) {
        RotorType.I -> "EKMFLGDQVZNTOWYHXUSPAIBRCJ"
        RotorType.II -> "AJDKSIRUXBLHWTMCQGZNPYFVOE"
        RotorType.III -> "BDFHJLCPRTXVZNYEIWGAKMUSQO"
        RotorType.IV -> "ESOVPZJAYQUIRHXLNFTGKDCMWB"
        RotorType.V -> "VZBRGITYUPSDNHLXAWMJQOFECK"
        RotorType.VI -> "JPGVOUMFYQBENHZRDKASXLICTW"
        RotorType.VII -> "NZJHGRCXMYSWBOUFAIVLPEKQDT"
        RotorType.VIII -> "FKQHTLXOCBJSPDZRAMEWNIUYGV"
        else -> throw IllegalArgumentException()
    }

    private fun expectedNotchPositions(rotorType: RotorType) = when (rotorType) {
        RotorType.I -> setOf('Q')
        RotorType.II -> setOf('E')
        RotorType.III -> setOf('V')
        RotorType.IV -> setOf('J')
        RotorType.V -> setOf('Z')
        RotorType.VI -> setOf('Z', 'M')
        RotorType.VII -> setOf('Z', 'M')
        RotorType.VIII -> setOf('Z', 'M')
        else -> throw IllegalArgumentException()
    }

    private fun expectedCompatibility(rotorType: RotorType) = when (rotorType) {
        RotorType.I -> setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3)
        RotorType.II -> setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3)
        RotorType.III -> setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3)
        RotorType.IV -> setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3)
        RotorType.V -> setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3)
        RotorType.VI -> setOf(EnigmaType.ENIGMA_M3)
        RotorType.VII -> setOf(EnigmaType.ENIGMA_M3)
        RotorType.VIII -> setOf(EnigmaType.ENIGMA_M3)
        else -> throw IllegalArgumentException()
    }

    @TestFactory
    fun `ensure factory creates with correct type`() = RotorType.entries.map { rotorType ->
        DynamicTest.dynamicTest("Test factory creation of rotor type: '${rotorType}'.") {
            val rotor = rotorType.create()
            assertEquals(
                expected = rotorType,
                actual = rotor.type,
                message = "Failed to ensure factory creates with correct type."
            )
        }
    }

    @TestFactory
    fun `ensure factory creates with correct notch positions`() = RotorType.entries.map { rotorType ->
        DynamicTest.dynamicTest("Test factory creation of rotor type: '${rotorType}'.") {
            val rotor = rotorType.create()
            Enigma.CHARACTER_SET.forEach { position ->
                rotor.position = Position(position)
                assertEquals(
                    expected = position in expectedNotchPositions(rotorType),
                    actual = rotor.isInNotchedPosition(),
                    message = "Failed to ensure factory creates with correct notch positions."
                )
            }
        }
    }

    @TestFactory
    fun `ensure factory creates with correct compatibility`() = RotorType.entries.map { rotorType ->
        DynamicTest.dynamicTest("Test factory creation of rotor type: '${rotorType}'.") {
            val rotor = rotorType.create()
            EnigmaType.entries.forEach { enigmaType ->
                assertEquals(
                    expected = enigmaType in expectedCompatibility(rotorType),
                    actual = rotor.isCompatible(enigmaType),
                    message = "Failed to ensure factory creates with correct compatibility."
                )
            }
        }
    }

    @TestFactory
    fun `ensure factory creates rotor that enciphers correctly`() = RotorType.entries.map { rotorType ->
        DynamicTest.dynamicTest("Test factory creation of rotor type: '${rotorType}'.") {
            val rotor = rotorType.create()
            Enigma.CHARACTER_SET.forEachIndexed { index, char ->
                assertEquals(
                    expected = expectedCipherSets(rotorType)[index],
                    actual = rotor.encipher(char),
                    message = "Failed to ensure factory creates rotor that enciphers correctly."
                )
            }
        }
    }

    @Test
    fun `ensure factory creates with given position`() {
        RotorType.entries.forEach { rotorType ->
            val rotor = rotorType.create(position = Position('L'))
            assertEquals(
                expected = 'L',
                actual = rotor.position.character,
                message = "Failed to ensure factory creates with given position."
            )
        }
    }

    @Test
    fun `ensure factory creates with given ring setting`() {
        RotorType.entries.forEach { rotorType ->
            val rotor = rotorType.create(ringSetting = RingSetting(24))
            assertEquals(
                expected = 24,
                actual = rotor.ringSetting.value,
                message = "Failed to ensure factory creates with given ring setting."
            )
        }
    }

    @Test
    fun `ensure list of available rotor types is correct`() {
        assertEquals(
            expected = RotorType.entries.map { it.name },
            actual = RotorType.list(),
            message = "Failed to ensure list of available rotor types is correct."
        )
    }
}
