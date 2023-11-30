package com.jacovanderbyl.enigmamachine

/**
 * Represents a connector, connecting one character with another.
 *
 * E.g.: a plugboard cable connecting 'A' and 'B'.
 */
class Connector(val first: Char, val second: Char) {
    init {
        require(first in Keys.CHARACTER_SET) {
            "Each character in connector pair must be in: '${Keys.CHARACTER_SET}'. Given: '${first}'."
        }
        require(second in Keys.CHARACTER_SET) {
            "Each character in connector pair must be in: '${Keys.CHARACTER_SET}'. Given: '${second}'."
        }
        require(first != second) {
            "It's not possible to connect a character to itself; must be unique. Given: '${first}${second}'."
        }
    }

    companion object {
        fun fromString(pair: String) : Connector {
            require(pair.length == 2) { "A connector pair must be two characters. Given: '${pair}'." }
            return Connector(pair[0], pair[1])
        }

        fun fromStrings(pairs: List<String>) : Array<Connector> = pairs.map { fromString(it) } .toTypedArray()
    }
}
