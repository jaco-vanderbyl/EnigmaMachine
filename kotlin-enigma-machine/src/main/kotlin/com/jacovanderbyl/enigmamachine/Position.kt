package com.jacovanderbyl.enigmamachine

/**
 * Represents a letter position on a rotor.
 */
class Position(val character: Char = 'A') {
    init {
        require(character in Enigma.CHARACTER_SET) {
            "Invalid character. Valid: '${Enigma.CHARACTER_SET}'. Given: '${character}'."
        }
    }

    val index = Enigma.CHARACTER_SET.indexOf(character)

    companion object {
        fun list() : List<Char> = Enigma.CHARACTER_SET.map { it }

        fun fromString(position: String) : Position {
            require(position.length == 1) {
                "Invalid string length. A rotor starting position must be a single character. Given: '${position}'."
            }

            return Position(position[0])
        }
    }
}
