package com.jacovanderbyl.enigmamachine.dsl

@Dsl
class EnigmaI : Enigma() {
    @Dsl fun singleReflector(init: EnigmaIReflectors.() -> Unit) : EnigmaIReflectors {
        val enigmaIReflectors = EnigmaIReflectors()
        enigmaIReflectors.init()
        reflector = enigmaIReflectors.get()
        return enigmaIReflectors
    }

    @Dsl fun threeRotors(init: EnigmaIRotors.() -> Unit) : EnigmaIRotors {
        val enigmaIRotors = EnigmaIRotors()
        enigmaIRotors.init()
        rotors.addAll(enigmaIRotors.get())
        return enigmaIRotors
    }
}
