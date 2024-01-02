package com.jacovanderbyl.enigmamachine

/**
 * Serves as the Rotor Factory.
 */
enum class RotorType {
    ROTOR_I {
        override fun create(position: Letter, ringSetting: Rotor.Ring) = Rotor.StepRotor(
            type = ROTOR_I,
            cipherSetMap = CipherSetMap("EKMFLGDQVZNTOWYHXUSPAIBRCJ"),
            notchPositions = setOf(Letter.Q),
            compatibility = setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3, EnigmaType.ENIGMA_M4),
            position = position,
            ringSetting = ringSetting
        )
    },
    ROTOR_II {
        override fun create(position: Letter, ringSetting: Rotor.Ring) = Rotor.StepRotor(
            type = ROTOR_II,
            cipherSetMap = CipherSetMap("AJDKSIRUXBLHWTMCQGZNPYFVOE"),
            notchPositions = setOf(Letter.E),
            compatibility = setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3, EnigmaType.ENIGMA_M4),
            position = position,
            ringSetting = ringSetting
        )
    },
    ROTOR_III {
        override fun create(position: Letter, ringSetting: Rotor.Ring) = Rotor.StepRotor(
            type = ROTOR_III,
            cipherSetMap = CipherSetMap("BDFHJLCPRTXVZNYEIWGAKMUSQO"),
            notchPositions = setOf(Letter.V),
            compatibility = setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3, EnigmaType.ENIGMA_M4),
            position = position,
            ringSetting = ringSetting
        )
    },
    ROTOR_IV {
        override fun create(position: Letter, ringSetting: Rotor.Ring) = Rotor.StepRotor(
            type = ROTOR_IV,
            cipherSetMap = CipherSetMap("ESOVPZJAYQUIRHXLNFTGKDCMWB"),
            notchPositions = setOf(Letter.J),
            compatibility = setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3, EnigmaType.ENIGMA_M4),
            position = position,
            ringSetting = ringSetting
        )
    },
    ROTOR_V {
        override fun create(position: Letter, ringSetting: Rotor.Ring) = Rotor.StepRotor(
            type = ROTOR_V,
            cipherSetMap = CipherSetMap("VZBRGITYUPSDNHLXAWMJQOFECK"),
            notchPositions = setOf(Letter.Z),
            compatibility = setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3, EnigmaType.ENIGMA_M4),
            position = position,
            ringSetting = ringSetting
        )
    },
    ROTOR_VI {
        override fun create(position: Letter, ringSetting: Rotor.Ring) = Rotor.StepRotor(
            type = ROTOR_VI,
            cipherSetMap = CipherSetMap("JPGVOUMFYQBENHZRDKASXLICTW"),
            notchPositions = setOf(Letter.Z, Letter.M),
            compatibility = setOf(EnigmaType.ENIGMA_M3, EnigmaType.ENIGMA_M4),
            position = position,
            ringSetting = ringSetting
        )
    },
    ROTOR_VII {
        override fun create(position: Letter, ringSetting: Rotor.Ring) = Rotor.StepRotor(
            type = ROTOR_VII,
            cipherSetMap = CipherSetMap("NZJHGRCXMYSWBOUFAIVLPEKQDT"),
            notchPositions = setOf(Letter.Z, Letter.M),
            compatibility = setOf(EnigmaType.ENIGMA_M3, EnigmaType.ENIGMA_M4),
            position = position,
            ringSetting = ringSetting
        )
    },
    ROTOR_VIII {
        override fun create(position: Letter, ringSetting: Rotor.Ring) = Rotor.StepRotor(
            type = ROTOR_VIII,
            cipherSetMap = CipherSetMap("FKQHTLXOCBJSPDZRAMEWNIUYGV"),
            notchPositions = setOf(Letter.Z, Letter.M),
            compatibility = setOf(EnigmaType.ENIGMA_M3, EnigmaType.ENIGMA_M4),
            position = position,
            ringSetting = ringSetting
        )
    },
    ROTOR_BETA {
        override fun create(position: Letter, ringSetting: Rotor.Ring) = Rotor.FixedRotor(
            type = ROTOR_BETA,
            cipherSetMap = CipherSetMap("LEYJVCNIXWPBQMDRTAKZGFUHOS"),
            compatibility = setOf(EnigmaType.ENIGMA_M4),
            position = position,
            ringSetting = ringSetting
        )
    },
    ROTOR_GAMMA {
        override fun create(position: Letter, ringSetting: Rotor.Ring) = Rotor.FixedRotor(
            type = ROTOR_GAMMA,
            cipherSetMap = CipherSetMap("FSOKANUERHMBTIYCWLQPZXVGJD"),
            compatibility = setOf(EnigmaType.ENIGMA_M4),
            position = position,
            ringSetting = ringSetting
        )
    };

    abstract fun create(position: Letter = Letter.A, ringSetting: Rotor.Ring = Rotor.Ring.SETTING_1) : Rotor

    companion object {
        fun list() : List<String> = entries.map { it.name }
    }
}
