package com.jacovanderbyl.enigmamachine

/**
 * Represents a notch on a rotor at a specific position.
 *
 * A rotor can have one or more notches.
 */
data class Notch(val positions: Set<Letter>)
