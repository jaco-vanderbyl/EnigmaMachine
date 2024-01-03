package com.jacovanderbyl.enigmamachine

/**
 * Serves as the Enigma Factory.
 */
enum class EnigmaType {
    ENIGMA_I {
        override fun create(reflector: Reflector, rotors: Set<Rotor>, plugboard: Plugboard) : Enigma {
            requireRotorCount(ENIGMA_I, rotors, count = 3)
            requireNoDuplicateRotors(rotors)
            requireCompatibility(ENIGMA_I, reflector, rotors)

            return Enigma(ENIGMA_I, reflector, rotors, plugboard)
        }
    },
    ENIGMA_M3 {
        override fun create(reflector: Reflector, rotors: Set<Rotor>, plugboard: Plugboard) : Enigma {
            requireRotorCount(ENIGMA_M3, rotors, count = 3)
            requireNoDuplicateRotors(rotors)
            requireCompatibility(ENIGMA_M3, reflector, rotors)

            return Enigma(ENIGMA_M3, reflector, rotors, plugboard)
        }
    },
    ENIGMA_M4 {
        override fun create(reflector: Reflector, rotors: Set<Rotor>, plugboard: Plugboard) : Enigma {
            requireRotorCount(ENIGMA_M4, rotors, count = 4)
            requireNoDuplicateRotors(rotors)
            requireCompatibility(ENIGMA_M4, reflector, rotors)
            requireRotorTypeForM4(
                rotor = rotors.first(),
                allowedTypes = setOf(RotorType.ROTOR_BETA, RotorType.ROTOR_GAMMA)
            )

            return Enigma(ENIGMA_M4, reflector, rotors, plugboard)
        }
    };

    abstract fun create(reflector: Reflector, rotors: Set<Rotor>, plugboard: Plugboard) : Enigma

    protected fun requireRotorCount(type: EnigmaType, rotors: Set<Rotor>, count: Int) {
        require(rotors.count() == count) {
            "Invalid rotor count. '${type}' must have '${count}' rotors. Given: '${rotors.count()}'."
        }
    }

    protected fun requireNoDuplicateRotors(rotors: Set<Rotor>) {
        require(rotors.none { rotor -> rotors.count { it.type == rotor.type } > 1 }) {
            "Duplicate rotor types are not allowed. Given: ${rotors.map { it.type }}."
        }
    }

    protected fun requireCompatibility(type: EnigmaType, reflector: Reflector, rotors: Set<Rotor>) {
        rotors.forEach { rotor ->
            require(rotor.isCompatible(type)) {
                "Incompatible rotor. '${rotor.type}' rotor is not compatible with '${type}'."
            }
        }
        require(reflector.isCompatible(type)) {
            "Incompatible reflector. '${reflector.type}' reflector is not compatible with '${type}'."
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
