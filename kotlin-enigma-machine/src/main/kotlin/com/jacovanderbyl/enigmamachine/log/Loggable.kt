package com.jacovanderbyl.enigmamachine.log

sealed interface Loggable {
    val type: Log.Type

    fun line(): String
}
