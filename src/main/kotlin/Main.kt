import com.jacovanderbyl.enigmamachine.*

fun main() {
    val enigma = EnigmaBuilder.makeFromCsvValues(
        type = EnigmaFactory.ENIGMA_I.name,
        reflector = "B",
        rotors = "I,II,III",
        ringSettings = "1,1,1",
        startingPositions = "A,A,A",
        plugboardConnectors = ""
    )

    val plaintextInput = "AAAAA"
    val cypher = enigma.encipher(plaintextInput)
    val plaintext = enigma.reset().encipher(cypher)

    println(plaintextInput)
    println(cypher)
    println(plaintext)
}
