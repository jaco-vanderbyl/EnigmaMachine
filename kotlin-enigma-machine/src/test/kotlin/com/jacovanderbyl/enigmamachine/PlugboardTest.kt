package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class PlugboardTest {
    private val validConnectors = listOf(
        listOf(),
        listOf(Plugboard.Connector(Letter.A, Letter.B), Plugboard.Connector(Letter.C, Letter.D)),
    )

    @TestFactory
    fun `ensure encipher works - given connectors in constructor`() = validConnectors.map { connectors ->
        DynamicTest.dynamicTest(
            "Test encipher with connectors '${connectors.map { "${it.first}${it.second}" } }' set in constructor."
        ) {
            val plugboard = Plugboard(*connectors.toTypedArray())
            encipherTest(plugboard, connectors)
        }
    }

    @TestFactory
    fun `ensure encipher works - given connectors in function`() = validConnectors.map { connectors ->
        DynamicTest.dynamicTest(
            "Test encipher with connectors '${connectors.map { "${it.first}${it.second}" } }' set in function."
        ) {
            val plugboard = Plugboard()
            plugboard.addConnectors(*connectors.toTypedArray())

            encipherTest(plugboard, connectors)
        }
    }

    private fun encipherTest(plugboard: Plugboard, connectorList: List<Plugboard.Connector>) {
        // Test connected characters encipher correctly
        connectorList.forEach { connector ->
            assertEquals(
                expected = connector.second.character,
                actual = plugboard.encipher(connector.first.character),
                message = "Failed to ensure encipher works - with connected characters."
            )
            assertEquals(
                expected = connector.first.character,
                actual = plugboard.encipher(connector.second.character),
                message = "Failed to ensure encipher works - with connected characters."
            )
        }

        // Test unconnected characters encipher correctly
        Letter.characterSet().filterNot { char ->
            char in connectorList.map { connector -> connector.first.character } ||
            char in connectorList.map { connector -> connector.second.character }
        }.forEach { char ->
            assertEquals(
                expected = char,
                actual = plugboard.encipher(char),
                message = "Failed to ensure encipher works - with unconnected characters."
            )
        }
    }

    @Test
    fun `ensure connectors can be reset`() {
        val plugboard = Plugboard(Plugboard.Connector(Letter.A, Letter.B))
        plugboard.reset()

        assertEquals(
            expected = 'A',
            actual = plugboard.encipher('A'),
            message = "Failed to ensure connectors can be reset."
        )
    }

    @TestFactory
    fun `ensure invalid character throws`() = listOf(
        ' ',
        'a',
        '@',
    ).map { char ->
        DynamicTest.dynamicTest("Invalid character '${char}' should throw.") {
            val plugboard = Plugboard()
            val exception = assertFailsWith<IllegalArgumentException>(
                block = { plugboard.encipher(char) },
                message = "Failed to ensure invalid character throws."
            )
            exception.message?.let {
                assertContains(it, "invalid character", ignoreCase = true)
            }
        }
    }

    @TestFactory
    fun `ensure duplicate connectors throws`() = listOf(
        listOf(Plugboard.Connector(Letter.A, Letter.B), Plugboard.Connector(Letter.A, Letter.Z)),
        listOf(Plugboard.Connector(Letter.A, Letter.B), Plugboard.Connector(Letter.B, Letter.Z)),
        listOf(Plugboard.Connector(Letter.A, Letter.B), Plugboard.Connector(Letter.Z, Letter.A)),
        listOf(Plugboard.Connector(Letter.A, Letter.B), Plugboard.Connector(Letter.Z, Letter.B)),
    ).map { connectors ->
        DynamicTest.dynamicTest(
            "Duplicate connectors '${connectors.map { "${it.first}${it.second}" } }' should throw."
        ) {
            val exception = assertFailsWith<IllegalArgumentException>(
                block = { Plugboard(*connectors.toTypedArray()) },
                message = "Failed to ensure duplicate connectors throws."
            )
            exception.message?.let {
                assertContains(it, "duplicate connector", ignoreCase = true)
            }
        }
    }

    @Test
    fun `ensure invalid character pair throws - first and second must be different`() {
        val exception = assertFailsWith<IllegalArgumentException>(
            block = { Plugboard.Connector(first = Letter.A, second = Letter.A) },
            message = "Failed to ensure first and second are different."
        )
        exception.message?.let {
            assertContains(it, "invalid character pair", ignoreCase = true)
        }
    }

    @Test
    fun `ensure named constructor works`() {
        val connector = Plugboard.Connector.fromString("AB")
        assertEquals(
            expected = "AB",
            actual = "${connector.first}${connector.second}",
            message = "Failed to ensure named constructor works."
        )
    }

    @Test
    fun `ensure named constructor works with string list`() {
        val connectorList = Plugboard.Connector.fromStrings(listOf("AB", "CD", "EF"))
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
                block = { Plugboard.Connector.fromString(characterPair) },
                message = "Failed to ensure invalid string length throws."
            )
            exception.message?.let {
                assertContains(it, "invalid string length", ignoreCase = true)
            }
        }
    }
}
