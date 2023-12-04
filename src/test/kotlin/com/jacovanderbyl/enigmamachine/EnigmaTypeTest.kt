package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

import kotlin.IllegalArgumentException
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class EnigmaTypeTest {
    private val plaintext = "AAAAA"

    @Test
    fun `ensure factory-built EnigmaI enciphers correctly`() {
        val enigma = EnigmaType.ENIGMA_I.create(
            rotorUnit = RotorUnit(
                reflector = ReflectorType.B.create(),
                rotors = setOf(RotorType.I.create(), RotorType.V.create(), RotorType.III.create())
            ),
            plugboard = Plugboard()
        )

        assertEquals(
            expected = "SCSUX",
            actual = enigma.encipher(plaintext),
            message = "Failed to ensure factory-built EnigmaI enciphers correctly."
        )
    }

    @Test
    fun `ensure factory-built EnigmaM3 enciphers correctly`() {
        val enigma = EnigmaType.ENIGMA_M3.create(
            rotorUnit = RotorUnit(
                reflector = ReflectorType.B.create(),
                rotors = setOf(RotorType.VI.create(), RotorType.VII.create(), RotorType.VIII.create())
            ),
            plugboard = Plugboard()
        )

        assertEquals(
            expected = "GJUBB",
            actual = enigma.encipher(plaintext),
            message = "Failed to ensure factory-built EnigmaM3 enciphers correctly."
        )
    }

    private val badRotorCounts = listOf(
        setOf(RotorType.I.create()),
        setOf(RotorType.I.create(), RotorType.II.create()),
        setOf(RotorType.I.create(), RotorType.II.create(), RotorType.III.create(), RotorType.IV.create()),
    )

    @TestFactory
    fun `ensure EnigmaI has exactly 3 rotors`() = badRotorCounts.map { rotors ->
        DynamicTest.dynamicTest("EnigmaI must have 3 rotors. Providing '${rotors.size}' rotors should throw.") {
            val ex = assertFailsWith<IllegalArgumentException>(
                block = {
                    EnigmaType.ENIGMA_I.create(
                        rotorUnit = RotorUnit(
                            reflector = ReflectorType.B.create(),
                            rotors = rotors
                        ),
                        plugboard = Plugboard()
                    )
                },
                message = "Failed to ensure EnigmaI has exactly 3 rotors."
            )
            ex.message?.let { msg -> assertContains(charSequence = msg, other = "must have 3 rotors") }
        }
    }

    @TestFactory
    fun `ensure EnigmaM3 has exactly 3 rotors`() = badRotorCounts.map { rotors ->
        DynamicTest.dynamicTest("EnigmaM3 must have 3 rotors. Providing '${rotors.size}' rotors should throw.") {
            val ex = assertFailsWith<IllegalArgumentException>(
                block = {
                    EnigmaType.ENIGMA_M3.create(
                        rotorUnit = RotorUnit(
                            reflector = ReflectorType.B.create(),
                            rotors = rotors
                        ),
                        plugboard = Plugboard()
                    )
                },
                message = "Failed to ensure EnigmaM3 has exactly 3 rotors."
            )
            ex.message?.let { msg -> assertContains(charSequence = msg, other = "must have 3 rotors") }
        }
    }

    private val incompatibleRotors = listOf(
        setOf(createIncompatibleRotor(), RotorType.I.create(), RotorType.II.create()),
        setOf(RotorType.I.create(), createIncompatibleRotor(), RotorType.II.create()),
        setOf(RotorType.I.create(), RotorType.II.create(), createIncompatibleRotor()),
    )

    @TestFactory
    fun `ensure EnigmaI only accepts compatible rotors`() = incompatibleRotors.map { rotors ->
        DynamicTest.dynamicTest("EnigmaI must accept compatible rotors only. Incompatible rotors should throw.") {
            val ex = assertFailsWith<IllegalArgumentException>(
                block = {
                    EnigmaType.ENIGMA_I.create(
                        rotorUnit = RotorUnit(
                            reflector = ReflectorType.B.create(),
                            rotors = rotors
                        ),
                        plugboard = Plugboard()
                    )
                },
                message = "Failed to ensure EnigmaI only accepts compatible rotors."
            )
            ex.message?.let { msg -> assertContains(charSequence = msg, other = "rotor is not compatible") }
        }
    }

    @TestFactory
    fun `ensure EnigmaM3 only accepts compatible rotors`() = incompatibleRotors.map { rotors ->
        DynamicTest.dynamicTest("EnigmaM3 must accept compatible rotors only. Incompatible rotors should throw.") {
            val ex = assertFailsWith<IllegalArgumentException>(
                block = {
                    EnigmaType.ENIGMA_M3.create(
                        rotorUnit = RotorUnit(
                            reflector = ReflectorType.B.create(),
                            rotors = rotors
                        ),
                        plugboard = Plugboard()
                    )
                },
                message = "Failed to ensure EnigmaM3 only accepts compatible rotors."
            )
            ex.message?.let { msg -> assertContains(charSequence = msg, other = "rotor is not compatible") }
        }
    }

    @Test
    fun `ensure EnigmaI only accepts compatible reflector`() {
        val ex = assertFailsWith<IllegalArgumentException>(
            block = {
                EnigmaType.ENIGMA_I.create(
                    rotorUnit = RotorUnit(
                        reflector = createIncompatibleReflector(),
                        rotors = setOf(RotorType.I.create(), RotorType.II.create(), RotorType.III.create())
                    ),
                    plugboard = Plugboard()
                )
            },
            message = "Failed to ensure EnigmaI only accepts compatible reflector."
        )
        ex.message?.let { msg -> assertContains(charSequence = msg, other = "reflector is not compatible") }
    }

    @Test
    fun `ensure EnigmaM3 only accepts compatible reflector`() {
        val ex = assertFailsWith<IllegalArgumentException>(
            block = {
                EnigmaType.ENIGMA_M3.create(
                    rotorUnit = RotorUnit(
                        reflector = createIncompatibleReflector(),
                        rotors = setOf(RotorType.I.create(), RotorType.II.create(), RotorType.III.create())
                    ),
                    plugboard = Plugboard()
                )
            },
            message = "Failed to ensure EnigmaM3 only accepts compatible reflector."
        )
        ex.message?.let { msg -> assertContains(charSequence = msg, other = "reflector is not compatible") }
    }

    private fun createIncompatibleRotor() : Rotor = Rotor(
        cipherSetMap = CipherSetMap("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
        notch = Notch(setOf('A')),
        type = RotorType.VIII,
        compatibility = setOf(),
        position = Position(),
        ringSetting = RingSetting()
    )

    private fun createIncompatibleReflector() : Reflector = Reflector(
        cipherSetMap = CipherSetMap("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
        type = ReflectorType.B,
        compatibility = setOf()
    )
}
