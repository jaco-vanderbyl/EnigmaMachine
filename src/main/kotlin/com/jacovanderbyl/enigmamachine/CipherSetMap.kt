package com.jacovanderbyl.enigmamachine

/**
 * Represents a map between two character sets, used for substituting one character with another.
 *
 * E.g.:
 *     Character set: ABCDEFGHIJKLMNOPQRSTUVWXYZ
 *                    ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
 *     Cipher set:    EKMFLGDQVZNTOWYHXUSPAIBRCJ
 */
class CipherSetMap(private val cipherSet: String) : CanEncipherBidirectionally {
    val characterSet: String = Enigma.CHARACTER_SET

    init {
        require(String(cipherSet.toCharArray().apply { sort() }) == characterSet) {
            "The provided cipher set '$cipherSet' does not map to: '${characterSet}'."
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
