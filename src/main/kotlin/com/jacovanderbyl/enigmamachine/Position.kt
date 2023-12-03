package com.jacovanderbyl.enigmamachine

/**
 * Represents a letter position on a rotor.
 */
class Position(val character: Char = 'A') {
    init {
        require(character in Keys.CHARACTER_SET) {
            "The position must linked to a character in: '${Keys.CHARACTER_SET}'. Given: '${character}'."
        }
    }

    val index = Keys.CHARACTER_SET.indexOf(character)

    companion object {
        fun fromString(position: String) : Position {
            require(position.length == 1) {
                "A rotor starting position must be a single character. Given: '${position}'."
            }

            return Position(position[0])
        }
    }
}
