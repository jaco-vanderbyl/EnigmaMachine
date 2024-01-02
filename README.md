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
val ringSettings = Rotor.Ring.list()
val positions = Letter.list()

println(enigmaTypes)    // prints: [ENIGMA_I, ENIGMA_M3, ENIGMA_M4]
println(reflectorTypes) // prints: [REFLECTOR_B, REFLECTOR_C, REFLECTOR_B_THIN, REFLECTOR_C_THIN]
println(rotorTypes)     // prints: [ROTOR_I, ROTOR_II, ROTOR_III, ROTOR_IV, ROTOR_V, ROTOR_VI, ROTOR_VII, ROTOR_VIII, ROTOR_BETA, ROTOR_GAMMA]
println(ringSettings)   // prints: [SETTING_1, SETTING_2, SETTING_3, SETTING_4, SETTING_5, SETTING_6, SETTING_7, SETTING_8, SETTING_9, SETTING_10, SETTING_11, SETTING_12, SETTING_13, SETTING_14, SETTING_15, SETTING_16, SETTING_17, SETTING_18, SETTING_19, SETTING_20, SETTING_21, SETTING_22, SETTING_23, SETTING_24, SETTING_25, SETTING_26]
println(positions)      // prints: [A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z]
```

### Build 'stock' Enigma Machine
'Stock' Enigma Machine is here defined as having:
* B-reflector
* I, II and III rotors (from left to right), III being the entry rotor
* All ring settings set to default, i.e. '1'
* All rotor start positions set to default, i.e. 'A'
* No plugboard connectors

The following six examples all build the same 'stock' Enigma Machine. 
```kotlin
// Using DSL builder
val firstEnigmaI = enigmaI {
    singleReflector { reflectorB() }
    threeRotors { rotorI(); rotorII(); rotorIII() }
}

// Using alternative builder - with enums
val secondEnigmaI = EnigmaBuilder()
    .addType(EnigmaType.ENIGMA_I)
    .addReflector(ReflectorType.REFLECTOR_B)
    .addRotors(RotorType.ROTOR_I, RotorType.ROTOR_II, RotorType.ROTOR_III)
    .build()

// Using alternative builder - with names
val thirdEnigmaI = EnigmaBuilder()
    .addType("ENIGMA_I")
    .addReflector("REFLECTOR_B")
    .addRotors("ROTOR_I,ROTOR_II,ROTOR_III")
    .build()

// Name prefixes ('ENIGMA_', 'REFLECTOR', 'ROTOR_') may be omitted
val fourthEnigmaI = EnigmaBuilder()
    .addType("I")
    .addReflector("B")
    .addRotors("I,II,III")
    .build()

// Lastly, for a 'stock' Enigma Machine, configuration may be omitted altogether
val fifthEnigmaI = enigmaI {}
val sixthEnigmaI = EnigmaBuilder().addType(EnigmaType.ENIGMA_I).build()
```

### Build with an arrangement of rotors, ring settings, starting positions, and a reflector
```kotlin
val firstEnigmaM3 = enigmaM3 {
    singleReflector { reflectorC() }
    threeRotors {
        rotorI(Rotor.Ring.SETTING_14, position = Letter.W)
        rotorV(Rotor.Ring.SETTING_9, position = Letter.N)
        rotorIII(Rotor.Ring.SETTING_24, position = Letter.Y)
    }
}

// Or
val secondEnigmaM3 = EnigmaBuilder()
    .addType(EnigmaType.ENIGMA_M3)
    .addReflector(ReflectorType.REFLECTOR_C)
    .addRotors(RotorType.ROTOR_I, RotorType.ROTOR_V, RotorType.ROTOR_III)
    .addRotorRingSettings(Rotor.Ring.SETTING_14, Rotor.Ring.SETTING_9, Rotor.Ring.SETTING_24)
    .addRotorPositions(Letter.W, Letter.N, Letter.Y)
    .build()

// Or
val thirdEnigmaM3 = EnigmaBuilder()
    .addType("ENIGMA_M3")
    .addReflector("C")
    .addRotors("I,V,III")
    .addRotorRingSettings("14,9,24")
    .addRotorPositions("W,N,Y")
    .build()
```

### Build with plugboard connectors
```kotlin
val firstEnigmaM4 = enigmaM4 {
    singleReflector { reflectorBThin() }
    fourRotors {
        rotorGamma(Rotor.Ring.SETTING_4, position = Letter.E)
        rotorI(Rotor.Ring.SETTING_14, position = Letter.W)
        rotorV(Rotor.Ring.SETTING_9, position = Letter.N)
        rotorIII(Rotor.Ring.SETTING_24, position = Letter.Y)
    }
    upToThirteenPlugboardConnectors {
        connect(Letter.S, Letter.Z); connect(Letter.G, Letter.T)
        connect(Letter.D, Letter.V); connect(Letter.K, Letter.U)
        connect(Letter.F, Letter.O); connect(Letter.M, Letter.Y)
        connect(Letter.E, Letter.W); connect(Letter.J, Letter.N)
        connect(Letter.I, Letter.X); connect(Letter.L, Letter.Q)
    }
}

// Or
val secondEnigmaM4 = EnigmaBuilder()
    .addType(EnigmaType.ENIGMA_M4)
    .addReflector(ReflectorType.REFLECTOR_B_THIN)
    .addRotors(RotorType.ROTOR_GAMMA, RotorType.ROTOR_I, RotorType.ROTOR_V, RotorType.ROTOR_III)
    .addRotorRingSettings(Rotor.Ring.SETTING_4, Rotor.Ring.SETTING_14, Rotor.Ring.SETTING_9, Rotor.Ring.SETTING_24)
    .addRotorPositions(Letter.E, Letter.W, Letter.N, Letter.Y)
    .addPlugboardConnectors(
        Connector(Letter.S, Letter.Z), Connector(Letter.G, Letter.T),
        Connector(Letter.D, Letter.V), Connector(Letter.K, Letter.U),
        Connector(Letter.F, Letter.O), Connector(Letter.M, Letter.Y),
        Connector(Letter.E, Letter.W), Connector(Letter.J, Letter.N),
        Connector(Letter.I, Letter.X), Connector(Letter.L, Letter.Q),
    )
    .build()

// Or
val thirdEnigmaM4 = EnigmaBuilder()
    .addType("ENIGMA_M4")
    .addReflector("B_THIN")
    .addRotors("GAMMA,I,V,III")
    .addRotorRingSettings("4,14,9,24")
    .addRotorPositions("E,W,N,Y")
    .addPlugboardConnectors("SZ,GT,DV,KU,FO,MY,EW,JN,IX,LQ")
    .build()
```

### Encipher plaintext and 'decipher' ciphertext
```kotlin
val firstEnigmaI = enigmaI {}
val secondEnigmaI = enigmaI {}

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
Logger.enable()

val enigma = enigmaI {}
enigma.encipher('A')

Logger.print(LogType.SUBSTITUTE)
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
Logger.enable()

val enigma = enigmaI {}
enigma.encipher('A')

Logger.print()
```
Prints:
```
Log Types requested for printing : [STEP, SUBSTITUTE, SHIFT, DE_SHIFT].
Log Types available for printing : [STEP, SUBSTITUTE, SHIFT, DE_SHIFT].

LOG TYPE   | RESULT       | ACTOR            | INFO
STEP       | AAA -> AAB   | ROTOR_UNIT       | Rotor types: ROTOR_I—ROTOR_II—ROTOR_III; Notch characters: [Q]—[E]—[V]
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
Logger.enable()

val enigma = enigmaM4 {
    singleReflector { reflectorCThin() }
    fourRotors {
        rotorGamma(Rotor.Ring.SETTING_5, position = Letter.P)
        rotorI(Rotor.Ring.SETTING_14, position = Letter.W)
        rotorV(Rotor.Ring.SETTING_9, position = Letter.N)
        rotorIII(Rotor.Ring.SETTING_24, position = Letter.B)
    }
    upToThirteenPlugboardConnectors {
        connect(Letter.S, Letter.Z); connect(Letter.G, Letter.T)
        connect(Letter.D, Letter.V); connect(Letter.K, Letter.U)
        connect(Letter.F, Letter.O); connect(Letter.M, Letter.Y)
        connect(Letter.E, Letter.W); connect(Letter.J, Letter.N)
        connect(Letter.I, Letter.X); connect(Letter.L, Letter.Q)
    }
}
enigma.encipher('A')

Logger.print()
```
Prints:
```
Log Types requested for printing : [STEP, SUBSTITUTE, SHIFT, DE_SHIFT].
Log Types available for printing : [STEP, SUBSTITUTE, SHIFT, DE_SHIFT].

LOG TYPE   | RESULT       | ACTOR            | INFO
STEP       | PWNB -> PWNC | ROTOR_UNIT       | Rotor types: ROTOR_GAMMA—ROTOR_VI—ROTOR_V—ROTOR_VIII; Notch characters: [_]—[Z, M]—[Z]—[Z, M]
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
Logger.enable()
Logger.restrictTo(LogType.STEP) // Only record STEP logs to avoid the max log size limit.

val enigma = enigmaI {
    threeRotors {
        rotorI(position = Letter.A)
        rotorII(position = Letter.D)
        rotorIII(position = Letter.S)
    }
}
enigma.encipher("AAAAAA")

Logger.print(LogType.STEP)
```
Prints:
```
Log Types requested for printing : [STEP].
Log Types available for printing : [STEP].

LOG TYPE   | RESULT       | ACTOR            | INFO
STEP       | ADS -> ADT   | ROTOR_UNIT       | Rotor types: ROTOR_I—ROTOR_II—ROTOR_III; Notch characters: [Q]—[E]—[V]
STEP       | ADT -> ADU   | ROTOR_UNIT       | Rotor types: ROTOR_I—ROTOR_II—ROTOR_III; Notch characters: [Q]—[E]—[V]
STEP       | ADU -> ADV   | ROTOR_UNIT       | Rotor types: ROTOR_I—ROTOR_II—ROTOR_III; Notch characters: [Q]—[E]—[V]
STEP       | ADV -> AEW   | ROTOR_UNIT       | Rotor types: ROTOR_I—ROTOR_II—ROTOR_III; Notch characters: [Q]—[E]—[V]
STEP       | AEW -> BFX   | ROTOR_UNIT       | Rotor types: ROTOR_I—ROTOR_II—ROTOR_III; Notch characters: [Q]—[E]—[V]
STEP       | BFX -> BFY   | ROTOR_UNIT       | Rotor types: ROTOR_I—ROTOR_II—ROTOR_III; Notch characters: [Q]—[E]—[V]
```