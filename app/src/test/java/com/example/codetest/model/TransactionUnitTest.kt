package com.example.codetest.model

import org.junit.Assert
import org.junit.Test

class TransactionUnitTest {
    @Test
    fun testIsSuccessTransaction() {
        val transaction = Transaction()
        transaction.referenceNumber = "REF001"

        Assert.assertTrue("Invalid reference number format", transaction.isSuccessTransaction())
    }
}