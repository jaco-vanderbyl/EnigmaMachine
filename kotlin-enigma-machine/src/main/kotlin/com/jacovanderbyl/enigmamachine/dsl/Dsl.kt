package com.jacovanderbyl.enigmamachine.dsl

import com.jacovanderbyl.enigmamachine.Enigma
import com.jacovanderbyl.enigmamachine.EnigmaType

@DslMarker
annotation class Dsl

fun enigmaI(init: EnigmaI.() -> Unit) : Enigma {
    val enigmaI = EnigmaI()
    enigmaI.init()
    return enigmaI.get(EnigmaType.ENIGMA_I)
}

fun enigmaM3(init: EnigmaM3.() -> Unit) : Enigma {
    val enigmaM3 = EnigmaM3()
    enigmaM3.init()
    return enigmaM3.get(EnigmaType.ENIGMA_M3)
}

fun enigmaM4(init: EnigmaM4.() -> Unit) : Enigma {
    val enigmaM4 = EnigmaM4()
    enigmaM4.init()
    return enigmaM4.get(EnigmaType.ENIGMA_M4)
}
