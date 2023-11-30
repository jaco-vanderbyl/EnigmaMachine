package com.jacovanderbyl.enigmamachine

interface CanEncipher {
    fun encipher(character: Char) : Char
}
interface CanEncipherBidirectionally {
    fun encipher(character: Char, reverse: Boolean = false) : Char
}
