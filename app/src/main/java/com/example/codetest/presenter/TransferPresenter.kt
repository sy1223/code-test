package com.example.codetest.presenter

import com.example.codetest.helper.NumberHelper
import com.example.codetest.model.DummyData
import com.example.codetest.view.TransferView

class TransferPresenter {

    enum class TransactionDataError {
        MISSING_FROM_ACCOUNT,
        MISSING_TO_ACCOUNT,
        SAME_FROM_AND_TO_ACCOUNT,
        MISSING_AMOUNT,
        INVALID_AMOUNT
    }

    private var transferView: TransferView? = null

    constructor(transferView: TransferView) {
        this.transferView = transferView
    }

    fun onShowFromAccounts() {
        transferView?.onShowFromAccounts(DummyData.accounts)
    }

    fun onShowToAccounts() {
        transferView?.onShowToAccounts(DummyData.accounts)
    }

    fun onTransfer(fromAccountNumber: String, toAccountNumber: String, amount: String) {
        val transactionDataError = validateUserInput(fromAccountNumber, toAccountNumber, amount)

        if (transactionDataError == null) {

        } else {
            val errorMessage = when (transactionDataError) {
                TransactionDataError.MISSING_FROM_ACCOUNT -> "MISSING_FROM_ACCOUNT"
                TransactionDataError.MISSING_TO_ACCOUNT -> "MISSING_TO_ACCOUNT"
                TransactionDataError.SAME_FROM_AND_TO_ACCOUNT -> "SAME_FROM_AND_TO_ACCOUNT"
                TransactionDataError.MISSING_AMOUNT -> "MISSING_AMOUNT"
                TransactionDataError.INVALID_AMOUNT -> "INVALID_AMOUNT"
            }

            transferView?.onTransferError(errorMessage)
        }
    }

    fun onDestroy() {
        transferView = null
    }

    //

    private fun validateUserInput(fromAccountNumber: String, toAccountNumber: String, amount: String): TransactionDataError? {
        if (fromAccountNumber.isEmpty()) {
            return TransactionDataError.MISSING_FROM_ACCOUNT
        } else if (toAccountNumber.isEmpty()) {
            return TransactionDataError.MISSING_TO_ACCOUNT
        } else if (amount.isEmpty()) {
            return TransactionDataError.MISSING_AMOUNT
        } else if (!NumberHelper.isValidDollarAmount(amount)) {
            return TransactionDataError.INVALID_AMOUNT
        } else if (fromAccountNumber == toAccountNumber) {
            return TransactionDataError.SAME_FROM_AND_TO_ACCOUNT
        } else {
            val amountInDouble = amount.toDoubleOrNull()

            if (amountInDouble == null || amountInDouble <= 0.0) {
                return TransactionDataError.INVALID_AMOUNT
            }
        }

        return null
    }
}