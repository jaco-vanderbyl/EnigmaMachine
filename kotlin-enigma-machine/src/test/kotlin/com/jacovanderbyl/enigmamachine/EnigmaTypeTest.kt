package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class EnigmaTypeTest {
    private fun createIncompatibleRotor() = Rotor.StepRotor(
        type = RotorType.ROTOR_V,
        cipherSetMap = CipherSetMap("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
        notchPositions = setOf(Letter.A),
        compatibility = setOf(),
        position = Letter.A,
        ringSetting = Ring.SETTING_1
    )

    private fun createIncompatibleReflector() : Reflector = Reflector(
        type = ReflectorType.REFLECTOR_B,
        cipherSetMap = CipherSetMap("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
        compatibility = setOf()
    )

    private fun validRotors(enigmaType: EnigmaType) : Set<Rotor> = when (enigmaType) {
        EnigmaType.ENIGMA_I -> setOf(
            RotorType.ROTOR_I.create(),
            RotorType.ROTOR_V.create(),
            RotorType.ROTOR_III.create()
        )
        EnigmaType.ENIGMA_M3 -> setOf(
            RotorType.ROTOR_VI.create(),
            RotorType.ROTOR_VII.create(),
            RotorType.ROTOR_VIII.create()
        )
        EnigmaType.ENIGMA_M4 -> setOf(
            RotorType.ROTOR_BETA.create(),
            RotorType.ROTOR_VI.create(),
            RotorType.ROTOR_VII.create(),
            RotorType.ROTOR_VIII.create(),
        )
        else -> throw IllegalArgumentException()
    }

    private fun validReflector(enigmaType: EnigmaType) : Reflector = when (enigmaType) {
        EnigmaType.ENIGMA_I -> ReflectorType.REFLECTOR_B.create()
        EnigmaType.ENIGMA_M3 -> ReflectorType.REFLECTOR_C.create()
        EnigmaType.ENIGMA_M4 -> ReflectorType.REFLECTOR_B_THIN.create()
        else -> throw IllegalArgumentException()
    }

    private fun invalidRotorCount(enigmaType: EnigmaType) : List<Set<Rotor>> = when (enigmaType) {
        EnigmaType.ENIGMA_I -> listOf(
                setOf(RotorType.ROTOR_I.create()),
                setOf(RotorType.ROTOR_I.create(), RotorType.ROTOR_II.create()),
                setOf(
                    RotorType.ROTOR_I.create(),
                    RotorType.ROTOR_II.create(),
                    RotorType.ROTOR_III.create(),
                    RotorType.ROTOR_IV.create()
                ),
            )
        EnigmaType.ENIGMA_M3 -> listOf(
                setOf(RotorType.ROTOR_I.create()),
                setOf(RotorType.ROTOR_I.create(), RotorType.ROTOR_II.create()),
                setOf(
                    RotorType.ROTOR_I.create(),
                    RotorType.ROTOR_II.create(),
                    RotorType.ROTOR_III.create(),
                    RotorType.ROTOR_IV.create()
                ),
            )
        EnigmaType.ENIGMA_M4 -> listOf(
            setOf(RotorType.ROTOR_BETA.create()),
            setOf(RotorType.ROTOR_BETA.create(), RotorType.ROTOR_II.create()),
            setOf(RotorType.ROTOR_BETA.create(), RotorType.ROTOR_II.create(), RotorType.ROTOR_III.create()),
            setOf(
                RotorType.ROTOR_BETA.create(),
                RotorType.ROTOR_I.create(),
                RotorType.ROTOR_II.create(),
                RotorType.ROTOR_III.create(),
                RotorType.ROTOR_IV.create()
            ),
        )
        else -> throw IllegalArgumentException()
    }

    private fun incompatibleRotors(enigmaType: EnigmaType) : List<Set<Rotor>> = when (enigmaType) {
        EnigmaType.ENIGMA_I -> listOf(
                setOf(createIncompatibleRotor(), RotorType.ROTOR_I.create(), RotorType.ROTOR_II.create()),
                setOf(RotorType.ROTOR_I.create(), createIncompatibleRotor(), RotorType.ROTOR_II.create()),
                setOf(RotorType.ROTOR_I.create(), RotorType.ROTOR_II.create(), createIncompatibleRotor()),
            )
        EnigmaType.ENIGMA_M3 -> listOf(
                setOf(createIncompatibleRotor(), RotorType.ROTOR_VII.create(), RotorType.ROTOR_VIII.create()),
                setOf(RotorType.ROTOR_VI.create(), createIncompatibleRotor(), RotorType.ROTOR_VIII.create()),
                setOf(RotorType.ROTOR_VI.create(), RotorType.ROTOR_VII.create(), createIncompatibleRotor()),
            )
        EnigmaType.ENIGMA_M4 -> listOf(
            setOf(
                createIncompatibleRotor(),
                RotorType.ROTOR_VI.create(),
                RotorType.ROTOR_VII.create(),
                RotorType.ROTOR_VIII.create(),
            ),
            setOf(
                RotorType.ROTOR_BETA.create(),
                createIncompatibleRotor(),
                RotorType.ROTOR_VII.create(),
                RotorType.ROTOR_VIII.create(),
            ),
            setOf(
                RotorType.ROTOR_BETA.create(),
                RotorType.ROTOR_VI.create(),
                createIncompatibleRotor(),
                RotorType.ROTOR_VIII.create(),
            ),
            setOf(
                RotorType.ROTOR_BETA.create(),
                RotorType.ROTOR_VI.create(),
                RotorType.ROTOR_VII.create(),
                createIncompatibleRotor(),
            ),
        )
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
                            reflector = createIncompatibleReflector(),
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
                            RotorType.ROTOR_V.create(),
                            RotorType.ROTOR_VI.create(),
                            RotorType.ROTOR_VII.create(),
                            RotorType.ROTOR_VIII.create(),
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
