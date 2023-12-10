package com.jacovanderbyl.enigmamachine.log

import com.jacovanderbyl.enigmamachine.Plugboard

class PlugboardLog(
    val first: Char,
    val second: Char,
    private val connectors: String
) : Loggable {
    override val type: LogType = LogType.SUBSTITUTE

    override fun line(): String = String.format(
        "%-${Logger.logTypeMax}s | %-${Logger.resultMax}s | %-${Logger.typeMax}s | %s",
        type,
        "$first -> $second",
        "PLUGBOARD",
        if (connectors.isEmpty()) "No connectors" else "Connectors: $connectors"
    )

    companion object {
        fun fromPlugboard(first: Char, second: Char, plugboard: Plugboard) : Loggable = PlugboardLog(
            first,
            second,
            plugboard.connectorList.joinToString("") { "${it.first}${it.second} " }
        )
    }
}
