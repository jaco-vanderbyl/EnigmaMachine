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
    @Test
    fun `ensure 'stock' enigma can encipher a single character`() {
        val enigma = EnigmaFake(RotorUnit(ReflectorB(), setOf(RotorI(), RotorII(), RotorIII())), Plugboard())
        assertEquals(
            message = "Failed to ensure 'stock' enigma can encipher a single character.",
            expected = 'B',
            actual = enigma.encipher('A')
        )
    }

    @Test
    fun `ensure 'stock' enigma can encipher a string`() {
        val enigma = EnigmaFake(RotorUnit(ReflectorB(), setOf(RotorI(), RotorII(), RotorIII())), Plugboard())
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
            val enigma = EnigmaFake(RotorUnit(ReflectorB(), setOf(RotorI(), RotorII(), RotorIII())), Plugboard())
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
            val enigma = buildEnigma(fileName)
            val ciphertext = enigma.encipher(plaintext)
            assertEquals(
                message = "Failed to ensure enigma enciphers correctly with different configurations.",
                expected = ClassLoader.getSystemResource("ciphertexts/${fileName}").readText(),
                actual = ciphertext
            )

            val enigma2 = buildEnigma(fileName)
            val ciphertext2 = enigma2.encipher(ciphertext)
            assertEquals(
                message = "Failed to ensure enigma enciphers correctly with different configurations.",
                expected = plaintext,
                actual = ciphertext2
            )
        }
    }

    private val plaintext = ClassLoader.getSystemResource("./plaintext").readText()

    private fun buildEnigma(fileName: String) : Enigma = when (fileName) {
        "B-I-II-III-A-A-A-1-1-1" -> {
            EnigmaFake(
                rotorUnit = RotorUnit(
                    reflector = ReflectorB(),
                    rotors = setOf(RotorI(), RotorII(), RotorIII())),
                plugboard = Plugboard()
            )
        }
        "B-I-II-III-Q-E-V-1-1-1" -> {
            EnigmaFake(
                rotorUnit = RotorUnit(
                    reflector = ReflectorB(),
                    rotors = setOf(
                        RotorI(position = Position('Q')),
                        RotorII(position = Position('E')),
                        RotorIII(position = Position('V')))
                ),
                Plugboard()
            )
        }
        "B-I-II-III-A-A-A-5-11-24" -> {
            EnigmaFake(
                rotorUnit = RotorUnit(
                    reflector = ReflectorB(),
                    rotors = setOf(
                        RotorI(ringSetting = RingSetting(5)),
                        RotorII(ringSetting = RingSetting(11)),
                        RotorIII(ringSetting = RingSetting(24)))
                ),
                Plugboard()
            )
        }
        "B-I-II-III-Q-E-V-5-11-24" -> {
            EnigmaFake(
                rotorUnit = RotorUnit(
                    reflector = ReflectorB(),
                    rotors = setOf(
                        RotorI(position = Position('Q'), ringSetting = RingSetting(5)),
                        RotorII(position = Position('E'), ringSetting = RingSetting(11)),
                        RotorIII(position = Position('V'), ringSetting = RingSetting(24)))
                ),
                Plugboard())
        }
        "B-IV-V-VI-A-B-C-1-2-3" -> {
            EnigmaFake(
                rotorUnit = RotorUnit(
                    reflector = ReflectorB(),
                    rotors = setOf(
                        RotorIV(),
                        RotorV(position = Position('B'), ringSetting = RingSetting(2)),
                        RotorVI(position = Position('C'), ringSetting = RingSetting(3)))
                ),
                Plugboard()
            )
        }
        "C-VI-VII-VIII-Z-R-S-26-8-15" -> {
            EnigmaFake(
                rotorUnit = RotorUnit(
                    reflector = ReflectorC(),
                    rotors = setOf(
                        RotorVI(position = Position('Z'), ringSetting = RingSetting(26)),
                        RotorVII(position = Position('R'), ringSetting = RingSetting(8)),
                        RotorVIII(position = Position('S'), ringSetting = RingSetting(15)))
                ),
                Plugboard()
            )
        }
        "C-VI-VII-VIII-Z-R-S-26-8-15-AB-CD-EF-GH-IJ-KL-MN-OP-QR-ST" -> {
            EnigmaFake(
                rotorUnit = RotorUnit(
                    reflector = ReflectorC(),
                    rotors = setOf(
                        RotorVI(position = Position('Z'), ringSetting = RingSetting(26)),
                        RotorVII(position = Position('R'), ringSetting = RingSetting(8)),
                        RotorVIII(position = Position('S'), ringSetting = RingSetting(15)))
                ),
                Plugboard(
                    Connector('A', 'B'), Connector('C', 'D'), Connector('E', 'F'), Connector('G', 'H'),
                    Connector('I', 'J'), Connector('K', 'L'), Connector('M', 'N'), Connector('O', 'P'),
                    Connector('Q', 'R'), Connector('S', 'T')
                )
            )
        }
        "C-VI-VII-VIII-Z-R-S-26-8-15-UV-WX-YZ" -> {
            EnigmaFake(
                rotorUnit = RotorUnit(
                    reflector = ReflectorC(),
                    rotors = setOf(
                        RotorVI(position = Position('Z'), ringSetting = RingSetting(26)),
                        RotorVII(position = Position('R'), ringSetting = RingSetting(8)),
                        RotorVIII(position = Position('S'), ringSetting = RingSetting(15)))
                ),
                Plugboard(
                    Connector('U', 'V'), Connector('W', 'X'), Connector('Y', 'Z')
                )
            )
        }
        else -> EnigmaFake(RotorUnit(ReflectorB(), setOf(RotorI(), RotorII(), RotorIII())), Plugboard())
    }
}
