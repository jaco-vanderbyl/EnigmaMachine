package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals

class RotorTypeTest {
    private fun expectedCipherSets(rotorType: RotorType) : String = when (rotorType) {
        RotorType.ROTOR_I -> "EKMFLGDQVZNTOWYHXUSPAIBRCJ"
        RotorType.ROTOR_II -> "AJDKSIRUXBLHWTMCQGZNPYFVOE"
        RotorType.ROTOR_III -> "BDFHJLCPRTXVZNYEIWGAKMUSQO"
        RotorType.ROTOR_IV -> "ESOVPZJAYQUIRHXLNFTGKDCMWB"
        RotorType.ROTOR_V -> "VZBRGITYUPSDNHLXAWMJQOFECK"
        RotorType.ROTOR_VI -> "JPGVOUMFYQBENHZRDKASXLICTW"
        RotorType.ROTOR_VII -> "NZJHGRCXMYSWBOUFAIVLPEKQDT"
        RotorType.ROTOR_VIII -> "FKQHTLXOCBJSPDZRAMEWNIUYGV"
        RotorType.ROTOR_BETA -> "LEYJVCNIXWPBQMDRTAKZGFUHOS"
        RotorType.ROTOR_GAMMA -> "FSOKANUERHMBTIYCWLQPZXVGJD"
        else -> throw IllegalArgumentException()
    }

    private fun expectedNotchPositions(rotorType: RotorType) : Set<Char> = when (rotorType) {
        RotorType.ROTOR_I -> setOf('Q')
        RotorType.ROTOR_II -> setOf('E')
        RotorType.ROTOR_III -> setOf('V')
        RotorType.ROTOR_IV -> setOf('J')
        RotorType.ROTOR_V -> setOf('Z')
        RotorType.ROTOR_VI -> setOf('Z', 'M')
        RotorType.ROTOR_VII -> setOf('Z', 'M')
        RotorType.ROTOR_VIII -> setOf('Z', 'M')
        else -> throw IllegalArgumentException()
    }

    private fun expectedCompatibility(rotorType: RotorType) : Set<EnigmaType> = when (rotorType) {
        RotorType.ROTOR_I -> setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3, EnigmaType.ENIGMA_M4)
        RotorType.ROTOR_II -> setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3, EnigmaType.ENIGMA_M4)
        RotorType.ROTOR_III -> setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3, EnigmaType.ENIGMA_M4)
        RotorType.ROTOR_IV -> setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3, EnigmaType.ENIGMA_M4)
        RotorType.ROTOR_V -> setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3, EnigmaType.ENIGMA_M4)
        RotorType.ROTOR_VI -> setOf(EnigmaType.ENIGMA_M3, EnigmaType.ENIGMA_M4)
        RotorType.ROTOR_VII -> setOf(EnigmaType.ENIGMA_M3, EnigmaType.ENIGMA_M4)
        RotorType.ROTOR_VIII -> setOf(EnigmaType.ENIGMA_M3, EnigmaType.ENIGMA_M4)
        RotorType.ROTOR_BETA -> setOf(EnigmaType.ENIGMA_M4)
        RotorType.ROTOR_GAMMA -> setOf(EnigmaType.ENIGMA_M4)
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
            if (rotor is StepRotor) {
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
