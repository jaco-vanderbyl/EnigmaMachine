package com.jacovanderbyl.enigmamachine

/**
 * Represents the Enigma Machine's character set.
 * E.g., the keys on the keyboard, the positions on a rotor, the notches on a rotor, the plugboard ports.
 */
enum class Letter(val character: Char, val index: Int) {
    A('A', 0),
    B('B', 1),
    C('C', 2),
    D('D', 3),
    E('E', 4),
    F('F', 5),
    G('G', 6),
    H('H', 7),
    I('I', 8),
    J('J', 9),
    K('K', 10),
    L('L', 11),
    M('M', 12),
    N('N', 13),
    O('O', 14),
    P('P', 15),
    Q('Q', 16),
    R('R', 17),
    S('S', 18),
    T('T', 19),
    U('U', 20),
    V('V', 21),
    W('W', 22),
    X('X', 23),
    Y('Y', 24),
    Z('Z', 25);

    companion object {
        fun list() : List<Char> = entries.map { it.character }
        fun characterSet() : String = list().joinToString("")
    }
}
