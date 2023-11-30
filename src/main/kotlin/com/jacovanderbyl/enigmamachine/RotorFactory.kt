package com.jacovanderbyl.enigmamachine

enum class RotorFactory {
    I { override fun create() = RotorI() },
    II { override fun create() = RotorII() },
    III { override fun create() = RotorIII() },
    IV { override fun create() = RotorIV() },
    V { override fun create() = RotorV() },
    VI { override fun create() = RotorVI() },
    VII { override fun create() = RotorVII() },
    VIII { override fun create() = RotorVIII() };

    abstract fun create() : Rotor
}
