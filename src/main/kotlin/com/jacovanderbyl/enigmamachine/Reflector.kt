package com.jacovanderbyl.enigmamachine

/**
 * Represents a base reflector that can substitute a letter for another given a simple cipher set map.
 */
abstract class Reflector(cipherSetMap: CipherSetMap) : CanEncipher {
    private val characterSet: String = cipherSetMap.characterSet
    private val cipherSet: String = cipherSetMap.cipherSet

    override fun encipher(character: Char) : Char = cipherSet[characterSet.indexOf(character)]
}
