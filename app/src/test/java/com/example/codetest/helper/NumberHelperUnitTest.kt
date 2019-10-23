package com.example.codetest.helper

import org.junit.Test
import org.junit.Assert.*

class NumberHelperUnitTest {
    @Test
    fun testValidDollarAmount() {
        assertTrue(NumberHelper.isValidDollarAmount("1.1"))
        assertTrue(NumberHelper.isValidDollarAmount("1.20"))
        assertTrue(NumberHelper.isValidDollarAmount("1000000"))
        assertTrue(NumberHelper.isValidDollarAmount("1000000.99"))

        assertFalse(NumberHelper.isValidDollarAmount("1000000s99"))
        assertFalse(NumberHelper.isValidDollarAmount("-1000000.99"))
        assertFalse(NumberHelper.isValidDollarAmount("a1000000"))
    }
}