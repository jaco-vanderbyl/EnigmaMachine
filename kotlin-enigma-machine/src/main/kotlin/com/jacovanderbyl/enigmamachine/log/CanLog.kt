package com.jacovanderbyl.enigmamachine.log

interface CanLog {
    fun restrictTo(vararg logTypes: Log.Type)
    fun write(log: Loggable)
}
