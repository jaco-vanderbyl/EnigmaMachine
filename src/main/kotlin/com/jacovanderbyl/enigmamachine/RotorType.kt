package com.jacovanderbyl.enigmamachine

/**
 * Serves as Rotor Factory.
 */
enum class RotorType {
    I {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = Rotor(
            type = RotorType.I,
            cipherSetMap = CipherSetMap("EKMFLGDQVZNTOWYHXUSPAIBRCJ"),
            notch = Notch(setOf('Q')),
            compatibility = setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3),
            position = position,
            ringSetting = ringSetting
        )
    },
    II {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = Rotor(
            type = RotorType.II,
            cipherSetMap = CipherSetMap("AJDKSIRUXBLHWTMCQGZNPYFVOE"),
            notch = Notch(setOf('E')),
            compatibility = setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3),
            position = position,
            ringSetting = ringSetting
        )
    },
    III {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = Rotor(
            type = RotorType.III,
            cipherSetMap = CipherSetMap("BDFHJLCPRTXVZNYEIWGAKMUSQO"),
            notch = Notch(setOf('V')),
            compatibility = setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3),
            position = position,
            ringSetting = ringSetting
        )
    },
    IV {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = Rotor(
            type = RotorType.IV,
            cipherSetMap = CipherSetMap("ESOVPZJAYQUIRHXLNFTGKDCMWB"),
            notch = Notch(setOf('J')),
            compatibility = setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3),
            position = position,
            ringSetting = ringSetting
        )
    },
    V {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = Rotor(
            type = RotorType.V,
            cipherSetMap = CipherSetMap("VZBRGITYUPSDNHLXAWMJQOFECK"),
            notch = Notch(setOf('Z')),
            compatibility = setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3),
            position = position,
            ringSetting = ringSetting
        )
    },
    VI {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = Rotor(
            type = RotorType.VI,
            cipherSetMap = CipherSetMap("JPGVOUMFYQBENHZRDKASXLICTW"),
            notch = Notch(setOf('Z', 'M')),
            compatibility = setOf(EnigmaType.ENIGMA_M3),
            position = position,
            ringSetting = ringSetting
        )
    },
    VII {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = Rotor(
            type = RotorType.VII,
            cipherSetMap = CipherSetMap("NZJHGRCXMYSWBOUFAIVLPEKQDT"),
            notch = Notch(setOf('Z', 'M')),
            compatibility = setOf(EnigmaType.ENIGMA_M3),
            position = position,
            ringSetting = ringSetting
        )
    },
    VIII {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = Rotor(
            type = RotorType.VIII,
            cipherSetMap = CipherSetMap("FKQHTLXOCBJSPDZRAMEWNIUYGV"),
            notch = Notch(setOf('Z', 'M')),
            compatibility = setOf(EnigmaType.ENIGMA_M3),
            position = position,
            ringSetting = ringSetting
        )
    };

    abstract fun create(position: Position, ringSetting: RingSetting) : Rotor
}
