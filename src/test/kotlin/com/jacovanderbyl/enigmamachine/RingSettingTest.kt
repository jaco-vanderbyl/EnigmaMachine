package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class RingSettingTest {
    @TestFactory
    fun `ensure invalid value throws`() = listOf(
        0,
        27,
        100,
    ).map { value ->
        DynamicTest.dynamicTest("Invalid value '${value}' should throw.") {
            val exception = assertFailsWith<IllegalArgumentException>(
                block = { RingSetting(value) },
                message = "Failed to ensure invalid value throws."
            )
            exception.message?.let {
                assertContains(it, "invalid value", ignoreCase = true)
            }
        }
    }

    @Test
    fun `ensure default value is '1'`() {
        assertEquals(
            expected = 1,
            actual = RingSetting().value,
            message = "Failed to ensure default value is: 1."
        )
    }

    @Test
    fun `ensure index is one less than value`() {
        (1..Enigma.CHARACTER_SET.length).forEachIndexed { index, value ->
            assertEquals(
                expected = index,
                actual = RingSetting(value).index,
                message = "Failed to ensure index is one less than value."
            )
        }
    }

    @Test
    fun `ensure named constructor works`() {
        assertEquals(
            expected = 1,
            actual = RingSetting.fromString("1").value,
            message = "Failed to ensure named constructor works."
        )
    }

    @TestFactory
    fun `ensure invalid number representation throws`() = listOf(
        " ",
        "One",
        "A",
    ).map { value ->
        DynamicTest.dynamicTest("Invalid invalid number representation '${value}' should throw.") {
            val exception = assertFailsWith<IllegalArgumentException>(
                block = { RingSetting.fromString(value) },
                message = "Failed to ensure invalid number representation throws."
            )
            exception.message?.let {
                assertContains(it, "invalid number representation", ignoreCase = true)
            }
        }
    }
}
