package com.jacovanderbyl.enigmamachine

/**
 * Serves as Reflector Factory.
 */
enum class ReflectorType {
    REFLECTOR_B {
        override fun create() : Reflector = Reflector(
            type = REFLECTOR_B,
            cipherSetMap = CipherSetMap("YRUHQSLDPXNGOKMIEBFZCWVJAT"),
            compatibility = setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3)
        )
    },
    REFLECTOR_C {
        override fun create() : Reflector = Reflector(
            type = REFLECTOR_C,
            cipherSetMap = CipherSetMap("FVPJIAOYEDRZXWGCTKUQSBNMHL"),
            compatibility = setOf(EnigmaType.ENIGMA_I, EnigmaType.ENIGMA_M3)
        )
    },
    REFLECTOR_B_THIN {
        override fun create() : Reflector = Reflector(
            type = REFLECTOR_B_THIN,
            cipherSetMap = CipherSetMap("ENKQAUYWJICOPBLMDXZVFTHRGS"),
            compatibility = setOf(EnigmaType.ENIGMA_M4)
        )
    },
    REFLECTOR_C_THIN {
        override fun create() : Reflector = Reflector(
            type = REFLECTOR_C_THIN,
            cipherSetMap = CipherSetMap("RDOBJNTKVEHMLFCWZAXGYIPSUQ"),
            compatibility = setOf(EnigmaType.ENIGMA_M4)
        )
    };

    abstract fun create() : Reflector

    companion object {
        fun list() : List<String> = entries.map { it.name }
    }
}
