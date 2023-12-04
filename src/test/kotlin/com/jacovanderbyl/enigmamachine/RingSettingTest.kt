package com.jacovanderbyl.enigmamachine

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertContains

import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class RingSettingTest {
    private val invalidValues = listOf(0, 27, 100)

    @TestFactory
    fun `ensure ring setting throws on invalid value`() = invalidValues.map { value ->
        DynamicTest.dynamicTest("Invalid value '${value}' should throw.") {
            val ex = assertFailsWith<IllegalArgumentException>(
                block = { RingSetting(value) },
                message = "Failed to ensure ring setting throws on invalid value."
            )
            ex.message?.let { msg -> assertContains(charSequence = msg, other = "Invalid value") }
        }
    }

    @Test
    fun `ensure default ring setting value is '1'`() {
        assertEquals(
            expected = 1,
            actual = RingSetting().value,
            message = "Failed to ensure default ring setting value is '1'."
        )
    }

    @Test
    fun `ensure ring setting index is one less than value`() {
        (1..Keys.CHARACTER_SET.length).forEachIndexed { index, value ->
            assertEquals(
                expected = index,
                actual = RingSetting(value).index,
                message = "Failed to ensure ring setting index is one less than value."
            )
        }
    }

    @Test
    fun `ensure named constructor can create object from string`() {
        assertEquals(
            expected = 1,
            actual = RingSetting.fromString("1").value,
            message = "Failed to ensure named constructor can create object from string."
        )
    }

    private val bogusRingSettingStrings = listOf(" ", "One", "A")

    @TestFactory
    fun `ensure named constructor only accepts integer in string`() = bogusRingSettingStrings.map { value ->
        DynamicTest.dynamicTest("Non integer string '${value}' should throw.") {
            val ex = assertFailsWith<IllegalArgumentException>(
                block = { RingSetting.fromString(value) },
                message = "Failed to ensure named constructor only accepts integer in string."
            )
            ex.message?.let { msg ->
                assertContains(charSequence = msg, other = "ring setting string must be an integer")
            }
        }
    }
}
