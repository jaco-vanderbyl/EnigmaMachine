package com.jacovanderbyl.enigmamachine

class EnigmaFake(rotorUnit: RotorUnit, plugboard: Plugboard) : Enigma(rotorUnit, plugboard) {
    override val name = "Fake Enigma"
}
