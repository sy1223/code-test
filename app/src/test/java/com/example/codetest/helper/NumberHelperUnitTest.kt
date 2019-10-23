package com.example.codetest.helper

import org.junit.Test
import org.junit.Assert.*

class NumberHelperUnitTest {
    @Test
    fun testIsValidDollarFormat() {
        assertTrue(NumberHelper.isValidDollarFormat("1.1"))
        assertTrue(NumberHelper.isValidDollarFormat("1.20"))
        assertTrue(NumberHelper.isValidDollarFormat("1000000"))
        assertTrue(NumberHelper.isValidDollarFormat("1000000.99"))

        assertFalse(NumberHelper.isValidDollarFormat("1000000s99"))
        assertFalse(NumberHelper.isValidDollarFormat("-1000000.99"))
        assertFalse(NumberHelper.isValidDollarFormat("a1000000"))
    }
}