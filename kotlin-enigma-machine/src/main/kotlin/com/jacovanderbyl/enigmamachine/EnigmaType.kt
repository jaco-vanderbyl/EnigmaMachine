package com.jacovanderbyl.enigmamachine

/**
 * Serves as Enigma Factory.
 */
enum class EnigmaType {
    ENIGMA_I {
        override val rotorCount = 3
        override fun create(rotorUnit: RotorUnit, plugboard: Plugboard) : Enigma {
            requireRotorCount(ENIGMA_I, rotorUnit, rotorCount)
            requireCompatibility(ENIGMA_I, rotorUnit)

            return Enigma(type = ENIGMA_I, rotorUnit = rotorUnit, plugboard = plugboard)
        }
    },
    ENIGMA_M3 {
        override val rotorCount = 3
        override fun create(rotorUnit: RotorUnit, plugboard: Plugboard) : Enigma {
            requireRotorCount(ENIGMA_M3, rotorUnit, rotorCount)
            requireCompatibility(ENIGMA_M3, rotorUnit)

            return Enigma(type = ENIGMA_M3, rotorUnit = rotorUnit, plugboard = plugboard)
        }
    },
    ENIGMA_M4 {
        override val rotorCount = 4
        override fun create(rotorUnit: RotorUnit, plugboard: Plugboard) : Enigma {
            requireRotorCount(ENIGMA_M4, rotorUnit, rotorCount)
            requireCompatibility(ENIGMA_M4, rotorUnit)
            requireRotorTypeForM4(rotorUnit.rotors.first(), setOf(RotorType.ROTOR_BETA, RotorType.ROTOR_GAMMA))

            return Enigma(type = ENIGMA_M4, rotorUnit = rotorUnit, plugboard = plugboard)
        }
    };

    abstract val rotorCount: Int

    abstract fun create(rotorUnit: RotorUnit, plugboard: Plugboard) : Enigma

    protected fun requireRotorCount(enigmaType: EnigmaType, rotorUnit: RotorUnit, count: Int) {
        require(rotorUnit.rotors.count() == count) {
            "Invalid rotor count. '${enigmaType}' must have '${count}' rotors. Given: '${rotorUnit.rotors.count()}'."
        }
    }

    protected fun requireCompatibility(enigmaType: EnigmaType, rotorUnit: RotorUnit) {
        rotorUnit.rotors.forEach { rotor ->
            require(rotor.isCompatible(enigmaType)) {
                "Incompatible rotor. '${rotor.type}' rotor is not compatible with '${enigmaType}'."
            }
        }
        require(rotorUnit.reflector.isCompatible(enigmaType)) {
            "Incompatible reflector. '${rotorUnit.reflector.type}' reflector is not compatible with '${enigmaType}'."
        }
    }

    protected fun requireRotorTypeForM4(rotor: Rotor, requiredTypes: Set<RotorType>) {
        require(rotor.type in requiredTypes) {
            "Invalid rotor type at order index. For enigma type '${ENIGMA_M4}', the rotor type at order index " +
                    "'0' (i.e. the left-most rotor) must be of type: '${requiredTypes}'."
        }
    }

    companion object {
        fun list() : List<String> = entries.map { it.name }
    }
}
