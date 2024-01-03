package com.jacovanderbyl.enigmamachine.log

class InMemoryLogger : CanLog, Printable {
    var maxLogSize = 100
    private val logs = mutableListOf<Loggable>()
    private val allowedLogTypes: MutableSet<Log.Type> = Log.Type.entries.toMutableSet()

    override fun restrictTo(vararg logTypes: Log.Type) {
        allowedLogTypes.clear()
        allowedLogTypes.addAll(logTypes)
    }

    override fun write(log: Loggable) {
        if (log.type !in allowedLogTypes) {
            return
        }
        if (logs.size >= maxLogSize) {
            logs.removeFirst()
        }
        logs.add(log)
    }

    override fun print(vararg logTypes: Log.Type) {
        val typeList = if (logTypes.isEmpty()) Log.Type.entries.toSet() else logTypes.toSet()

        println("Log Types requested for printing : $typeList.")
        println("Log Types available for printing : $allowedLogTypes.")
        if (logs.size >= maxLogSize) println("Max log size '$maxLogSize' exceeded, showing truncated logs...")

        println()
        println(header())
        logs.filter { it.type in typeList }.forEach { println(it.line()) }
    }

    private fun header(): String = String.format(
        "%-${Log.logTypeMax}s | %-${Log.resultMax}s | %-${Log.typeMax}s | %s",
        "LOG TYPE",
        "RESULT",
        "ACTOR",
        "INFO"
    )
}
