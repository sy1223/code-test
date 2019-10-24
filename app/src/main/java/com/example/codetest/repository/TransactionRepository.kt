package com.example.codetest.repository

import com.example.codetest.model.Transaction
import com.example.codetest.service.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface TransactionRepositoryListener {
    fun onTransferSuccess(transaction: Transaction)
    fun onTransferError()
}

class TransactionRepository {

    private var listener: TransactionRepositoryListener? = null

    private val apiService by lazy {
        APIService.create()
    }

    constructor(listener: TransactionRepositoryListener) {
        this.listener = listener
    }

    fun transfer(transaction: Transaction) {
        val call = apiService.transfer(transaction.fromAccountNumber, transaction.toAccountNumber, transaction.amount)

        call.enqueue(object : Callback<Transaction> {
            override fun onResponse(call: Call<Transaction>?, response: Response<Transaction>?) {
                val transaction = response?.body()

                if (response != null && response.isSuccessful() && transaction != null && transaction.isSuccessTransaction()) {
                    listener?.onTransferSuccess(transaction)
                } else {
                    listener?.onTransferError()
                }
            }

            override fun onFailure(call: Call<Transaction>?, t: Throwable?) {
                listener?.onTransferError()
            }
        })
    }

    fun destroy() {
        listener = null
    }
}