package com.example.codetest.contract

interface TransferSuccessContract {

    interface Presenter {
        fun onReceiveData(fromAccountNumber: String, toAccountNumber: String, amount: Double, referenceNumber: String)
        fun onCloseView()
        fun onDestroy()
    }

    interface View {
        fun showTransferSuccess(fromAccountNumber: String, toAccountNumber: String, amount: Double, referenceNumber: String)
        fun dismissView()
    }
}