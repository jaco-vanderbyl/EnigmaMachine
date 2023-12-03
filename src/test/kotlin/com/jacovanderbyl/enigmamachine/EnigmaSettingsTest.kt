package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.Test

import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class EnigmaSettingsTest {
    private val plugboard = Plugboard()
    private val enigma = EnigmaFake(
        RotorUnit(
            ReflectorFactory.B.create(),
            setOf(
                RotorFactory.I.create(Position(), RingSetting()),
                RotorFactory.II.create(Position(), RingSetting()),
                RotorFactory.III.create(Position(), RingSetting())
            )
        ),
        plugboard
    )

    private val positionsList = listOf(
        listOf(Position('X'), Position('Y'), Position('Z')),
        listOf(Position('A'), Position('B'), Position('C')),
    )

    @TestFactory
    fun `ensure rotor positions can be changed`() = positionsList.map { positionList ->
        DynamicTest.dynamicTest("Rotor positions can be changed.") {
            enigma.setRotorPositions(*positionList.toTypedArray())
            assertEquals(
                message = "Failed to ensure rotor positions can be changed.",
                expected = positionList.map { it.character },
                actual = enigma.getRotorPositions().map { it.character }
            )
        }
    }

    private val badPositionsCount = listOf(
        listOf(),
        listOf(Position('A'), Position('B')),
        listOf(Position('A'), Position('B'), Position('C'), Position('D')),
    )

    @TestFactory
    fun `ensure position count equals rotor count`() = badPositionsCount.map { positionList ->
        DynamicTest.dynamicTest("Position count equals rotor count.") {
            val ex = assertFailsWith<IllegalArgumentException>(
                message = "Failed to ensure position count equals rotor count.",
                block = { enigma.setRotorPositions(*positionList.toTypedArray()) }
            )
            ex.message?.let { msg ->
                assertContains(charSequence = msg, other = "number of rotor positions must equal the number of rotors")
            }
        }
    }

    private val connectorsList = listOf(
        listOf(Connector(first = 'A', second = 'B'), Connector(first = 'C', second = 'D')),
        listOf(Connector(first = 'E', second = 'F'), Connector(first = 'G', second = 'H')),
        listOf(Connector(first = 'I', second = 'J'), Connector(first = 'K', second = 'L')),
    )

    @TestFactory
    fun `ensure connector cables can be added to plugboard`() = connectorsList.mapIndexed { index, connectors ->
        DynamicTest.dynamicTest("Connector cables can be added to plugboard.") {
            enigma.addPlugboardConnectors(unplugConnectorsFirst = false, *connectors.toTypedArray())

            connectorsList.filterIndexed { i, _ -> i <= index } .forEach {
                it.forEach { connector ->
                    assertEquals(
                        message = "Failed to ensure connector cables can be added to plugboard.",
                        expected = connector.second,
                        actual = plugboard.encipher(connector.first)
                    )
                    assertEquals(
                        message = "Failed to ensure connector cables can be added to plugboard.",
                        expected = connector.first,
                        actual = plugboard.encipher(connector.second)
                    )
                }
            }
        }
    }

    @TestFactory
    fun `ensure connector cables can be unplugged`() = connectorsList.mapIndexed { index, connectors ->
        DynamicTest.dynamicTest("Connector cables can be unplugged.") {
            enigma.addPlugboardConnectors(unplugConnectorsFirst = true, *connectors.toTypedArray())

            connectorsList.filterIndexed { i, _ -> i < index } .forEach {
                it.forEach { connector ->
                    assertEquals(
                        message = "Failed to ensure connector cables can be unplugged.",
                        expected = connector.first,
                        actual = plugboard.encipher(connector.first)
                    )
                    assertEquals(
                        message = "Failed to ensure connector cables can be unplugged.",
                        expected = connector.second,
                        actual = plugboard.encipher(connector.second)
                    )
                }
            }

            connectors.forEach {connector ->
                assertEquals(
                    message = "Failed to ensure connector cables can be added to plugboard.",
                    expected = connector.second,
                    actual = plugboard.encipher(connector.first)
                )
                assertEquals(
                    message = "Failed to ensure connector cables can be added to plugboard.",
                    expected = connector.first,
                    actual = plugboard.encipher(connector.second)
                )
            }
        }
    }

    @Test
    fun `ensure reset function resets rotor positions to default`() {
        val em = EnigmaFake(
            rotorUnit = RotorUnit(
                reflector = ReflectorFactory.B.create(),
                rotors = setOf(
                    RotorFactory.I.create(Position(), RingSetting()),
                    RotorFactory.II.create(Position(), RingSetting()),
                    RotorFactory.III.create(Position(), RingSetting())
                )
            ),
            plugboard = Plugboard()
        )
        val defaultPositions = em.getRotorPositions().map { it.character }

        em.setRotorPositions(Position('X'), Position('Y'), Position('Z'))
        em.reset()

        assertEquals(
            message = "Failed to ensure reset function resets rotor positions to default.",
            expected = defaultPositions,
            actual = enigma.getRotorPositions().map { it.character }
        )
    }

    @Test
    fun `ensure reset function can reset plugboard connector plugs`() {
        val pb = Plugboard()
        val em = EnigmaFake(
            rotorUnit = RotorUnit(
                reflector = ReflectorFactory.B.create(),
                rotors = setOf(
                    RotorFactory.I.create(Position(), RingSetting()),
                    RotorFactory.II.create(Position(), RingSetting()),
                    RotorFactory.III.create(Position(), RingSetting())
                )
            ),
            plugboard = pb
        )

        em.addPlugboardConnectors(unplugConnectorsFirst = false, Connector(first = 'A', second = 'B'))
        em.reset(unplugConnectors = true)

        assertEquals(
            message = "Failed to ensure reset function can reset plugboard connector plugs.",
            expected = 'A',
            actual = pb.encipher('A')
        )
    }
}
