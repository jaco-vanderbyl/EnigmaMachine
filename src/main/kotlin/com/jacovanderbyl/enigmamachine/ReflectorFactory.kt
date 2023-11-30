package com.jacovanderbyl.enigmamachine

enum class ReflectorFactory {
    B { override fun create() = ReflectorB() },
    C { override fun create() = ReflectorC() };

    abstract fun create() : Reflector
}
