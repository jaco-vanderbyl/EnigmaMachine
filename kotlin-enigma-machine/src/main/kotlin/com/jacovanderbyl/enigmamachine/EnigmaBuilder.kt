package com.jacovanderbyl.enigmamachine

class EnigmaBuilder {
    private var enigmaType: EnigmaType? = null
    private var reflector: Reflector? = null
    private var rotors: Set<Rotor>? = null
    private var ringSettings: List<Ring>? = null
    private var positions: List<Letter>? = null
    private var connectors: Set<Connector>? = null

    fun addType(type: EnigmaType) : EnigmaBuilder {
        this.enigmaType = type
        return this
    }
    fun addType(name: String) : EnigmaBuilder {
        val typeName = enigmaTypeName(name)
        this.enigmaType = EnigmaType.valueOf(typeName)
        return this
    }

    fun addReflector(type: ReflectorType) : EnigmaBuilder {
        reflector = type.create()
        return this
    }
    fun addReflector(name: String) : EnigmaBuilder {
        val typeName = reflectorTypeName(name)
        reflector = ReflectorType.valueOf(typeName).create()
        return this
    }

    fun addRotors(vararg types: RotorType) : EnigmaBuilder {
        rotors = types.map { it.create() }.toSet()
        return this
    }
    fun addRotors(csv: String) : EnigmaBuilder {
        rotors = splitCsv(csv).map {
            val typeName = rotorTypeName(it)
            RotorType.valueOf(typeName).create()
        }.toSet()
        return this
    }

    fun addRotorRingSettings(vararg settings: Ring) : EnigmaBuilder {
        ringSettings = settings.toList()
        return this
    }
    fun addRotorRingSettings(csv: String) : EnigmaBuilder {
        ringSettings = splitCsv(csv).map {
            val settingName = ringSettingName(it)
            Ring.valueOf(settingName)
        }.toList()
        return this
    }

    fun addRotorPositions(vararg positions: Letter) : EnigmaBuilder {
        this.positions = positions.toList()
        return this
    }
    fun addRotorPositions(csv: String) : EnigmaBuilder {
        positions = splitCsv(csv).map { Letter.valueOf(it) }.toList()
        return this
    }

    fun addPlugboardConnectors(vararg connectors: Connector) : EnigmaBuilder {
        this.connectors = connectors.toSet()
        return this
    }
    fun addPlugboardConnectors(csv: String) : EnigmaBuilder {
        connectors = splitCsv(csv).map { Connector.fromString(it) }.toSet()
        return this
    }

    fun reset() {
        enigmaType = null
        reflector = null
        rotors = null
        ringSettings = null
        positions = null
        connectors = null
    }

    fun build() : Enigma {
        // Final configuration for build, using default values (of 'stock enigma') for omitted configuration.
        val bEnigmaType = enigmaType ?: EnigmaType.ENIGMA_I
        val bReflector = reflector ?: ReflectorType.REFLECTOR_B.create()
        val bRotors = rotors ?: setOf(
            RotorType.ROTOR_I.create(),
            RotorType.ROTOR_II.create(),
            RotorType.ROTOR_III.create()
        )
        val bRingSettings = ringSettings ?: List(bRotors.size) { Ring.SETTING_1 }
        val bPositions = positions ?: List(bRotors.size) { Letter.A }
        val bConnectors = connectors ?: setOf()

        // Validation
        require(bRingSettings.size == bRotors.size) {
            "Invalid ring setting count. Number of ring settings must equal number of " +
                    "rotors: '${bRotors.size}'. Given: '${bRingSettings.size}'."
        }
        require(bPositions.size == bRotors.size) {
            "Invalid position count. Number of positions must equal number of rotors: '${bRotors.size}'. " +
                    "Given: '${bPositions.size}'."
        }

        // Apply rotor configuration
        bRingSettings.forEachIndexed { index, setting -> bRotors.elementAt(index).ringSetting = setting }
        bPositions.forEachIndexed { index, position -> bRotors.elementAt(index).position = position }

        // Build enigma using configuration
        val enigma = bEnigmaType.create(
            rotorUnit = RotorUnit(
                reflector = bReflector,
                rotors = bRotors
            ),
            plugboard = Plugboard(*bConnectors.toTypedArray())
        )

        // Reset configuration
        reset()

        return enigma
    }

    private fun splitCsv(str: String?) : List<String> = when (str.isNullOrEmpty()) {
        true -> listOf()
        false -> str.filterNot { it.isWhitespace() }.split(",")
    }

    private fun enigmaTypeName(name: String) : String = when (name.contains("ENIGMA_")) {
        true -> name
        false -> "ENIGMA_$name"
    }

    private fun rotorTypeName(name: String) : String = when (name.contains("ROTOR_")) {
        true -> name
        false -> "ROTOR_$name"
    }

    private fun reflectorTypeName(name: String) : String = when (name.contains("REFLECTOR_")) {
        true -> name
        false -> "REFLECTOR_$name"
    }

    private fun ringSettingName(name: String) : String = when (name.contains("SETTING_")) {
        true -> name
        false -> "SETTING_$name"
    }
}
