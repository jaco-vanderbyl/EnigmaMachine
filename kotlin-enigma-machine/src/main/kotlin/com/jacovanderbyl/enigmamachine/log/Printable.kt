package com.jacovanderbyl.enigmamachine.log

interface Printable {
    fun print(vararg logTypes: Log.Type)
}
