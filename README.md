# Kotlin Enigma Machine
Kotlin library that simulates Enigma Machine enciphering.

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

Library supports the following models:
* Wehrmacht Enigma I — The most common wartime Enigma Machine, used by the German Army.
* Kriegsmarine Enigma M3 — A modified Enigma I model, used by German navy.

## Clone, build and test
It is recommended to use the latest released version of Intellij IDEA (Community or Ultimate Edition).
You can download IntelliJ IDEA [here](https://www.jetbrains.com/idea/download).

After cloning the project, import the project in IntelliJ by choosing the project directory in the Open project dialog.

**Build**: IntelliJ should build the project automatically using Gradle.
Alternatively, go to Build menu and choose Build Project.

**Test**: Right-click on `/src/test` and choose Run Tests.

## Usage

### List supported Enigma Machines, Reflectors, and Rotors
```kotlin
val supportedEnigmas = EnigmaFactory.entries.map { it.name }
val supportedReflectors = ReflectorFactory.entries.map { it.name }
val supportedRotors = RotorFactory.entries.map { it.name }

println(supportedEnigmas)    // prints: [ENIGMA_I, ENIGMA_M3]
println(supportedReflectors) // prints: [B, C]
println(supportedRotors)     // prints: [I, II, III, IV, V, VI, VII, VIII]

```

### Build 'stock' Enigma
'Stock' Enigma I is here defined as having:
* B-reflector
* I, II and III rotors (from left to right)
* All ring settings set to '1'
* All rotor start positions set to 'A'
* No plugboard connectors

```kotlin
val enigmaI = EnigmaBuilder.makeFromCsvValues(
    type = "ENIGMA_I",
    reflector = "B",
    rotors = "I,II,III",
    ringSettings = "1,1,1",
    startingPositions = "A,A,A"
)

val enigmaM3 = EnigmaBuilder.makeFromCsvValues(
    type = "ENIGMA_M3",
    reflector = "B",
    rotors = "I,II,III",
    ringSettings = "1,1,1",
    startingPositions = "A,A,A"
)
```

### Build Enigma with an arrangement of rotors, ring settings, starting positions, and reflector
```kotlin
val enigmaI = EnigmaBuilder.makeFromCsvValues(
    type = "ENIGMA_I",
    reflector = "C",
    rotors = "I,V,III",
    ringSettings = "14,9,24",
    startingPositions = "W,N,Y"
)
```

### Build Enigma plugboard connectors
```kotlin
val enigmaI = EnigmaBuilder.makeFromCsvValues(
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
val enigmaI = EnigmaBuilder.makeFromCsvValues(
    type = "ENIGMA_I",
    reflector = "B",
    rotors = "I,II,III",
    ringSettings = "1,1,1",
    startingPositions = "A,A,A"
)

val plaintextInput = "AAAAA"
val cypher = enigmaI.encipher(plaintextInput)
val plaintext = enigmaI.reset().encipher(cypher)

println(plaintextInput) // prints: AAAAA
println(cypher)         // prints: BDZGO
println(plaintext)      // prints: AAAAA
```