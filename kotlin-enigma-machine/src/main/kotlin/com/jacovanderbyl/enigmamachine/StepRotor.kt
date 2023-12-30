package com.jacovanderbyl.enigmamachine

/**
 * Represents a rotor that can step (turn one position at a time).
 *
 * A single rotor performs a simple letter-substitution, but multiple rotors are mounted side by side on a spindle in
 * the rotor unit, and the rotors turn with every letter substitution. From wikipedia: Enigma's security comes
 * from using several rotors in series (usually three or four) and the regular stepping movement of the rotors,
 * thus implementing a polyalphabetic substitution cipher.
 */
class StepRotor(
    type: RotorType,
    cipherSetMap: CipherSetMap,
    val notch: Notch,
    compatibility: Set<EnigmaType>,
    position: Letter,
    ringSetting: Ring
) : Rotor(type, cipherSetMap, compatibility, position, ringSetting) {
    fun step() {
        val stepToCharacter = characterSet[shiftIndex(position.index, shiftBy = 1)]
        position = Letter.valueOf(stepToCharacter.toString())
    }

    fun isInNotchedPosition() : Boolean = position.character in notch.positions.map { it.character }
}
