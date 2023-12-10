package com.jacovanderbyl.enigmamachine.log

interface Loggable {
    val type: LogType

    fun line(): String
}
