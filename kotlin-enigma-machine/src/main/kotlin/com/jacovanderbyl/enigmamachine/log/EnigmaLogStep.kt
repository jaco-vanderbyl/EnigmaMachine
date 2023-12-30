package com.jacovanderbyl.enigmamachine.log

import com.jacovanderbyl.enigmamachine.Enigma
import com.jacovanderbyl.enigmamachine.StepRotor

class EnigmaLogStep(
    val first: String,
    val second: String,
    private val enigmaType: String,
    private val rotors: String,
    private val notches: String
) : Loggable {
    override val type: LogType = LogType.STEP

    override fun line(): String = String.format(
        "%-${Logger.logTypeMax}s | %-${Logger.resultMax}s | %-${Logger.typeMax}s | " +
                "Rotor types: %s; Notch characters: %s",
        type,
        "$first -> $second",
        enigmaType,
        rotors,
        notches
    )

    companion object {
        fun fromEnigma(first: String, second: String, enigma: Enigma) : Loggable = EnigmaLogStep(
            first,
            second,
            enigma.type.name,
            enigma.getRotors().map { it.type }.joinToString("—"),
            enigma.getRotors().map {
                if (it is StepRotor) it.notch.positions.map { it.character } else setOf("_")
            }.joinToString("—")
        )
    }
}
