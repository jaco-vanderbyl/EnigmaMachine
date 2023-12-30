package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ReflectorTypeTest {
    private fun expectedCipherSets(reflectorType: ReflectorType) : String = when (reflectorType) {
        ReflectorType.REFLECTOR_B -> "YRUHQSLDPXNGOKMIEBFZCWVJAT"
        ReflectorType.REFLECTOR_C -> "FVPJIAOYEDRZXWGCTKUQSBNMHL"
        ReflectorType.REFLECTOR_B_THIN -> "ENKQAUYWJICOPBLMDXZVFTHRGS"
        ReflectorType.REFLECTOR_C_THIN -> "RDOBJNTKVEHMLFCWZAXGYIPSUQ"
        else -> throw IllegalArgumentException()
    }

    private fun expectedCompatibility(reflectorType: ReflectorType) : Set<EnigmaType> = when (reflectorType) {
        ReflectorType.REFLECTOR_B -> setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3)
        ReflectorType.REFLECTOR_C -> setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3)
        ReflectorType.REFLECTOR_B_THIN -> setOf(EnigmaType.ENIGMA_M4)
        ReflectorType.REFLECTOR_C_THIN -> setOf(EnigmaType.ENIGMA_M4)
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

            EnigmaType.entries.forEach { enigmaType ->
                if (enigmaType in expectedCompatibility(reflectorType)) {
                    assertTrue(
                        actual = reflector.isCompatible(enigmaType),
                        message = "Failed to ensure factory creates with correct compatibility. '${reflectorType}' " +
                                "should be compatible with '${enigmaType}'."
                    )
                } else {
                    assertFalse(
                        actual = reflector.isCompatible(enigmaType),
                        message = "Failed to ensure factory creates with correct compatibility. '${reflectorType}' " +
                                "should not be compatible with '${enigmaType}'."
                    )
                }
            }
        }
    }

    @TestFactory
    fun `ensure factory creates reflector that enciphers correctly`() = ReflectorType.entries.map { reflectorType ->
        DynamicTest.dynamicTest("Test factory creation of reflector type: '${reflectorType}'.") {
            val reflector = reflectorType.create()
            Letter.list().forEachIndexed { index, char ->
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
