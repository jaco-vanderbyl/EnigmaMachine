package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ConnectorTest {
    @TestFactory
    fun `ensure invalid character throws`() = listOf(
        ' ',
        'a',
        '@',
    ).map { char ->
        DynamicTest.dynamicTest("Invalid character '${char}' should throw.") {
            val exception = assertFailsWith<IllegalArgumentException>(
                block = { Connector(first = char, second = 'A') },
                message = "Failed to ensure invalid character throws for first."
            )
            exception.message?.let {
                assertContains(it, "invalid character for first", ignoreCase = true)
            }

            val exception2 = assertFailsWith<IllegalArgumentException>(
                block = { Connector(first = 'A', second = char) },
                message = "Failed to ensure invalid character throws for second."
            )
            exception2.message?.let {
                assertContains(it, "invalid character for second", ignoreCase = true)
            }
        }
    }

    @Test
    fun `ensure invalid character pair throws - first and second must be different`() {
        val exception = assertFailsWith<IllegalArgumentException>(
            block = { Connector(first = 'A', second = 'A') },
            message = "Failed to ensure first and second are different."
        )
        exception.message?.let {
            assertContains(it, "invalid character pair", ignoreCase = true)
        }
    }

    @Test
    fun `ensure named constructor works`() {
        val connector = Connector.fromString(characterPair = "AB")
        assertEquals(
            expected = "AB",
            actual = "${connector.first}${connector.second}",
            message = "Failed to ensure named constructor works."
        )
    }

    @Test
    fun `ensure named constructor works with string list`() {
        val connectorList = Connector.fromStrings(characterPairs = listOf("AB", "CD", "EF"))
        assertEquals(
            expected = listOf("AB", "CD", "EF"),
            actual = connectorList.map { connector -> "${connector.first}${connector.second}" },
            message = "Failed to ensure named constructor works with string list."
        )
    }

    @TestFactory
    fun `ensure invalid string length throws`() = listOf(
        "A",
        "ABC"
    ).map { characterPair ->
        DynamicTest.dynamicTest("Invalid string length '${characterPair.length}' should throw.") {
            val exception = assertFailsWith<IllegalArgumentException>(
                block = { Connector.fromString(characterPair) },
                message = "Failed to ensure invalid string length throws."
            )
            exception.message?.let {
                assertContains(it, "invalid string length", ignoreCase = true)
            }
        }
    }
}
