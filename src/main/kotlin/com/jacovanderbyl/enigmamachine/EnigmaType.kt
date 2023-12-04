package com.jacovanderbyl.enigmamachine

/**
 * Serves as Enigma Factory.
 */
enum class EnigmaType {
    ENIGMA_I {
        override fun create(rotorUnit: RotorUnit, plugboard: Plugboard) : Enigma {
            checkRequirements(ENIGMA_I, rotorUnit)
            return Enigma(type = ENIGMA_I, rotorUnit = rotorUnit, plugboard = plugboard)
        }
    },
    ENIGMA_M3 {
        override fun create(rotorUnit: RotorUnit, plugboard: Plugboard) : Enigma {
            checkRequirements(ENIGMA_M3, rotorUnit)
            return Enigma(type = ENIGMA_M3, rotorUnit = rotorUnit, plugboard = plugboard)
        }
    };

    abstract fun create(rotorUnit: RotorUnit, plugboard: Plugboard) : Enigma

    fun checkRequirements(type: EnigmaType, rotorUnit: RotorUnit) {
        require(rotorUnit.rotors.count() == 3) {
            "'${type}' must have 3 rotors. Given: '${rotorUnit.rotors.count()}'."
        }
        rotorUnit.rotors.forEach { rotor ->
            require(type in rotor.compatibility) {
                "'${rotor.type}' rotor is not compatible with '${type}'."
            }
        }
        require(type in rotorUnit.reflector.compatibility) {
            "'${rotorUnit.reflector.type}' reflector is not compatible with '${type}'."
        }
    }
}
