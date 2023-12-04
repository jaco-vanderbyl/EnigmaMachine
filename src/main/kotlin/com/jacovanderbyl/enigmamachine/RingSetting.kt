package com.jacovanderbyl.enigmamachine

/**
 * Represents the ring setting on a rotor.
 *
 * Ring settings are used to change the position of the character ring relative to the internal wiring of the rotor.
 */
class RingSetting(val value: Int = 1) {
    init {
        require(value in 1..Enigma.CHARACTER_SET.length) {
            "Invalid value. The ring setting must be an integer between 1 and ${Enigma.CHARACTER_SET.length}. " +
                    "Given: '${value}'."
        }
    }

    val index = value - 1

    companion object {
        fun fromString(setting: String) : RingSetting {
            require(setting.toIntOrNull() != null) {
                "A rotor ring setting string must be an integer. Given: '${setting}'."
            }

            return RingSetting(setting.toInt())
        }
    }
}
