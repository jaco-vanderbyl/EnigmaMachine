package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class NotchTest {
    @Test
    fun `ensure notch characters are set correctly`() {
        assertEquals(
            expected = setOf('A', 'B'),
            actual = Notch(setOf('A', 'B')).characters,
            message = "Failed to ensure enigma key character set is uppercase alphabet."
        )
    }

    private val invalidCharacters = listOf(' ', 'a', '@')

    @TestFactory
    fun `ensure invalid notch character throws`() = invalidCharacters.map { character ->
        DynamicTest.dynamicTest("Invalid notch character '${character}' should throw.") {
            val ex = assertFailsWith<IllegalArgumentException>(
                block = { Notch(setOf(character)) },
                message = "Failed to ensure invalid notch character throws."
            )
            ex.message?.let { msg -> assertContains(charSequence = msg, other = "Invalid notch character") }
        }
    }

    private val badCharacterCounts = listOf(
        setOf(),
        setOf(
            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','@'
        )
    )

    @TestFactory
    fun `ensure bad notch character count throws`() = badCharacterCounts.map { characterSet ->
        DynamicTest.dynamicTest("Bad notch character count '${characterSet}' should throw.") {
            val ex = assertFailsWith<IllegalArgumentException>(
                block = { Notch(characterSet) },
                message = "Failed to ensure bad notch character count throws."
            )
            ex.message?.let { msg ->
                assertContains(charSequence = msg, other = "cannot have more notches than the character set size")
            }
        }
    }
}
