package com.jacovanderbyl.enigmamachine

import com.jacovanderbyl.enigmamachine.log.Log
import com.jacovanderbyl.enigmamachine.log.Logger

/**
 * Represents a reflector, used to substitute a letter for another given a cipher set map.
 *
 * From wikipedia: The reflector connected outputs of the last rotor in pairs, redirecting current back through the
 * rotors by a different route. The reflector ensured that Enigma would be self-reciprocal; thus, with two identically
 * configured machines, a message could be encrypted on one and decrypted on the other
 */
class Reflector(
    val type: ReflectorType,
    private val cipherSetMap: CipherSetMap,
    private val compatibility: Set<EnigmaType>
) : CanEncipher, HasCompatibility {
    override fun encipher(character: Char) : Char {
        val substituteCharacter = cipherSetMap.encipher(character)
        Logger.add(Log.ReflectorSubstitute.create(character, substituteCharacter, reflector = this))
        return substituteCharacter
    }

    override fun isCompatible(enigmaType: EnigmaType) : Boolean = enigmaType in compatibility

    fun getCipherSetMaps() : Pair<String,String> = cipherSetMap.characterSet to cipherSetMap.cipherSet
}
