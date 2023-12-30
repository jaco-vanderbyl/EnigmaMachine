package com.jacovanderbyl.enigmamachine

class EnigmaBuilder {
    companion object {
        /**
         * All params, except 'type' and 'reflector', accept comma-separated values.
         *
         * Example usage:
         *     EnigmaBuilder.make(
         *         type = "ENIGMA_I",
         *         reflector = "B",
         *         rotors = "I,V,III",
         *         ringSettings = "14,9,24",
         *         startingPositions = "W,N,Y",
         *         plugboardConnectors = "SZ,GT,DV,KU,FO,MY,EW,JN,IX,LQ"
         *     )
         */
        fun make(
            type: String,
            reflector: String,
            rotors: String,
            ringSettings: String? = null,
            startingPositions: String? = null,
            plugboardConnectors: String? = null
        ) : Enigma {
            require(enigmaType(type) in EnigmaType.list()) {
                "Invalid enigma type. Valid: '${EnigmaType.list()}'. Given: '${type}'."
            }
            require(reflectorType(reflector) in ReflectorType.list()) {
                "Invalid reflector type. Valid: '${ReflectorType.list()}'. Given: '${reflector}'."
            }

            return EnigmaType.valueOf(enigmaType(type)).create(
                rotorUnit = RotorUnit(
                    reflector = ReflectorType.valueOf(reflectorType(reflector)).create(),
                    rotors = setOf(
                        *makeRotors(
                            split(rotors),
                            split(startingPositions),
                            split(ringSettings)
                        ).toTypedArray()
                    )
                ),
                plugboard = Plugboard(
                    *Connector.fromStrings(
                        split(plugboardConnectors)
                    ).toTypedArray()
                )
            )
        }

        private fun makeRotors(
            rotors: List<String>,
            positions: List<String>,
            ringSettings: List<String>
        ) : List<Rotor> {
            if (positions.isNotEmpty()) {
                require(rotors.size == positions.size) {
                    "Invalid position count. Number of positions must equal number of rotors: '${rotors.size}'. " +
                            "Given: '${positions.size}'."
                }
            }
            if (ringSettings.isNotEmpty()) {
                require(rotors.size == ringSettings.size) {
                    "Invalid ring setting count. Number of ring settings must equal number of " +
                            "rotors: '${rotors.size}'. Given: '${ringSettings.size}'."
                }
            }

            return rotors.mapIndexed { index, rotor ->
                makeRotor(rotor, positions.getOrNull(index), ringSettings.getOrNull(index))
            }
        }

        private fun makeRotor(rotor: String, position: String?, ringSetting: String?) : Rotor {
            require(rotorType(rotor) in RotorType.list()) {
                "Invalid rotor type. Valid: '${RotorType.list()}'. Given: '${rotor}'."
            }

            return RotorType.valueOf(rotorType(rotor)).create(
                position = if (position != null) Letter.valueOf(position) else Letter.A,
                ringSetting = if (ringSetting != null) Ring.valueOf(ringSetting(ringSetting)) else Ring.SETTING_1
            )
        }

        private fun split(str: String?) : List<String> = when (str.isNullOrEmpty()) {
            true -> listOf()
            false -> str.filterNot { it.isWhitespace() }.split(",")
        }

        private fun ringSetting(ringSetting: String) : String = when (ringSetting.contains("SETTING_")) {
            true -> ringSetting
            false -> "SETTING_$ringSetting"
        }

        private fun enigmaType(type: String) : String = when (type.contains("ENIGMA_")) {
            true -> type
            false -> "ENIGMA_$type"
        }

        private fun rotorType(rotorType: String) : String = when (rotorType.contains("ROTOR_")) {
            true -> rotorType
            false -> "ROTOR_$rotorType"
        }

        private fun reflectorType(reflectorType: String) : String = when (reflectorType.contains("REFLECTOR_")) {
            true -> reflectorType
            false -> "REFLECTOR_$reflectorType"
        }
    }
}
