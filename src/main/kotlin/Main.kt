import com.jacovanderbyl.enigmamachine.*

fun main() {
    val firstEnigmaI = EnigmaBuilder.makeFromCsv(
        type = "ENIGMA_I",
        reflector = "B",
        rotors = "I,II,III",
        ringSettings = "1,1,1",
        startingPositions = "A,A,A"
    )
    val secondEnigmaI = EnigmaBuilder.makeFromCsv(
        type = "ENIGMA_I",
        reflector = "B",
        rotors = "I,II,III",
        ringSettings = "1,1,1",
        startingPositions = "A,A,A"
    )

    val plaintextInput = "AAAAA"
    val ciphertext = firstEnigmaI.encipher(plaintextInput)
    val plaintext = secondEnigmaI.encipher(ciphertext)

    println(plaintextInput)
    println(ciphertext)
    println(plaintext)
}
