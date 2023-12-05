package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.IllegalArgumentException
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class EnigmaTypeTest {
    private fun createIncompatibleRotor() : Rotor = Rotor(
        cipherSetMap = CipherSetMap("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
        notch = Notch(setOf('A')),
        type = RotorType.V,
        compatibility = setOf(),
        position = Position(),
        ringSetting = RingSetting()
    )

    private fun createIncompatibleReflector() : Reflector = Reflector(
        cipherSetMap = CipherSetMap("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
        type = ReflectorType.B,
        compatibility = setOf()
    )

    private fun validRotors(enigmaType: EnigmaType) = when (enigmaType) {
        EnigmaType.ENIGMA_I -> setOf(RotorType.I.create(), RotorType.V.create(), RotorType.III.create())
        EnigmaType.ENIGMA_M3 -> setOf(RotorType.VI.create(), RotorType.VII.create(), RotorType.VIII.create())
        else -> throw IllegalArgumentException()
    }

    private fun validReflector(enigmaType: EnigmaType) = when (enigmaType) {
        EnigmaType.ENIGMA_I -> ReflectorType.B.create()
        EnigmaType.ENIGMA_M3 -> ReflectorType.C.create()
        else -> throw IllegalArgumentException()
    }

    private fun invalidRotorCount(enigmaType: EnigmaType) = when (enigmaType) {
        EnigmaType.ENIGMA_I -> listOf(
                setOf(RotorType.I.create()),
                setOf(RotorType.I.create(), RotorType.II.create()),
                setOf(RotorType.I.create(), RotorType.II.create(), RotorType.III.create(), RotorType.IV.create()),
            )
        EnigmaType.ENIGMA_M3 -> listOf(
                setOf(RotorType.I.create()),
                setOf(RotorType.I.create(), RotorType.II.create()),
                setOf(RotorType.I.create(), RotorType.II.create(), RotorType.III.create(), RotorType.IV.create()),
            )
        else -> throw IllegalArgumentException()
    }

    private fun incompatibleRotors(enigmaType: EnigmaType) = when (enigmaType) {
        EnigmaType.ENIGMA_I -> listOf(
                setOf(createIncompatibleRotor(), RotorType.I.create(), RotorType.II.create()),
                setOf(RotorType.I.create(), createIncompatibleRotor(), RotorType.II.create()),
                setOf(RotorType.I.create(), RotorType.II.create(), createIncompatibleRotor()),
            )
        EnigmaType.ENIGMA_M3 -> listOf(
                setOf(createIncompatibleRotor(), RotorType.VII.create(), RotorType.VIII.create()),
                setOf(RotorType.VI.create(), createIncompatibleRotor(), RotorType.VIII.create()),
                setOf(RotorType.VI.create(), RotorType.VII.create(), createIncompatibleRotor()),
            )
        else -> throw IllegalArgumentException()
    }

    private fun incompatibleReflector(enigmaType: EnigmaType) = when (enigmaType) {
        EnigmaType.ENIGMA_I -> createIncompatibleReflector()
        EnigmaType.ENIGMA_M3 -> createIncompatibleReflector()
        else -> throw IllegalArgumentException()
    }

    private fun expectedCiphers(enigmaType: EnigmaType) = when (enigmaType) {
        EnigmaType.ENIGMA_I -> "SCSUX"
        EnigmaType.ENIGMA_M3 -> "MWMJL"
        else -> throw IllegalArgumentException()
    }

    @Test
    fun `ensure factory creates enigma correctly`() {
        EnigmaType.entries.forEach { enigmaType ->
            val enigma = enigmaType.create(
                rotorUnit = RotorUnit(
                    reflector = validReflector(enigmaType),
                    rotors = validRotors(enigmaType)
                ),
                plugboard = Plugboard()
            )
            assertEquals(
                expected = enigmaType,
                actual = enigma.type,
                message = "Failed to ensure factory creates enigma with correct type."
            )
            assertEquals(
                expected = expectedCiphers(enigmaType),
                actual = enigma.encipher("AAAAA"),
                message = "Failed to ensure factory creates enigma that enciphers correctly."
            )
        }
    }

    @TestFactory
    fun `ensure invalid rotor count throws`(): List<DynamicTest> {
        val tests = mutableListOf<DynamicTest>()

        EnigmaType.entries.forEach { enigmaType ->
            tests += invalidRotorCount(enigmaType).map { rotors ->
                DynamicTest.dynamicTest(
                    "Invalid rotor count '${rotors.size}' for enigma '${enigmaType}' should throw."
                ) {
                    val exception = assertFailsWith<IllegalArgumentException>(
                        block = {
                            enigmaType.create(
                                rotorUnit = RotorUnit(
                                    reflector = validReflector(enigmaType),
                                    rotors = rotors
                                ),
                                plugboard = Plugboard()
                            )
                        },
                        message = "Failed to ensure invalid rotor count throws."
                    )
                    exception.message?.let {
                        assertContains(it, "invalid rotor count", ignoreCase = true)
                    }
                }
            }
        }

        return tests.toList()
    }

    @TestFactory
    fun `ensure incompatible rotor throws`(): List<DynamicTest> {
        val tests = mutableListOf<DynamicTest>()

        EnigmaType.entries.forEach { enigmaType ->
            tests += incompatibleRotors(enigmaType).map { rotors ->
                DynamicTest.dynamicTest("Incompatible rotor for enigma '${enigmaType}' should throw.") {
                    val exception = assertFailsWith<IllegalArgumentException>(
                        block = {
                            enigmaType.create(
                                rotorUnit = RotorUnit(
                                    reflector = validReflector(enigmaType),
                                    rotors = rotors
                                ),
                                plugboard = Plugboard()
                            )
                        },
                        message = "Failed to ensure incompatible rotor throws."
                    )
                    exception.message?.let {
                        assertContains(it, "incompatible rotor", ignoreCase = true)
                    }
                }
            }
        }

        return tests.toList()
    }

    @Test
    fun `ensure incompatible reflector throws`() {
        EnigmaType.entries.forEach { enigmaType ->
            val exception = assertFailsWith<IllegalArgumentException>(
                block = {
                    enigmaType.create(
                        rotorUnit = RotorUnit(
                            reflector = incompatibleReflector(enigmaType),
                            rotors = validRotors(enigmaType)
                        ),
                        plugboard = Plugboard()
                    )
                },
                message = "Failed to ensure incompatible reflector throws."
            )
            exception.message?.let {
                assertContains(it, "incompatible reflector", ignoreCase = true)
            }
        }
    }
}
