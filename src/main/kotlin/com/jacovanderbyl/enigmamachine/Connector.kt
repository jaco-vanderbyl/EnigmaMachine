package com.jacovanderbyl.enigmamachine

/**
 * Represents a connection between two distinct characters,
 * used by plugboard to substitute one character for another.
 */
class Connector(val first: Char, val second: Char) {
    init {
        require(first in Enigma.CHARACTER_SET) {
            "Invalid character for first. Valid: '${Enigma.CHARACTER_SET}'. Given: '${first}'."
        }
        require(second in Enigma.CHARACTER_SET) {
            "Invalid character for second. Valid: '${Enigma.CHARACTER_SET}'. Given: '${second}'."
        }
        require(first != second) {
            "Invalid character pair. First and second characters are the same. Given: '${first}${second}'."
        }
    }

    companion object {
        fun fromString(connector: String) : Connector {
            require(connector.length == 2) {
                "Invalid string length. A connector pair must be two characters. Given: '${connector}'."
            }
            return Connector(connector[0], connector[1])
        }

        fun fromStrings(connectors: List<String>) : List<Connector> = connectors.map {
            fromString(it)
        }
    }
}
