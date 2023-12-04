package com.jacovanderbyl.enigmamachine

/**
 * Represents a base reflector that can substitute a letter for another given a simple cipher set map.
 */
class Reflector(
    private val cipherSetMap: CipherSetMap,
    val type: ReflectorType,
    val compatibility: Set<EnigmaType>
) : CanEncipher, HasCompatibility {
    override fun encipher(character: Char) : Char = cipherSetMap.encipher(character)

    override fun isCompatible(enigmaType: EnigmaType) : Boolean = enigmaType in compatibility
}
