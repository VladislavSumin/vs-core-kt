package ru.vs.core.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class StringExtTest {
    @Test
    fun decapitalizeCapitalizedString() {
        assertEquals(
            expected = "test",
            actual = "Test".decapitalized()
        )

        // check test failed
        assertTrue(false)
    }

    @Test
    fun decapitalizeDecapitalizedString() {
        assertEquals(
            expected = "test",
            actual = "test".decapitalized()
        )
    }

    @Test
    fun decapitalizeOnlyFirstCharacterString() {
        assertEquals(
            expected = "tEST",
            actual = "TEST".decapitalized()
        )
    }

    @Test
    fun decapitalizeEmptyString() {
        assertEquals(
            expected = "",
            actual = "".decapitalized()
        )
    }
}