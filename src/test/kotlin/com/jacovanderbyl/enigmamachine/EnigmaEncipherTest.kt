package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.Test

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
class EnigmaEncipherTest {
    private val plaintext = ClassLoader.getSystemResource("./plaintext").readText()

    @Test
    fun `ensure 'stock' enigma can encipher a single character`() {
        val enigma = createStockEnigmaFake()
        assertEquals(
            message = "Failed to ensure 'stock' enigma can encipher a single character.",
            expected = 'B',
            actual = enigma.encipher('A')
        )
    }

    @Test
    fun `ensure 'stock' enigma can encipher a string`() {
        val enigma = createStockEnigmaFake()
        assertEquals(
            message = "Failed to ensure 'stock' enigma can encipher a string.",
            expected = "BDZGO",
            actual = enigma.encipher("AAAAA")
        )
    }

    private val invalidCharacters = listOf(' ', 'a', '@')

    @TestFactory
    fun `ensure enigma throws on invalid characters`() = invalidCharacters.map { character ->
        DynamicTest.dynamicTest("Invalid character '${character}' should throw.") {
            val enigma = createStockEnigmaFake()
            assertFailsWith<IllegalArgumentException>(
                message = "Failed to ensure engima throws on invalid characters.",
                block = { enigma.encipher(character) }
            )
        }
    }

    private val ciphertextFileNames = listOf(
        "B-I-II-III-A-A-A-1-1-1",
        "B-I-II-III-Q-E-V-1-1-1",
        "B-I-II-III-A-A-A-5-11-24",
        "B-I-II-III-Q-E-V-5-11-24",
        "B-IV-V-VI-A-B-C-1-2-3",
        "C-VI-VII-VIII-Z-R-S-26-8-15",
        "C-VI-VII-VIII-Z-R-S-26-8-15-AB-CD-EF-GH-IJ-KL-MN-OP-QR-ST",
        "C-VI-VII-VIII-Z-R-S-26-8-15-UV-WX-YZ",
    )

    @TestFactory
    fun `ensure enigma enciphers correctly with different configurations`() = ciphertextFileNames.map { fileName ->
        DynamicTest.dynamicTest("Testing enciphering with file: ${fileName}.") {
            val enigma = createEnigmaFake(fileName)
            val ciphertext = enigma.encipher(plaintext)
            assertEquals(
                message = "Failed to ensure enigma enciphers correctly with different configurations.",
                expected = ClassLoader.getSystemResource("ciphertexts/${fileName}").readText(),
                actual = ciphertext
            )

            val enigma2 = createEnigmaFake(fileName)
            val ciphertext2 = enigma2.encipher(ciphertext)
            assertEquals(
                message = "Failed to ensure enigma enciphers correctly with different configurations.",
                expected = plaintext,
                actual = ciphertext2
            )
        }
    }

    private fun createStockEnigmaFake() : Enigma = Enigma(
        type = EnigmaFactory.ENIGMA_I,
        rotorUnit = RotorUnit(
            reflector = ReflectorFactory.B.create(),
            rotors = setOf(
                RotorFactory.I.create(Position(), RingSetting()),
                RotorFactory.II.create(Position(), RingSetting()),
                RotorFactory.III.create(Position(), RingSetting())
            )
        ),
        plugboard = Plugboard()
    )

    private fun createEnigmaFake(fileName: String) : Enigma = when (fileName) {
        "B-I-II-III-A-A-A-1-1-1" -> {
            Enigma(
                rotorUnit = RotorUnit(
                    reflector = ReflectorFactory.B.create(),
                    rotors = setOf(
                        RotorFactory.I.create(Position(), RingSetting()),
                        RotorFactory.II.create(Position(), RingSetting()),
                        RotorFactory.III.create(Position(), RingSetting())
                    )
                ),
                plugboard = Plugboard(),
                type = EnigmaFactory.ENIGMA_M3,
            )
        }
        "B-I-II-III-Q-E-V-1-1-1" -> {
            Enigma(
                rotorUnit = RotorUnit(
                    reflector = ReflectorFactory.B.create(),
                    rotors = setOf(
                        RotorFactory.I.create(Position('Q'), RingSetting()),
                        RotorFactory.II.create(Position('E'), RingSetting()),
                        RotorFactory.III.create(Position('V'), RingSetting())
                    )
                ),
                plugboard = Plugboard(),
                type = EnigmaFactory.ENIGMA_M3,
            )
        }
        "B-I-II-III-A-A-A-5-11-24" -> {
            Enigma(
                rotorUnit = RotorUnit(
                    reflector = ReflectorFactory.B.create(),
                    rotors = setOf(
                        RotorFactory.I.create(Position(), RingSetting(5)),
                        RotorFactory.II.create(Position(), RingSetting(11)),
                        RotorFactory.III.create(Position(), RingSetting(24))
                    )
                ),
                plugboard = Plugboard(),
                type = EnigmaFactory.ENIGMA_M3,
            )
        }
        "B-I-II-III-Q-E-V-5-11-24" -> {
            Enigma(
                rotorUnit = RotorUnit(
                    reflector = ReflectorFactory.B.create(),
                    rotors = setOf(
                        RotorFactory.I.create(Position('Q'), RingSetting(5)),
                        RotorFactory.II.create(Position('E'), RingSetting(11)),
                        RotorFactory.III.create(Position('V'), RingSetting(24))
                    )
                ),
                plugboard = Plugboard(),
                type = EnigmaFactory.ENIGMA_M3,
            )
        }
        "B-IV-V-VI-A-B-C-1-2-3" -> {
            Enigma(
                rotorUnit = RotorUnit(
                    reflector = ReflectorFactory.B.create(),
                    rotors = setOf(
                        RotorFactory.IV.create(Position(), RingSetting()),
                        RotorFactory.V.create(Position('B'), RingSetting(2)),
                        RotorFactory.VI.create(Position('C'), RingSetting(3))
                    )
                ),
                plugboard = Plugboard(),
                type = EnigmaFactory.ENIGMA_M3,
            )
        }
        "C-VI-VII-VIII-Z-R-S-26-8-15" -> {
            Enigma(
                rotorUnit = RotorUnit(
                    reflector = ReflectorFactory.C.create(),
                    rotors = setOf(
                        RotorFactory.VI.create(Position('Z'), RingSetting(26)),
                        RotorFactory.VII.create(Position('R'), RingSetting(8)),
                        RotorFactory.VIII.create(Position('S'), RingSetting(15))
                    )
                ),
                plugboard = Plugboard(),
                type = EnigmaFactory.ENIGMA_M3,
            )
        }
        "C-VI-VII-VIII-Z-R-S-26-8-15-AB-CD-EF-GH-IJ-KL-MN-OP-QR-ST" -> {
            Enigma(
                rotorUnit = RotorUnit(
                    reflector = ReflectorFactory.C.create(),
                    rotors = setOf(
                        RotorFactory.VI.create(Position('Z'), RingSetting(26)),
                        RotorFactory.VII.create(Position('R'), RingSetting(8)),
                        RotorFactory.VIII.create(Position('S'), RingSetting(15))
                    )
                ),
                plugboard = Plugboard(
                    Connector('A', 'B'), Connector('C', 'D'), Connector('E', 'F'), Connector('G', 'H'),
                    Connector('I', 'J'), Connector('K', 'L'), Connector('M', 'N'), Connector('O', 'P'),
                    Connector('Q', 'R'), Connector('S', 'T')
                ),
                type = EnigmaFactory.ENIGMA_M3,
            )
        }
        "C-VI-VII-VIII-Z-R-S-26-8-15-UV-WX-YZ" -> {
            Enigma(
                rotorUnit = RotorUnit(
                    reflector = ReflectorFactory.C.create(),
                    rotors = setOf(
                        RotorFactory.VI.create(Position('Z'), RingSetting(26)),
                        RotorFactory.VII.create(Position('R'), RingSetting(8)),
                        RotorFactory.VIII.create(Position('S'), RingSetting(15))
                    )
                ),
                plugboard = Plugboard(
                    Connector('U', 'V'), Connector('W', 'X'), Connector('Y', 'Z')
                ),
                type = EnigmaFactory.ENIGMA_M3,
            )
        }
        else -> createStockEnigmaFake()
    }
}
