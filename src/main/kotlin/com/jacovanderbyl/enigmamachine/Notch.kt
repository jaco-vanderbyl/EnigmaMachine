package com.jacovanderbyl.enigmamachine

/**
 * Represents one or more notches on a rotor.
 */
class Notch(val characters: Set<Char>) {
    init {
        require(characters.size in 1..Enigma.CHARACTER_SET.length) {
            "Invalid character count. A rotor cannot have more notches than the character set" +
                    "size: '${Enigma.CHARACTER_SET.length}'. Given: '${characters.size}'."
        }
        characters.forEach {
            require(it in Enigma.CHARACTER_SET) {
                "Invalid character. Valid: '${Enigma.CHARACTER_SET}'. Given: '${it}'."
            }
        }
    }
}
