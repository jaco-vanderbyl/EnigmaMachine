package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class RotorUnitTest {
    @TestFactory
    fun `ensure duplicate rotor type throws`() = listOf(
        setOf(RotorType.ROTOR_I.create(), RotorType.ROTOR_I.create()),
        setOf(RotorType.ROTOR_I.create(), RotorType.ROTOR_II.create(), RotorType.ROTOR_III.create(), RotorType.ROTOR_II.create()),
    ).map { rotors ->
        DynamicTest.dynamicTest("Duplicate rotor type '${rotors.map { it.type } }' should throw.") {
            val exception = assertFailsWith<IllegalArgumentException>(
                block = {
                    RotorUnit(
                        reflector = ReflectorType.REFLECTOR_B.create(),
                        rotors = rotors
                    )
                },
                message = "Failed to ensure duplicate rotor type throws."
            )
            exception.message?.let {
                assertContains(it, "duplicate rotor types", ignoreCase = true)
            }
        }
    }

    @TestFactory
    fun `ensure positions can be changed`() = listOf(
        listOf(Position('X'), Position('Y'), Position('Z')),
        listOf(Position('A'), Position('B'), Position('C')),
    ).map { positions ->
        DynamicTest.dynamicTest("Test setting positions to: '${positions}'.") {
            val rotorUnit = RotorUnit(
                reflector = ReflectorType.REFLECTOR_B.create(),
                rotors = setOf(RotorType.ROTOR_I.create(), RotorType.ROTOR_II.create(), RotorType.ROTOR_III.create())
            )
            positions.forEachIndexed { index, position ->
                rotorUnit.setRotorPosition(index, position)
            }

            assertEquals(
                expected = positions.map { it.character },
                actual = rotorUnit.rotors.map { it.position.character },
                message = "Failed to ensure positions can be changed."
            )
        }
    }

    @TestFactory
    fun `ensure positions can be reset`() = listOf(
        listOf(Position('X'), Position('Y'), Position('Z')),
        listOf(Position('A'), Position('B'), Position('C')),
    ).map { positions ->
        DynamicTest.dynamicTest("Test that positions '${positions}' are reset.") {
            val rotorUnit = RotorUnit(
                reflector = ReflectorType.REFLECTOR_B.create(),
                rotors = setOf(RotorType.ROTOR_I.create(), RotorType.ROTOR_II.create(), RotorType.ROTOR_III.create())
            )
            positions.forEachIndexed { index, position ->
                rotorUnit.setRotorPosition(index, position)
            }
            rotorUnit.resetRotorPositions()

            assertEquals(
                expected = listOf('A', 'A', 'A'),
                actual = rotorUnit.rotors.map { it.position.character },
                message = "Failed to ensure positions can be reset."
            )
        }
    }

    @TestFactory
    fun `ensure rotor unit enciphers with default position and ring setting`() = mapOf(
        'A' to 'U',
        'B' to 'E',
        'K' to 'N',
        'O' to 'D',
        'Y' to 'X',
        'Z' to 'H',
    ).map {
        DynamicTest.dynamicTest("Rotor unit should encipher '${it.key}' to '${it.value}}'.") {
            val rotorUnit = RotorUnit(
                reflector = ReflectorType.REFLECTOR_B.create(),
                rotors = setOf(RotorType.ROTOR_I.create(), RotorType.ROTOR_II.create(), RotorType.ROTOR_III.create())
            )

            assertEquals(
                expected = it.value,
                actual = rotorUnit.encipher(it.key),
                message = "Failed to ensure rotor unit enciphers with default position and ring setting."
            )
        }
    }

    @TestFactory
    fun `ensure rotor unit enciphers with set position and default ring setting`() = mapOf(
        'A' to 'I',
        'B' to 'L',
        'K' to 'R',
        'O' to 'W',
        'Y' to 'D',
        'Z' to 'J',
    ).map {
        DynamicTest.dynamicTest("Rotor unit should encipher '${it.key}' to '${it.value}}'.") {
            val rotorUnit = RotorUnit(
                reflector = ReflectorType.REFLECTOR_B.create(),
                rotors = setOf(
                    RotorType.ROTOR_I.create(Position('X')),
                    RotorType.ROTOR_II.create(Position('Y')),
                    RotorType.ROTOR_III.create(Position('Z')),
                )
            )

            assertEquals(
                expected = it.value,
                actual = rotorUnit.encipher(it.key),
                message = "Failed to ensure rotor unit enciphers with set position and default ring setting."
            )
        }
    }

    @TestFactory
    fun `ensure rotor unit enciphers with set position and set ring setting`() = mapOf(
        'A' to 'S',
        'B' to 'M',
        'K' to 'G',
        'O' to 'L',
        'Y' to 'C',
        'Z' to 'N',
    ).map {
        DynamicTest.dynamicTest("Rotor unit should encipher '${it.key}' to '${it.value}}'.") {
            val rotorUnit = RotorUnit(
                reflector = ReflectorType.REFLECTOR_B.create(),
                rotors = setOf(
                    RotorType.ROTOR_I.create(ringSetting = RingSetting(9)),
                    RotorType.ROTOR_II.create(ringSetting = RingSetting(17)),
                    RotorType.ROTOR_III.create(ringSetting = RingSetting(22)),
                )
            )

            assertEquals(
                expected = it.value,
                actual = rotorUnit.encipher(it.key),
                message = "Failed to ensure rotor unit enciphers with set position and set ring setting."
            )
        }
    }

    private val rotorUnitForNormalStep = RotorUnit(
        reflector = ReflectorType.REFLECTOR_B.create(),
        rotors = setOf(
            RotorType.ROTOR_I.create(),
            RotorType.ROTOR_II.create(),
            RotorType.ROTOR_III.create(Position('S'))
        )
    )

    @TestFactory
    fun `ensure normal stepping works`() = listOf(
        "AAT",
        "AAU",
        "AAV",
        "ABW",
        "ABX",
        "ABY",
    ).map { positions ->
        DynamicTest.dynamicTest("Test rotor positions is '${positions}' after step.") {
            rotorUnitForNormalStep.stepRotors()

            assertEquals(
                expected = positions,
                actual = rotorUnitForNormalStep.rotors.map { it.position.character }.joinToString(""),
                message = "Failed to ensure normal stepping works."
            )
        }
    }

    private val rotorUnitForDoubleStep = RotorUnit(
        reflector = ReflectorType.REFLECTOR_B.create(),
        rotors = setOf(
            RotorType.ROTOR_I.create(),
            RotorType.ROTOR_II.create(Position('D')),
            RotorType.ROTOR_III.create(Position('S'))
        )
    )

    @TestFactory
    fun `ensure double stepping works`() = listOf(
        "ADT",
        "ADU",
        "ADV",
        "AEW",
        "BFX",
        "BFY",
    ).map { positions ->
        DynamicTest.dynamicTest("Test rotor positions is '${positions}' after step.") {
            rotorUnitForDoubleStep.stepRotors()

            assertEquals(
                expected = positions,
                actual = rotorUnitForDoubleStep.rotors.map { it.position.character }.joinToString(""),
                message = "Failed to ensure double stepping works."
            )
        }
    }
}
