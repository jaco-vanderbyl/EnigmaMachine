package com.jacovanderbyl.enigmamachine

import com.jacovanderbyl.enigmamachine.log.Logger
import com.jacovanderbyl.enigmamachine.log.ReflectorLog

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
    val characterSet: String = cipherSetMap.characterSet
    val cipherSet: String = cipherSetMap.cipherSet
    private var logger: Logger? = null

    override fun encipher(character: Char) : Char {
        val substituteCharacter = cipherSetMap.encipher(character)
        logger?.add(ReflectorLog.fromReflector(character, substituteCharacter, reflector = this))
        return substituteCharacter
    }

    override fun isCompatible(enigmaType: EnigmaType) : Boolean = enigmaType in compatibility

    fun addLogger(logger: Logger) {
        this.logger = logger
    }
}
