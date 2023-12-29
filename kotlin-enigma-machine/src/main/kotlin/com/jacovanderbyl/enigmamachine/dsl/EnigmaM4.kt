package com.jacovanderbyl.enigmamachine.dsl

@Dsl
class EnigmaM4 : Enigma() {
    @Dsl fun singleReflector(init: EnigmaM4Reflectors.() -> Unit) : EnigmaM4Reflectors {
        val enigmaM4Reflectors = EnigmaM4Reflectors()
        enigmaM4Reflectors.init()
        reflector = enigmaM4Reflectors.get()
        return enigmaM4Reflectors
    }

    @Dsl fun fourRotors(init: EnigmaM4Rotors.() -> Unit) : EnigmaM4Rotors {
        val enigmaM4Rotors = EnigmaM4Rotors()
        enigmaM4Rotors.init()
        rotors.addAll(enigmaM4Rotors.get())
        return enigmaM4Rotors
    }
}
