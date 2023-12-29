package com.jacovanderbyl.enigmamachine.dsl

import com.jacovanderbyl.enigmamachine.*

@Dsl
class PlugboardConnectors {
    private val connectors = mutableSetOf<Connector>()
    fun get() : Set<Connector> = connectors

    @Dsl fun connect(first: Char, second: Char) {
        connectors.add(Connector(first, second))
    }
}
