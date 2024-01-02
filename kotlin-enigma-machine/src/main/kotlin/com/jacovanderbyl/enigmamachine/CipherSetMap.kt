package com.jacovanderbyl.enigmamachine

/**
 * Represents a map between the Enigma Machine character set and a given cipher set.
 * Used by reflectors and rotors to do letter-substitution.
 *
 * E.g.:
 *     Enigma Character Set: ABCDEFGHIJKLMNOPQRSTUVWXYZ
 *                           ↕↕↕↕↕↕↕↕↕↕↕↕↕↕↕↕↕↕↕↕↕↕↕↕↕↕
 *     Cipher Set:           EKMFLGDQVZNTOWYHXUSPAIBRCJ
 */
class CipherSetMap(val cipherSet: String) {
    val characterSet: String = Letter.characterSet()

    init {
        require(String(cipherSet.toCharArray().apply { sort() }) == characterSet) {
            "Invalid cipher set. The provided cipher set '$cipherSet' does not map to: '${characterSet}'."
        }
    }

    fun encipher(character: Char, reverse: Boolean = false) : Char {
        require(character in characterSet) {
            "Invalid character. Valid: '${characterSet}'. Given: '${character}'."
        }

        return when(reverse) {
            false -> cipherSet[characterSet.indexOf(character)]
            true -> characterSet[cipherSet.indexOf(character)]
        }
    }
}
