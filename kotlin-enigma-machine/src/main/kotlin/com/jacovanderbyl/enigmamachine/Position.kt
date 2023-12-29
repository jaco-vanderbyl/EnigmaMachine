package com.jacovanderbyl.enigmamachine

/**
 * Represents a letter position on a rotor.
 */
class Position(val character: Char = 'A') {
    val index = Enigma.CHARACTER_SET.indexOf(character)

    init {
        require(character in Enigma.CHARACTER_SET) {
            "Invalid character. Valid: '${Enigma.CHARACTER_SET}'. Given: '${character}'."
        }
    }

    companion object {
        const val A = 'A'
        const val B = 'B'
        const val C = 'C'
        const val D = 'D'
        const val E = 'E'
        const val F = 'F'
        const val G = 'G'
        const val H = 'H'
        const val I = 'I'
        const val J = 'J'
        const val K = 'K'
        const val L = 'L'
        const val M = 'M'
        const val N = 'N'
        const val O = 'O'
        const val P = 'P'
        const val Q = 'Q'
        const val R = 'R'
        const val S = 'S'
        const val T = 'T'
        const val U = 'U'
        const val V = 'V'
        const val W = 'W'
        const val X = 'X'
        const val Y = 'Y'
        const val Z = 'Z'

        fun fromString(position: String) : Position {
            require(position.length == 1) {
                "Invalid string length. A rotor starting position must be a single character. Given: '${position}'."
            }

            return Position(position[0])
        }

        fun list() : List<Char> = Enigma.CHARACTER_SET.map { it }
    }
}
