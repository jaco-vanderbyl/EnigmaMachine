package com.jacovanderbyl.enigmamachine.log

sealed interface Loggable {
    val type: LogType

    fun line(): String
}
