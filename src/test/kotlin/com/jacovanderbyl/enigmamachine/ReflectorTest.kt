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
                expected = cipherSet[index],
                actual = reflector.encipher(character),
                message = "Failed to ensure reflector enciphers correctly given a cipher set map."
            )
        }
    }

    @Test
    fun `ensure reflector compatibility works correctly`() {
        assertTrue(
            actual = reflector.isCompatible(EnigmaType.ENIGMA_I),
            message = "Failed to ensure reflector compatibility works correctly."
        )
        assertFalse(
            actual = reflector.isCompatible(EnigmaType.ENIGMA_M3),
            message = "Failed to ensure reflector compatibility works correctly."
        )
    }
}
