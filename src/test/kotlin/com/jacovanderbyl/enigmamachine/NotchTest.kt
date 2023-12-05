package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class NotchTest {
    @Test
    fun `ensure notch characters are correct`() {
        assertEquals(
            expected = setOf('A', 'B'),
            actual = Notch(Position('A'), Position('B')).characters,
            message = "Failed to ensure notch characters are correct."
        )
    }

    @TestFactory
    fun `ensure invalid character count throws`() = listOf(
        listOf(),
        listOf(
            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','A'
        )
    ).map { chars ->
        DynamicTest.dynamicTest("Invalid character count '${chars.size}' should throw.") {
            val exception = assertFailsWith<IllegalArgumentException>(
                block = { Notch(*chars.map { Position(it) }.toTypedArray()) },
                message = "Failed to ensure invalid character count throws."
            )
            exception.message?.let {
                assertContains(it, "invalid character count", ignoreCase = true)
            }
        }
    }
}
