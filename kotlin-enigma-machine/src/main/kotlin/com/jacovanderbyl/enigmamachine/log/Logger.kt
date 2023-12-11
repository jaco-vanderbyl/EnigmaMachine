package com.jacovanderbyl.enigmamachine.log

import com.jacovanderbyl.enigmamachine.EnigmaType
import com.jacovanderbyl.enigmamachine.ReflectorType
import com.jacovanderbyl.enigmamachine.RotorType

class Logger(private val maxLogSize: Int = 100) {
    val logs = mutableListOf<Loggable>()
    private val allowedLogTypes: MutableSet<LogType> = LogType.entries.toMutableSet()

    fun add(log: Loggable) {
        if (log.type !in allowedLogTypes) return
        if (logs.size >= maxLogSize) logs.removeFirst()
        logs.add(log)
    }

    fun restrictTo(vararg logTypes: LogType) {
        allowedLogTypes.clear()
        allowedLogTypes.addAll(logTypes)
    }

    fun print(vararg logTypes: LogType) {
        val typeList = if (logTypes.isEmpty()) LogType.entries.toSet() else logTypes.toSet()

        println("Log Types requested for printing : $typeList.")
        println("Log Types available for printing : $allowedLogTypes.")
        if (logs.size >= maxLogSize) println("Max log size '$maxLogSize' exceeded, showing truncated logs...")

        println()
        println(header())
        logs.filter { it.type in typeList }.forEach { println(it.line()) }
    }

    private fun header(): String = String.format(
        "%-${logTypeMax}s | %-${resultMax}s | %-${typeMax}s | %s",
        "LOG TYPE",
        "RESULT",
        "ACTOR",
        "INFO"
    )

    companion object {
        val logTypeMax = LogType.entries.maxOf { it.name.length }
        val typeMax = (RotorType.entries + ReflectorType.entries + EnigmaType.entries).maxOf { it.name.length }
        val resultMax = EnigmaType.entries.maxOf { it.rotorCount } * 2 + 4
    }
}