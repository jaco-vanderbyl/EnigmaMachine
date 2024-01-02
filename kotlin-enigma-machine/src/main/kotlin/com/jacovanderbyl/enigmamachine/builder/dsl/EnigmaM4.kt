package com.jacovanderbyl.enigmamachine.builder.dsl

import com.jacovanderbyl.enigmamachine.*

class EnigmaM4 : EnigmaContext() {
    @Dsl
    fun singleReflector(init: ReflectorsForEnigmaM4.() -> Unit) : ReflectorsForEnigmaM4 {
        val context = ReflectorsForEnigmaM4()
        context.init()
        reflector = context.getReflector()
        return context
    }

    @Dsl
    fun fourRotors(init: RotorsForEnigmaM4.() -> Unit) : RotorsForEnigmaM4 {
        val context = RotorsForEnigmaM4()
        context.init()
        rotors = context.getRotors()
        return context
    }

    override fun buildEnigma() : Enigma = EnigmaType.ENIGMA_M4.create(
        rotorUnit = RotorUnit(
            reflector = reflector ?: ReflectorType.REFLECTOR_B_THIN.create(),
            rotors = rotors ?: setOf(
                RotorType.ROTOR_GAMMA.create(),
                RotorType.ROTOR_I.create(),
                RotorType.ROTOR_II.create(),
                RotorType.ROTOR_III.create(),
            )
        ),
        plugboard = Plugboard(*run { connectors ?: setOf() }.toTypedArray())
    )
}
