package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertContains

import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class PositionTest {
    private val invalidCharacters = listOf(' ', 'a', '@')

    @TestFactory
    fun `ensure position throws on invalid characters`() = invalidCharacters.map { character ->
        DynamicTest.dynamicTest("Invalid character '${character}' should throw.") {
            val ex = assertFailsWith<IllegalArgumentException>(
                message = "Failed to ensure position throws on invalid characters.",
                block = { Position(character) }
            )
            ex.message?.let { msg -> assertContains(charSequence = msg, other = "Invalid character") }
        }
    }

    @Test
    fun `ensure default position character is 'A'`() {
        assertEquals(
            message = "Failed to ensure default position character is 'A'.",
            expected = 'A',
            actual = Position().character
        )
    }

    @Test
    fun `ensure position index equals character set index of character`() {
        Keys.CHARACTER_SET.forEachIndexed { index, character ->
            assertEquals(
                message = "Failed to ensure position index equals character set index of character.",
                expected = index,
                actual = Position(character).index
            )
        }
    }

    @Test
    fun `ensure named constructor can create object from string`() {
        assertEquals(
            message = "Failed to ensure named constructor can create object from string.",
            expected = 'A',
            actual = Position.fromString("A").character
        )
    }

    private val bogusPositionStrings = listOf("AA", "A,A")

    @TestFactory
    fun `ensure named constructor only accepts one-character string`() = bogusPositionStrings.map { position ->
        DynamicTest.dynamicTest("Non one-character string '${position}' should throw.") {
            val ex = assertFailsWith<IllegalArgumentException>(
                message = "Failed to ensure named constructor only accepts one-character string.",
                block = { Position.fromString(position) }
            )
            ex.message?.let { msg ->
                assertContains(charSequence = msg, other = "position must be a single character")
            }
        }
    }
}