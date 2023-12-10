package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class EnigmaSettingsTest {
    private fun createStockEnigma(plugboard: Plugboard = Plugboard()) : Enigma = Enigma(
        type = EnigmaType.ENIGMA_I,
        rotorUnit = RotorUnit(
            reflector = ReflectorType.REFLECTOR_B.create(),
            rotors = setOf(RotorType.ROTOR_I.create(), RotorType.ROTOR_II.create(), RotorType.ROTOR_III.create())
        ),
        plugboard = plugboard
    )

    @Test
    fun `ensure character set is correct`() {
        assertEquals(
            expected = "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
            actual = Enigma.CHARACTER_SET,
            message = "Failed to ensure character set is correct."
        )
    }

    @TestFactory
    fun `ensure positions can be changed`() = listOf(
        listOf(Position('X'), Position('Y'), Position('Z')),
        listOf(Position('A'), Position('B'), Position('C')),
    ).map { positions ->
        DynamicTest.dynamicTest("Test setting positions to: '${positions}'.") {
            val enigma = createStockEnigma()
            enigma.setRotorPositions(*positions.toTypedArray())

            assertEquals(
                expected = positions.map { it.character },
                actual = enigma.getRotorPositions().map { it.character },
                message = "Failed to ensure positions can be changed."
            )
        }
    }

    @TestFactory
    fun `ensure invalid position count throws`() = listOf(
        listOf(),
        listOf(Position('A'), Position('B')),
        listOf(Position('A'), Position('B'), Position('C'), Position('D')),
    ).map { positions ->
        DynamicTest.dynamicTest("Invalid position count '${positions.size}' should throw.") {
            val enigma = createStockEnigma()
            val exception = assertFailsWith<IllegalArgumentException>(
                block = {
                    enigma.setRotorPositions(*positions.toTypedArray())
                },
                message = "Failed to ensure invalid position count throws."
            )
            exception.message?.let {
                assertContains(it, "invalid position count", ignoreCase = true)
            }
        }
    }

    @Test
    fun `ensure positions can be reset`() {
        val enigma = createStockEnigma()
        enigma.setRotorPositions(Position('X'), Position('Y'), Position('Z'))
        enigma.resetRotorPositions()

        assertEquals(
            expected = listOf('A', 'A', 'A'),
            actual = enigmaSingle.getRotorPositions().map { it.character },
            message = "Failed to ensure positions can be reset."
        )
    }

    @Test
    fun `ensure plugboard can be reset`() {
        val plugboard = Plugboard()
        val enigma = createStockEnigma(plugboard)
        enigma.addPlugboardConnectors(Connector(first = 'A', second = 'B'))
        enigma.resetPlugboard()

        assertEquals(
            expected = 'A',
            actual = plugboard.encipher('A'),
            message = "Failed to ensure plugboard can be reset."
        )
    }

    private val plugboardSingle = Plugboard()
    private val enigmaSingle = createStockEnigma(plugboardSingle)
    private val connectorsList = listOf(
        listOf(Connector('A', 'B'), Connector('C', 'D')),
        listOf(Connector('E', 'F'), Connector('G', 'H')),
        listOf(Connector('I', 'J'), Connector('K', 'L')),
    )

    @TestFactory
    fun `ensure connectors can be added`() = connectorsList.mapIndexed { index, connectors ->
        DynamicTest.dynamicTest(
            "Test adding connectors: '${connectors.map { "${it.first}${it.first}" } }'."
        ) {
            enigmaSingle.addPlugboardConnectors(*connectors.toTypedArray())

            // Test that all connectors are added, including connectors added in previous test case sequence.
            connectorsList.filterIndexed { i, _ -> i <= index }.forEach {
                it.forEach { connector ->
                    assertEquals(
                        expected = connector.second,
                        actual = plugboardSingle.encipher(connector.first),
                        message = "Failed to ensure connectors can be added."
                    )
                    assertEquals(
                        expected = connector.first,
                        actual = plugboardSingle.encipher(connector.second),
                        message = "Failed to ensure connectors can be added."
                    )
                }
            }
        }
    }

    @TestFactory
    fun `ensure connectors can be replaced`() = connectorsList.mapIndexed { index, connectors ->
        DynamicTest.dynamicTest(
            "Test connectors '${connectors.map { "${it.first}${it.first}" } }' replaced previous connectors."
        ) {
            enigmaSingle.replacePlugboardConnectors(*connectors.toTypedArray())

            // Test connectors (from previous test case sequences) have been removed.
            connectorsList.filterIndexed { i, _ -> i < index } .forEach {
                it.forEach { connector ->
                    assertEquals(
                        expected = connector.first,
                        actual = plugboardSingle.encipher(connector.first),
                        message = "Failed to ensure previous connectors were removed."
                    )
                    assertEquals(
                        expected = connector.second,
                        actual = plugboardSingle.encipher(connector.second),
                        message = "Failed to ensure previous connectors were removed."
                    )
                }
            }

            // Test connectors (from current test case sequence) have been added.
            connectors.forEach {connector ->
                assertEquals(
                    expected = connector.second,
                    actual = plugboardSingle.encipher(connector.first),
                    message = "Failed to ensure connectors were added."
                )
                assertEquals(
                    expected = connector.first,
                    actual = plugboardSingle.encipher(connector.second),
                    message = "Failed to ensure connectors were added."
                )
            }
        }
    }
}
