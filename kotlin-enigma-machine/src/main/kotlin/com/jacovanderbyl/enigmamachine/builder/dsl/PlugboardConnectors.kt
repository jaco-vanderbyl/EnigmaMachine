package com.jacovanderbyl.enigmamachine.builder.dsl

import com.jacovanderbyl.enigmamachine.Letter
import com.jacovanderbyl.enigmamachine.Plugboard

@Dsl
class
PlugboardConnectors {
    private val connectors = mutableSetOf<Plugboard.Connector>()

    fun getConnectors() : Set<Plugboard.Connector>? = if (connectors.isEmpty()) null else connectors.toSet()

    @Dsl
    fun connect(letters: Pair<Letter,Letter>) {
        connectors.add(Plugboard.Connector(letters.first, letters.second))
    }
}
