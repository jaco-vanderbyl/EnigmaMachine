package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertContains

import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class ReflectorTest {
    private val cipherSetMap = CipherSetMap("ZYXWVUTSRQPONMLKJIHGFEDCBA")
    private val reflector = Reflector(
        cipherSetMap = cipherSetMap,
        type = ReflectorType.B,
        compatibility = setOf(EnigmaType.ENIGMA_I)
    )

    private val invalidCharacters = listOf(' ', 'a', '@')

    @TestFactory
    fun `ensure reflector throws on invalid characters`() = invalidCharacters.map { character ->
        DynamicTest.dynamicTest("Invalid character '${character}' should throw.") {
            val ex = assertFailsWith<IllegalArgumentException>(
                message = "Failed to ensure reflector throws on invalid characters.",
                block = { reflector.encipher(character) }
            )
            ex.message?.let { msg -> assertContains(charSequence = msg, other = "Invalid character") }
        }
    }

    @Test
    fun `ensure reflector enciphers correctly given a cipher set map`() {
        Keys.CHARACTER_SET.forEachIndexed { index, character ->
            assertEquals(
                message = "Failed to ensure reflector enciphers correctly given a cipher set map.",
                expected = cipherSetMap.cipherSet[index],
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
