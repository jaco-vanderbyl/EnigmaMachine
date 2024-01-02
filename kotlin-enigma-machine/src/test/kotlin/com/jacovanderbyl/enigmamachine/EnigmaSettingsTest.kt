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
            actual = Letter.characterSet(),
            message = "Failed to ensure character set is correct."
        )
    }

    @TestFactory
    fun `ensure positions can be changed`() = listOf(
        listOf(Letter.X, Letter.Y, Letter.Z),
        listOf(Letter.A, Letter.B, Letter.C),
    ).map { positions ->
        DynamicTest.dynamicTest("Test setting positions to: '${positions}'.") {
            val enigma = createStockEnigma()
            enigma.setPositions(*positions.toTypedArray())

            assertEquals(
                expected = positions.map { it.character },
                actual = enigma.getRotors().map { it.position.character },
                message = "Failed to ensure positions can be changed."
            )
        }
    }

    @TestFactory
    fun `ensure invalid position count throws`() = listOf(
        listOf(),
        listOf(Letter.A, Letter.B),
        listOf(Letter.A, Letter.B, Letter.C, Letter.D),
    ).map { positions ->
        DynamicTest.dynamicTest("Invalid position count '${positions.size}' should throw.") {
            val enigma = createStockEnigma()
            val exception = assertFailsWith<IllegalArgumentException>(
                block = {
                    enigma.setPositions(*positions.toTypedArray())
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
        enigma.setPositions(Letter.X, Letter.Y, Letter.Z)
        enigma.resetPositions()

        assertEquals(
            expected = listOf('A', 'A', 'A'),
            actual = enigmaSingle.getRotors().map { it.position.character },
            message = "Failed to ensure positions can be reset."
        )
    }

    @Test
    fun `ensure plugboard can be reset`() {
        val plugboard = Plugboard()
        val enigma = createStockEnigma(plugboard)
        enigma.addConnectors(Plugboard.Connector(first = Letter.A, Letter.B))
        enigma.resetConnectors()

        assertEquals(
            expected = 'A',
            actual = plugboard.encipher('A'),
            message = "Failed to ensure plugboard can be reset."
        )
    }

    private val plugboardSingle = Plugboard()
    private val enigmaSingle = createStockEnigma(plugboardSingle)
    private val connectorsList = listOf(
        listOf(Plugboard.Connector(Letter.A, Letter.B), Plugboard.Connector(Letter.C, Letter.D)),
        listOf(Plugboard.Connector(Letter.E, Letter.F), Plugboard.Connector(Letter.G, Letter.H)),
        listOf(Plugboard.Connector(Letter.I, Letter.J), Plugboard.Connector(Letter.K, Letter.L)),
    )

    @TestFactory
    fun `ensure connectors can be added`() = connectorsList.mapIndexed { index, connectors ->
        DynamicTest.dynamicTest(
            "Test adding connectors: '${connectors.map { "${it.first}${it.first}" } }'."
        ) {
            enigmaSingle.addConnectors(*connectors.toTypedArray())

            // Test that all connectors are added, including connectors added in the previous test case sequence.
            connectorsList.filterIndexed { i, _ -> i <= index }.forEach {
                it.forEach { connector ->
                    assertEquals(
                        expected = connector.second.character,
                        actual = plugboardSingle.encipher(connector.first.character),
                        message = "Failed to ensure connectors can be added."
                    )
                    assertEquals(
                        expected = connector.first.character,
                        actual = plugboardSingle.encipher(connector.second.character),
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
            enigmaSingle.replaceConnectors(*connectors.toTypedArray())

            // Test connectors (from previous test case sequences) have been removed.
            connectorsList.filterIndexed { i, _ -> i < index } .forEach {
                it.forEach { connector ->
                    assertEquals(
                        expected = connector.first.character,
                        actual = plugboardSingle.encipher(connector.first.character),
                        message = "Failed to ensure previous connectors were removed."
                    )
                    assertEquals(
                        expected = connector.second.character,
                        actual = plugboardSingle.encipher(connector.second.character),
                        message = "Failed to ensure previous connectors were removed."
                    )
                }
            }

            // Test connectors (from the current test case sequence) have been added.
            connectors.forEach {connector ->
                assertEquals(
                    expected = connector.second.character,
                    actual = plugboardSingle.encipher(connector.first.character),
                    message = "Failed to ensure connectors were added."
                )
                assertEquals(
                    expected = connector.first.character,
                    actual = plugboardSingle.encipher(connector.second.character),
                    message = "Failed to ensure connectors were added."
                )
            }
        }
    }
}
