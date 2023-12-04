package com.jacovanderbyl.enigmamachine

/**
 * Represents a notch (at one or more characters) on a rotor.
 */
class Notch(val characters: Set<Char>) {
    init {
        require(characters.size in 1..Keys.CHARACTER_SET.length) {
            "A rotor cannot have more notches than the character set size: '${Keys.CHARACTER_SET.length}'. " +
                    "Given: '${characters.size}'."
        }
        characters.forEach {
            require(it in Keys.CHARACTER_SET) {
                "Invalid notch character. Valid: '${Keys.CHARACTER_SET}'. Given: '${it}'."
            }
        }
    }
}
