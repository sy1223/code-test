package com.example.codetest.presenter

import com.example.codetest.contract.TransferSuccessContract

class TransferSuccessPresenter : TransferSuccessContract.Presenter {

    private var view: TransferSuccessContract.View? = null

    constructor(view: TransferSuccessContract.View, fromAccountNumber: String, toAccountNumber: String, amount: Double, referenceNumber: String) {
        this.view = view

        view.showTransferSuccess(fromAccountNumber, toAccountNumber, amount, referenceNumber)
    }

    // TransferSuccessContract.Presenter functions

    override fun onReceiveData(fromAccountNumber: String, toAccountNumber: String, amount: Double, referenceNumber: String) {
        view?.showTransferSuccess(fromAccountNumber, toAccountNumber, amount, referenceNumber)
    }

    override fun onCloseView() {
        view?.dismissView()
    }

    override fun onDestroy() {
        view = null
    }
}