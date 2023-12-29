package com.jacovanderbyl.enigmamachine.dsl

import com.jacovanderbyl.enigmamachine.ReflectorType

@Dsl
class EnigmaM4Reflectors : SingleReflector() {
    @Dsl fun reflectorBThin() { reflector = ReflectorType.REFLECTOR_B_THIN.create() }
    @Dsl fun reflectorCThin() { reflector = ReflectorType.REFLECTOR_C_THIN.create() }
}
