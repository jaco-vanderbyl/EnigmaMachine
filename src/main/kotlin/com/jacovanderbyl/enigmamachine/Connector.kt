package com.jacovanderbyl.enigmamachine

/**
 * Represents a connector, connecting one character with another.
 *
 * E.g.: a plugboard cable connecting 'A' and 'B'.
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
        fun fromString(characterPair: String) : Connector {
            require(characterPair.length == 2) {
                "Invalid string length. A connector pair must be two characters. Given: '${characterPair}'."
            }
            return Connector(characterPair[0], characterPair[1])
        }

        fun fromStrings(characterPairs: List<String>) : Array<Connector> = characterPairs.map {
            fromString(it)
        } .toTypedArray()
    }
}
