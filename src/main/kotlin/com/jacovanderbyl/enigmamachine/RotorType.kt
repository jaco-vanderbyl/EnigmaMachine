package com.jacovanderbyl.enigmamachine

/**
 * Serves as Rotor Factory.
 */
enum class RotorType {
    I {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = StepRotor(
            type = I,
            cipherSetMap = CipherSetMap("EKMFLGDQVZNTOWYHXUSPAIBRCJ"),
            compatibility = setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3, EnigmaType.ENIGMA_M4),
            position = position,
            ringSetting = ringSetting,
            notch = Notch(Position('Q'))
        )
    },
    II {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = StepRotor(
            type = II,
            cipherSetMap = CipherSetMap("AJDKSIRUXBLHWTMCQGZNPYFVOE"),
            compatibility = setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3, EnigmaType.ENIGMA_M4),
            position = position,
            ringSetting = ringSetting,
            notch = Notch(Position('E'))
        )
    },
    III {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = StepRotor(
            type = III,
            cipherSetMap = CipherSetMap("BDFHJLCPRTXVZNYEIWGAKMUSQO"),
            compatibility = setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3, EnigmaType.ENIGMA_M4),
            position = position,
            ringSetting = ringSetting,
            notch = Notch(Position('V'))
        )
    },
    IV {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = StepRotor(
            type = IV,
            cipherSetMap = CipherSetMap("ESOVPZJAYQUIRHXLNFTGKDCMWB"),
            compatibility = setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3, EnigmaType.ENIGMA_M4),
            position = position,
            ringSetting = ringSetting,
            notch = Notch(Position('J'))
        )
    },
    V {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = StepRotor(
            type = V,
            cipherSetMap = CipherSetMap("VZBRGITYUPSDNHLXAWMJQOFECK"),
            compatibility = setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3, EnigmaType.ENIGMA_M4),
            position = position,
            ringSetting = ringSetting,
            notch = Notch(Position('Z'))
        )
    },
    VI {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = StepRotor(
            type = VI,
            cipherSetMap = CipherSetMap("JPGVOUMFYQBENHZRDKASXLICTW"),
            compatibility = setOf(EnigmaType.ENIGMA_M3, EnigmaType.ENIGMA_M4),
            position = position,
            ringSetting = ringSetting,
            notch = Notch(Position('Z'), Position('M'))
        )
    },
    VII {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = StepRotor(
            type = VII,
            cipherSetMap = CipherSetMap("NZJHGRCXMYSWBOUFAIVLPEKQDT"),
            compatibility = setOf(EnigmaType.ENIGMA_M3, EnigmaType.ENIGMA_M4),
            position = position,
            ringSetting = ringSetting,
            notch = Notch(Position('Z'), Position('M'))
        )
    },
    VIII {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = StepRotor(
            type = VIII,
            cipherSetMap = CipherSetMap("FKQHTLXOCBJSPDZRAMEWNIUYGV"),
            compatibility = setOf(EnigmaType.ENIGMA_M3, EnigmaType.ENIGMA_M4),
            position = position,
            ringSetting = ringSetting,
            notch = Notch(Position('Z'), Position('M'))
        )
    },
    BETA {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = Rotor(
            type = BETA,
            cipherSetMap = CipherSetMap("LEYJVCNIXWPBQMDRTAKZGFUHOS"),
            compatibility = setOf(EnigmaType.ENIGMA_M4),
            position = position,
            ringSetting = ringSetting
        )
    },
    GAMMA {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = Rotor(
            type = GAMMA,
            cipherSetMap = CipherSetMap("FSOKANUERHMBTIYCWLQPZXVGJD"),
            compatibility = setOf(EnigmaType.ENIGMA_M4),
            position = position,
            ringSetting = ringSetting
        )
    };

    abstract fun create(position: Position = Position(), ringSetting: RingSetting = RingSetting()) : Rotor

    companion object {
        fun list() : List<String> = entries.map { it.name }
    }
}
