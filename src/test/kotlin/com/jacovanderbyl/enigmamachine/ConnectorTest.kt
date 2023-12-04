package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.Test

import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ConnectorTest {
    private val invalidCharacters = listOf(' ', 'a', '@')

    @TestFactory
    fun `ensure first connector character is valid`() = invalidCharacters.map { character ->
        DynamicTest.dynamicTest("Invalid character '${character}' should throw.") {
            val ex = assertFailsWith<IllegalArgumentException>(
                message = "Failed to ensure first connector character is valid.",
                block = { Connector(first = character, second = 'A') }
            )
            ex.message?.let { msg ->
                assertContains(charSequence = msg, other = "First character is invalid")
            }
        }
    }

    @TestFactory
    fun `ensure second connector character is valid`() = invalidCharacters.map { character ->
        DynamicTest.dynamicTest("Invalid character '${character}' should throw.") {
            val ex = assertFailsWith<IllegalArgumentException>(
                message = "Failed to ensure second connector character is valid.",
                block = { Connector(first = 'A', second = character) }
            )
            ex.message?.let { msg ->
                assertContains(charSequence = msg, other = "Second character is invalid")
            }
        }
    }

    @Test
    fun `ensure first and second connector characters are not the same`() {
        val ex = assertFailsWith<IllegalArgumentException>(
            message = "Failed to ensure first and second connector characters are not the same.",
            block = { Connector(first = 'A', second = 'A') }
        )
        ex.message?.let { msg ->
            assertContains(charSequence = msg, other = "The first and second characters cannot be the same")
        }
    }

    @Test
    fun `ensure named constructor can create object from string`() {
        val connector = Connector.fromString(characterPair = "AB")
        assertEquals(
            message = "Failed to ensure named constructor can create object from string.",
            expected = "AB",
            actual = "${connector.first}${connector.second}"
        )
    }

    private val bogusCharacterPairs = listOf("A", "ABC")

    @TestFactory
    fun `ensure named constructor only accepts two-character string`() = bogusCharacterPairs.map { characterPair ->
        DynamicTest.dynamicTest("Non two-character pair string '${characterPair}' should throw.") {
            val ex = assertFailsWith<IllegalArgumentException>(
                message = "Failed to ensure named constructor only accepts two-character string.",
                block = { Connector.fromString(characterPair) }
            )
            ex.message?.let { msg ->
                assertContains(charSequence = msg, other = "A connector pair must be two characters")
            }
        }
    }

    @Test
    fun `ensure named constructor can create object list from string list`() {
        val connectorList = Connector.fromStrings(characterPairs = listOf("AB", "CD", "EF"))
        assertEquals(
            message = "Failed to ensure named constructor can create object list from string list.",
            expected = listOf("AB", "CD", "EF"),
            actual = connectorList.map { connector -> "${connector.first}${connector.second}" }
        )
    }
}
