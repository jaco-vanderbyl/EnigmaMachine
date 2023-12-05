package com.jacovanderbyl.enigmamachine

/**
 * Represents a map between the Enigma Machine character set and a given cipher set,
 * used to substitute one character for another.
 *
 * E.g.:
 *     Enigma Character Set: ABCDEFGHIJKLMNOPQRSTUVWXYZ
 *                           ↕↕↕↕↕↕↕↕↕↕↕↕↕↕↕↕↕↕↕↕↕↕↕↕↕↕
 *     Cipher Set:           EKMFLGDQVZNTOWYHXUSPAIBRCJ
 */
class CipherSetMap(private val cipherSet: String) : CanEncipherBidirectionally {
    val characterSet: String = Enigma.CHARACTER_SET

    init {
        require(String(cipherSet.toCharArray().apply { sort() }) == characterSet) {
            "Invalid cipher set. The provided cipher set '$cipherSet' does not map to: '${characterSet}'."
        }
    }

    override fun encipher(character: Char, reverse: Boolean) : Char {
        require(character in characterSet) {
            "Invalid character. Valid: '${characterSet}'. Given: '${character}'."
        }

        return when(reverse) {
            false -> cipherSet[characterSet.indexOf(character)]
            true -> characterSet[cipherSet.indexOf(character)]
        }
    }
}
