package com.jacovanderbyl.enigmamachine

/**
 * Represents a map between two character sets, used for substituting one character with another.
 *
 * E.g.:
 *     Character set: ABCDEFGHIJKLMNOPQRSTUVWXYZ
 *                    ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
 *     Cipher set:    EKMFLGDQVZNTOWYHXUSPAIBRCJ
 */
class CipherSetMap(val cipherSet: String) {
    val characterSet: String = Keys.CHARACTER_SET

    init {
        require(String(cipherSet.toCharArray().apply { sort() }) == characterSet) {
            "The provided cipher set '$cipherSet' does not map to: '${characterSet}'."
        }
    }
}
