package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ReflectorTypeTest {
    private fun expectedCipherSets(reflectorType: ReflectorType) = when (reflectorType) {
        ReflectorType.B -> "YRUHQSLDPXNGOKMIEBFZCWVJAT"
        ReflectorType.C -> "FVPJIAOYEDRZXWGCTKUQSBNMHL"
        else -> throw IllegalArgumentException()
    }

    private fun expectedCompatibility(reflectorType: ReflectorType) = when (reflectorType) {
        ReflectorType.B -> setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3)
        ReflectorType.C -> setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3)
        else -> throw IllegalArgumentException()
    }

    @TestFactory
    fun `ensure factory creates reflector correctly`() = ReflectorType.entries.map { reflectorType ->
        DynamicTest.dynamicTest("Test factory creation of reflector type: '${reflectorType}'.") {
            val reflector = reflectorType.create()
            assertEquals(
                expected = reflectorType,
                actual = reflector.type,
                message = "Failed to ensure factory creates reflector with correct type."
            )

            // Currently all reflector types are compatible with all Enigma Types. assertFalse case should be added
            // when that is no longer the case.
            expectedCompatibility(reflectorType).forEach { enigmaType ->
                assertTrue(
                    actual = reflector.isCompatible(enigmaType),
                    message = "Failed to ensure factory creates reflector with correct compatibility."
                )
            }

            Enigma.CHARACTER_SET.forEachIndexed { index, character ->
                assertEquals(
                    expected = expectedCipherSets(reflectorType)[index],
                    actual = reflector.encipher(character),
                    message = "Failed to ensure factory creates reflector with correct cipher set."
                )
            }
        }
    }
}
