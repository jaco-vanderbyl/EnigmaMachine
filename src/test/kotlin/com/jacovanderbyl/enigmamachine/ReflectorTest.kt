package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.Test

import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class ReflectorTest {
    private val cipherSet = "ZYXWVUTSRQPONMLKJIHGFEDCBA"
    private val cipherSetMap = CipherSetMap(cipherSet)
    private val reflector = Reflector(
        cipherSetMap = cipherSetMap,
        type = ReflectorType.B,
        compatibility = setOf(EnigmaType.ENIGMA_I)
    )

    @Test
    fun `ensure reflector enciphers correctly given a cipher set map`() {
        cipherSetMap.characterSet.forEachIndexed { index, character ->
            assertEquals(
                message = "Failed to ensure reflector enciphers correctly given a cipher set map.",
                expected = cipherSet[index],
                actual = reflector.encipher(character)
            )
        }
    }

    @Test
    fun `ensure reflector compatibility works correctly`() {
        assertTrue(
            message = "Failed to ensure reflector compatibility works correctly.",
            actual = reflector.isCompatible(EnigmaType.ENIGMA_I)
        )
        assertFalse(
            message = "Failed to ensure reflector compatibility works correctly.",
            actual = reflector.isCompatible(EnigmaType.ENIGMA_M3)
        )
    }
}
