package ru.vs.core.utils

import kotlin.test.Test
import kotlin.test.assertEquals

class StringExtTest {
    @Test
    fun decapitalizeCapitalizedString() {
        assertEquals(
            expected = "test",
            actual = "Test".decapitalized()
        )
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