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
            actual = Notch(setOf('A', 'B')).characters,
            message = "Failed to ensure notch characters are correct."
        )
    }

    @TestFactory
    fun `ensure invalid character throws`() = listOf(
        ' ',
        'a',
        '@',
    ).map { character ->
        DynamicTest.dynamicTest("Invalid character '${character}' should throw.") {
            val exception = assertFailsWith<IllegalArgumentException>(
                block = { Notch(setOf(character)) },
                message = "Failed to ensure invalid character throws."
            )
            exception.message?.let {
                assertContains(it, "invalid character", ignoreCase = true)
            }
        }
    }

    @TestFactory
    fun `ensure invalid character count throws`() = listOf(
        setOf(),
        setOf(
            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','@'
        )
    ).map { chars ->
        DynamicTest.dynamicTest("Invalid character count '${chars.size}' should throw.") {
            val exception = assertFailsWith<IllegalArgumentException>(
                block = { Notch(chars) },
                message = "Failed to ensure invalid character count throws."
            )
            exception.message?.let {
                assertContains(it, "invalid character count", ignoreCase = true)
            }
        }
    }
}
