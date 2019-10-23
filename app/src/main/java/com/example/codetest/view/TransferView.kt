package com.example.codetest.view

interface TransferView {
    fun onShowFromAccounts(accounts: Array<String>)
    fun onShowToAccounts(accounts: Array<String>)
    fun onClearErrorMessage()
    fun onTransferSuccess(referenceNumber: String)
    fun onTransferError(errorMessageRedId: Int)
}