package com.jacovanderbyl.enigmamachine

class EnigmaBuilder {
    companion object {
        /**
         * Example usage:
         *     EnigmaBuilder.makeFromCsvValues(
         *         model = "ENIGMA_I",
         *         reflector = "B",
         *         rotors = "I,V,III",
         *         ringSettings = "14,9,24",
         *         startingPositions = "W,N,Y",
         *         plugboardConnectors = "SZ,GT,DV,KU,FO,MY,EW,JN,IX,LQ"
         *     )
         */
        fun makeFromCsvValues(
            model: String,
            reflector: String,
            rotors: String,
            ringSettings: String? = null,
            startingPositions: String? = null,
            plugboardConnectors: String? = null
        ): Enigma {
            require(model in EnigmaFactory.entries.map { it.name }) {
                "Valid model types: '${EnigmaFactory.entries.map { it.name }}'. Given: '${model}'."
            }
            require(reflector in ReflectorFactory.entries.map { it.name }) {
                "Valid reflector types: '${ReflectorFactory.entries.map { it.name }}'. Given: '${reflector}'."
            }

            val enigma = EnigmaFactory.valueOf(model).create(
                rotorUnit = RotorUnit(
                    reflector = ReflectorFactory.valueOf(reflector).create(),
                    rotors = setOf(*makeRotors(split(rotors), split(ringSettings)))
                ),
                plugboard = Plugboard()
            )

            enigma.setRotorPositions(*Position.fromStrings(split(startingPositions)))
            enigma.addPlugboardConnectors(false, *Connector.fromStrings(split(plugboardConnectors)))

            return enigma
        }

        private fun makeRotors(rotors: List<String>, ringSettings: List<String>?) : Array<Rotor> {
            if (ringSettings != null) {
                require(rotors.size == ringSettings.size) {
                    "Number of ring settings must equal number of rotors: '${rotors.size}'. " +
                            "Given: '${ringSettings.size}'."
                }
            }

            return rotors.mapIndexed { index, rotor ->
                makeRotor(rotor, ringSettings?.get(index))
            }.toTypedArray()
        }

        private fun makeRotor(rotor: String, ringSetting: String?): Rotor {
            require(rotor in RotorFactory.entries.map { it.name }) {
                "Valid rotor types: '${RotorFactory.entries.map { it.name }}'. Given: '${rotor}'."
            }

            val rotorObj = RotorFactory.valueOf(rotor).create()
            if (ringSetting != null) rotorObj.ringSetting = RingSetting.fromString(ringSetting)
            return rotorObj
        }

        private fun split(str: String?) : List<String> =
            if (str.isNullOrEmpty())
                listOf()
            else
                str.filterNot { it.isWhitespace() }.split(",")
    }
}
