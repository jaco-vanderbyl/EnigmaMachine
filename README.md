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
import com.jacovanderbyl.enigmamachine.*

val enigmaTypes = EnigmaType.list()
val reflectorTypes = ReflectorType.list()
val rotorTypes = RotorType.list()
val ringSettings = RingSetting.list()
val positions = Position.list()

println(enigmaTypes)    // prints: [ENIGMA_I, ENIGMA_M3, ENIGMA_M4]
println(reflectorTypes) // prints: [B, C, B_THIN, C_THIN]
println(rotorTypes)     // prints: [I, II, III, IV, V, VI, VII, VIII, BETA, GAMMA]
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
import com.jacovanderbyl.enigmamachine.*

val enigmaI = EnigmaBuilder.make(
    type = "ENIGMA_I",
    reflector = "B",
    rotors = "I,II,III"
)

val enigmaM3 = EnigmaBuilder.make(
    type = "ENIGMA_M3",
    reflector = "B",
    rotors = "I,II,III"
)
```

### Make with an arrangement of rotors, ring settings, starting positions, and a reflector
```kotlin
import com.jacovanderbyl.enigmamachine.*

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
import com.jacovanderbyl.enigmamachine.*

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
import com.jacovanderbyl.enigmamachine.*

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
