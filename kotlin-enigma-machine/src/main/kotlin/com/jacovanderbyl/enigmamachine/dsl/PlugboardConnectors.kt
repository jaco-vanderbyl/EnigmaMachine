package com.jacovanderbyl.enigmamachine.dsl

import com.jacovanderbyl.enigmamachine.*

@Dsl
class PlugboardConnectors {
    private val connectors = mutableSetOf<Connector>()
    fun get() : Set<Connector> = connectors

    @Dsl fun connect(first: Letter, second: Letter) {
        connectors.add(Connector(first, second))
    }
}
