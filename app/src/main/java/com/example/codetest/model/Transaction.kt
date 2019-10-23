package com.example.codetest.model

import com.google.gson.annotations.SerializedName

class Transaction {

    @SerializedName("reference_number")
    var referenceNumber: String = ""

    @SerializedName("from")
    var fromAccountNumber: String = ""

    @SerializedName("to")
    var toAccountNumber: String = ""

    @SerializedName("amount")
    var amount: Double = 0.0

    constructor(fromAccountNumber: String, toAccountNumber: String, amount: Double) {
        this.fromAccountNumber = fromAccountNumber
        this.toAccountNumber = toAccountNumber
        this.amount = amount
    }

    fun isSuccessTransaction(): Boolean {
        return referenceNumber.startsWith("REF")
    }
}