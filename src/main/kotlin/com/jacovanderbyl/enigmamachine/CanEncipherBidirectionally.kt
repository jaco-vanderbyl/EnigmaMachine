package com.jacovanderbyl.enigmamachine

interface CanEncipherBidirectionally {
    fun encipher(character: Char, reverse: Boolean = false) : Char
}
