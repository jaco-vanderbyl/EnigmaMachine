package com.jacovanderbyl.enigmamachine

import com.jacovanderbyl.enigmamachine.log.Logger
import com.jacovanderbyl.enigmamachine.log.PlugboardLog

/**
 * Represents a plugboard, which has 26 letters, each of which can be connected with one other letter (therefore
 * allowing up to 13 connectors), and these connections are used to substitute one letter with another.
 *
 * The plugboard adds significant cryptographic strength to the machine.
 */
class Plugboard(vararg connectors: Connector) : CanEncipher {
    val connectorList = connectors.toList()
    private val connectorMap = mutableMapOf<Char,Char>()
    var logger: Logger? = null

    init {
        addConnectors(*connectors)
    }

    /**
     * Substitute character if it's connected with another, otherwise return original character.
     */
    override fun encipher(character: Char) : Char {
        require(character in Enigma.CHARACTER_SET) {
            "Invalid character. Valid: '${Enigma.CHARACTER_SET}'. Given: '${character}'."
        }

        val substituteCharacter = connectorMap.getOrDefault(character, character)
        logger?.add(PlugboardLog.fromPlugboard(character, substituteCharacter, plugboard = this))
        return substituteCharacter
    }

    fun addConnectors(vararg connectors: Connector) {
        connectors.forEach {
            require(!connectorMap.containsKey(it.first) && !connectorMap.containsKey(it.second)) {
                "Duplicate connector. Cannot connect character that's already been connected. " +
                        "Given: '${it.first}${it.second}'."
            }

            connectorMap[it.first] = it.second
            connectorMap[it.second] = it.first
        }
    }

    fun reset() {
        connectorMap.clear()
    }

    companion object {
        const val A = 'A'
        const val B = 'B'
        const val C = 'C'
        const val D = 'D'
        const val E = 'E'
        const val F = 'F'
        const val G = 'G'
        const val H = 'H'
        const val I = 'I'
        const val J = 'J'
        const val K = 'K'
        const val L = 'L'
        const val M = 'M'
        const val N = 'N'
        const val O = 'O'
        const val P = 'P'
        const val Q = 'Q'
        const val R = 'R'
        const val S = 'S'
        const val T = 'T'
        const val U = 'U'
        const val V = 'V'
        const val W = 'W'
        const val X = 'X'
        const val Y = 'Y'
        const val Z = 'Z'
    }
}
