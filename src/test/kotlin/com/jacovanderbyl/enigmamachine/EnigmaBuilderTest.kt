package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class EnigmaBuilderTest {
    private val plaintext = "AAAAA"

    @Test
    fun `ensure build with optional fields omitted enciphers correctly`() {
        val enigma = EnigmaBuilder.makeFromCsv(
            type = "ENIGMA_I",
            reflector = "B",
            rotors = "I,V,III"
        )

        assertEquals(
            expected = "SCSUX",
            actual = enigma.encipher(plaintext),
            message = "Failed to ensure build with optional fields omitted enciphers correctly."
        )
    }

    @Test
    fun `ensure build with optional fields empty enciphers correctly`() {
        val enigma = EnigmaBuilder.makeFromCsv(
            type = "ENIGMA_I",
            reflector = "B",
            rotors = "I,V,III",
            ringSettings = "14,9,24",
            startingPositions = "W,N,Y",
            plugboardConnectors = "SZ,GT,DV,KU,FO,MY,EW,JN,IX,LQ"
        )

        assertEquals(
            expected = "RRQMR",
            actual = enigma.encipher(plaintext),
            message = "Failed to ensure build with optional fields empty enciphers correctly."
        )
    }

    @Test
    fun `ensure build with CSV fields containing whitespace enciphers correctly`() {
        val enigma = EnigmaBuilder.makeFromCsv(
            type = "ENIGMA_I",
            reflector = "B",
            rotors = " I, V, III ",
            ringSettings = " 14, 9, 24 ",
            startingPositions = " W , N , Y ",
            plugboardConnectors = "SZ, GT, DV, KU, FO, MY, EW, JN, IX, LQ"
        )

        assertEquals(
            expected = "RRQMR",
            actual = enigma.encipher(plaintext),
            message = "Failed to ensure build with CSV fields containing whitespace enciphers correctly."
        )
    }

    @Test
    fun `ensure build with invalid type throws`() {
        val ex = assertFailsWith<IllegalArgumentException>(
            block = {
                EnigmaBuilder.makeFromCsv(
                    type = "BOGUS_TYPE",
                    reflector = "B",
                    rotors = "I,V,III"
                )
            },
            message = "Failed to ensure build with bogus type throws."
        )
        ex.message?.let { msg -> assertContains(charSequence = msg, other = "Type is invalid") }
    }

    @Test
    fun `ensure build with invalid reflector throws`() {
        val ex = assertFailsWith<IllegalArgumentException>(
            block = {
                EnigmaBuilder.makeFromCsv(
                    type = "ENIGMA_I",
                    reflector = "BOGUS_REFLECTOR",
                    rotors = "I,V,III"
                )
            },
            message = "Failed to ensure build with bogus reflector throws."
        )
        ex.message?.let { msg -> assertContains(charSequence = msg, other = "Reflector is invalid") }
    }

    private val invalidRotors = listOf(
        "BOGUS_ROTOR,II,III",
        "I,BOGUS_ROTOR,III",
        "I,II,BOGUS_ROTOR",
    )

    @TestFactory
    fun `ensure build with invalid rotor throws`() = invalidRotors.map { rotors ->
        DynamicTest.dynamicTest("Invalid rotor string '${rotors}' should throw.") {
            val ex = assertFailsWith<IllegalArgumentException>(
                block = {
                    EnigmaBuilder.makeFromCsv(
                        type = "ENIGMA_I",
                        reflector = "B",
                        rotors = rotors
                    )
                },
                message = "Failed to ensure build with invalid rotor throws."
            )
            ex.message?.let { msg -> assertContains(charSequence = msg, other = "Rotor is invalid") }
        }
    }

    private val badRingSettingCounts = listOf(
        "1",
        "1,1",
        "1,1,1,1",
    )

    @TestFactory
    fun `ensure build with bad ring setting count throws`() = badRingSettingCounts.map { ringSettings ->
        DynamicTest.dynamicTest("Bad ring settings count '${ringSettings}' should throw.") {
            val ex = assertFailsWith<IllegalArgumentException>(
                block = {
                    EnigmaBuilder.makeFromCsv(
                        type = "ENIGMA_I",
                        reflector = "B",
                        rotors = "I,V,III",
                        ringSettings = ringSettings
                    )
                },
                message = "Failed to ensure build with bad ring setting count throws."
            )
            ex.message?.let { msg ->
                assertContains(charSequence = msg, other = "Number of ring settings must equal number of rotors")
            }
        }
    }

    private val badPositionCounts = listOf(
        "A",
        "A,A",
        "A,A,A,A",
    )

    @TestFactory
    fun `ensure build with bad position count throws`() = badPositionCounts.map { positions ->
        DynamicTest.dynamicTest("Bad position count '${positions}' should throw.") {
            val ex = assertFailsWith<IllegalArgumentException>(
                block = {
                    EnigmaBuilder.makeFromCsv(
                        type = "ENIGMA_I",
                        reflector = "B",
                        rotors = "I,V,III",
                        startingPositions = positions
                    )
                },
                message = "Failed to ensure build with bad position count throws."
            )
            ex.message?.let { msg ->
                assertContains(charSequence = msg, other = "Number of positions must equal number of rotors")
            }
        }
    }
}
