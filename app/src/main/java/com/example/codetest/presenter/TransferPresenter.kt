package com.example.codetest.presenter

import com.example.codetest.R
import com.example.codetest.helper.NumberHelper
import com.example.codetest.model.DummyData
import com.example.codetest.model.Transaction
import com.example.codetest.repository.TransactionRepository
import com.example.codetest.repository.TransactionRepositoryListener
import com.example.codetest.view.TransferView

class TransferPresenter : TransactionRepositoryListener {

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
        transferView?.onClearErrorMessage()

        val transactionDataError = validateUserInput(fromAccountNumber, toAccountNumber, amount)

        if (transactionDataError == null) {
            val transaction = Transaction(fromAccountNumber, toAccountNumber, amount.toDouble())

            val transactionRepository = TransactionRepository(this)
            transactionRepository.transfer(transaction)
        } else {
            val errorMessageRedId = when (transactionDataError) {
                TransactionDataError.MISSING_FROM_ACCOUNT -> R.string.error_missing_from_account
                TransactionDataError.MISSING_TO_ACCOUNT -> R.string.error_missing_to_account
                TransactionDataError.SAME_FROM_AND_TO_ACCOUNT -> R.string.error_from_and_to_account_cannot_be_the_same
                TransactionDataError.MISSING_AMOUNT -> R.string.error_missing_amount
                TransactionDataError.INVALID_AMOUNT -> R.string.error_invalid_amount
            }

            transferView?.onTransferError(errorMessageRedId)
        }
    }

    fun onDestroy() {
        transferView = null
    }

    //

    private fun validateUserInput(fromAccountNumber: String, toAccountNumber: String, amount: String): TransactionDataError? {
        val transactionDataError = when {
            fromAccountNumber.isEmpty() -> TransactionDataError.MISSING_FROM_ACCOUNT
            toAccountNumber.isEmpty() -> TransactionDataError.MISSING_TO_ACCOUNT
            amount.isEmpty() -> TransactionDataError.MISSING_AMOUNT
            !NumberHelper.isValidDollarFormat(amount) -> TransactionDataError.INVALID_AMOUNT
            (fromAccountNumber == toAccountNumber) -> TransactionDataError.SAME_FROM_AND_TO_ACCOUNT
            else -> {
                val amountInDouble = amount.toDoubleOrNull()

                if (amountInDouble == null || amountInDouble <= 0.0) {
                    TransactionDataError.INVALID_AMOUNT
                } else {
                    null
                }
            }
        }

        return transactionDataError
    }

    // TransactionRepositoryListener functions

    override fun onTransferSuccess(transaction: Transaction) {
        transferView?.onTransferSuccess(transaction.fromAccountNumber, transaction.toAccountNumber, transaction.amount, transaction.referenceNumber)
    }

    override fun onTransferError() {
        transferView?.onTransferError(R.string.error_server_error)
    }
}