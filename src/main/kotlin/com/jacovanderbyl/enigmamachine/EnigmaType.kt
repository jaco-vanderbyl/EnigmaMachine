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

    fun checkRequirements(enigmaType: EnigmaType, rotorUnit: RotorUnit) {
        require(rotorUnit.rotors.count() == 3) {
            "'${enigmaType}' must have 3 rotors. Given: '${rotorUnit.rotors.count()}'."
        }
        rotorUnit.rotors.forEach { rotor ->
            require(rotor.isCompatible(enigmaType)) {
                "'${rotor.type}' rotor is not compatible with '${enigmaType}'."
            }
        }
        require(rotorUnit.reflector.isCompatible(enigmaType)) {
            "'${rotorUnit.reflector.type}' reflector is not compatible with '${enigmaType}'."
        }
    }
}
