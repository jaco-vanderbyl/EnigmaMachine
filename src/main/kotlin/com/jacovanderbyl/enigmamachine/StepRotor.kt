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
    compatibility: Set<EnigmaType>,
    position: Position,
    ringSetting: RingSetting,
    private val notch: Notch
) : Rotor(type, cipherSetMap, compatibility, position, ringSetting) {
    fun step() {
        position = Position(characterSet[shiftIndex(position.index, shiftBy = 1)])
    }

    fun isInNotchedPosition() : Boolean = position.character in notch.characters
}
