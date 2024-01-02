package com.jacovanderbyl.enigmamachine

import com.jacovanderbyl.enigmamachine.log.Log
import com.jacovanderbyl.enigmamachine.log.Logger

/**
 * Represents a plugboard, which has 26 letters, each of which can be connected with one other letter (therefore
 * allowing up to 13 connectors), and these connections are used to substitute one letter with another.
 *
 * The plugboard adds significant cryptographic strength to the machine.
 */
class Plugboard(vararg connectors: Connector) {
    val connectorList = connectors.toList()
    private val connectorMap = mutableMapOf<Char,Char>()

    init { addConnectors(*connectors) }

    /**
     * Substitute character if it's connected with another, otherwise return original character.
     */
    fun encipher(character: Char) : Char {
        require(character in Letter.list()) {
            "Invalid character. Valid: '${Letter.list()}'. Given: '${character}'."
        }

        val substituteCharacter = connectorMap.getOrDefault(character, character)
        Logger.add(Log.PlugboardSubstitute.create(character, substituteCharacter, plugboard = this))
        return substituteCharacter
    }

    fun addConnectors(vararg connectors: Connector) {
        connectors.forEach {
            require(!connectorMap.containsKey(it.first.character) && !connectorMap.containsKey(it.second.character)) {
                "Duplicate connector. Cannot connect character that's already been connected. " +
                        "Given: '${it.first}${it.second}'."
            }

            connectorMap[it.first.character] = it.second.character
            connectorMap[it.second.character] = it.first.character
        }
    }

    fun reset() {
        connectorMap.clear()
    }
}
