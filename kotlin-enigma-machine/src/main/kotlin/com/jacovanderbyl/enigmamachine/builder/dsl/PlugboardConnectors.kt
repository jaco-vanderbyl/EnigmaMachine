package com.jacovanderbyl.enigmamachine.builder.dsl

import com.jacovanderbyl.enigmamachine.Connector
import com.jacovanderbyl.enigmamachine.Letter

@Dsl
class PlugboardConnectors {
    private val connectors = mutableSetOf<Connector>()

    fun getConnectors() : Set<Connector>? = if (connectors.isEmpty()) null else connectors.toSet()

    @Dsl
    fun connect(first: Letter, second: Letter) {
        connectors.add(Connector(first, second))
    }
}
