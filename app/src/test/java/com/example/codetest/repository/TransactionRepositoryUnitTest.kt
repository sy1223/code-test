package com.example.codetest.repository

import com.example.codetest.model.Transaction
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.then
import junit.framework.Assert.assertEquals
import org.hamcrest.CoreMatchers.startsWith
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit

class TransactionRepositoryUnitTest {

    private lateinit var transactionRepository: TransactionRepository

    @JvmField @Rule
    var mockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var mockListener: TransactionRepositoryListener

    @Before
    fun setUp() {
        transactionRepository = TransactionRepository(mockListener)
    }

    @Test
    fun testPostTransferRequest() {
        val transaction = Transaction("507-123456-001", "507-123456-838", 16.77)
        val captor = argumentCaptor<Transaction>()

        transactionRepository.transfer(transaction)
        Thread.sleep(2000) // Wait for 2 seconds
        then(mockListener).should().onTransferSuccess(captor.capture())

        assertEquals("507-123456-001", captor.firstValue.fromAccountNumber)
        assertEquals("507-123456-838", captor.firstValue.toAccountNumber)
        assertEquals(16.77, captor.firstValue.amount)
        assertThat(captor.firstValue.referenceNumber, startsWith("REF"))
    }

    @After
    fun tearDown() {
        transactionRepository.destroy()
    }
}