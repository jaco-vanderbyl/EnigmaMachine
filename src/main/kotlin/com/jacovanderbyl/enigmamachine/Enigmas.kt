package com.jacovanderbyl.enigmamachine

class EnigmaI(rotorUnit: RotorUnit, plugboard: Plugboard) : Enigma(rotorUnit, plugboard) {
    override val name = "Enigma I"

    init {
        require(rotorUnit.rotors.count() == 3) {
            "'${name}' must have 3 rotors. Given: '${rotorUnit.rotors.count()}'."
        }
        rotorUnit.rotors.forEach {
            require(it is CompatibleWithEnigmaI) {
                "'${it.javaClass.kotlin.simpleName}' rotor is not compatible with '${name}'."
            }
        }
        require(rotorUnit.reflector is CompatibleWithEnigmaI) {
            "'${rotorUnit.reflector.javaClass.kotlin.simpleName}' reflector is not compatible with '${name}'."
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
            require(it is CompatibleWithEnigmaM3) {
                "'${it.javaClass.kotlin.simpleName}' rotor is not compatible with '${name}'."
            }
        }
        require(rotorUnit.reflector is CompatibleWithEnigmaM3) {
            "'${rotorUnit.reflector.javaClass.kotlin.simpleName}' reflector is not compatible with '${name}'."
        }
    }
}
