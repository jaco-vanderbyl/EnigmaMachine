package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ConnectorTest {
    @Test
    fun `ensure invalid character pair throws - first and second must be different`() {
        val exception = assertFailsWith<IllegalArgumentException>(
            block = { Connector(first = Letter.A, second = Letter.A) },
            message = "Failed to ensure first and second are different."
        )
        exception.message?.let {
            assertContains(it, "invalid character pair", ignoreCase = true)
        }
    }

    @Test
    fun `ensure named constructor works`() {
        val connector = Connector.fromString("AB")
        assertEquals(
            expected = "AB",
            actual = "${connector.first}${connector.second}",
            message = "Failed to ensure named constructor works."
        )
    }

    @Test
    fun `ensure named constructor works with string list`() {
        val connectorList = Connector.fromStrings(listOf("AB", "CD", "EF"))
        assertEquals(
            expected = listOf("AB", "CD", "EF"),
            actual = connectorList.map { "${it.first}${it.second}" },
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
