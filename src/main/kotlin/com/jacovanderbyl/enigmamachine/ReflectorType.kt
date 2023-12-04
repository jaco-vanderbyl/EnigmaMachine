package com.jacovanderbyl.enigmamachine

/**
 * Serves as Reflector Factory.
 */
enum class ReflectorType {
    B {
        override fun create() = Reflector(
            cipherSetMap = CipherSetMap("YRUHQSLDPXNGOKMIEBFZCWVJAT"),
            type = ReflectorType.B,
            compatibility = setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3)
        )
    },
    C {
        override fun create() = Reflector(
            cipherSetMap = CipherSetMap("FVPJIAOYEDRZXWGCTKUQSBNMHL"),
            type = ReflectorType.C,
            compatibility = setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3)
        )
    };

    abstract fun create() : Reflector
}
