package com.jacovanderbyl.enigmamachine

enum class EnigmaFactory {
    ENIGMA_I { override fun create(rotorUnit: RotorUnit, plugboard: Plugboard) = EnigmaI(rotorUnit, plugboard) },
    ENIGMA_M3 { override fun create(rotorUnit: RotorUnit, plugboard: Plugboard) = EnigmaM3(rotorUnit, plugboard) };

    abstract fun create(rotorUnit: RotorUnit, plugboard: Plugboard) : Enigma
}
