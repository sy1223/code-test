package com.example.codetest.presenter

import com.example.codetest.view.TransferSuccessView

class TransferSuccessPresenter {

    private var transferSuccessView: TransferSuccessView? = null

    constructor(transferSuccessView: TransferSuccessView, referenceNumber: String) {
        this.transferSuccessView = transferSuccessView

        transferSuccessView.onShowTransferSuccess(referenceNumber)
    }

    fun onCloseView() {
        transferSuccessView?.onDismissView()
    }

    fun onDestroy() {
        transferSuccessView = null
    }
}