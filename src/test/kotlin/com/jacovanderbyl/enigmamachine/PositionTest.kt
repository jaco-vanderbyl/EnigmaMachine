package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class PositionTest {
    @TestFactory
    fun `ensure invalid character throws`() = listOf(
        ' ',
        'a',
        '@',
    ).map { char ->
        DynamicTest.dynamicTest("Invalid character '${char}' should throw.") {
            val exception = assertFailsWith<IllegalArgumentException>(
                block = { Position(char) },
                message = "Failed to ensure invalid character throws."
            )
            exception.message?.let {
                assertContains(it, "invalid character", ignoreCase = true)
            }
        }
    }

    @Test
    fun `ensure default character is 'A'`() {
        assertEquals(
            expected = 'A',
            actual = Position().character,
            message = "Failed to ensure default character is 'A'."
        )
    }

    @Test
    fun `ensure index equals character's index in enigma character set`() {
        Enigma.CHARACTER_SET.forEachIndexed { index, char ->
            assertEquals(
                expected = index,
                actual = Position(char).index,
                message = "Failed to ensure index equals character's index in enigma character set."
            )
        }
    }

    @Test
    fun `ensure named constructor works`() {
        assertEquals(
            expected = 'A',
            actual = Position.fromString("A").character,
            message = "Failed to ensure named constructor works."
        )
    }

    @TestFactory
    fun `ensure invalid string length throws`() = listOf(
        "AA",
        "A,A",
    ).map { position ->
        DynamicTest.dynamicTest("Invalid string length '${position.length}' should throw.") {
            val exception = assertFailsWith<IllegalArgumentException>(
                block = { Position.fromString(position) },
                message = "Failed to ensure invalid string length throws."
            )
            exception.message?.let {
                assertContains(it, "invalid string length", ignoreCase = true)
            }
        }
    }
}
