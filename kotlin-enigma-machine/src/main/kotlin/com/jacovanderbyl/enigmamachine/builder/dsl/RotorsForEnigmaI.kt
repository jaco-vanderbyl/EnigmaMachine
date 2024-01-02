package com.jacovanderbyl.enigmamachine.builder.dsl

import com.jacovanderbyl.enigmamachine.Letter
import com.jacovanderbyl.enigmamachine.Rotor
import com.jacovanderbyl.enigmamachine.RotorType

class RotorsForEnigmaI : RotorContext() {
    @Dsl
    fun rotorI(ringSetting: Rotor.Ring? = null, position: Letter? = null) = addRotor(
        RotorType.ROTOR_I, ringSetting, position
    )

    @Dsl
    fun rotorII(ringSetting: Rotor.Ring? = null, position: Letter? = null) = addRotor(
        RotorType.ROTOR_II, ringSetting, position
    )

    @Dsl
    fun rotorIII(ringSetting: Rotor.Ring? = null, position: Letter? = null) = addRotor(
        RotorType.ROTOR_III, ringSetting, position
    )

    @Dsl
    fun rotorIV(ringSetting: Rotor.Ring? = null, position: Letter? = null) = addRotor(
        RotorType.ROTOR_IV, ringSetting, position
    )

    @Dsl
    fun rotorV(ringSetting: Rotor.Ring? = null, position: Letter? = null) = addRotor(
        RotorType.ROTOR_V, ringSetting, position
    )
}
