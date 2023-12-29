package com.jacovanderbyl.enigmamachine

/**
 * Represents the ring setting on a rotor.
 *
 * From Wikipedia: The ring settings, or Ringstellung, are used to change the position of the alphabet ring relative
 * to the internal wiring. Notch and alphabet ring are fixed together. Changing the ring setting will therefore change
 * the positions of the wiring, relative to the turnover-point and start position.
 */
class RingSetting(val value: Int = 1) {
    val index = value - 1

    init {
        require(value in list()) {
            "Invalid value. The ring setting must be an integer between 1 and ${Enigma.CHARACTER_SET.length}. " +
                    "Given: '${value}'."
        }
    }

    companion object {
        const val NUMBER_1 = 1
        const val NUMBER_2 = 2
        const val NUMBER_3 = 3
        const val NUMBER_4 = 4
        const val NUMBER_5 = 5
        const val NUMBER_6 = 6
        const val NUMBER_7 = 7
        const val NUMBER_8 = 8
        const val NUMBER_9 = 9
        const val NUMBER_10 = 10
        const val NUMBER_11 = 11
        const val NUMBER_12 = 12
        const val NUMBER_13 = 13
        const val NUMBER_14 = 14
        const val NUMBER_15 = 15
        const val NUMBER_16 = 16
        const val NUMBER_17 = 17
        const val NUMBER_18 = 18
        const val NUMBER_19 = 19
        const val NUMBER_20 = 20
        const val NUMBER_21 = 21
        const val NUMBER_22 = 22
        const val NUMBER_23 = 23
        const val NUMBER_24 = 24
        const val NUMBER_25 = 25
        const val NUMBER_26 = 26

        fun fromString(ringSetting: String) : RingSetting {
            require(ringSetting.toIntOrNull() != null) {
                "Invalid number representation. A rotor ring setting string must be an integer. " +
                        "Given: '${ringSetting}'."
            }

            return RingSetting(ringSetting.toInt())
        }

        fun list() : List<Int> = Enigma.CHARACTER_SET.map { Enigma.CHARACTER_SET.indexOf(it) + 1 }
    }
}
