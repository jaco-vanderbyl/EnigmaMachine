package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.Test

import kotlin.test.assertEquals

class KeysTest {
    @Test
    fun `ensure enigma key character set is uppercase alphabet`() {
        assertEquals(
            message = "Failed to ensure enigma key character set is uppercase alphabet.",
            expected = "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
            actual = Keys.CHARACTER_SET
        )
    }
}