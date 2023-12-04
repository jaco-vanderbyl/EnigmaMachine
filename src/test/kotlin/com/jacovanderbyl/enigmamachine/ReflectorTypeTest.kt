package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.Test

import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ReflectorTypeTest {
    @Test
    fun `ensure ReflectorB is created correctly`() {
        val reflectorB = ReflectorType.B.create()

        Enigma.CHARACTER_SET.forEachIndexed { index, character ->
            assertEquals(
                expected = "YRUHQSLDPXNGOKMIEBFZCWVJAT"[index],
                actual = reflectorB.encipher(character),
                message = "Failed to ensure ReflectorB is created correctly."
            )
        }

        assertEquals(
            expected = ReflectorType.B,
            actual = reflectorB.type,
            message = "Failed to ensure ReflectorB is created correctly."
        )

        assertTrue(
            actual = reflectorB.isCompatible(EnigmaType.ENIGMA_I) && reflectorB.isCompatible(EnigmaType.ENIGMA_M3),
            message = "Failed to ensure ReflectorB is created correctly."
        )
    }

    @Test
    fun `ensure ReflectorC is created correctly`() {
        val reflectorB = ReflectorType.C.create()

        Enigma.CHARACTER_SET.forEachIndexed { index, character ->
            assertEquals(
                expected = "FVPJIAOYEDRZXWGCTKUQSBNMHL"[index],
                actual = reflectorB.encipher(character),
                message = "Failed to ensure ReflectorC is created correctly."
            )
        }

        assertEquals(
            expected = ReflectorType.C,
            actual = reflectorB.type,
            message = "Failed to ensure ReflectorC is created correctly."
        )

        assertTrue(
            actual = reflectorB.isCompatible(EnigmaType.ENIGMA_I) && reflectorB.isCompatible(EnigmaType.ENIGMA_M3),
            message = "Failed to ensure ReflectorC is created correctly."
        )
    }
}