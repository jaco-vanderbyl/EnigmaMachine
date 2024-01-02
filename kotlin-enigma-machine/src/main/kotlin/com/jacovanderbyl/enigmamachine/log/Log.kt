package com.jacovanderbyl.enigmamachine.log

import com.jacovanderbyl.enigmamachine.*

sealed class Log : Loggable {
    class PlugboardSubstitute(
        private val first: Char,
        private val second: Char,
        private val connectors: String
    ) : Log() {
        override val type: LogType = LogType.SUBSTITUTE

        override fun line(): String = String.format(
            "%-${Logger.logTypeMax}s | %-${Logger.resultMax}s | %-${Logger.typeMax}s | %s",
            type,
            "$first -> $second",
            "PLUGBOARD",
            if (connectors.isEmpty()) "No connectors" else "Connectors: $connectors"
        )

        companion object {
            fun create(first: Char, second: Char, plugboard: Plugboard) : Log = PlugboardSubstitute(
                first,
                second,
                plugboard.connectorList.joinToString("") { "${it.first}${it.second} " }
            )
        }
    }

    class ReflectorSubstitute(
        private val first: Char,
        private val second: Char,
        private val reflectorType: String,
        private val characterSet: String,
        private val cipherSet: String
    ) : Log() {
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
            fun create(first: Char, second: Char, reflector: Reflector) : Log = ReflectorSubstitute(
                first,
                second,
                reflector.type.name,
                reflector.getCipherSetMaps().first,
                reflector.getCipherSetMaps().second,
            )
        }
    }

    class RotorDeshift(
        private val first: Char,
        private val second: Char,
        private val rotorType: String,
        private val offset: Int,
    ) : Log() {
        override val type: LogType = LogType.DE_SHIFT

        override fun line(): String = String.format(
            "%-${Logger.logTypeMax}s | %-${Logger.resultMax}s | %-${Logger.typeMax}s | Rotor offset: %s",
            type,
            "$first .. $second",
            rotorType,
            offset
        )

        companion object {
            fun create(first: Char, second: Char, rotor: Rotor) : Log = RotorDeshift(
                first,
                second,
                rotor.type.name,
                rotor.offset()
            )
        }
    }

    class RotorShift(
        private val first: Char,
        private val second: Char,
        private val rotorType: String,
        private val offset: Int,
        private val position: Char,
        private val positionIndex: Int,
        private val ringSetting: Int,
        private val ringSettingIndex: Int,
    ) : Log() {
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
            fun create(first: Char, second: Char, rotor: Rotor) : Log = RotorShift(
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

    class RotorSubstitute(
        private val first: Char,
        private val second: Char,
        private val rotorType: String,
        private val characterSet: String,
        private val cipherSet: String,
        private val reverse: Boolean
    ) : Log() {
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
            fun create(first: Char, second: Char, reverse: Boolean, rotor: Rotor) : Log = RotorSubstitute(
                first,
                second,
                rotor.type.name,
                rotor.getCipherSetMaps().first,
                rotor.getCipherSetMaps().second,
                reverse
            )
        }
    }

    class RotorUnitStep(
        private val first: String,
        private val second: String,
        private val rotors: String,
        private val notches: String
    ) : Log() {
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
            fun create(first: List<Rotor>, rotorUnit: RotorUnit) : Log = RotorUnitStep(
                first.map { it.position }.joinToString(""),
                rotorUnit.rotors.map { it.position }.joinToString(""),
                rotorUnit.rotors.map { it.type }.joinToString("—"),
                rotorUnit.rotors.map { rotor ->
                    if (rotor is Rotor.StepRotor) rotor.notchPositions.map { it.character } else setOf("_")
                }.joinToString("—")
            )
        }
    }
}
