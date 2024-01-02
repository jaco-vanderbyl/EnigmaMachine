package com.jacovanderbyl.enigmamachine.builder.dsl

import com.jacovanderbyl.enigmamachine.ReflectorType

class ReflectorsForEnigmaI : ReflectorContext() {
    @Dsl
    fun reflectorB() { setReflector(ReflectorType.REFLECTOR_B.create()) }

    @Dsl
    fun reflectorC() { setReflector(ReflectorType.REFLECTOR_C.create()) }
}
