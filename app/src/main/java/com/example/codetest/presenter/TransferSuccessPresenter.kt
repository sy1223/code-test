package com.example.codetest.presenter

import com.example.codetest.contract.TransferSuccessContract

class TransferSuccessPresenter : TransferSuccessContract.Presenter {

    private var view: TransferSuccessContract.View? = null

    constructor(view: TransferSuccessContract.View) {
        this.view = view
    }

    // TransferSuccessContract.Presenter functions

    override fun onReceiveData(referenceNumber: String, fromAccountNumber: String, toAccountNumber: String, amount: Double) {
        view?.showTransferSuccess(referenceNumber, fromAccountNumber, toAccountNumber, amount)
    }

    override fun onCloseView() {
        view?.dismissView()
    }

    override fun onDestroy() {
        view = null
    }
}