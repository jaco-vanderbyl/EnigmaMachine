package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

import kotlin.IllegalArgumentException
import kotlin.test.assertContains
import kotlin.test.assertFailsWith

class EnigmaM3RequirementsTest {
    private val badRotorCounts = listOf<Set<Rotor>>(
        setOf(RotorI()),
        setOf(RotorI(), RotorII()),
        setOf(RotorI(), RotorII(), RotorIII(), RotorIV()),
    )

    @TestFactory
    fun `ensure EnigmaM3 has exactly 3 rotors`() = badRotorCounts.map { rotors ->
        DynamicTest.dynamicTest("EnigmaM3 must have 3 rotors. Providing '${rotors.size}' rotors should throw.") {
            val ex = assertFailsWith<IllegalArgumentException>(
                message = "Failed to ensure EnigmaM3 has exactly 3 rotors.",
                block = { EnigmaM3(RotorUnit(ReflectorB(), rotors), Plugboard()) }
            )
            ex.message?.let { msg -> assertContains(charSequence = msg, other = "must have 3 rotors") }
        }
    }

    private val incompatibleRotors = listOf<Set<Rotor>>(
        setOf(IncompatibleWithEnigmaM3Rotor(), RotorI(), RotorII()),
        setOf(RotorI(), IncompatibleWithEnigmaM3Rotor(), RotorII()),
        setOf(RotorI(), RotorII(), IncompatibleWithEnigmaM3Rotor()),
    )

    @TestFactory
    fun `ensure EnigmaM3 only accepts compatible rotors`() = incompatibleRotors.map { rotors ->
        DynamicTest.dynamicTest("EnigmaM3 must accept compatible rotors only. Incompatible rotors should throw.") {
            val ex = assertFailsWith<IllegalArgumentException>(
                message = "Failed to ensure EnigmaM3 only accepts compatible rotors.",
                block = { EnigmaM3(RotorUnit(ReflectorB(), rotors), Plugboard()) }
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
                    RotorUnit(IncompatibleWithEnigmaM3Reflector(), setOf(RotorI(), RotorII(), RotorIII())),
                    Plugboard()
                )
            }
        )
        ex.message?.let { msg -> assertContains(charSequence = msg, other = "reflector is not compatible") }
    }
}

class IncompatibleWithEnigmaM3Rotor :
    Rotor(CipherSetMap("ABCDEFGHIJKLMNOPQRSTUVWXYZ"), Notch(setOf('A'))),
    CompatibleWithEnigmaI

class IncompatibleWithEnigmaM3Reflector :
    Reflector(CipherSetMap("ABCDEFGHIJKLMNOPQRSTUVWXYZ")),
    CompatibleWithEnigmaI
