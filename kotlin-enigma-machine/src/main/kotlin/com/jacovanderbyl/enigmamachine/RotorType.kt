package com.jacovanderbyl.enigmamachine

/**
 * Serves as Rotor Factory.
 */
enum class RotorType {
    ROTOR_I {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = StepRotor(
            type = ROTOR_I,
            cipherSetMap = CipherSetMap("EKMFLGDQVZNTOWYHXUSPAIBRCJ"),
            notch = Notch(Position('Q')),
            compatibility = setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3, EnigmaType.ENIGMA_M4),
            position = position,
            ringSetting = ringSetting
        )
    },
    ROTOR_II {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = StepRotor(
            type = ROTOR_II,
            cipherSetMap = CipherSetMap("AJDKSIRUXBLHWTMCQGZNPYFVOE"),
            notch = Notch(Position('E')),
            compatibility = setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3, EnigmaType.ENIGMA_M4),
            position = position,
            ringSetting = ringSetting
        )
    },
    ROTOR_III {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = StepRotor(
            type = ROTOR_III,
            cipherSetMap = CipherSetMap("BDFHJLCPRTXVZNYEIWGAKMUSQO"),
            notch = Notch(Position('V')),
            compatibility = setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3, EnigmaType.ENIGMA_M4),
            position = position,
            ringSetting = ringSetting
        )
    },
    ROTOR_IV {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = StepRotor(
            type = ROTOR_IV,
            cipherSetMap = CipherSetMap("ESOVPZJAYQUIRHXLNFTGKDCMWB"),
            notch = Notch(Position('J')),
            compatibility = setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3, EnigmaType.ENIGMA_M4),
            position = position,
            ringSetting = ringSetting
        )
    },
    ROTOR_V {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = StepRotor(
            type = ROTOR_V,
            cipherSetMap = CipherSetMap("VZBRGITYUPSDNHLXAWMJQOFECK"),
            notch = Notch(Position('Z')),
            compatibility = setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3, EnigmaType.ENIGMA_M4),
            position = position,
            ringSetting = ringSetting
        )
    },
    ROTOR_VI {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = StepRotor(
            type = ROTOR_VI,
            cipherSetMap = CipherSetMap("JPGVOUMFYQBENHZRDKASXLICTW"),
            notch = Notch(Position('Z'), Position('M')),
            compatibility = setOf(EnigmaType.ENIGMA_M3, EnigmaType.ENIGMA_M4),
            position = position,
            ringSetting = ringSetting
        )
    },
    ROTOR_VII {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = StepRotor(
            type = ROTOR_VII,
            cipherSetMap = CipherSetMap("NZJHGRCXMYSWBOUFAIVLPEKQDT"),
            notch = Notch(Position('Z'), Position('M')),
            compatibility = setOf(EnigmaType.ENIGMA_M3, EnigmaType.ENIGMA_M4),
            position = position,
            ringSetting = ringSetting
        )
    },
    ROTOR_VIII {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = StepRotor(
            type = ROTOR_VIII,
            cipherSetMap = CipherSetMap("FKQHTLXOCBJSPDZRAMEWNIUYGV"),
            notch = Notch(Position('Z'), Position('M')),
            compatibility = setOf(EnigmaType.ENIGMA_M3, EnigmaType.ENIGMA_M4),
            position = position,
            ringSetting = ringSetting
        )
    },
    ROTOR_BETA {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = Rotor(
            type = ROTOR_BETA,
            cipherSetMap = CipherSetMap("LEYJVCNIXWPBQMDRTAKZGFUHOS"),
            compatibility = setOf(EnigmaType.ENIGMA_M4),
            position = position,
            ringSetting = ringSetting
        )
    },
    ROTOR_GAMMA {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = Rotor(
            type = ROTOR_GAMMA,
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
