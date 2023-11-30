package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

import kotlin.IllegalArgumentException
import kotlin.test.DefaultAsserter.assertEquals
import kotlin.test.assertFailsWith

class CipherSetMapTest {
    private val bogusCipherSets = listOf(
        "",
        "AAAAAAAAAAAAAAAAAAAAAAAAAA",
        "ABCDEFGHIJKLMNOPQRSTUVWXYZABC",
        "AACDEFGHIJKLMNOPQRSTUVWXYZ"
    )

    @Test
    fun `ensure character set prop is the same as the enigma key character set`() {
        assertEquals(
            message = "Failed to ensure character set prop is the same as the enigma key character set.",
            expected = Keys.CHARACTER_SET,
            actual = CipherSetMap("ZYXWVUTSRQPONMLKJIHGFEDCBA").characterSet
        )
    }

    @Test
    fun `ensure cipher set prop is the same as the given cipher set`() {
        assertEquals(
            message = "Failed to ensure cipher set prop is the same as the given cipher set.",
            expected = "ZYXWVUTSRQPONMLKJIHGFEDCBA",
            actual = CipherSetMap("ZYXWVUTSRQPONMLKJIHGFEDCBA").cipherSet
        )
    }

    @TestFactory
    fun `ensure each character in given cipher set can be mapped to character set`() {
        bogusCipherSets.map {
            DynamicTest.dynamicTest("Bogus cipher set input '${it}' should throw.") {
                assertFailsWith<IllegalArgumentException>(
                    message = "Failed to ensure each character in given cipher set can be mapped to character set.",
                    block = { CipherSetMap(it) }
                )
            }
        }
    }
}
