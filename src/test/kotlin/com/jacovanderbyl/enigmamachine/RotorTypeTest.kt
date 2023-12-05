package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RotorTypeTest {
    private val rotorCipherSets = mapOf(
        RotorType.I to "EKMFLGDQVZNTOWYHXUSPAIBRCJ",
        RotorType.II to "AJDKSIRUXBLHWTMCQGZNPYFVOE",
        RotorType.III to "BDFHJLCPRTXVZNYEIWGAKMUSQO",
        RotorType.IV to "ESOVPZJAYQUIRHXLNFTGKDCMWB",
        RotorType.V to "VZBRGITYUPSDNHLXAWMJQOFECK",
        RotorType.VI to "JPGVOUMFYQBENHZRDKASXLICTW",
        RotorType.VII to "NZJHGRCXMYSWBOUFAIVLPEKQDT",
        RotorType.VIII to "FKQHTLXOCBJSPDZRAMEWNIUYGV",
    )

    private val rotorNotchPositions = mapOf(
        RotorType.I to setOf('Q'),
        RotorType.II to setOf('E'),
        RotorType.III to setOf('V'),
        RotorType.IV to setOf('J'),
        RotorType.V to setOf('Z'),
        RotorType.VI to setOf('Z', 'M'),
        RotorType.VII to setOf('Z', 'M'),
        RotorType.VIII to setOf('Z', 'M'),
    )

    private val rotorCompatibility = mapOf(
        RotorType.I to setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3),
        RotorType.II to setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3),
        RotorType.III to setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3),
        RotorType.IV to setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3),
        RotorType.V to setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3),
        RotorType.VI to setOf(EnigmaType.ENIGMA_M3),
        RotorType.VII to setOf(EnigmaType.ENIGMA_M3),
        RotorType.VIII to setOf(EnigmaType.ENIGMA_M3),
    )

    @Test
    fun `ensure factory-created rotors are created with correct configuration`() {
        RotorType.entries.forEach { rotorType ->
            require(rotorCipherSets.containsKey(rotorType))
            require(rotorNotchPositions.containsKey(rotorType))
            require(rotorCompatibility.containsKey(rotorType))

            val rotor = rotorType.create()

            Enigma.CHARACTER_SET.forEachIndexed { index, character ->
                assertEquals(
                    expected = rotorCipherSets[rotorType]?.getOrNull(index),
                    actual = rotor.encipher(character),
                    message = "Failed to ensure factory-created rotor '${rotorType}' enciphered correctly."
                )
            }

            assertEquals(
                expected = rotorType,
                actual = rotor.type,
                message = "Failed to ensure factory-created rotor '${rotorType}' has correct type."
            )

            rotorCompatibility[rotorType]?.forEach { enigmaType ->
                assertTrue(
                    actual = rotor.isCompatible(enigmaType),
                    message = "Failed to ensure factory-created rotor '${rotorType}' has correct compatibility."
                )
            }

            rotorNotchPositions[rotorType]?.forEach { notchPosition ->
                rotor.position = Position(notchPosition)
                assertTrue(
                    actual = rotor.isInNotchedPosition(),
                    message = "Failed to ensure factory-created rotor '${rotorType}' has correct notch characters."
                )
            }
        }
    }

    @Test
    fun `ensure factory-created rotors are created correctly with given position and ring setting`() {
        RotorType.entries.forEach { rotorType ->
            val rotor = rotorType.create(Position('L'), RingSetting(24))

            assertEquals(
                expected = 'L',
                actual = rotor.position.character,
                message = "Failed to ensure rotor '${rotorType}' is created with given position."
            )

            assertEquals(
                expected = 24,
                actual = rotor.ringSetting.value,
                message = "Failed to ensure rotor '${rotorType}' is created with given ring setting."
            )
        }
    }
}
