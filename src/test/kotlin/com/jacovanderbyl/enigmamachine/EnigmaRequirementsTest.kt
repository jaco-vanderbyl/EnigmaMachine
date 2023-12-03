package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

import kotlin.IllegalArgumentException
import kotlin.test.assertContains
import kotlin.test.assertFailsWith

class EnigmaRequirementsTest {
    private val badRotorCounts = listOf(
        setOf(RotorFactory.I.create(Position(), RingSetting())),
        setOf(RotorFactory.I.create(Position(), RingSetting()), RotorFactory.II.create(Position(), RingSetting())),
        setOf(
            RotorFactory.I.create(Position(), RingSetting()),
            RotorFactory.II.create(Position(), RingSetting()),
            RotorFactory.III.create(Position(), RingSetting()),
            RotorFactory.IV.create(Position(), RingSetting())
        ),
    )

    @TestFactory
    fun `ensure EnigmaI has exactly 3 rotors`() = badRotorCounts.map { rotors ->
        DynamicTest.dynamicTest("EnigmaI must have 3 rotors. Providing '${rotors.size}' rotors should throw.") {
            val ex = assertFailsWith<IllegalArgumentException>(
                message = "Failed to ensure EnigmaI has exactly 3 rotors.",
                block = {
                    EnigmaFactory.ENIGMA_I.create(
                        rotorUnit = RotorUnit(
                            reflector = ReflectorFactory.B.create(),
                            rotors = rotors
                        ),
                        plugboard = Plugboard()
                    )
                }
            )
            ex.message?.let { msg -> assertContains(charSequence = msg, other = "must have 3 rotors") }
        }
    }

    @TestFactory
    fun `ensure EnigmaM3 has exactly 3 rotors`() = badRotorCounts.map { rotors ->
        DynamicTest.dynamicTest("EnigmaM3 must have 3 rotors. Providing '${rotors.size}' rotors should throw.") {
            val ex = assertFailsWith<IllegalArgumentException>(
                message = "Failed to ensure EnigmaM3 has exactly 3 rotors.",
                block = {
                    EnigmaFactory.ENIGMA_M3.create(
                        rotorUnit = RotorUnit(
                            reflector = ReflectorFactory.B.create(),
                            rotors = rotors
                        ),
                        plugboard = Plugboard()
                    )
                }
            )
            ex.message?.let { msg -> assertContains(charSequence = msg, other = "must have 3 rotors") }
        }
    }

    private val incompatibleRotors = listOf(
        setOf(
            createIncompatibleRotor(),
            RotorFactory.I.create(Position(), RingSetting()),
            RotorFactory.II.create(Position(), RingSetting())
        ),
        setOf(
            RotorFactory.I.create(Position(), RingSetting()),
            createIncompatibleRotor(),
            RotorFactory.II.create(Position(), RingSetting())
        ),
        setOf(
            RotorFactory.I.create(Position(), RingSetting()),
            RotorFactory.II.create(Position(), RingSetting()),
            createIncompatibleRotor()
        ),
    )

    @TestFactory
    fun `ensure EnigmaI only accepts compatible rotors`() = incompatibleRotors.map { rotors ->
        DynamicTest.dynamicTest("EnigmaI must accept compatible rotors only. Incompatible rotors should throw.") {
            val ex = assertFailsWith<IllegalArgumentException>(
                message = "Failed to ensure EnigmaI only accepts compatible rotors.",
                block = {
                    EnigmaFactory.ENIGMA_I.create(
                        rotorUnit = RotorUnit(
                            reflector = ReflectorFactory.B.create(),
                            rotors = rotors
                        ),
                        plugboard = Plugboard()
                    )
                }
            )
            ex.message?.let { msg -> assertContains(charSequence = msg, other = "rotor is not compatible") }
        }
    }

    @TestFactory
    fun `ensure EnigmaM3 only accepts compatible rotors`() = incompatibleRotors.map { rotors ->
        DynamicTest.dynamicTest("EnigmaM3 must accept compatible rotors only. Incompatible rotors should throw.") {
            val ex = assertFailsWith<IllegalArgumentException>(
                message = "Failed to ensure EnigmaM3 only accepts compatible rotors.",
                block = {
                    EnigmaFactory.ENIGMA_M3.create(
                        rotorUnit = RotorUnit(
                            reflector = ReflectorFactory.B.create(),
                            rotors = rotors
                        ),
                        plugboard = Plugboard()
                    )
                }
            )
            ex.message?.let { msg -> assertContains(charSequence = msg, other = "rotor is not compatible") }
        }
    }

    @Test
    fun `ensure EnigmaI only accepts compatible reflector`() {
        val ex = assertFailsWith<IllegalArgumentException>(
            message = "Failed to ensure EnigmaI only accepts compatible reflector.",
            block = {
                EnigmaFactory.ENIGMA_I.create(
                    rotorUnit = RotorUnit(
                        reflector = createIncompatibleReflector(),
                        rotors = setOf(
                            RotorFactory.I.create(Position(), RingSetting()),
                            RotorFactory.II.create(Position(), RingSetting()),
                            RotorFactory.III.create(Position(), RingSetting())
                        )
                    ),
                    plugboard = Plugboard()
                )
            }
        )
        ex.message?.let { msg -> assertContains(charSequence = msg, other = "reflector is not compatible") }
    }

    @Test
    fun `ensure EnigmaM3 only accepts compatible reflector`() {
        val ex = assertFailsWith<IllegalArgumentException>(
            message = "Failed to ensure EnigmaM3 only accepts compatible reflector.",
            block = {
                EnigmaFactory.ENIGMA_M3.create(
                    rotorUnit = RotorUnit(
                        reflector = createIncompatibleReflector(),
                        rotors = setOf(
                            RotorFactory.I.create(Position(), RingSetting()),
                            RotorFactory.II.create(Position(), RingSetting()),
                            RotorFactory.III.create(Position(), RingSetting())
                        )
                    ),
                    plugboard = Plugboard()
                )
            }
        )
        ex.message?.let { msg -> assertContains(charSequence = msg, other = "reflector is not compatible") }
    }

    private fun createIncompatibleRotor() : Rotor = Rotor(
        cipherSetMap = CipherSetMap("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
        notch = Notch(setOf('A')),
        type = RotorFactory.VIII,
        compatibility = setOf(),
        position = Position(),
        ringSetting = RingSetting()
    )

    private fun createIncompatibleReflector() : Reflector = Reflector(
        cipherSetMap = CipherSetMap("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
        type = ReflectorFactory.B,
        compatibility = setOf()
    )
}
