package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

import kotlin.IllegalArgumentException
import kotlin.test.assertContains
import kotlin.test.assertFailsWith

class EnigmaIRequirementsTest {
    private val badRotorCounts = listOf<Set<Rotor>>(
        setOf(RotorI()),
        setOf(RotorI(), RotorII()),
        setOf(RotorI(), RotorII(), RotorIII(), RotorIV()),
    )

    @TestFactory
    fun `ensure EnigmaI has exactly 3 rotors`() = badRotorCounts.map { rotors ->
        DynamicTest.dynamicTest("EnigmaI must have 3 rotors. Providing '${rotors.size}' rotors should throw.") {
            val ex = assertFailsWith<IllegalArgumentException>(
                message = "Failed to ensure EnigmaI has exactly 3 rotors.",
                block = { EnigmaI(RotorUnit(ReflectorB(), rotors), Plugboard()) }
            )
            ex.message?.let { msg -> assertContains(charSequence = msg, other = "must have 3 rotors") }
        }
    }

    private val incompatibleRotors = listOf<Set<Rotor>>(
        setOf(IncompatibleWithEnigmaIRotor(), RotorI(), RotorII()),
        setOf(RotorI(), IncompatibleWithEnigmaIRotor(), RotorII()),
        setOf(RotorI(), RotorII(), IncompatibleWithEnigmaIRotor()),
    )

    @TestFactory
    fun `ensure EnigmaI only accepts compatible rotors`() = incompatibleRotors.map { rotors ->
        DynamicTest.dynamicTest("EnigmaI must accept compatible rotors only. Incompatible rotors should throw.") {
            val ex = assertFailsWith<IllegalArgumentException>(
                message = "Failed to ensure EnigmaI only accepts compatible rotors.",
                block = { EnigmaI(RotorUnit(ReflectorB(), rotors), Plugboard()) }
            )
            ex.message?.let { msg -> assertContains(charSequence = msg, other = "rotor is not compatible") }
        }
    }

    @Test
    fun `ensure EnigmaI only accepts compatible reflector`() {
        val ex = assertFailsWith<IllegalArgumentException>(
            message = "Failed to ensure EnigmaI only accepts compatible reflector.",
            block = {
                EnigmaI(
                    RotorUnit(IncompatibleWithEnigmaIReflector(), setOf(RotorI(), RotorII(), RotorIII())),
                    Plugboard()
                )
            }
        )
        ex.message?.let { msg -> assertContains(charSequence = msg, other = "reflector is not compatible") }
    }
}

class IncompatibleWithEnigmaIRotor :
    Rotor(CipherSetMap("ABCDEFGHIJKLMNOPQRSTUVWXYZ"), Notch(setOf('A'))),
    CompatibleWithEnigmaM3

class IncompatibleWithEnigmaIReflector :
    Reflector(CipherSetMap("ABCDEFGHIJKLMNOPQRSTUVWXYZ")),
    CompatibleWithEnigmaM3
