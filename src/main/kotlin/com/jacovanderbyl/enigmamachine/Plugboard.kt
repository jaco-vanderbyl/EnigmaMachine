package com.jacovanderbyl.enigmamachine

/**
 * Represents a plugboard on an Enigma Machine.
 *
 * The plugboard allowed for even more letter substitutions to happen when enciphering a letter, which added
 * significant cryptographic strength to the machine.
 *
 * The plugboard may or may not actually substitute a letter for a new one. Substitution is determined
 * by the plugboard cable connections. If no cables are connected, no substitutions will occur. If only 'A' and 'B'
 * are connected with a cable, then only those two letters will be substituted. It was common to use ten cables,
 * connecting 20 letters.
 */
class Plugboard(vararg connectors: Connector) : CanEncipher {
    private val connectorMap = mutableMapOf<Char,Char>()

    init {
        connectPlugs(*connectors)
    }

    /**
     * Substitute character if it's connected with another, otherwise return original character.
     */
    override fun encipher(character: Char) : Char {
        require(character in Keys.CHARACTER_SET) {
            "Invalid character. Valid: '${Keys.CHARACTER_SET}'. Given: '${character}'."
        }

        return connectorMap.getOrDefault(character, character)
    }

    fun reset() {
        connectorMap.clear()
    }

    fun connectPlugs(vararg connectors: Connector) {
        connectors.forEach {
            require(!connectorMap.containsKey(it.first) && !connectorMap.containsKey(it.second)) {
                "Cannot connect character that's already been connected. Given: '${it.first}${it.second}'."
            }

            connectorMap[it.first] = it.second
            connectorMap[it.second] = it.first
        }
    }
}
