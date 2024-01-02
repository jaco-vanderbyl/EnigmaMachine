package com.jacovanderbyl.enigmamachine.builder.dsl

import com.jacovanderbyl.enigmamachine.Letter
import com.jacovanderbyl.enigmamachine.Rotor
import com.jacovanderbyl.enigmamachine.RotorType

@Dsl
abstract class RotorContext {
    private val rotors = mutableSetOf<Rotor>()

    fun getRotors() : Set<Rotor>? = if (rotors.isEmpty()) null else rotors.toSet()

    protected fun addRotor(type: RotorType, ringSetting: Rotor.Ring?, position: Letter?) {
        rotors.add(type.create(
            ringSetting = ringSetting ?: Rotor.Ring.SETTING_1,
            position = position ?: Letter.A,
        ))
    }
}
