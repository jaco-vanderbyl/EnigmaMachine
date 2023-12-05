package com.jacovanderbyl.enigmamachine

/**
 * Represents the ring setting on a rotor.
 *
 * From Wikipedia: The ring settings, or Ringstellung, are used to change the position of the alphabet ring relative
 * to the internal wiring. Notch and alphabet ring are fixed together. Changing the ring setting will therefore change
 * the positions of the wiring, relative to the turnover-point and start position.
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
                "Invalid number representation. A rotor ring setting string must be an integer. Given: '${setting}'."
            }

            return RingSetting(setting.toInt())
        }
    }
}
