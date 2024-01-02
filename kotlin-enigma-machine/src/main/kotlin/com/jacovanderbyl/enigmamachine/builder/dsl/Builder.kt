package com.jacovanderbyl.enigmamachine.builder.dsl

import com.jacovanderbyl.enigmamachine.Enigma

@DslMarker
annotation class Dsl

fun enigmaI(init: EnigmaI.() -> Unit) : Enigma {
    val context = EnigmaI()
    context.init()
    return context.buildEnigma()
}

fun enigmaM3(init: EnigmaM3.() -> Unit) : Enigma {
    val context = EnigmaM3()
    context.init()
    return context.buildEnigma()
}

fun enigmaM4(init: EnigmaM4.() -> Unit) : Enigma {
    val context = EnigmaM4()
    context.init()
    return context.buildEnigma()
}
