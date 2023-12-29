package com.jacovanderbyl.enigmamachine.dsl

import com.jacovanderbyl.enigmamachine.RotorType

@Dsl
class EnigmaIRotors : Rotors() {
    @Dsl fun rotorI(ringSetting: Int? = null, position: Char? = null) = addRotor(
        RotorType.ROTOR_I, ringSetting, position
    )
    @Dsl fun rotorII(ringSetting: Int? = null, position: Char? = null) = addRotor(
        RotorType.ROTOR_II, ringSetting, position
    )
    @Dsl fun rotorIII(ringSetting: Int? = null, position: Char? = null) = addRotor(
        RotorType.ROTOR_III, ringSetting, position
    )
    @Dsl fun rotorIV(ringSetting: Int? = null, position: Char? = null) = addRotor(
        RotorType.ROTOR_IV, ringSetting, position
    )
    @Dsl fun rotorV(ringSetting: Int? = null, position: Char? = null) = addRotor(
        RotorType.ROTOR_V, ringSetting, position
    )
}
