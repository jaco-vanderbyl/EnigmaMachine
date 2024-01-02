package com.jacovanderbyl.enigmamachine.builder.dsl

import com.jacovanderbyl.enigmamachine.Connector
import com.jacovanderbyl.enigmamachine.Enigma
import com.jacovanderbyl.enigmamachine.Reflector
import com.jacovanderbyl.enigmamachine.Rotor

@Dsl
abstract class EnigmaContext {
    protected var reflector: Reflector? = null
    protected var rotors: Set<Rotor>? = null
    protected var connectors: Set<Connector>? = null

    @Dsl
    fun upToThirteenPlugboardConnectors(init: PlugboardConnectors.() -> Unit) : PlugboardConnectors {
        val context = PlugboardConnectors()
        context.init()
        connectors = context.getConnectors()
        return context
    }

    abstract fun buildEnigma() : Enigma
}
