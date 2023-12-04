package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.Test

import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ReflectorTypeTest {
    @Test
    fun `ensure ReflectorB is created correctly`() {
        val reflectorB = ReflectorType.B.create()

        Keys.CHARACTER_SET.forEachIndexed { index, character ->
            assertEquals(
                message = "Failed to ensure ReflectorB is created correctly.",
                expected = "YRUHQSLDPXNGOKMIEBFZCWVJAT"[index],
                actual = reflectorB.encipher(character)
            )
        }

        assertEquals(
            message = "Failed to ensure ReflectorB is created correctly.",
            expected = ReflectorType.B,
            actual = reflectorB.type
        )

        assertTrue(
            message = "Failed to ensure ReflectorB is created correctly.",
            actual = reflectorB.isCompatible(EnigmaType.ENIGMA_I) && reflectorB.isCompatible(EnigmaType.ENIGMA_M3)
        )
    }

    @Test
    fun `ensure ReflectorC is created correctly`() {
        val reflectorB = ReflectorType.C.create()

        Keys.CHARACTER_SET.forEachIndexed { index, character ->
            assertEquals(
                message = "Failed to ensure ReflectorC is created correctly.",
                expected = "FVPJIAOYEDRZXWGCTKUQSBNMHL"[index],
                actual = reflectorB.encipher(character)
            )
        }

        assertEquals(
            message = "Failed to ensure ReflectorC is created correctly.",
            expected = ReflectorType.C,
            actual = reflectorB.type
        )

        assertTrue(
            message = "Failed to ensure ReflectorC is created correctly.",
            actual = reflectorB.isCompatible(EnigmaType.ENIGMA_I) && reflectorB.isCompatible(EnigmaType.ENIGMA_M3)
        )
    }
}