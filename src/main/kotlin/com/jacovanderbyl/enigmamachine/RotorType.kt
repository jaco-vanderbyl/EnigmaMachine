package com.jacovanderbyl.enigmamachine

/**
 * Serves as Rotor Factory.
 */
enum class RotorType {
    I {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = Rotor(
            cipherSetMap = CipherSetMap("EKMFLGDQVZNTOWYHXUSPAIBRCJ"),
            notch = Notch(setOf('Q')),
            type = RotorType.I,
            compatibility = setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3),
            position = position,
            ringSetting = ringSetting
        )
    },
    II {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = Rotor(
            cipherSetMap = CipherSetMap("AJDKSIRUXBLHWTMCQGZNPYFVOE"),
            notch = Notch(setOf('E')),
            type = RotorType.II,
            compatibility = setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3),
            position = position,
            ringSetting = ringSetting
        )
    },
    III {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = Rotor(
            cipherSetMap = CipherSetMap("BDFHJLCPRTXVZNYEIWGAKMUSQO"),
            notch = Notch(setOf('V')),
            type = RotorType.III,
            compatibility = setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3),
            position = position,
            ringSetting = ringSetting
        )
    },
    IV {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = Rotor(
            cipherSetMap = CipherSetMap("ESOVPZJAYQUIRHXLNFTGKDCMWB"),
            notch = Notch(setOf('J')),
            type = RotorType.IV,
            compatibility = setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3),
            position = position,
            ringSetting = ringSetting
        )
    },
    V {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = Rotor(
            cipherSetMap = CipherSetMap("VZBRGITYUPSDNHLXAWMJQOFECK"),
            notch = Notch(setOf('Z')),
            type = RotorType.V,
            compatibility = setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3),
            position = position,
            ringSetting = ringSetting
        )
    },
    VI {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = Rotor(
            cipherSetMap = CipherSetMap("JPGVOUMFYQBENHZRDKASXLICTW"),
            notch = Notch(setOf('Z', 'M')),
            type = RotorType.VI,
            compatibility = setOf(EnigmaType.ENIGMA_M3),
            position = position,
            ringSetting = ringSetting
        )
    },
    VII {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = Rotor(
            cipherSetMap = CipherSetMap("NZJHGRCXMYSWBOUFAIVLPEKQDT"),
            notch = Notch(setOf('Z', 'M')),
            type = RotorType.VII,
            compatibility = setOf(EnigmaType.ENIGMA_M3),
            position = position,
            ringSetting = ringSetting
        )
    },
    VIII {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = Rotor(
            cipherSetMap = CipherSetMap("FKQHTLXOCBJSPDZRAMEWNIUYGV"),
            notch = Notch(setOf('Z', 'M')),
            type = RotorType.VIII,
            compatibility = setOf(EnigmaType.ENIGMA_M3),
            position = position,
            ringSetting = ringSetting
        )
    };

    abstract fun create(position: Position, ringSetting: RingSetting) : Rotor
}
