package com.jacovanderbyl.enigmamachine.log

import com.jacovanderbyl.enigmamachine.Rotor
import com.jacovanderbyl.enigmamachine.RotorUnit
import com.jacovanderbyl.enigmamachine.StepRotor

class RotorUnitLogStep(
    val first: String,
    val second: String,
    private val rotors: String,
    private val notches: String
) : Loggable {
    override val type: LogType = LogType.STEP

    override fun line(): String = String.format(
        "%-${Logger.logTypeMax}s | %-${Logger.resultMax}s | %-${Logger.typeMax}s | " +
                "Rotor types: %s; Notch characters: %s",
        type,
        "$first -> $second",
        "ROTOR_UNIT",
        rotors,
        notches
    )

    companion object {
        fun fromRotorUnit(first: List<Rotor>, rotorUnit: RotorUnit) : Loggable = RotorUnitLogStep(
            first.map { it.position }.joinToString(""),
            rotorUnit.rotors.map { it.position }.joinToString(""),
            rotorUnit.rotors.map { it.type }.joinToString("—"),
            rotorUnit.rotors.map { rotor ->
                if (rotor is StepRotor) rotor.notch.positions.map { it.character } else setOf("_")
            }.joinToString("—")
        )
    }
}
