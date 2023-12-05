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
        fun fromString(characterPair: String) : Connector {
            require(characterPair.length == 2) {
                "Invalid string length. A connector pair must be two characters. Given: '${characterPair}'."
            }
            return Connector(characterPair[0], characterPair[1])
        }

        fun fromStrings(characterPairs: List<String>) : Array<Connector> = characterPairs.map {
            fromString(it)
        }.toTypedArray()
    }
}
