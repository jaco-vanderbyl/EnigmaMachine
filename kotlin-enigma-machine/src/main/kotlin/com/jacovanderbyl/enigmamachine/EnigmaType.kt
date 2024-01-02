package com.jacovanderbyl.enigmamachine

/**
 * Serves as Enigma Factory.
 */
enum class EnigmaType {
    ENIGMA_I {
        override fun create(rotorUnit: RotorUnit, plugboard: Plugboard) : Enigma {
            requireRotorCount(ENIGMA_I, rotorUnit, count = 3)
            requireCompatibility(ENIGMA_I, rotorUnit)

            return Enigma(ENIGMA_I, rotorUnit, plugboard)
        }
    },
    ENIGMA_M3 {
        override fun create(rotorUnit: RotorUnit, plugboard: Plugboard) : Enigma {
            requireRotorCount(ENIGMA_M3, rotorUnit, count = 3)
            requireCompatibility(ENIGMA_M3, rotorUnit)

            return Enigma(ENIGMA_M3, rotorUnit, plugboard)
        }
    },
    ENIGMA_M4 {
        override fun create(rotorUnit: RotorUnit, plugboard: Plugboard) : Enigma {
            requireRotorCount(ENIGMA_M4, rotorUnit, count = 4)
            requireCompatibility(ENIGMA_M4, rotorUnit)
            requireRotorTypeForM4(
                rotor = rotorUnit.rotors.first(),
                allowedTypes = setOf(RotorType.ROTOR_BETA, RotorType.ROTOR_GAMMA)
            )

            return Enigma(ENIGMA_M4, rotorUnit, plugboard)
        }
    };

    abstract fun create(rotorUnit: RotorUnit, plugboard: Plugboard) : Enigma

    protected fun requireRotorCount(type: EnigmaType, rotorUnit: RotorUnit, count: Int) {
        require(rotorUnit.rotors.count() == count) {
            "Invalid rotor count. '${type}' must have '${count}' rotors. Given: '${rotorUnit.rotors.count()}'."
        }
    }

    protected fun requireCompatibility(type: EnigmaType, rotorUnit: RotorUnit) {
        rotorUnit.rotors.forEach { rotor ->
            require(rotor.isCompatible(type)) {
                "Incompatible rotor. '${rotor.type}' rotor is not compatible with '${type}'."
            }
        }
        require(rotorUnit.reflector.isCompatible(type)) {
            "Incompatible reflector. '${rotorUnit.reflector.type}' reflector is not compatible with '${type}'."
        }
    }

    protected fun requireRotorTypeForM4(rotor: Rotor, allowedTypes: Set<RotorType>) {
        require(rotor.type in allowedTypes) {
            "Invalid rotor type at order index. For enigma type '${ENIGMA_M4}', the rotor type at order index " +
                    "'0' (i.e. the left-most rotor) must be of type: '${allowedTypes}'."
        }
    }

    companion object {
        fun list() : List<String> = entries.map { it.name }
    }
}
