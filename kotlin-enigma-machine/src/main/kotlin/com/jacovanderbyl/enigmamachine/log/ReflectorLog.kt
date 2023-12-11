package com.jacovanderbyl.enigmamachine.log

import com.jacovanderbyl.enigmamachine.Reflector

class ReflectorLog(
    val first: Char,
    val second: Char,
    private val reflectorType: String,
    private val characterSet: String,
    private val cipherSet: String
) : Loggable {
    override val type: LogType = LogType.SUBSTITUTE

    override fun line(): String = String.format(
        "%-${Logger.logTypeMax}s | %-${Logger.resultMax}s | %-${Logger.typeMax}s | Cipher set map: %s => %s",
        type,
        "$first -> $second",
        reflectorType,
        characterSet,
        cipherSet
    )

    companion object {
        fun fromReflector(first: Char, second: Char, reflector: Reflector) : Loggable = ReflectorLog(
            first,
            second,
            reflector.type.name,
            reflector.getCipherSetMaps().first,
            reflector.getCipherSetMaps().second,
        )
    }
}
