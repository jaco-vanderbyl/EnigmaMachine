package com.jacovanderbyl.enigmamachine.log

import com.jacovanderbyl.enigmamachine.Rotor

class RotorLogSubstitute(
    val first: Char,
    val second: Char,
    private val rotorType: String,
    private val characterSet: String,
    private val cipherSet: String,
    private val reverse: Boolean
) : Loggable {
    override val type: LogType = LogType.SUBSTITUTE

    override fun line(): String = String.format(
        "%-${Logger.logTypeMax}s | %-${Logger.resultMax}s | %-${Logger.typeMax}s | Cipher set map: %s => %s",
        type,
        "$first -> $second",
        rotorType,
        if (reverse) cipherSet else characterSet,
        if (reverse) characterSet else cipherSet
    )

    companion object {
        fun fromRotor(first: Char, second: Char, reverse: Boolean, rotor: Rotor) : Loggable = RotorLogSubstitute(
            first,
            second,
            rotor.type.name,
            rotor.getCipherSetMaps().first,
            rotor.getCipherSetMaps().second,
            reverse
        )
    }
}
