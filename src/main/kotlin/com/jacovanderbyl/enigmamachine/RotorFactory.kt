package com.jacovanderbyl.enigmamachine

enum class RotorFactory {
    I {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = Rotor(
            cipherSetMap = CipherSetMap("EKMFLGDQVZNTOWYHXUSPAIBRCJ"),
            notch = Notch(setOf('Q')),
            type = RotorFactory.I,
            compatibility = setOf(EnigmaFactory.ENIGMA_I, EnigmaFactory.ENIGMA_M3),
            position = position,
            ringSetting = ringSetting
        )
    },
    II {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = Rotor(
            cipherSetMap = CipherSetMap("AJDKSIRUXBLHWTMCQGZNPYFVOE"),
            notch = Notch(setOf('E')),
            type = RotorFactory.II,
            compatibility = setOf(EnigmaFactory.ENIGMA_I, EnigmaFactory.ENIGMA_M3),
            position = position,
            ringSetting = ringSetting
        )
    },
    III {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = Rotor(
            cipherSetMap = CipherSetMap("BDFHJLCPRTXVZNYEIWGAKMUSQO"),
            notch = Notch(setOf('V')),
            type = RotorFactory.III,
            compatibility = setOf(EnigmaFactory.ENIGMA_I, EnigmaFactory.ENIGMA_M3),
            position = position,
            ringSetting = ringSetting
        )
    },
    IV {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = Rotor(
            cipherSetMap = CipherSetMap("ESOVPZJAYQUIRHXLNFTGKDCMWB"),
            notch = Notch(setOf('J')),
            type = RotorFactory.IV,
            compatibility = setOf(EnigmaFactory.ENIGMA_I, EnigmaFactory.ENIGMA_M3),
            position = position,
            ringSetting = ringSetting
        )
    },
    V {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = Rotor(
            cipherSetMap = CipherSetMap("VZBRGITYUPSDNHLXAWMJQOFECK"),
            notch = Notch(setOf('Z')),
            type = RotorFactory.V,
            compatibility = setOf(EnigmaFactory.ENIGMA_I, EnigmaFactory.ENIGMA_M3),
            position = position,
            ringSetting = ringSetting
        )
    },
    VI {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = Rotor(
            cipherSetMap = CipherSetMap("JPGVOUMFYQBENHZRDKASXLICTW"),
            notch = Notch(setOf('Z', 'M')),
            type = RotorFactory.VI,
            compatibility = setOf(EnigmaFactory.ENIGMA_M3),
            position = position,
            ringSetting = ringSetting
        )
    },
    VII {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = Rotor(
            cipherSetMap = CipherSetMap("NZJHGRCXMYSWBOUFAIVLPEKQDT"),
            notch = Notch(setOf('Z', 'M')),
            type = RotorFactory.VII,
            compatibility = setOf(EnigmaFactory.ENIGMA_M3),
            position = position,
            ringSetting = ringSetting
        )
    },
    VIII {
        override fun create(position: Position, ringSetting: RingSetting) : Rotor = Rotor(
            cipherSetMap = CipherSetMap("FKQHTLXOCBJSPDZRAMEWNIUYGV"),
            notch = Notch(setOf('Z', 'M')),
            type = RotorFactory.VIII,
            compatibility = setOf(EnigmaFactory.ENIGMA_M3),
            position = position,
            ringSetting = ringSetting
        )
    };

    abstract fun create(position: Position, ringSetting: RingSetting) : Rotor
}
