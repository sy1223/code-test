package com.example.codetest.contract

interface TransferContract {

    interface Presenter {
        fun onShowFromAccounts()
        fun onShowToAccounts()
        fun onTransfer(fromAccountNumber: String, toAccountNumber: String, amount: String)
        fun onDestroy()
    }

    interface View {
        fun showFromAccounts(accounts: Array<String>)
        fun showToAccounts(accounts: Array<String>)
        fun showTransferSuccess(fromAccountNumber: String, toAccountNumber: String, amount: Double, referenceNumber: String)
        fun showTransferError(errorMessageRedId: Int)
        fun clearErrorMessage()
        fun resetViewData()
    }
}