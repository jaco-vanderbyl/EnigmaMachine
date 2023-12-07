package com.jacovanderbyl.enigmamachine

/**
 * Serves as Reflector Factory.
 */
enum class ReflectorType {
    B {
        override fun create() : Reflector = Reflector(
            type = B,
            cipherSetMap = CipherSetMap("YRUHQSLDPXNGOKMIEBFZCWVJAT"),
            compatibility = setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3)
        )
    },
    C {
        override fun create() : Reflector = Reflector(
            type = C,
            cipherSetMap = CipherSetMap("FVPJIAOYEDRZXWGCTKUQSBNMHL"),
            compatibility = setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3)
        )
    },
    B_THIN {
        override fun create() : Reflector = Reflector(
            type = B_THIN,
            cipherSetMap = CipherSetMap("ENKQAUYWJICOPBLMDXZVFTHRGS"),
            compatibility = setOf(EnigmaType.ENIGMA_M4)
        )
    },
    C_THIN {
        override fun create() : Reflector = Reflector(
            type = C_THIN,
            cipherSetMap = CipherSetMap("RDOBJNTKVEHMLFCWZAXGYIPSUQ"),
            compatibility = setOf(EnigmaType.ENIGMA_M4)
        )
    };

    abstract fun create() : Reflector

    companion object {
        fun list() : List<String> = entries.map { it.name }
    }
}
