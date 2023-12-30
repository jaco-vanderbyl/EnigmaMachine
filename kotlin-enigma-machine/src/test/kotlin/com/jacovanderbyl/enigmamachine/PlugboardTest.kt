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
        listOf(Connector(Letter.A, Letter.B), Connector(Letter.C, Letter.D)),
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

    private fun encipherTest(plugboard: Plugboard, connectorList: List<Connector>) {
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
        val plugboard = Plugboard(Connector(Letter.A, Letter.B))
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
        listOf(Connector(Letter.A, Letter.B), Connector(Letter.A, Letter.Z)),
        listOf(Connector(Letter.A, Letter.B), Connector(Letter.B, Letter.Z)),
        listOf(Connector(Letter.A, Letter.B), Connector(Letter.Z, Letter.A)),
        listOf(Connector(Letter.A, Letter.B), Connector(Letter.Z, Letter.B)),
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
}
