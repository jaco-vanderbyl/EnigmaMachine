# Kotlin Enigma Machine
Kotlin library that simulates Enigma Machine enciphering.

Supports the following models:
* **_Wehrmacht_ Enigma I** — Most common wartime Enigma, used by the German army.
* **_Kriegsmarine_ Enigma M3** — Modified Enigma I model, used by German navy.
* **_Kriegsmarine_ Enigma M4** — More secure version of M3, used by German navy, particularly in their U-boats

From [wikipedia](https://en.wikipedia.org/wiki/Enigma_machine):
> The Enigma machine is a cipher device developed and used in the early- to mid-20th century to protect commercial,
> diplomatic, and military communication. It was employed extensively by Nazi Germany during World War II, in all
> branches of the German military. The Enigma machine was considered so secure that it was used to encipher the most
> top-secret messages.
>
> The Enigma has an electromechanical rotor mechanism that scrambles the 26 letters of the alphabet. In typical use,
> one person enters text on the Enigma's keyboard and another person writes down which of the 26 lights above the
> keyboard illuminated at each key press. If plain text is entered, the illuminated letters are the ciphertext.
> Entering ciphertext transforms it back into readable plaintext. The rotor mechanism changes the electrical
> connections between the keys and the lights with each keypress.

## Requirements
* [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

## Build the project
It is recommended to open the project in the latest released version of
[Intellij IDEA](https://www.jetbrains.com/idea/download) (Community or Ultimate Edition), which will
automatically install the appropriate version of Gradle — the required build tool for the project.

### IntelliJ IDEA
Clone the repo and open the project in IntelliJ IDEA. The IDE will automatically build the project.

### Gradle Wrapper
To build manually, clone the repo, change to the project root directory, and run the following command from console:
```
$ ./gradlew build
```
In Windows:
```
$ .\gradlew.bat build
```
_The Gradle Wrapper is the preferred way of starting a Gradle build. The Wrapper downloads (if needed) and then 
invokes a specific version of Gradle declared in the build._

## Library usage
### List available Enigma Machines, Reflectors, Rotors, Ring Settings, and Positions
```kotlin
val enigmaTypes = EnigmaType.list()
val reflectorTypes = ReflectorType.list()
val rotorTypes = RotorType.list()
val ringSettings = RingSetting.list()
val positions = Position.list()

println(enigmaTypes)    // prints: [ENIGMA_I, ENIGMA_M3, ENIGMA_M4]
println(reflectorTypes) // prints: [REFLECTOR_B, REFLECTOR_C, REFLECTOR_B_THIN, REFLECTOR_C_THIN]
println(rotorTypes)     // prints: [ROTOR_I, ROTOR_II, ROTOR_III, ROTOR_IV, ROTOR_V, ROTOR_VI, ROTOR_VII, ROTOR_VIII, ROTOR_BETA, ROTOR_GAMMA]
println(ringSettings)   // prints: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26]
println(positions)      // prints: [A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z]
```

### Make 'stock' Enigma Machine
'Stock' Enigma Machine is here defined as having:
* B-reflector
* I, II and III rotors (from left to right), III being the entry rotor
* All ring settings set to default, i.e. '1'
* All rotor start positions set to default, i.e. 'A'
* No plugboard connectors

```kotlin
val enigmaI = EnigmaBuilder.make(
    type = "ENIGMA_I",
    reflector = "REFLECTOR_B",
    rotors = "ROTOR_I, ROTOR_II, ROTOR_III"
)

// Note that type prefixes ('ENIGMA_', 'REFLECTOR', 'ROTOR_') may be omitted.
val enigmaM3 = EnigmaBuilder.make(
    type = "M3",
    reflector = "B",
    rotors = "I,II,III"
)
```

### Make with an arrangement of rotors, ring settings, starting positions, and a reflector
```kotlin
val enigmaI = EnigmaBuilder.make(
    type = "ENIGMA_I",
    reflector = "C",
    rotors = "I,V,III",
    ringSettings = "14,9,24",
    startingPositions = "W,N,Y"
)
```

### Make with plugboard connectors
```kotlin
val enigmaI = EnigmaBuilder.make(
    type = "ENIGMA_I",
    reflector = "C",
    rotors = "I,V,III",
    ringSettings = "14,9,24",
    startingPositions = "W,N,Y",
    plugboardConnectors = "SZ,GT,DV,KU,FO,MY,EW,JN,IX,LQ"
)
```

### Encipher plaintext and 'decipher' ciphertext
```kotlin
val firstEnigmaI = EnigmaBuilder.make(
    type = "ENIGMA_I",
    reflector = "B",
    rotors = "I,II,III"
)

val secondEnigmaI = EnigmaBuilder.make(
    type = "ENIGMA_I",
    reflector = "B",
    rotors = "I,II,III"
)

val plaintextInput = "AAAAA"
val ciphertext = firstEnigmaI.encipher(plaintextInput)
val plaintext = secondEnigmaI.encipher(ciphertext)

println(plaintextInput) // prints: AAAAA
println(ciphertext)     // prints: BDZGO
println(plaintext)      // prints: AAAAA
```

### In-memory logs of internal workings
#### Print letter substitution journey
```kotlin
val enigma = EnigmaBuilder.make(
    type = "ENIGMA_I",
    reflector = "B",
    rotors = "I,II,III"
)

enigma.encipher('A')
enigma.logger?.print(LogType.SUBSTITUTE)
```
Prints:
```
Log Types requested for printing : [SUBSTITUTE].
Log Types available for printing : [STEP, SUBSTITUTE, SHIFT, DE_SHIFT].

LOG TYPE   | RESULT       | ACTOR            | INFO
SUBSTITUTE | A -> A       | PLUGBOARD        | No connectors
SUBSTITUTE | B -> D       | ROTOR_III        | Cipher set map: ABCDEFGHIJKLMNOPQRSTUVWXYZ => BDFHJLCPRTXVZNYEIWGAKMUSQO
SUBSTITUTE | C -> D       | ROTOR_II         | Cipher set map: ABCDEFGHIJKLMNOPQRSTUVWXYZ => AJDKSIRUXBLHWTMCQGZNPYFVOE
SUBSTITUTE | D -> F       | ROTOR_I          | Cipher set map: ABCDEFGHIJKLMNOPQRSTUVWXYZ => EKMFLGDQVZNTOWYHXUSPAIBRCJ
SUBSTITUTE | F -> S       | REFLECTOR_B      | Cipher set map: ABCDEFGHIJKLMNOPQRSTUVWXYZ => YRUHQSLDPXNGOKMIEBFZCWVJAT
SUBSTITUTE | S -> S       | ROTOR_I          | Cipher set map: EKMFLGDQVZNTOWYHXUSPAIBRCJ => ABCDEFGHIJKLMNOPQRSTUVWXYZ
SUBSTITUTE | S -> E       | ROTOR_II         | Cipher set map: AJDKSIRUXBLHWTMCQGZNPYFVOE => ABCDEFGHIJKLMNOPQRSTUVWXYZ
SUBSTITUTE | F -> C       | ROTOR_III        | Cipher set map: BDFHJLCPRTXVZNYEIWGAKMUSQO => ABCDEFGHIJKLMNOPQRSTUVWXYZ
SUBSTITUTE | B -> B       | PLUGBOARD        | No connectors
```

#### Print all log types - example 1
```kotlin
val enigma = EnigmaBuilder.make(
    type = "ENIGMA_I",
    reflector = "B",
    rotors = "I,II,III"
)

enigma.encipher('A')
enigma.logger?.print()
```
Prints:
```
Log Types requested for printing : [STEP, SUBSTITUTE, SHIFT, DE_SHIFT].
Log Types available for printing : [STEP, SUBSTITUTE, SHIFT, DE_SHIFT].

LOG TYPE   | RESULT       | ACTOR            | INFO
STEP       | AAA -> AAB   | ENIGMA_I         | Rotor types: ROTOR_I—ROTOR_II—ROTOR_III; Notch characters: [Q]—[E]—[V]
SUBSTITUTE | A -> A       | PLUGBOARD        | No connectors
SHIFT      | A .. B       | ROTOR_III        | Rotor offset: 1 = offset 1 (Position B) minus offset 0 (Ring Setting 1)
SUBSTITUTE | B -> D       | ROTOR_III        | Cipher set map: ABCDEFGHIJKLMNOPQRSTUVWXYZ => BDFHJLCPRTXVZNYEIWGAKMUSQO
DE_SHIFT   | D .. C       | ROTOR_III        | Rotor offset: 1
SHIFT      | C .. C       | ROTOR_II         | Rotor offset: 0 = offset 0 (Position A) minus offset 0 (Ring Setting 1)
SUBSTITUTE | C -> D       | ROTOR_II         | Cipher set map: ABCDEFGHIJKLMNOPQRSTUVWXYZ => AJDKSIRUXBLHWTMCQGZNPYFVOE
DE_SHIFT   | D .. D       | ROTOR_II         | Rotor offset: 0
SHIFT      | D .. D       | ROTOR_I          | Rotor offset: 0 = offset 0 (Position A) minus offset 0 (Ring Setting 1)
SUBSTITUTE | D -> F       | ROTOR_I          | Cipher set map: ABCDEFGHIJKLMNOPQRSTUVWXYZ => EKMFLGDQVZNTOWYHXUSPAIBRCJ
DE_SHIFT   | F .. F       | ROTOR_I          | Rotor offset: 0
SUBSTITUTE | F -> S       | REFLECTOR_B      | Cipher set map: ABCDEFGHIJKLMNOPQRSTUVWXYZ => YRUHQSLDPXNGOKMIEBFZCWVJAT
SHIFT      | S .. S       | ROTOR_I          | Rotor offset: 0 = offset 0 (Position A) minus offset 0 (Ring Setting 1)
SUBSTITUTE | S -> S       | ROTOR_I          | Cipher set map: EKMFLGDQVZNTOWYHXUSPAIBRCJ => ABCDEFGHIJKLMNOPQRSTUVWXYZ
DE_SHIFT   | S .. S       | ROTOR_I          | Rotor offset: 0
SHIFT      | S .. S       | ROTOR_II         | Rotor offset: 0 = offset 0 (Position A) minus offset 0 (Ring Setting 1)
SUBSTITUTE | S -> E       | ROTOR_II         | Cipher set map: AJDKSIRUXBLHWTMCQGZNPYFVOE => ABCDEFGHIJKLMNOPQRSTUVWXYZ
DE_SHIFT   | E .. E       | ROTOR_II         | Rotor offset: 0
SHIFT      | E .. F       | ROTOR_III        | Rotor offset: 1 = offset 1 (Position B) minus offset 0 (Ring Setting 1)
SUBSTITUTE | F -> C       | ROTOR_III        | Cipher set map: BDFHJLCPRTXVZNYEIWGAKMUSQO => ABCDEFGHIJKLMNOPQRSTUVWXYZ
DE_SHIFT   | C .. B       | ROTOR_III        | Rotor offset: 1
SUBSTITUTE | B -> B       | PLUGBOARD        | No connectors
```

#### Print all log types - example 2
```kotlin
val enigma = EnigmaBuilder.make(
    type = "ENIGMA_M4",
    reflector = "C_THIN",
    rotors = "GAMMA,VI,V,VIII",
    ringSettings = "5,14,9,24",
    startingPositions = "P,W,N,B",
    plugboardConnectors = "SZ,GT,DV,KU,FO,MY,EW,JN,IX,LQ"
)

enigma.encipher('A')
enigma.logger?.print()
```
Prints:
```
Log Types requested for printing : [STEP, SUBSTITUTE, SHIFT, DE_SHIFT].
Log Types available for printing : [STEP, SUBSTITUTE, SHIFT, DE_SHIFT].

LOG TYPE   | RESULT       | ACTOR            | INFO
STEP       | PWNB -> PWNC | ENIGMA_M4        | Rotor types: ROTOR_GAMMA—ROTOR_VI—ROTOR_V—ROTOR_VIII; Notch characters: [_]—[Z, M]—[Z]—[Z, M]
SUBSTITUTE | A -> A       | PLUGBOARD        | Connectors: SZ GT DV KU FO MY EW JN IX LQ 
SHIFT      | A .. F       | ROTOR_VIII       | Rotor offset: -21 = offset 2 (Position C) minus offset 23 (Ring Setting 24)
SUBSTITUTE | F -> L       | ROTOR_VIII       | Cipher set map: ABCDEFGHIJKLMNOPQRSTUVWXYZ => FKQHTLXOCBJSPDZRAMEWNIUYGV
DE_SHIFT   | L .. G       | ROTOR_VIII       | Rotor offset: -21
SHIFT      | G .. L       | ROTOR_V          | Rotor offset: 5 = offset 13 (Position N) minus offset 8 (Ring Setting 9)
SUBSTITUTE | L -> D       | ROTOR_V          | Cipher set map: ABCDEFGHIJKLMNOPQRSTUVWXYZ => VZBRGITYUPSDNHLXAWMJQOFECK
DE_SHIFT   | D .. Y       | ROTOR_V          | Rotor offset: 5
SHIFT      | Y .. H       | ROTOR_VI         | Rotor offset: 9 = offset 22 (Position W) minus offset 13 (Ring Setting 14)
SUBSTITUTE | H -> F       | ROTOR_VI         | Cipher set map: ABCDEFGHIJKLMNOPQRSTUVWXYZ => JPGVOUMFYQBENHZRDKASXLICTW
DE_SHIFT   | F .. W       | ROTOR_VI         | Rotor offset: 9
SHIFT      | W .. H       | ROTOR_GAMMA      | Rotor offset: 11 = offset 15 (Position P) minus offset 4 (Ring Setting 5)
SUBSTITUTE | H -> E       | ROTOR_GAMMA      | Cipher set map: ABCDEFGHIJKLMNOPQRSTUVWXYZ => FSOKANUERHMBTIYCWLQPZXVGJD
DE_SHIFT   | E .. T       | ROTOR_GAMMA      | Rotor offset: 11
SUBSTITUTE | T -> G       | REFLECTOR_C_THIN | Cipher set map: ABCDEFGHIJKLMNOPQRSTUVWXYZ => RDOBJNTKVEHMLFCWZAXGYIPSUQ
SHIFT      | G .. R       | ROTOR_GAMMA      | Rotor offset: 11 = offset 15 (Position P) minus offset 4 (Ring Setting 5)
SUBSTITUTE | R -> I       | ROTOR_GAMMA      | Cipher set map: FSOKANUERHMBTIYCWLQPZXVGJD => ABCDEFGHIJKLMNOPQRSTUVWXYZ
DE_SHIFT   | I .. X       | ROTOR_GAMMA      | Rotor offset: 11
SHIFT      | X .. G       | ROTOR_VI         | Rotor offset: 9 = offset 22 (Position W) minus offset 13 (Ring Setting 14)
SUBSTITUTE | G -> C       | ROTOR_VI         | Cipher set map: JPGVOUMFYQBENHZRDKASXLICTW => ABCDEFGHIJKLMNOPQRSTUVWXYZ
DE_SHIFT   | C .. T       | ROTOR_VI         | Rotor offset: 9
SHIFT      | T .. Y       | ROTOR_V          | Rotor offset: 5 = offset 13 (Position N) minus offset 8 (Ring Setting 9)
SUBSTITUTE | Y -> H       | ROTOR_V          | Cipher set map: VZBRGITYUPSDNHLXAWMJQOFECK => ABCDEFGHIJKLMNOPQRSTUVWXYZ
DE_SHIFT   | H .. C       | ROTOR_V          | Rotor offset: 5
SHIFT      | C .. H       | ROTOR_VIII       | Rotor offset: -21 = offset 2 (Position C) minus offset 23 (Ring Setting 24)
SUBSTITUTE | H -> D       | ROTOR_VIII       | Cipher set map: FKQHTLXOCBJSPDZRAMEWNIUYGV => ABCDEFGHIJKLMNOPQRSTUVWXYZ
DE_SHIFT   | D .. Y       | ROTOR_VIII       | Rotor offset: -21
SUBSTITUTE | Y -> M       | PLUGBOARD        | Connectors: SZ GT DV KU FO MY EW JN IX LQ 
```
#### Print rotor stepping
```kotlin
val enigma = EnigmaBuilder.make(
    type = "ENIGMA_I",
    reflector = "B",
    rotors = "I,II,III",
    startingPositions = "A,D,S"
)

enigma.logger?.restrictTo(LogType.STEP) // Only record STEP logs to avoid the max log size limit.
enigma.encipher("AAAAAA")
enigma.logger?.print(LogType.STEP)
```
Prints:
```
Log Types requested for printing : [STEP].
Log Types available for printing : [STEP].

LOG TYPE   | RESULT       | ACTOR            | INFO
STEP       | ADS -> ADT   | ENIGMA_I         | Rotor types: ROTOR_I—ROTOR_II—ROTOR_III; Notch characters: [Q]—[E]—[V]
STEP       | ADT -> ADU   | ENIGMA_I         | Rotor types: ROTOR_I—ROTOR_II—ROTOR_III; Notch characters: [Q]—[E]—[V]
STEP       | ADU -> ADV   | ENIGMA_I         | Rotor types: ROTOR_I—ROTOR_II—ROTOR_III; Notch characters: [Q]—[E]—[V]
STEP       | ADV -> AEW   | ENIGMA_I         | Rotor types: ROTOR_I—ROTOR_II—ROTOR_III; Notch characters: [Q]—[E]—[V]
STEP       | AEW -> BFX   | ENIGMA_I         | Rotor types: ROTOR_I—ROTOR_II—ROTOR_III; Notch characters: [Q]—[E]—[V]
STEP       | BFX -> BFY   | ENIGMA_I         | Rotor types: ROTOR_I—ROTOR_II—ROTOR_III; Notch characters: [Q]—[E]—[V]
```