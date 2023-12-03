package com.jacovanderbyl.enigmamachine

/**
 * Represents a connector, connecting one character with another.
 *
 * E.g.: a plugboard cable connecting 'A' and 'B'.
 */
class Connector(val first: Char, val second: Char) {
    init {
        require(first in Keys.CHARACTER_SET) {
            "First character is invalid. Character must be in: '${Keys.CHARACTER_SET}'. Given: '${first}'."
        }
        require(second in Keys.CHARACTER_SET) {
            "Second character is invalid. Character must be in: '${Keys.CHARACTER_SET}'. Given: '${second}'."
        }
        require(first != second) {
            "The first and second characters cannot be the same. Given: '${first}${second}'."
        }
    }

    companion object {
        fun fromString(characterPair: String) : Connector {
            require(characterPair.length == 2) {
                "A connector pair must be two characters. Given: '${characterPair}'."
            }
            return Connector(characterPair[0], characterPair[1])
        }

        fun fromStrings(characterPairs: List<String>) : Array<Connector> = characterPairs.map {
            fromString(it)
        } .toTypedArray()
    }
}
