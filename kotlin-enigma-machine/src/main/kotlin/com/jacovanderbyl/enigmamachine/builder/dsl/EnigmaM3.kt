package com.jacovanderbyl.enigmamachine.builder.dsl

import com.jacovanderbyl.enigmamachine.*

class EnigmaM3 : EnigmaContext() {
    @Dsl
    fun singleReflector(init: ReflectorsForEnigmaM3.() -> Unit) : ReflectorsForEnigmaM3 {
        val context = ReflectorsForEnigmaM3()
        context.init()
        reflector = context.getReflector()
        return context
    }

    @Dsl
    fun threeRotors(init: RotorsForEnigmaM3.() -> Unit) : RotorsForEnigmaM3 {
        val context = RotorsForEnigmaM3()
        context.init()
        rotors = context.getRotors()
        return context
    }

    override fun buildEnigma() : Enigma = EnigmaType.ENIGMA_M3.create(
        rotorUnit = RotorUnit(
            reflector = reflector ?: ReflectorType.REFLECTOR_B.create(),
            rotors = rotors ?: setOf(
                RotorType.ROTOR_I.create(),
                RotorType.ROTOR_II.create(),
                RotorType.ROTOR_III.create(),
            )
        ),
        plugboard = Plugboard(*run { connectors ?: setOf() }.toTypedArray())
    )
}
