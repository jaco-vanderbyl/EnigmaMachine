package com.jacovanderbyl.enigmamachine.dsl

import com.jacovanderbyl.enigmamachine.*
import com.jacovanderbyl.enigmamachine.Enigma

@Dsl
open class Enigma {
    protected lateinit var reflector: Reflector
    protected val rotors = mutableSetOf<Rotor>()
    private val connectors = mutableSetOf<Connector>()

    @Dsl fun upToThirteenPlugboardConnectors(init: PlugboardConnectors.() -> Unit) : PlugboardConnectors {
        val plugboardConnectors = PlugboardConnectors()
        plugboardConnectors.init()
        connectors.addAll(plugboardConnectors.get())
        return plugboardConnectors
    }

    fun get(type: EnigmaType) : Enigma {
        require(this::reflector.isInitialized) { "At least one reflector required. None given." }

        return type.create(
            rotorUnit = RotorUnit(
                reflector = reflector,
                rotors = rotors
            ),
            plugboard = Plugboard(*connectors.toTypedArray())
        )
    }
}
