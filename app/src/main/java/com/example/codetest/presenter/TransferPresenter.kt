package com.example.codetest.presenter

import com.example.codetest.model.DummyData
import com.example.codetest.view.TransferView

class TransferPresenter {

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

    }

    fun onDestroy() {
        transferView = null
    }
}