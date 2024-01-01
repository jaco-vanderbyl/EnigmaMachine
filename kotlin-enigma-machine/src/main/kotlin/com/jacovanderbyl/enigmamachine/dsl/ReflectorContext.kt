package com.jacovanderbyl.enigmamachine.dsl

import com.jacovanderbyl.enigmamachine.*

@Dsl
abstract class ReflectorContext {
    private var reflector: Reflector? = null

    fun getReflector() : Reflector? = reflector

    protected fun setReflector(reflector: Reflector) {
        require(this.reflector == null) { "Single reflector required. Multiple given." }
        this.reflector = reflector
    }
}
