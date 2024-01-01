package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

/**
 * 'Stock' enigma is here defined as having:
 *     - B-reflector
 *     - I, II and III rotors (from left to right)
 *     - All ring settings set to '1'
 *     - All rotor start positions set to 'A'
 *     - No plugboard connectors
 *
 *  With this configuration 'AAAAA' will produce the encoded sequence 'BDZGO'
 */
class EnigmaContextEncipherTest {
    private val plaintext = ClassLoader.getSystemResource("plaintext").readText()

    private fun createStockEnigma() : Enigma = Enigma(
        type = EnigmaType.ENIGMA_I,
        rotorUnit = RotorUnit(
            reflector = ReflectorType.REFLECTOR_B.create(),
            rotors = setOf(RotorType.ROTOR_I.create(), RotorType.ROTOR_II.create(), RotorType.ROTOR_III.create())
        ),
        plugboard = Plugboard()
    )

    private fun createEnigma(fileName: String) : Enigma = when (fileName) {
        "B-I-II-III-A-A-A-1-1-1" -> {
            Enigma(
                rotorUnit = RotorUnit(
                    reflector = ReflectorType.REFLECTOR_B.create(),
                    rotors = setOf(
                        RotorType.ROTOR_I.create(),
                        RotorType.ROTOR_II.create(),
                        RotorType.ROTOR_III.create()
                    )
                ),
                plugboard = Plugboard(),
                type = EnigmaType.ENIGMA_M3
            )
        }
        "B-I-II-III-Q-E-V-1-1-1" -> {
            Enigma(
                rotorUnit = RotorUnit(
                    reflector = ReflectorType.REFLECTOR_B.create(),
                    rotors = setOf(
                        RotorType.ROTOR_I.create(position = Letter.Q),
                        RotorType.ROTOR_II.create(position = Letter.E),
                        RotorType.ROTOR_III.create(position = Letter.V)
                    )
                ),
                plugboard = Plugboard(),
                type = EnigmaType.ENIGMA_M3
            )
        }
        "B-I-II-III-A-A-A-5-11-24" -> {
            Enigma(
                rotorUnit = RotorUnit(
                    reflector = ReflectorType.REFLECTOR_B.create(),
                    rotors = setOf(
                        RotorType.ROTOR_I.create(ringSetting = Ring.SETTING_5),
                        RotorType.ROTOR_II.create(ringSetting = Ring.SETTING_11),
                        RotorType.ROTOR_III.create(ringSetting = Ring.SETTING_24)
                    )
                ),
                plugboard = Plugboard(),
                type = EnigmaType.ENIGMA_M3
            )
        }
        "B-I-II-III-Q-E-V-5-11-24" -> {
            Enigma(
                rotorUnit = RotorUnit(
                    reflector = ReflectorType.REFLECTOR_B.create(),
                    rotors = setOf(
                        RotorType.ROTOR_I.create(position = Letter.Q, ringSetting = Ring.SETTING_5),
                        RotorType.ROTOR_II.create(position = Letter.E, ringSetting = Ring.SETTING_11),
                        RotorType.ROTOR_III.create(position = Letter.V, ringSetting = Ring.SETTING_24)
                    )
                ),
                plugboard = Plugboard(),
                type = EnigmaType.ENIGMA_M3
            )
        }
        "B-IV-V-VI-A-B-C-1-2-3" -> {
            Enigma(
                rotorUnit = RotorUnit(
                    reflector = ReflectorType.REFLECTOR_B.create(),
                    rotors = setOf(
                        RotorType.ROTOR_IV.create(),
                        RotorType.ROTOR_V.create(position = Letter.B, ringSetting = Ring.SETTING_2),
                        RotorType.ROTOR_VI.create(position = Letter.C, ringSetting = Ring.SETTING_3)
                    )
                ),
                plugboard = Plugboard(),
                type = EnigmaType.ENIGMA_M3
            )
        }
        "C-VI-VII-VIII-Z-R-S-26-8-15" -> {
            Enigma(
                rotorUnit = RotorUnit(
                    reflector = ReflectorType.REFLECTOR_C.create(),
                    rotors = setOf(
                        RotorType.ROTOR_VI.create(position = Letter.Z, ringSetting = Ring.SETTING_26),
                        RotorType.ROTOR_VII.create(position = Letter.R, ringSetting = Ring.SETTING_8),
                        RotorType.ROTOR_VIII.create(position = Letter.S, ringSetting = Ring.SETTING_15)
                    )
                ),
                plugboard = Plugboard(),
                type = EnigmaType.ENIGMA_M3
            )
        }
        "C-VI-VII-VIII-Z-R-S-26-8-15-AB-CD-EF-GH-IJ-KL-MN-OP-QR-ST" -> {
            Enigma(
                rotorUnit = RotorUnit(
                    reflector = ReflectorType.REFLECTOR_C.create(),
                    rotors = setOf(
                        RotorType.ROTOR_VI.create(position = Letter.Z, ringSetting = Ring.SETTING_26),
                        RotorType.ROTOR_VII.create(position = Letter.R, ringSetting = Ring.SETTING_8),
                        RotorType.ROTOR_VIII.create(position = Letter.S, ringSetting = Ring.SETTING_15)
                    )
                ),
                plugboard = Plugboard(
                    Connector(Letter.A, Letter.B), Connector(Letter.C, Letter.D), Connector(Letter.E, Letter.F),
                    Connector(Letter.G, Letter.H), Connector(Letter.I, Letter.J), Connector(Letter.K, Letter.L),
                    Connector(Letter.M, Letter.N), Connector(Letter.O, Letter.P), Connector(Letter.Q, Letter.R),
                    Connector(Letter.S, Letter.T)
                ),
                type = EnigmaType.ENIGMA_M3
            )
        }
        "C-VI-VII-VIII-Z-R-S-26-8-15-UV-WX-YZ" -> {
            Enigma(
                rotorUnit = RotorUnit(
                    reflector = ReflectorType.REFLECTOR_C.create(),
                    rotors = setOf(
                        RotorType.ROTOR_VI.create(position = Letter.Z, ringSetting = Ring.SETTING_26),
                        RotorType.ROTOR_VII.create(position = Letter.R, ringSetting = Ring.SETTING_8),
                        RotorType.ROTOR_VIII.create(position = Letter.S, ringSetting = Ring.SETTING_15)
                    )
                ),
                plugboard = Plugboard(
                    Connector(Letter.U, Letter.V), Connector(Letter.W, Letter.X), Connector(Letter.Y, Letter.Z)
                ),
                type = EnigmaType.ENIGMA_M3
            )
        }
        "B_THIN-BETA-VI-VII-VIII-E-Z-R-S-10-26-8-15-UV-WX-YZ" -> {
            Enigma(
                rotorUnit = RotorUnit(
                    reflector = ReflectorType.REFLECTOR_B_THIN.create(),
                    rotors = setOf(
                        RotorType.ROTOR_BETA.create(position = Letter.E, ringSetting = Ring.SETTING_10),
                        RotorType.ROTOR_VI.create(position = Letter.Z, ringSetting = Ring.SETTING_26),
                        RotorType.ROTOR_VII.create(position = Letter.R, ringSetting = Ring.SETTING_8),
                        RotorType.ROTOR_VIII.create(position = Letter.S, ringSetting = Ring.SETTING_15)
                    )
                ),
                plugboard = Plugboard(
                    Connector(Letter.U, Letter.V), Connector(Letter.W, Letter.X), Connector(Letter.Y, Letter.Z)
                ),
                type = EnigmaType.ENIGMA_M4
            )
        }
        "C_THIN-GAMMA-VI-VII-VIII-E-Z-R-S-10-26-8-15-UV-WX-YZ" -> {
            Enigma(
                rotorUnit = RotorUnit(
                    reflector = ReflectorType.REFLECTOR_C_THIN.create(),
                    rotors = setOf(
                        RotorType.ROTOR_GAMMA.create(position = Letter.E, ringSetting = Ring.SETTING_10),
                        RotorType.ROTOR_VI.create(position = Letter.Z, ringSetting = Ring.SETTING_26),
                        RotorType.ROTOR_VII.create(position = Letter.R, ringSetting = Ring.SETTING_8),
                        RotorType.ROTOR_VIII.create(position = Letter.S, ringSetting = Ring.SETTING_15)
                    )
                ),
                plugboard = Plugboard(
                    Connector(Letter.U, Letter.V), Connector(Letter.W, Letter.X), Connector(Letter.Y, Letter.Z)
                ),
                type = EnigmaType.ENIGMA_M4
            )
        }
        else -> createStockEnigma()
    }

    @Test
    fun `ensure encipher works with a single character`() {
        val enigma = createStockEnigma()
        assertEquals(
            expected = 'B',
            actual = enigma.encipher('A'),
            message = "Failed to ensure encipher works with a single character."
        )
    }

    @Test
    fun `ensure encipher works with a string`() {
        val enigma = createStockEnigma()
        assertEquals(
            expected = "BDZGO",
            actual = enigma.encipher("AAAAA"),
            message = "Failed to ensure encipher works with a string."
        )
    }

    @TestFactory
    fun `ensure invalid character throws`() = listOf(
        ' ',
        'a',
        '@',
    ).map { char ->
        DynamicTest.dynamicTest("Invalid character '${char}' should throw.") {
            val enigma = createStockEnigma()
            val exception = assertFailsWith<IllegalArgumentException>(
                block = { enigma.encipher(char) },
                message = "Failed to ensure invalid character throws."
            )
            exception.message?.let {
                assertContains(it, "invalid character", ignoreCase = true)
            }
        }
    }

    /**
     * Ciphertext fixtures created using EnigmaZone (https://zoezap.com/enigmazone).
     */
    @TestFactory
    fun `ensure encipher works with different configurations`() = listOf(
        "B-I-II-III-A-A-A-1-1-1",
        "B-I-II-III-Q-E-V-1-1-1",
        "B-I-II-III-A-A-A-5-11-24",
        "B-I-II-III-Q-E-V-5-11-24",
        "B-IV-V-VI-A-B-C-1-2-3",
        "C-VI-VII-VIII-Z-R-S-26-8-15",
        "C-VI-VII-VIII-Z-R-S-26-8-15-AB-CD-EF-GH-IJ-KL-MN-OP-QR-ST",
        "C-VI-VII-VIII-Z-R-S-26-8-15-UV-WX-YZ",
        "B_THIN-BETA-VI-VII-VIII-E-Z-R-S-10-26-8-15-UV-WX-YZ",
        "C_THIN-GAMMA-VI-VII-VIII-E-Z-R-S-10-26-8-15-UV-WX-YZ",
    ).map { fileName ->
        DynamicTest.dynamicTest("Test encipher with configuration: '${fileName}'.") {
            val enigma = createEnigma(fileName)
            val ciphertext = enigma.encipher(plaintext)
            assertEquals(
                expected = ClassLoader.getSystemResource("ciphertexts/${fileName}").readText(),
                actual = ciphertext,
                message = "Failed to ensure encipher works with different configurations."
            )

            val enigma2 = createEnigma(fileName)
            assertEquals(
                expected = plaintext,
                actual = enigma2.encipher(ciphertext),
                message = "Failed to ensure enigma enciphers correctly with different configurations."
            )
        }
    }
}
