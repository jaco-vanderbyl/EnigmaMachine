package com.jacovanderbyl.enigmamachine

class EnigmaI(rotorUnit: RotorUnit, plugboard: Plugboard) : Enigma(rotorUnit, plugboard) {
    override val name = "Enigma I"

    init {
        require(rotorUnit.rotors.count() == 3) {
            "'${name}' must have 3 rotors. Given: '${rotorUnit.rotors.count()}'."
        }
        rotorUnit.rotors.forEach {
            require(EnigmaFactory.ENIGMA_I in it.compatibility) {
                "'${it.type.name}' rotor is not compatible with '${name}'."
            }
        }
        require(EnigmaFactory.ENIGMA_I in rotorUnit.reflector.compatibility) {
            "'${rotorUnit.reflector.type.name}' reflector is not compatible with '${name}'."
        }
    }
}

class EnigmaM3(rotorUnit: RotorUnit, plugboard: Plugboard) : Enigma(rotorUnit, plugboard) {
    override val name = "Enigma M3"

    init {
        require(rotorUnit.rotors.count() == 3) {
            "'${name}' must have 3 rotors. Given: '${rotorUnit.rotors.count()}'."
        }
        rotorUnit.rotors.forEach {
            require(EnigmaFactory.ENIGMA_M3 in it.compatibility) {
                "'${it.type.name}' rotor is not compatible with '${name}'."
            }
        }
        require(EnigmaFactory.ENIGMA_M3 in rotorUnit.reflector.compatibility) {
            "'${rotorUnit.reflector.type.name}' reflector is not compatible with '${name}'."
        }
    }
}
