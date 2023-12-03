package com.jacovanderbyl.enigmamachine

enum class ReflectorFactory {
    B {
        override fun create() = Reflector(
            cipherSetMap = CipherSetMap("YRUHQSLDPXNGOKMIEBFZCWVJAT"),
            type = ReflectorFactory.B,
            compatibility = setOf(EnigmaFactory.ENIGMA_I, EnigmaFactory.ENIGMA_M3)
        )
    },
    C {
        override fun create() = Reflector(
            cipherSetMap = CipherSetMap("FVPJIAOYEDRZXWGCTKUQSBNMHL"),
            type = ReflectorFactory.C,
            compatibility = setOf(EnigmaFactory.ENIGMA_I, EnigmaFactory.ENIGMA_M3)
        )
    };

    abstract fun create() : Reflector
}
