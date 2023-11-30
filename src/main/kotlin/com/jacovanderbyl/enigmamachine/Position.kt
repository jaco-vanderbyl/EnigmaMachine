package com.jacovanderbyl.enigmamachine

/**
 * Represents a letter position on a rotor.
 */
class Position(val character: Char) {
    init {
        require(character in Keys.CHARACTER_SET) {
            "The position must linked to a character in: '${Keys.CHARACTER_SET}'. Given: '${character}'."
        }
    }

    val index = Keys.CHARACTER_SET.indexOf(character)

    companion object {
        fun fromStrings(positions: List<String>) : Array<Position> {
            positions.forEach { require(it.length == 1) {
                "A rotor starting position must be a single character. Given: '${it}'."
            }}

            return positions.map { Position(it[0]) }.toTypedArray()
        }
    }
}
