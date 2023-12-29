package com.jacovanderbyl.enigmamachine.dsl

import com.jacovanderbyl.enigmamachine.Position
import com.jacovanderbyl.enigmamachine.RingSetting
import com.jacovanderbyl.enigmamachine.Rotor
import com.jacovanderbyl.enigmamachine.RotorType

@Dsl
open class Rotors {
    protected val rotors = mutableSetOf<Rotor>()
    fun get() : Set<Rotor> = rotors

    fun addRotor(type: RotorType, ringSetting: Int?, position: Char?) {
        rotors.add(type.create(
            ringSetting = if (ringSetting != null) RingSetting(ringSetting) else RingSetting(),
            position = if (position != null) Position(position) else Position(),
        ))
    }
}
