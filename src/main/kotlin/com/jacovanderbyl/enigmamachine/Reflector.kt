package com.jacovanderbyl.enigmamachine

/**
 * Represents a base reflector that can substitute a letter for another given a simple cipher set map.
 */
class Reflector(
    cipherSetMap: CipherSetMap,
    val type: ReflectorType,
    val compatibility: Set<EnigmaType>
) : CanEncipher, HasCompatibility {
    private val characterSet: String = cipherSetMap.characterSet
    private val cipherSet: String = cipherSetMap.cipherSet

    override fun encipher(character: Char) : Char {
        require(character in Keys.CHARACTER_SET) {
            "Invalid character. Valid: '${Keys.CHARACTER_SET}'. Given: '${character}'."
        }

        return cipherSet[characterSet.indexOf(character)]
    }

    override fun isCompatible(enigmaType: EnigmaType) : Boolean = enigmaType in compatibility
}
