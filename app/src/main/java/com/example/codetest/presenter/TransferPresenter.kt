package com.example.codetest.presenter

import com.example.codetest.helper.NumberHelper
import com.example.codetest.model.DummyData
import com.example.codetest.model.Transaction
import com.example.codetest.service.APIService
import com.example.codetest.view.TransferView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransferPresenter {

    enum class TransactionDataError {
        MISSING_FROM_ACCOUNT,
        MISSING_TO_ACCOUNT,
        SAME_FROM_AND_TO_ACCOUNT,
        MISSING_AMOUNT,
        INVALID_AMOUNT
    }

    private var transferView: TransferView? = null

    private val apiService by lazy {
        APIService.create()
    }

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
            val transaction = Transaction(fromAccountNumber, toAccountNumber, amount.toDouble())
            postTransferRequest(transaction)
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

    private fun postTransferRequest(transaction: Transaction) {
        val call = apiService.transfer(transaction.fromAccountNumber, transaction.toAccountNumber, transaction.amount)
        call.enqueue(object : Callback<Transaction> {
            override fun onResponse(call: Call<Transaction>?, response: Response<Transaction>?) {
                val transaction = response?.body()

                if (response != null && response.isSuccessful() && transaction != null && transaction.isSuccessTransaction()) {
                    transferView?.onTransferSuccess(transaction.referenceNumber)
                } else {
                    transferView?.onTransferError("SERVER_ERROR")
                }
            }

            override fun onFailure(call: Call<Transaction>?, t: Throwable?) {
                transferView?.onTransferError("SERVER_ERROR")
            }
        })
    }
}