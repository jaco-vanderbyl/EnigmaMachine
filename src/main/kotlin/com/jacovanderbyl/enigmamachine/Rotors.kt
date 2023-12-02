package com.jacovanderbyl.enigmamachine

class RotorI(position: Position = Position('A'), ringSetting: RingSetting = RingSetting(1)) :
    Rotor(CipherSetMap("EKMFLGDQVZNTOWYHXUSPAIBRCJ"), Notch(setOf('Q')), position, ringSetting),
    CompatibleWithEnigmaI,
    CompatibleWithEnigmaM3
class RotorII(position: Position = Position('A'), ringSetting: RingSetting = RingSetting(1)) :
    Rotor(CipherSetMap("AJDKSIRUXBLHWTMCQGZNPYFVOE"), Notch(setOf('E')), position, ringSetting),
    CompatibleWithEnigmaI,
    CompatibleWithEnigmaM3
class RotorIII(position: Position = Position('A'), ringSetting: RingSetting = RingSetting(1)) :
    Rotor(CipherSetMap("BDFHJLCPRTXVZNYEIWGAKMUSQO"), Notch(setOf('V')), position, ringSetting),
    CompatibleWithEnigmaI,
    CompatibleWithEnigmaM3
class RotorIV(position: Position = Position('A'), ringSetting: RingSetting = RingSetting(1)) :
    Rotor(CipherSetMap("ESOVPZJAYQUIRHXLNFTGKDCMWB"), Notch(setOf('J')), position, ringSetting),
    CompatibleWithEnigmaI,
    CompatibleWithEnigmaM3
class RotorV(position: Position = Position('A'), ringSetting: RingSetting = RingSetting(1)) :
    Rotor(CipherSetMap("VZBRGITYUPSDNHLXAWMJQOFECK"), Notch(setOf('Z')), position, ringSetting),
    CompatibleWithEnigmaI,
    CompatibleWithEnigmaM3
class RotorVI(position: Position = Position('A'), ringSetting: RingSetting = RingSetting(1)) :
    Rotor(CipherSetMap("JPGVOUMFYQBENHZRDKASXLICTW"), Notch(setOf('Z', 'M')), position, ringSetting),
    CompatibleWithEnigmaM3
class RotorVII(position: Position = Position('A'), ringSetting: RingSetting = RingSetting(1)) :
    Rotor(CipherSetMap("NZJHGRCXMYSWBOUFAIVLPEKQDT"), Notch(setOf('Z', 'M')), position, ringSetting),
    CompatibleWithEnigmaM3
class RotorVIII(position: Position = Position('A'), ringSetting: RingSetting = RingSetting(1)) :
    Rotor(CipherSetMap("FKQHTLXOCBJSPDZRAMEWNIUYGV"), Notch(setOf('Z', 'M')), position, ringSetting),
    CompatibleWithEnigmaM3
