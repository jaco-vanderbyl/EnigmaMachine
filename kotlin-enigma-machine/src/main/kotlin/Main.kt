import com.jacovanderbyl.enigmamachine.*
import com.jacovanderbyl.enigmamachine.log.LogType
import com.jacovanderbyl.enigmamachine.log.Logger

fun main() {
//    val enigma = EnigmaBuilder.make(
//        type = "ENIGMA_I",
//        reflector = "B",
//        rotors = "I,II,III",
//        startingPositions = "A,D,S"
//    )

//    val enigmaI = EnigmaBuilder.make(
//        type = "ENIGMA_I",
//        reflector = "C",
//        rotors = "I,V,III",
//        ringSettings = "14,9,24",
//        startingPositions = "W,N,Y"
//    )

//    val enigmaI = EnigmaBuilder.make(
//        type = "ENIGMA_I",
//        reflector = "C",
//        rotors = "I,V,III",
//        ringSettings = "14,9,24",
//        startingPositions = "W,N,Y",
//        plugboardConnectors = "SZ,GT,DV,KU,FO,MY,EW,JN,IX,LQ"
//    )

val enigmaI = EnigmaType.ENIGMA_I.create(
    rotorUnit = RotorUnit(
        reflector = ReflectorType.REFLECTOR_C.create(),
        rotors = setOf(
            RotorType.ROTOR_I.create(ringSetting = RingSetting(14), position = Position('W')),
            RotorType.ROTOR_V.create(ringSetting = RingSetting(9), position = Position('N')),
            RotorType.ROTOR_III.create(ringSetting = RingSetting(24), position = Position('Y'))
        )
    ),
    plugboard = Plugboard(
        Connector('A', 'Z'), Connector('G', 'T'), Connector('D', 'V'), Connector('K', 'U'), Connector('F', 'O'),
        Connector('M', 'Y'), Connector('E', 'W'), Connector('J', 'N'), Connector('I', 'X'), Connector('L', 'W')
    )
)
}
