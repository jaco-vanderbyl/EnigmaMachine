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
            require(type in EnigmaType.entries.map { it.name }) {
                "Invalid enigma type. Valid: '${EnigmaType.entries.map { it.name }}'. Given: '${type}'."
            }
            require(reflector in ReflectorType.entries.map { it.name }) {
                "Invalid reflector type. Valid: '${ReflectorType.entries.map { it.name }}'. Given: '${reflector}'."
            }

            return EnigmaType.valueOf(type).create(
                rotorUnit = RotorUnit(
                    reflector = ReflectorType.valueOf(reflector).create(),
                    rotors = setOf(*makeRotors(split(rotors), split(startingPositions), split(ringSettings)))
                ),
                plugboard = Plugboard(*Connector.fromStrings(split(plugboardConnectors)))
            )
        }

        private fun makeRotors(
            rotors: List<String>,
            positions: List<String>,
            ringSettings: List<String>
        ) : Array<Rotor> {
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
            }.toTypedArray()
        }

        private fun makeRotor(rotor: String, position: String?, ringSetting: String?) : Rotor {
            require(rotor in RotorType.entries.map { it.name }) {
                "Invalid rotor type. Valid: '${RotorType.entries.map { it.name }}'. Given: '${rotor}'."
            }

            return RotorType.valueOf(rotor).create(
                position = if (position != null) Position.fromString(position) else Position(),
                ringSetting = if (ringSetting != null) RingSetting.fromString(ringSetting) else RingSetting()
            )
        }

        private fun split(str: String?) : List<String> = when (str.isNullOrEmpty()) {
            true -> listOf()
            false -> str.filterNot { it.isWhitespace() }.split(",")
        }
    }
}
