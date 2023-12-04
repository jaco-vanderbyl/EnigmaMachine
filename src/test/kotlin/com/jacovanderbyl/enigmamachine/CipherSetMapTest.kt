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
    fun `ensure character set prop is the same as the enigma key character set`() {
        assertEquals(
            message = "Failed to ensure character set prop is the same as the enigma key character set.",
            expected = Keys.CHARACTER_SET,
            actual = cipherSetMap.characterSet
        )
    }

    private val bogusCipherSets = listOf(
        "",
        "AAAAAAAAAAAAAAAAAAAAAAAAAA",
        "ABCDEFGHIJKLMNOPQRSTUVWXYZABC",
        "AACDEFGHIJKLMNOPQRSTUVWXYZ"
    )

    @TestFactory
    fun `ensure given cipher set maps to character set`() = bogusCipherSets.map { cipherSet ->
        DynamicTest.dynamicTest("Bogus cipher set input '${cipherSet}' should throw.") {
            assertFailsWith<IllegalArgumentException>(
                message = "Failed to ensure given cipher set maps to character set.",
                block = { CipherSetMap(cipherSet) }
            )
        }
    }

    @Test
    fun `ensure cipher set map enciphers correctly`() {
        cipherSetMap.characterSet.forEachIndexed { index, character ->
            assertEquals(
                message = "Failed to ensure cipher set map enciphers correctly.",
                expected = cipherSet[index],
                actual = cipherSetMap.encipher(character)
            )
        }
    }

    @Test
    fun `ensure cipher set map enciphers in reverse correctly`() {
        cipherSet.forEachIndexed { index, character ->
            assertEquals(
                message = "Failed to ensure cipher set map enciphers in reverse correctly.",
                expected = cipherSetMap.characterSet[index],
                actual = cipherSetMap.encipher(character, reverse = true)
            )
        }
    }

    private val invalidCharacters = listOf(' ', 'a', '@')

    @TestFactory
    fun `ensure cipher set map throws on invalid characters`() = invalidCharacters.map { character ->
        DynamicTest.dynamicTest("Invalid character '${character}' should throw.") {
            val ex = assertFailsWith<IllegalArgumentException>(
                message = "Failed to ensure cipher set map throws on invalid characters.",
                block = { cipherSetMap.encipher(character) }
            )
            ex.message?.let { msg -> assertContains(charSequence = msg, other = "Invalid character") }
        }
    }
}
