package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class ReflectorTest {
    private fun createReflector(
        cipherSet: String = "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
        compatibility: Set<EnigmaType> = setOf()
    ) : Reflector = Reflector(
        type = ReflectorType.B,
        cipherSetMap = CipherSetMap(cipherSet),
        compatibility = compatibility
    )

    @TestFactory
    fun `ensure encipher works with different cipher sets`() = listOf(
        "ZYXWVUTSRQPONMLKJIHGFEDCBA",
        "YRUHQSLDPXNGOKMIEBFZCWVJAT",
        "FVPJIAOYEDRZXWGCTKUQSBNMHL",
    ).map { cipherSet ->
        DynamicTest.dynamicTest("Test encipher with cipher set: '${cipherSet}'.") {
            val reflector = createReflector(cipherSet = cipherSet)
            Enigma.CHARACTER_SET.forEachIndexed { index, char ->
                assertEquals(
                    expected = cipherSet[index],
                    actual = reflector.encipher(char),
                    message = "Failed to ensure encipher works."
                )
            }
        }
    }

    @Test
    fun `ensure compatibility works`() {
        val reflector = createReflector(compatibility = setOf(EnigmaType.ENIGMA_I))
        assertTrue(
            actual = reflector.isCompatible(EnigmaType.ENIGMA_I),
            message = "Failed to ensure compatibility works - should be compatible."
        )
        assertFalse(
            actual = reflector.isCompatible(EnigmaType.ENIGMA_M3),
            message = "Failed to ensure compatibility works - should not be compatible."
        )
    }
}
