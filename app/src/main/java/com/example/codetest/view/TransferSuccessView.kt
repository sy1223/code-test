package com.example.codetest.view

interface TransferSuccessView {
    fun onShowTransferSuccess(fromAccountNumber: String, toAccountNumber: String, amount: Double, referenceNumber: String)
    fun onDismissView()
}