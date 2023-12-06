package com.jacovanderbyl.enigmamachine

/**
 * Serves as Reflector Factory.
 */
enum class ReflectorType {
    B {
        override fun create() = Reflector(
            type = B,
            cipherSetMap = CipherSetMap("YRUHQSLDPXNGOKMIEBFZCWVJAT"),
            compatibility = setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3)
        )
    },
    C {
        override fun create() = Reflector(
            type = C,
            cipherSetMap = CipherSetMap("FVPJIAOYEDRZXWGCTKUQSBNMHL"),
            compatibility = setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3)
        )
    };

    abstract fun create() : Reflector

    companion object {
        fun list() : List<String> = entries.map { it.name }
    }
}
