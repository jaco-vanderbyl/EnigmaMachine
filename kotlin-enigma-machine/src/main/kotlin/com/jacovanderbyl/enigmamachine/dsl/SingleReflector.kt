package com.jacovanderbyl.enigmamachine.dsl

import com.jacovanderbyl.enigmamachine.*

@Dsl
open class SingleReflector {
    protected var reflector: Reflector? = null
        set(value) {
            require(field == null) { "Single reflector required. Multiple given." }
            field = value
        }
    fun get() : Reflector {
        val ref = reflector
        require(ref != null) { "At least one reflector required. None given." }
        return ref
    }
}
