package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class EnigmaBuilderTest {
     @Test
    fun `ensure build works with optional fields omitted`() {
        val enigma = EnigmaBuilder.makeFromCsv(
            type = "ENIGMA_I",
            reflector = "B",
            rotors = "I,V,III"
        )
        assertEquals(
            expected = "SCSUX",
            actual = enigma.encipher("AAAAA"),
            message = "Failed to ensure build works with optional fields omitted."
        )
    }

    @Test
    fun `ensure build works with optional fields`() {
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
            actual = enigma.encipher("AAAAA"),
            message = "Failed to ensure build works with optional fields."
        )
    }

    @Test
    fun `ensure build works with CSV fields containing whitespace`() {
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
            actual = enigma.encipher("AAAAA"),
            message = "Failed to ensure build works with CSV fields containing whitespace."
        )
    }

    @Test
    fun `ensure invalid type throws`() {
        val exception = assertFailsWith<IllegalArgumentException>(
            block = {
                EnigmaBuilder.makeFromCsv(
                    type = "BOGUS_TYPE",
                    reflector = "B",
                    rotors = "I,V,III"
                )
            },
            message = "Failed to ensure invalid type throws."
        )
        exception.message?.let {
            assertContains(it, "type is invalid", ignoreCase = true)
        }
    }

    @Test
    fun `ensure invalid reflector type throws`() {
        val exception = assertFailsWith<IllegalArgumentException>(
            block = {
                EnigmaBuilder.makeFromCsv(
                    type = "ENIGMA_I",
                    reflector = "BOGUS_REFLECTOR",
                    rotors = "I,V,III"
                )
            },
            message = "Failed to ensure invalid reflector type throws."
        )
        exception.message?.let {
            assertContains(it, "reflector is invalid", ignoreCase = true)
        }
    }

    @TestFactory
    fun `ensure invalid rotor type throws`() = listOf(
        "BOGUS_ROTOR,II,III",
        "I,BOGUS_ROTOR,III",
        "I,II,BOGUS_ROTOR",
    ).map { rotors ->
        DynamicTest.dynamicTest("Invalid rotor type '${rotors}' should throw.") {
            val exception = assertFailsWith<IllegalArgumentException>(
                block = {
                    EnigmaBuilder.makeFromCsv(
                        type = "ENIGMA_I",
                        reflector = "B",
                        rotors = rotors
                    )
                },
                message = "Failed to ensure invalid rotor type throws."
            )
            exception.message?.let {
                assertContains(it, "Rotor is invalid", ignoreCase = true)
            }
        }
    }

    @TestFactory
    fun `ensure invalid ring settings count throws`() = listOf(
        "1",
        "1,1",
        "1,1,1,1",
    ).map { ringSettings ->
        DynamicTest.dynamicTest("Invalid ring settings count '${ringSettings}' should throw.") {
            val exception = assertFailsWith<IllegalArgumentException>(
                block = {
                    EnigmaBuilder.makeFromCsv(
                        type = "ENIGMA_I",
                        reflector = "B",
                        rotors = "I,V,III",
                        ringSettings = ringSettings
                    )
                },
                message = "Failed to ensure invalid ring settings count throws."
            )
            exception.message?.let {
                assertContains(it, "number of ring settings must equal number of rotors", ignoreCase = true)
            }
        }
    }

    @TestFactory
    fun `ensure invalid positions count throws`() = listOf(
        "A",
        "A,A",
        "A,A,A,A",
    ).map { positions ->
        DynamicTest.dynamicTest("Invalid positions count '${positions}' should throw.") {
            val exception = assertFailsWith<IllegalArgumentException>(
                block = {
                    EnigmaBuilder.makeFromCsv(
                        type = "ENIGMA_I",
                        reflector = "B",
                        rotors = "I,V,III",
                        startingPositions = positions
                    )
                },
                message = "Failed to ensure invalid positions count throws."
            )
            exception.message?.let {
                assertContains(it, "number of positions must equal number of rotors", ignoreCase = true)
            }
        }
    }
}
