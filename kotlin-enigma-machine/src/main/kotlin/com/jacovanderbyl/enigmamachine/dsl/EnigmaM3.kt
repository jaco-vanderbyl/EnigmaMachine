package com.jacovanderbyl.enigmamachine.dsl

@Dsl
class EnigmaM3 : Enigma() {
    @Dsl fun singleReflector(init: EnigmaM3Reflectors.() -> Unit) : EnigmaM3Reflectors {
        val enigmaM3Reflectors = EnigmaM3Reflectors()
        enigmaM3Reflectors.init()
        reflector = enigmaM3Reflectors.get()
        return enigmaM3Reflectors
    }

    @Dsl fun threeRotors(init: EnigmaM3Rotors.() -> Unit) : EnigmaM3Rotors {
        val enigmaM3Rotors = EnigmaM3Rotors()
        enigmaM3Rotors.init()
        rotors.addAll(enigmaM3Rotors.get())
        return enigmaM3Rotors
    }
}
