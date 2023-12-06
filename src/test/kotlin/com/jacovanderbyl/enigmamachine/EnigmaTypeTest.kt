package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class EnigmaTypeTest {
    private fun createIncompatibleRotor() : StepRotor = StepRotor(
        type = RotorType.V,
        cipherSetMap = CipherSetMap("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
        compatibility = setOf(),
        position = Position(),
        ringSetting = RingSetting(),
        notch = Notch(Position('A'))
    )

    private fun createIncompatibleReflector() : Reflector = Reflector(
        cipherSetMap = CipherSetMap("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
        type = ReflectorType.B,
        compatibility = setOf()
    )

    private fun validRotors(enigmaType: EnigmaType) : Set<Rotor> = when (enigmaType) {
        EnigmaType.ENIGMA_I -> setOf(RotorType.I.create(), RotorType.V.create(), RotorType.III.create())
        EnigmaType.ENIGMA_M3 -> setOf(RotorType.VI.create(), RotorType.VII.create(), RotorType.VIII.create())
        EnigmaType.ENIGMA_M4 -> setOf(
            RotorType.BETA.create(),
            RotorType.VI.create(),
            RotorType.VII.create(),
            RotorType.VIII.create(),
        )
        else -> throw IllegalArgumentException()
    }

    private fun validReflector(enigmaType: EnigmaType) : Reflector = when (enigmaType) {
        EnigmaType.ENIGMA_I -> ReflectorType.B.create()
        EnigmaType.ENIGMA_M3 -> ReflectorType.C.create()
        EnigmaType.ENIGMA_M4 -> ReflectorType.B_THIN.create()
        else -> throw IllegalArgumentException()
    }

    private fun invalidRotorCount(enigmaType: EnigmaType) : List<Set<Rotor>> = when (enigmaType) {
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
        EnigmaType.ENIGMA_M4 -> listOf(
            setOf(RotorType.BETA.create()),
            setOf(RotorType.BETA.create(), RotorType.II.create()),
            setOf(RotorType.BETA.create(), RotorType.II.create(), RotorType.III.create()),
            setOf(
                RotorType.BETA.create(),
                RotorType.I.create(),
                RotorType.II.create(),
                RotorType.III.create(),
                RotorType.IV.create()
            ),
        )
        else -> throw IllegalArgumentException()
    }

    private fun incompatibleRotors(enigmaType: EnigmaType) : List<Set<Rotor>> = when (enigmaType) {
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
        EnigmaType.ENIGMA_M4 -> listOf(
            setOf(
                createIncompatibleRotor(),
                RotorType.VI.create(),
                RotorType.VII.create(),
                RotorType.VIII.create(),
            ),
            setOf(
                RotorType.BETA.create(),
                createIncompatibleRotor(),
                RotorType.VII.create(),
                RotorType.VIII.create(),
            ),
            setOf(
                RotorType.BETA.create(),
                RotorType.VI.create(),
                createIncompatibleRotor(),
                RotorType.VIII.create(),
            ),
            setOf(
                RotorType.BETA.create(),
                RotorType.VI.create(),
                RotorType.VII.create(),
                createIncompatibleRotor(),
            ),
        )
        else -> throw IllegalArgumentException()
    }

    private fun incompatibleReflector(enigmaType: EnigmaType) : Reflector = when (enigmaType) {
        EnigmaType.ENIGMA_I -> createIncompatibleReflector()
        EnigmaType.ENIGMA_M3 -> createIncompatibleReflector()
        EnigmaType.ENIGMA_M4 -> createIncompatibleReflector()
        else -> throw IllegalArgumentException()
    }

    // Given plaintext 'AAAAA' and the configuration define in valid validRotors and validReflector
    private fun expectedCiphers(enigmaType: EnigmaType) : String = when (enigmaType) {
        EnigmaType.ENIGMA_I -> "SCSUX"
        EnigmaType.ENIGMA_M3 -> "MWMJL"
        EnigmaType.ENIGMA_M4 -> "GJUBB"
        else -> throw IllegalArgumentException()
    }

    @TestFactory
    fun `ensure factory creates with correct type`() = EnigmaType.entries.map { enigmaType ->
        DynamicTest.dynamicTest("Test factory creation of enigma type: '${enigmaType}'.") {
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
                message = "Failed to ensure factory creates with correct type."
            )
        }
    }

    @TestFactory
    fun `ensure factory creates enigma that enciphers correctly`() = EnigmaType.entries.map { enigmaType ->
        DynamicTest.dynamicTest("Test factory creation of enigma type: '${enigmaType}'.") {
            val enigma = enigmaType.create(
                rotorUnit = RotorUnit(
                    reflector = validReflector(enigmaType),
                    rotors = validRotors(enigmaType)
                ),
                plugboard = Plugboard()
            )
            assertEquals(
                expected = expectedCiphers(enigmaType),
                actual = enigma.encipher("AAAAA"),
                message = "Failed to ensure factory creates enigma that enciphers correctly."
            )
        }
    }

    @TestFactory
    fun `ensure invalid rotor count throws`() : List<DynamicTest> {
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
    fun `ensure incompatible rotor throws`() : List<DynamicTest> {
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

    @TestFactory
    fun `ensure incompatible reflector throws`() = EnigmaType.entries.map { enigmaType ->
        DynamicTest.dynamicTest("Incompatible reflector type '${enigmaType}' should throw.") {
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

    @Test
    fun `ensure invalid rotor type at order index 0 throws for M4`() {
        val exception = assertFailsWith<IllegalArgumentException>(
            block = {
                EnigmaType.ENIGMA_M4.create(
                    rotorUnit = RotorUnit(
                        reflector = validReflector(EnigmaType.ENIGMA_M4),
                        rotors = setOf(
                            RotorType.V.create(),
                            RotorType.VI.create(),
                            RotorType.VII.create(),
                            RotorType.VIII.create(),
                        )
                    ),
                    plugboard = Plugboard()
                )
            },
            message = "Failed to ensure invalid rotor type at order index 0 throws for M4."
        )
        exception.message?.let {
            assertContains(it, "invalid rotor type at order index", ignoreCase = true)
        }
    }

    @Test
    fun `ensure list of available enigma types is correct`() {
        assertEquals(
            expected = EnigmaType.entries.map { it.name },
            actual = EnigmaType.list(),
            message = "Failed to ensure list of available enigma types is correct."
        )
    }
}
