package com.jacovanderbyl.enigmamachine.builder

import com.jacovanderbyl.enigmamachine.*
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class EnigmaBuilderTest {
    @Test
    fun `ensure 'stock enigma' is built when all config is omitted`() {
        val enigma = EnigmaBuilder().build()
        assertEquals(
            expected = "BDZGO",
            actual = enigma.encipher("AAAAA"),
            message = "Failed to ensure build works with optional fields omitted."
        )
    }

     @Test
    fun `ensure build works with ring settings, positions, and connectors omitted - stringly typed`() {
        val enigma = EnigmaBuilder()
            .addType("ENIGMA_I")
            .addReflector("B")
            .addRotors("I,V,III")
            .build()
        assertEquals(
            expected = "SCSUX",
            actual = enigma.encipher("AAAAA"),
            message = "Failed to ensure build works with ring settings, positions, and connectors omitted."
        )
    }
    @Test
    fun `ensure build works with ring settings, positions, and connectors omitted`() {
        val enigma = EnigmaBuilder()
            .addType(EnigmaType.ENIGMA_I)
            .addReflector(ReflectorType.REFLECTOR_B)
            .addRotors(RotorType.ROTOR_I, RotorType.ROTOR_V, RotorType.ROTOR_III)
            .build()
        assertEquals(
            expected = "SCSUX",
            actual = enigma.encipher("AAAAA"),
            message = "Failed to ensure build works with ring settings, positions, and connectors omitted."
        )
    }

    @Test
    fun `ensure build works with all configuration - stringly typed`() {
        val enigma = EnigmaBuilder()
            .addType("ENIGMA_I")
            .addReflector("B")
            .addRotors("I,V,III")
            .addRotorRingSettings("14,9,24")
            .addRotorPositions("W,N,Y")
            .addPlugboardConnectors("SZ,GT,DV,KU,FO,MY,EW,JN,IX,LQ")
            .build()
        assertEquals(
            expected = "RRQMR",
            actual = enigma.encipher("AAAAA"),
            message = "Failed to ensure build works with all configuration."
        )
    }

    @Test
    fun `ensure build works with all configuration`() {
        val enigma = EnigmaBuilder()
            .addType(EnigmaType.ENIGMA_I)
            .addReflector(ReflectorType.REFLECTOR_B)
            .addRotors(RotorType.ROTOR_I, RotorType.ROTOR_V, RotorType.ROTOR_III)
            .addRotorRingSettings(Rotor.Ring.SETTING_14, Rotor.Ring.SETTING_9, Rotor.Ring.SETTING_24)
            .addRotorPositions(Letter.W, Letter.N, Letter.Y)
            .addPlugboardConnectors(
                Connector(Letter.S, Letter.Z), Connector(Letter.G, Letter.T),
                Connector(Letter.D, Letter.V), Connector(Letter.K, Letter.U),
                Connector(Letter.F, Letter.O), Connector(Letter.M, Letter.Y),
                Connector(Letter.E, Letter.W), Connector(Letter.J, Letter.N),
                Connector(Letter.I, Letter.X), Connector(Letter.L, Letter.Q),
            )
            .build()
        assertEquals(
            expected = "RRQMR",
            actual = enigma.encipher("AAAAA"),
            message = "Failed to ensure build works with all configuration."
        )
    }

    @Test
    fun `ensure build works with CSV fields containing whitespace`() {
        val enigma = EnigmaBuilder()
            .addType("ENIGMA_I")
            .addReflector("B")
            .addRotors(" I, V, III ")
            .addRotorRingSettings(" 14, 9, 24 ")
            .addRotorPositions(" W , N , Y ")
            .addPlugboardConnectors("SZ, GT, DV, KU, FO, MY, EW, JN, IX, LQ")
            .build()
        assertEquals(
            expected = "RRQMR",
            actual = enigma.encipher("AAAAA"),
            message = "Failed to ensure build works with CSV fields containing whitespace."
        )
    }

    @Test
    fun `ensure build works with stringly typed shorthand and full values`() {
        val enigmaFirst = EnigmaBuilder()
            .addType("I")
            .addReflector("B")
            .addRotors("I,V,III")
            .addRotorRingSettings("14,9,24")
            .build()
        val enigmaSecond = EnigmaBuilder()
            .addType("ENIGMA_I")
            .addReflector("REFLECTOR_B")
            .addRotors("ROTOR_I,ROTOR_V,ROTOR_III")
            .addRotorRingSettings("SETTING_14,SETTING_9,SETTING_24")
            .build()
        assertEquals(
            expected = enigmaFirst.encipher("AAAAA"),
            actual = enigmaSecond.encipher("AAAAA"),
            message = "Failed to ensure build works with stringly typed shorthand and full values."
        )
    }

    @Test
    fun `ensure configuration is reset after build`() {
        val enigmaBuilder = EnigmaBuilder()
        val enigmaFirst = enigmaBuilder
            .addType("I")
            .addReflector("B")
            .addRotors("I,V,III")
            .addRotorRingSettings("14,9,24")
            .addPlugboardConnectors("SZ, GT, DV, KU, FO, MY, EW, JN, IX, LQ")
            .build()
        val enigmaSecond = enigmaBuilder.build()
        assertEquals(
            expected = "CDPRL",
            actual = enigmaFirst.encipher("AAAAA"),
            message = "Failed to ensure configuration is reset after build."
        )
        assertEquals(
            expected = "BDZGO",
            actual = enigmaSecond.encipher("AAAAA"),
            message = "Failed to ensure configuration is reset after build."
        )
    }

    @Test
    fun `ensure invalid enigma type throws`() {
        val exception = assertFailsWith<IllegalArgumentException>(
            block = {
                EnigmaBuilder().addType("BOGUS_TYPE").build()
            },
            message = "Failed to ensure invalid enigma type throws."
        )
        exception.message?.let {
            assertContains(it, "no enum constant", ignoreCase = true)
        }
    }

    @Test
    fun `ensure invalid reflector type throws`() {
        val exception = assertFailsWith<IllegalArgumentException>(
            block = {
                EnigmaBuilder().addReflector("BOGUS_REFLECTOR").build()
            },
            message = "Failed to ensure invalid reflector type throws."
        )
        exception.message?.let {
            assertContains(it, "no enum constant", ignoreCase = true)
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
                    EnigmaBuilder().addRotors(rotors).build()
                },
                message = "Failed to ensure invalid rotor type throws."
            )
            exception.message?.let {
                assertContains(it, "no enum constant", ignoreCase = true)
            }
        }
    }

    @TestFactory
    fun `ensure invalid ring setting count throws`() = listOf(
        "1",
        "1,1",
        "1,1,1,1",
    ).map { ringSettings ->
        DynamicTest.dynamicTest("Invalid ring setting count '${ringSettings}' should throw.") {
            val exception = assertFailsWith<IllegalArgumentException>(
                block = {
                    EnigmaBuilder().addRotorRingSettings(ringSettings).build()
                },
                message = "Failed to ensure invalid ring setting count throws."
            )
            exception.message?.let {
                assertContains(it, "invalid ring setting count", ignoreCase = true)
            }
        }
    }

    @TestFactory
    fun `ensure invalid position count throws`() = listOf(
        "A",
        "A,A",
        "A,A,A,A",
    ).map { positions ->
        DynamicTest.dynamicTest("Invalid position count '${positions}' should throw.") {
            val exception = assertFailsWith<IllegalArgumentException>(
                block = {
                    EnigmaBuilder().addRotorPositions(positions).build()
                },
                message = "Failed to ensure invalid position count throws."
            )
            exception.message?.let {
                assertContains(it, "invalid position count", ignoreCase = true)
            }
        }
    }
}
