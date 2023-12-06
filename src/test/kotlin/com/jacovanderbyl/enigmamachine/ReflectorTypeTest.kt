package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ReflectorTypeTest {
    private fun expectedCipherSets(reflectorType: ReflectorType) : String = when (reflectorType) {
        ReflectorType.B -> "YRUHQSLDPXNGOKMIEBFZCWVJAT"
        ReflectorType.C -> "FVPJIAOYEDRZXWGCTKUQSBNMHL"
        ReflectorType.B_THIN -> "ENKQAUYWJICOPBLMDXZVFTHRGS"
        ReflectorType.C_THIN -> "RDOBJNTKVEHMLFCWZAXGYIPSUQ"
        else -> throw IllegalArgumentException()
    }

    private fun expectedCompatibility(reflectorType: ReflectorType) : Set<EnigmaType> = when (reflectorType) {
        ReflectorType.B -> setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3)
        ReflectorType.C -> setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3)
        ReflectorType.B_THIN -> setOf(EnigmaType.ENIGMA_M4)
        ReflectorType.C_THIN -> setOf(EnigmaType.ENIGMA_M4)
        else -> throw IllegalArgumentException()
    }

    @TestFactory
    fun `ensure factory creates with correct type`() = ReflectorType.entries.map { reflectorType ->
        DynamicTest.dynamicTest("Test factory creation of reflector type: '${reflectorType}'.") {
            val reflector = reflectorType.create()
            assertEquals(
                expected = reflectorType,
                actual = reflector.type,
                message = "Failed to ensure factory creates with correct type."
            )
        }
    }

    @TestFactory
    fun `ensure factory creates with correct compatibility`() = ReflectorType.entries.map { reflectorType ->
        DynamicTest.dynamicTest("Test factory creation of reflector type: '${reflectorType}'.") {
            val reflector = reflectorType.create()

            // Currently all reflector types are compatible with all Enigma Types. assertFalse case should be added
            // when that is no longer the case.
            expectedCompatibility(reflectorType).forEach { enigmaType ->
                assertTrue(
                    actual = reflector.isCompatible(enigmaType),
                    message = "Failed to ensure factory creates with correct compatibility."
                )
            }
        }
    }

    @TestFactory
    fun `ensure factory creates reflector that enciphers correctly`() = ReflectorType.entries.map { reflectorType ->
        DynamicTest.dynamicTest("Test factory creation of reflector type: '${reflectorType}'.") {
            val reflector = reflectorType.create()
            Enigma.CHARACTER_SET.forEachIndexed { index, char ->
                assertEquals(
                    expected = expectedCipherSets(reflectorType)[index],
                    actual = reflector.encipher(char),
                    message = "ensure factory creates reflector that enciphers correctly."
                )
            }
        }
    }

    @Test
    fun `ensure list of available reflector types is correct`() {
        assertEquals(
            expected = ReflectorType.entries.map { it.name },
            actual = ReflectorType.list(),
            message = "Failed to ensure list of available reflector types is correct."
        )
    }
}
