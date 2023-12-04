package com.jacovanderbyl.enigmamachine

/**
 * Represents a base reflector that can substitute a letter for another given a simple cipher set map.
 */
class Reflector(
    val type: ReflectorType,
    private val cipherSetMap: CipherSetMap,
    private val compatibility: Set<EnigmaType>
) : CanEncipher, HasCompatibility {
    override fun encipher(character: Char) : Char = cipherSetMap.encipher(character)

    override fun isCompatible(enigmaType: EnigmaType) : Boolean = enigmaType in compatibility
}
