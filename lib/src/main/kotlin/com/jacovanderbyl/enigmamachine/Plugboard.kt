package com.jacovanderbyl.enigmamachine

/**
 * Represents a plugboard, which has 26 letters, each of which can be connected with one other letter (therefore
 * allowing up to 13 connectors), and these connections are used to substitute one letter with another.
 *
 * The plugboard adds significant cryptographic strength to the machine.
 */
class Plugboard(vararg connectors: Connector) : CanEncipher {
    private val connectorMap = mutableMapOf<Char,Char>()

    init {
        addConnectors(*connectors)
    }

    /**
     * Substitute character if it's connected with another, otherwise return original character.
     */
    override fun encipher(character: Char) : Char {
        require(character in Enigma.CHARACTER_SET) {
            "Invalid character. Valid: '${Enigma.CHARACTER_SET}'. Given: '${character}'."
        }

        return connectorMap.getOrDefault(character, character)
    }

    fun reset() {
        connectorMap.clear()
    }

    fun addConnectors(vararg connectors: Connector) {
        connectors.forEach {
            require(!connectorMap.containsKey(it.first) && !connectorMap.containsKey(it.second)) {
                "Duplicate connector. Cannot connect character that's already been connected. " +
                        "Given: '${it.first}${it.second}'."
            }

            connectorMap[it.first] = it.second
            connectorMap[it.second] = it.first
        }
    }
}
