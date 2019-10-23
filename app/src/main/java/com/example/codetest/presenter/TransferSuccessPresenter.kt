package com.example.codetest.presenter

import com.example.codetest.view.TransferSuccessView

class TransferSuccessPresenter {

    private var transferSuccessView: TransferSuccessView? = null

    constructor(transferSuccessView: TransferSuccessView, fromAccountNumber: String, toAccountNumber: String, amount: Double, referenceNumber: String) {
        this.transferSuccessView = transferSuccessView

        transferSuccessView.onShowTransferSuccess(fromAccountNumber, toAccountNumber, amount, referenceNumber)
    }

    fun onCloseView() {
        transferSuccessView?.onDismissView()
    }

    fun onDestroy() {
        transferSuccessView = null
    }
}