package com.jacovanderbyl.enigmamachine.builder

import com.jacovanderbyl.enigmamachine.*

class EnigmaBuilder {
    private var enigmaType: EnigmaType? = null
    private var reflector: Reflector? = null
    private var rotors: Set<Rotor>? = null
    private var ringSettings: List<Rotor.Ring>? = null
    private var positions: List<Letter>? = null
    private var connectors: Set<Plugboard.Connector>? = null

    fun addType(type: EnigmaType) : EnigmaBuilder {
        enigmaType = type
        return this
    }
    fun addType(name: String) : EnigmaBuilder {
        val typeName = enigmaTypeName(name)
        enigmaType = EnigmaType.valueOf(typeName)
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

    fun addRotorRingSettings(vararg settings: Rotor.Ring) : EnigmaBuilder {
        ringSettings = settings.toList()
        return this
    }
    fun addRotorRingSettings(csv: String) : EnigmaBuilder {
        ringSettings = splitCsv(csv).map {
            val settingName = ringSettingName(it)
            Rotor.Ring.valueOf(settingName)
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

    fun addPlugboardConnectors(vararg connectors: Pair<Letter,Letter>) : EnigmaBuilder {
        this.connectors = connectors.map{ Plugboard.Connector(it.first, it.second) }.toSet()
        return this
    }
    fun addPlugboardConnectors(csv: String) : EnigmaBuilder {
        connectors = splitCsv(csv).map { Plugboard.Connector.fromString(it) }.toSet()
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
        // Create function-scope configuration from class-scope, using defaults for omitted configuration.
        val cfgEnigmaType = enigmaType ?: EnigmaType.ENIGMA_I
        val cfgReflector = reflector ?: ReflectorType.REFLECTOR_B.create()
        val cfgRotors = rotors ?: setOf(
            RotorType.ROTOR_I.create(),
            RotorType.ROTOR_II.create(),
            RotorType.ROTOR_III.create()
        )
        val cfgRingSettings = ringSettings ?: List(cfgRotors.size) { Rotor.Ring.SETTING_1 }
        val cfgPositions = positions ?: List(cfgRotors.size) { Letter.A }
        val cfgConnectors = connectors ?: setOf()

        // Reset class-scope configuration
        reset()

        // Validate ring setting and position counts
        require(cfgRingSettings.size == cfgRotors.size) {
            "Invalid ring setting count. Number of ring settings must equal number of " +
                    "rotors: '${cfgRotors.size}'. Given: '${cfgRingSettings.size}'."
        }
        require(cfgPositions.size == cfgRotors.size) {
            "Invalid position count. Number of positions must equal number of rotors: '${cfgRotors.size}'. " +
                    "Given: '${cfgPositions.size}'."
        }

        // Apply ring setting and position configuration to rotors
        cfgRingSettings.forEachIndexed { index, setting -> cfgRotors.elementAt(index).ringSetting = setting }
        cfgPositions.forEachIndexed { index, position -> cfgRotors.elementAt(index).position = position }

        // Build and return enigma
        return cfgEnigmaType.create(
            rotorUnit = RotorUnit(
                reflector = cfgReflector,
                rotors = cfgRotors
            ),
            plugboard = Plugboard(*cfgConnectors.toTypedArray())
        )
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
