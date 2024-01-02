package com.jacovanderbyl.enigmamachine.builder.dsl

import com.jacovanderbyl.enigmamachine.*

class EnigmaI : EnigmaContext() {
    @Dsl
    fun singleReflector(init: ReflectorsForEnigmaI.() -> Unit) : ReflectorsForEnigmaI {
        val context = ReflectorsForEnigmaI()
        context.init()
        reflector = context.getReflector()
        return context
    }

    @Dsl
    fun threeRotors(init: RotorsForEnigmaI.() -> Unit) : RotorsForEnigmaI {
        val context = RotorsForEnigmaI()
        context.init()
        rotors = context.getRotors()
        return context
    }

    override fun buildEnigma() : Enigma = EnigmaType.ENIGMA_I.create(
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
