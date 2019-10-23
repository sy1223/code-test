package com.example.codetest.presenter

import com.example.codetest.R
import com.example.codetest.view.TransferView
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.then
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit

class TransferPresenterUnitTest {

    private lateinit var transferPresenter: TransferPresenter

    @JvmField @Rule
    var mockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var mockTransferView: TransferView

    @Before
    fun setUp() {
        transferPresenter = TransferPresenter(mockTransferView)
    }

    @Test
    fun when_transferWithEmptyFromAccount_then_returnMissingFromAccountError() {
        transferPresenter.onTransfer("", "507-123456-838", "16.77")
        then(mockTransferView).should().onTransferError(R.string.error_missing_from_account)
    }

    @Test
    fun when_transferWithEmptyToAccount_then_returnMissingToAccountError() {
        transferPresenter.onTransfer("507-123456-001", "", "16.77")
        then(mockTransferView).should().onTransferError(R.string.error_missing_to_account)
    }

    @Test
    fun when_transferWithEmptyAmount_then_returnMissingAmountError() {
        transferPresenter.onTransfer("507-123456-001", "507-123456-838", "")
        then(mockTransferView).should().onTransferError(R.string.error_missing_amount)
    }

    @Test
    fun when_transferWithSameAccount_then_returnSameFromAndToAccountError() {
        transferPresenter.onTransfer("507-123456-001", "507-123456-001", "16.77")
        then(mockTransferView).should().onTransferError(R.string.error_from_and_to_account_cannot_be_the_same)
    }

    @Test
    fun when_transferWithInvalidAmount_then_returnInvalidAmountError() {
        transferPresenter.onTransfer("507-123456-001", "507-123456-838", "16.777")
        then(mockTransferView).should().onTransferError(R.string.error_invalid_amount)
    }

    @Test
    fun when_transferWithZeroAmount_then_returnInvalidAmountError() {
        transferPresenter.onTransfer("507-123456-001", "507-123456-838", "0")
        then(mockTransferView).should().onTransferError(R.string.error_invalid_amount)
    }

    @Test
    fun when_transferWithNegativeAmount_then_returnInvalidAmountError() {
        transferPresenter.onTransfer("507-123456-001", "507-123456-838", "-16.77")
        then(mockTransferView).should().onTransferError(R.string.error_invalid_amount)
    }

    @Test
    fun when_transferWithValidInformation_then_callOnTransferSuccess() {
        transferPresenter.onTransfer("507-123456-001", "507-123456-838", "16.77")
        Thread.sleep(2000) // Wait for 2 seconds
        then(mockTransferView).should().onTransferSuccess(eq("507-123456-001"), eq("507-123456-838"), eq(16.77), ArgumentMatchers.startsWith("REF"))
    }

    @After
    fun tearDown() {
        transferPresenter.onDestroy()
    }

}