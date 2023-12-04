package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertContains

import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class PlugboardTest {
    private val connectors = listOf(
        listOf(),
        listOf(Connector('A', 'B'), Connector('C', 'D')),
    )

    @TestFactory
    fun `ensure plugboard enciphers correctly given connectors in constructor`() = connectors.map { connectorList ->
        DynamicTest.dynamicTest("Plugboard with connectors '${connectorList}' in constructor should encipher.") {
            val plugboard = Plugboard(*connectorList.toTypedArray())
            encipherTest(plugboard, connectorList)
        }
    }

    @TestFactory
    fun `ensure plugboard enciphers correctly given connectors in function`() = connectors.map { connectorList ->
        DynamicTest.dynamicTest("Plugboard with connectors '${connectorList}' in function should encipher.") {
            val plugboard = Plugboard()
            plugboard.addConnectors(*connectorList.toTypedArray())
            encipherTest(plugboard, connectorList)
        }
    }

    private fun encipherTest(plugboard: Plugboard, connectorList: List<Connector>) {
        // Test connected characters encipher correctly
        connectorList.forEach { connector ->
            assertEquals(
                message = "Failed to ensure plugboard enciphers connected characters correctly.",
                expected = connector.second,
                actual = plugboard.encipher(connector.first)
            )
            assertEquals(
                message = "Failed to ensure plugboard enciphers connected characters correctly.",
                expected = connector.first,
                actual = plugboard.encipher(connector.second)
            )
        }

        // Test unconnected characters encipher correctly
        Keys.CHARACTER_SET.filterNot { character ->
            character in connectorList.map { connector -> connector.first } ||
            character in connectorList.map { connector -> connector.second }
        }.forEach { character ->
            assertEquals(
                message = "Failed to ensure plugboard enciphers unconnected characters correctly.",
                expected = character,
                actual = plugboard.encipher(character)
            )
        }
    }

    @Test
    fun `ensure plugboard connectors can be reset`() {
        val plugboard = Plugboard(Connector('A', 'B'))
        plugboard.reset()
        assertEquals(
            message = "Failed to ensure plugboard enciphers unconnected characters correctly.",
            expected = 'A',
            actual = plugboard.encipher('A')
        )
    }

    private val invalidCharacters = listOf(' ', 'a', '@')

    @TestFactory
    fun `ensure plugboard throws on invalid characters`() = invalidCharacters.map { character ->
        DynamicTest.dynamicTest("Invalid character '${character}' should throw.") {
            val plugboard = Plugboard()
            val ex = assertFailsWith<IllegalArgumentException>(
                message = "Failed to ensure plugboard throws on invalid characters.",
                block = { plugboard.encipher(character) }
            )
            ex.message?.let { msg -> assertContains(charSequence = msg, other = "Invalid character") }
        }
    }

    private val duplicateConnectors = listOf(
        listOf(Connector('A', 'B'), Connector('A', 'Z')),
        listOf(Connector('A', 'B'), Connector('B', 'Z')),
        listOf(Connector('A', 'B'), Connector('Z', 'A')),
        listOf(Connector('A', 'B'), Connector('Z', 'B')),
    )

    @TestFactory
    fun `ensure plugboard throws when adding duplicate connectors`() = duplicateConnectors.map { connectors ->
        DynamicTest.dynamicTest("Duplicate connector letters '${connectors}' should throw.") {
            val ex = assertFailsWith<IllegalArgumentException>(
                message = "Failed to ensure plugboard throws when connecting a character that is already connected.",
                block = { Plugboard(*connectors.toTypedArray()) }
            )
            ex.message?.let { msg ->
                assertContains(charSequence = msg, other = "Cannot connect character that's already been connected")
            }
        }
    }
}
