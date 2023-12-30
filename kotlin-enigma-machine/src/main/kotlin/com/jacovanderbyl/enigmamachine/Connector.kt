package com.jacovanderbyl.enigmamachine

/**
 * Represents a connection between two distinct characters,
 * used by plugboard to substitute one character for another.
 */
data class Connector(val first: Letter, val second: Letter) {
    init {
        require(first != second) {
            "Invalid character pair. First and second characters are the same. Given: '${first}${second}'."
        }
    }

    companion object {
        fun fromString(connector: String) : Connector {
            require(connector.length == 2) {
                "Invalid string length. A connector pair must be two characters. Given: '${connector}'."
            }
            return Connector(Letter.valueOf(connector[0].toString()), Letter.valueOf(connector[1].toString()))
        }

        fun fromStrings(connectors: List<String>) : List<Connector> = connectors.map { fromString(it) }
    }
}
