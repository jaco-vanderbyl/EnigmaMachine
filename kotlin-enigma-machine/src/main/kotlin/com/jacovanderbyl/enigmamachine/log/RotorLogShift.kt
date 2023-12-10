package com.jacovanderbyl.enigmamachine.log

import com.jacovanderbyl.enigmamachine.Rotor

class RotorLogShift(
    val first: Char,
    val second: Char,
    private val rotorType: String,
    private val offset: Int,
    private val position: Char,
    private val positionIndex: Int,
    private val ringSetting: Int,
    private val ringSettingIndex: Int,
) : Loggable {
    override val type: LogType = LogType.SHIFT

    override fun line(): String = String.format(
        "%-${Logger.logTypeMax}s | %-${Logger.resultMax}s | %-${Logger.typeMax}s | " +
                "Rotor offset: %s = offset %s (Position %s) minus offset %s (Ring Setting %s)",
        type,
        "$first .. $second",
        rotorType,
        offset,
        positionIndex,
        position,
        ringSettingIndex,
        ringSetting
    )

    companion object {
        fun fromRotor(first: Char, second: Char, rotor: Rotor) : Loggable = RotorLogShift(
            first,
            second,
            rotor.type.name,
            rotor.offset(),
            rotor.position.character,
            rotor.position.index,
            rotor.ringSetting.value,
            rotor.ringSetting.index
        )
    }
}
