package com.jacovanderbyl.enigmamachine.dsl

import com.jacovanderbyl.enigmamachine.ReflectorType

@Dsl
class EnigmaM3Reflectors : SingleReflector() {
    @Dsl fun reflectorB() { reflector = ReflectorType.REFLECTOR_B.create() }
    @Dsl fun reflectorC() { reflector = ReflectorType.REFLECTOR_C.create() }
}
