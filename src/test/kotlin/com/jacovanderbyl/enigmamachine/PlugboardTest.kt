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
        listOf(Connector('A', 'B'), Connector('C', 'D')),
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
                expected = connector.second,
                actual = plugboard.encipher(connector.first),
                message = "Failed to ensure encipher works - with connected characters."
            )
            assertEquals(
                expected = connector.first,
                actual = plugboard.encipher(connector.second),
                message = "Failed to ensure encipher works - with connected characters."
            )
        }

        // Test unconnected characters encipher correctly
        Enigma.CHARACTER_SET.filterNot { character ->
            character in connectorList.map { connector -> connector.first } ||
            character in connectorList.map { connector -> connector.second }
        }.forEach { character ->
            assertEquals(
                expected = character,
                actual = plugboard.encipher(character),
                message = "Failed to ensure encipher works - with unconnected characters."
            )
        }
    }

    @Test
    fun `ensure connectors can be reset`() {
        val plugboard = Plugboard(Connector('A', 'B'))
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
        listOf(Connector('A', 'B'), Connector('A', 'Z')),
        listOf(Connector('A', 'B'), Connector('B', 'Z')),
        listOf(Connector('A', 'B'), Connector('Z', 'A')),
        listOf(Connector('A', 'B'), Connector('Z', 'B')),
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
