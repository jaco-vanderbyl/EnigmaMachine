package com.jacovanderbyl.enigmamachine

class RotorI :
    Rotor(CipherSetMap("EKMFLGDQVZNTOWYHXUSPAIBRCJ"), Notch(setOf('Q'))),
    CompatibleWithEnigmaI,
    CompatibleWithEnigmaM3
class RotorII :
    Rotor(CipherSetMap("AJDKSIRUXBLHWTMCQGZNPYFVOE"), Notch(setOf('E'))),
    CompatibleWithEnigmaI,
    CompatibleWithEnigmaM3
class RotorIII :
    Rotor(CipherSetMap("BDFHJLCPRTXVZNYEIWGAKMUSQO"), Notch(setOf('V'))),
    CompatibleWithEnigmaI,
    CompatibleWithEnigmaM3
class RotorIV :
    Rotor(CipherSetMap("ESOVPZJAYQUIRHXLNFTGKDCMWB"), Notch(setOf('J'))),
    CompatibleWithEnigmaI,
    CompatibleWithEnigmaM3
class RotorV :
    Rotor(CipherSetMap("VZBRGITYUPSDNHLXAWMJQOFECK"), Notch(setOf('Z'))),
    CompatibleWithEnigmaI,
    CompatibleWithEnigmaM3
class RotorVI :
    Rotor(CipherSetMap("JPGVOUMFYQBENHZRDKASXLICTW"), Notch(setOf('Z', 'M'))),
    CompatibleWithEnigmaM3
class RotorVII :
    Rotor(CipherSetMap("NZJHGRCXMYSWBOUFAIVLPEKQDT"), Notch(setOf('Z', 'M'))),
    CompatibleWithEnigmaM3
class RotorVIII :
    Rotor(CipherSetMap("FKQHTLXOCBJSPDZRAMEWNIUYGV"), Notch(setOf('Z', 'M'))),
    CompatibleWithEnigmaM3
