package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.IllegalArgumentException
import kotlin.test.DefaultAsserter.assertEquals
import kotlin.test.assertContains
import kotlin.test.assertFailsWith

class CipherSetMapTest {
    private val cipherSet = "EKMFLGDQVZNTOWYHXUSPAIBRCJ"
    private val cipherSetMap = CipherSetMap(cipherSet)

    @Test
    fun `ensure character set equals the enigma character set`() {
        assertEquals(
            expected = Enigma.CHARACTER_SET,
            actual = cipherSetMap.characterSet,
            message = "Failed to ensure character set equals the enigma character set."
        )
    }

    @TestFactory
    fun `ensure invalid cipher set throws`() = listOf(
        "",
        "AAAAAAAAAAAAAAAAAAAAAAAAAA",
        "ABCDEFGHIJKLMNOPQRSTUVWXYZABC",
        "AACDEFGHIJKLMNOPQRSTUVWXYZ",
    ).map { cipherSet ->
        DynamicTest.dynamicTest("Invalid cipher set '${cipherSet}' should throw.") {
            assertFailsWith<IllegalArgumentException>(
                block = { CipherSetMap(cipherSet) },
                message = "Failed to ensure invalid cipher set throws."
            )
        }
    }

    @Test
    fun `ensure encipher works`() {
        cipherSetMap.characterSet.forEachIndexed { index, char ->
            assertEquals(
                expected = cipherSet[index],
                actual = cipherSetMap.encipher(char),
                message = "Failed to ensure encipher works."
            )
        }
    }

    @Test
    fun `ensure encipher works in reverse`() {
        cipherSet.forEachIndexed { index, character ->
            assertEquals(
                expected = cipherSetMap.characterSet[index],
                actual = cipherSetMap.encipher(character, reverse = true),
                message = "Failed to ensure encipher works in reverse."
            )
        }
    }

    @TestFactory
    fun `ensure invalid character throws`() = listOf(
        ' ',
        'a',
        '@',
    ).map { char ->
        DynamicTest.dynamicTest("Invalid character '${char}' should throw.") {
            val ex = assertFailsWith<IllegalArgumentException>(
                block = { cipherSetMap.encipher(char) },
                message = "Failed to ensure invalid character throws."
            )
            ex.message?.let {msg ->
                assertContains(charSequence = msg, other = "invalid character", ignoreCase = true)
            }
        }
    }
}
