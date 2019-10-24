package com.example.codetest.contract

interface TransferSuccessContract {

    interface Presenter {
        fun onReceiveData(referenceNumber: String, fromAccountNumber: String, toAccountNumber: String, amount: Double)
        fun onCloseView()
        fun onDestroy()
    }

    interface View {
        fun showTransferSuccess(referenceNumber: String, fromAccountNumber: String, toAccountNumber: String, amount: Double)
        fun dismissView()
    }
}