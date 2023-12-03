package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

import kotlin.IllegalArgumentException
import kotlin.test.assertContains
import kotlin.test.assertFailsWith

class EnigmaM3RequirementsTest {
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
    fun `ensure EnigmaM3 has exactly 3 rotors`() = badRotorCounts.map { rotors ->
        DynamicTest.dynamicTest("EnigmaM3 must have 3 rotors. Providing '${rotors.size}' rotors should throw.") {
            val ex = assertFailsWith<IllegalArgumentException>(
                message = "Failed to ensure EnigmaM3 has exactly 3 rotors.",
                block = { EnigmaM3(RotorUnit(ReflectorFactory.B.create(), rotors), Plugboard()) }
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
    fun `ensure EnigmaM3 only accepts compatible rotors`() = incompatibleRotors.map { rotors ->
        DynamicTest.dynamicTest("EnigmaM3 must accept compatible rotors only. Incompatible rotors should throw.") {
            val ex = assertFailsWith<IllegalArgumentException>(
                message = "Failed to ensure EnigmaM3 only accepts compatible rotors.",
                block = { EnigmaM3(RotorUnit(ReflectorFactory.B.create(), rotors), Plugboard()) }
            )
            ex.message?.let { msg -> assertContains(charSequence = msg, other = "rotor is not compatible") }
        }
    }

    @Test
    fun `ensure EnigmaM3 only accepts compatible reflector`() {
        val ex = assertFailsWith<IllegalArgumentException>(
            message = "Failed to ensure EnigmaM3 only accepts compatible reflector.",
            block = {
                EnigmaM3(
                    RotorUnit(
                        createIncompatibleReflector(),
                        setOf(
                            RotorFactory.I.create(Position(), RingSetting()),
                            RotorFactory.II.create(Position(), RingSetting()),
                            RotorFactory.III.create(Position(), RingSetting())
                        )
                    ),
                    Plugboard()
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
