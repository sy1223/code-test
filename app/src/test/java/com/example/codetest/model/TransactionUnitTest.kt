package com.example.codetest.model

import junit.framework.Assert.assertTrue
import org.junit.Test

class TransactionUnitTest {
    @Test
    fun testIsSuccessTransaction() {
        val transaction = Transaction()
        transaction.referenceNumber = "REF001"

        assertTrue("Invalid reference number format", transaction.isSuccessTransaction())
    }
}