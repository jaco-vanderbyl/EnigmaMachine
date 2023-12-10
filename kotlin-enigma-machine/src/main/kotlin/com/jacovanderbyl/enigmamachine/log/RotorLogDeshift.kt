package com.jacovanderbyl.enigmamachine.log

import com.jacovanderbyl.enigmamachine.Rotor

class RotorLogDeshift(
    val first: Char,
    val second: Char,
    private val rotorType: String,
    private val offset: Int,
) : Loggable {
    override val type: LogType = LogType.DE_SHIFT

    override fun line(): String = String.format(
        "%-${Logger.logTypeMax}s | %-${Logger.resultMax}s | %-${Logger.typeMax}s | Rotor offset: %s",
        type,
        "$first .. $second",
        rotorType,
        offset
    )

    companion object {
        fun fromRotor(first: Char, second: Char, rotor: Rotor) : Loggable = RotorLogDeshift(
            first,
            second,
            rotor.type.name,
            rotor.offset()
        )
    }
}
