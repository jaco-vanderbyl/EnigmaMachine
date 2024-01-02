package com.jacovanderbyl.enigmamachine.builder.dsl

import com.jacovanderbyl.enigmamachine.Letter
import com.jacovanderbyl.enigmamachine.Ring
import com.jacovanderbyl.enigmamachine.RotorType

class RotorsForEnigmaI : RotorContext() {
    @Dsl
    fun rotorI(ringSetting: Ring? = null, position: Letter? = null) = addRotor(
        RotorType.ROTOR_I, ringSetting, position
    )

    @Dsl
    fun rotorII(ringSetting: Ring? = null, position: Letter? = null) = addRotor(
        RotorType.ROTOR_II, ringSetting, position
    )

    @Dsl
    fun rotorIII(ringSetting: Ring? = null, position: Letter? = null) = addRotor(
        RotorType.ROTOR_III, ringSetting, position
    )

    @Dsl
    fun rotorIV(ringSetting: Ring? = null, position: Letter? = null) = addRotor(
        RotorType.ROTOR_IV, ringSetting, position
    )

    @Dsl
    fun rotorV(ringSetting: Ring? = null, position: Letter? = null) = addRotor(
        RotorType.ROTOR_V, ringSetting, position
    )
}
