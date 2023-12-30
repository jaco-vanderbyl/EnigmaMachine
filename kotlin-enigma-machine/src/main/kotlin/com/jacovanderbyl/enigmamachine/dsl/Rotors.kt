package com.jacovanderbyl.enigmamachine.dsl

import com.jacovanderbyl.enigmamachine.*

@Dsl
open class Rotors {
    protected val rotors = mutableSetOf<Rotor>()
    fun get() : Set<Rotor> = rotors

    fun addRotor(type: RotorType, ringSetting: Ring?, position: Letter?) {
        rotors.add(type.create(
            ringSetting = ringSetting ?: Ring.SETTING_1,
            position = position ?: Letter.A,
        ))
    }
}
