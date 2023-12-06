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

    protected fun checkRequirements(enigmaType: EnigmaType, rotorUnit: RotorUnit) {
        require(rotorUnit.rotors.count() == 3) {
            "Invalid rotor count. '${enigmaType}' must have 3 rotors. Given: '${rotorUnit.rotors.count()}'."
        }
        rotorUnit.rotors.forEach { rotor ->
            require(rotor.isCompatible(enigmaType)) {
                "Incompatible rotor. '${rotor.type}' rotor is not compatible with '${enigmaType}'."
            }
        }
        require(rotorUnit.reflector.isCompatible(enigmaType)) {
            "Incompatible reflector. '${rotorUnit.reflector.type}' reflector is not compatible with '${enigmaType}'."
        }
    }

    companion object {
        fun list() : List<String> = entries.map { it.name }
    }
}
