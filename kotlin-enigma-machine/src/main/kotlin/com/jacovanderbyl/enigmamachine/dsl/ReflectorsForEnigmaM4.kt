package com.jacovanderbyl.enigmamachine.dsl

import com.jacovanderbyl.enigmamachine.ReflectorType

class ReflectorsForEnigmaM4 : ReflectorContext() {
    @Dsl fun reflectorBThin() { setReflector(ReflectorType.REFLECTOR_B_THIN.create()) }

    @Dsl fun reflectorCThin() { setReflector(ReflectorType.REFLECTOR_C_THIN.create()) }
}
