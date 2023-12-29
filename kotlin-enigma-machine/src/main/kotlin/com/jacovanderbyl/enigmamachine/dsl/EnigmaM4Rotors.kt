package com.jacovanderbyl.enigmamachine.dsl

import com.jacovanderbyl.enigmamachine.RotorType

@Dsl
class EnigmaM4Rotors : Rotors() {
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
    @Dsl fun rotorVI(ringSetting: Int? = null, position: Char? = null) = addRotor(
        RotorType.ROTOR_VI, ringSetting, position
    )
    @Dsl fun rotorVII(ringSetting: Int? = null, position: Char? = null) = addRotor(
        RotorType.ROTOR_VII, ringSetting, position
    )
    @Dsl fun rotorVIII(ringSetting: Int? = null, position: Char? = null) = addRotor(
        RotorType.ROTOR_VIII, ringSetting, position
    )
    @Dsl fun rotorBeta(ringSetting: Int? = null, position: Char? = null) = addRotor(
        RotorType.ROTOR_BETA, ringSetting, position
    )
    @Dsl fun rotorGamma(ringSetting: Int? = null, position: Char? = null) = addRotor(
        RotorType.ROTOR_GAMMA, ringSetting, position
    )
}
