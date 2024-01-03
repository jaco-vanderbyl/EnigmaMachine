package com.jacovanderbyl.enigmamachine.log

object Logger {
    enum class Strategy(val type: CanLog) {
        IN_MEMORY(InMemoryLogger())
    }

    private var strategy: Strategy? = null

    fun enable(strategy: Strategy) {
        this.strategy = strategy
    }

    fun get() : CanLog? = strategy?.type
}
