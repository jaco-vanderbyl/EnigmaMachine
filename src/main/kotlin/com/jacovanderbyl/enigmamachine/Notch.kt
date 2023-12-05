package com.jacovanderbyl.enigmamachine

/**
 * Represents a notch on a rotor at a specific position.
 *
 * A rotor can have one or more notches.
 */
class Notch(vararg positions: Position) {
    val characters = positions.map { it.character }.toSet()

    init {
        require(positions.size in 1..Enigma.CHARACTER_SET.length) {
            "Invalid character count. A rotor cannot have more notches than the character set size: " +
                    "'${Enigma.CHARACTER_SET.length}'. Given: '${positions.size}'."
        }
    }
}
