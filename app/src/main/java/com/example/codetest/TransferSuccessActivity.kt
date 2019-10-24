package com.example.codetest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.codetest.contract.TransferSuccessContract
import com.example.codetest.presenter.TransferSuccessPresenter
import kotlinx.android.synthetic.main.activity_transfer_success.*

class TransferSuccessActivity : AppCompatActivity(), TransferSuccessContract.View {

    companion object {
        val DATA_FROM_ACCOUNT_NUMBER = "DATA_FROM_ACCOUNT_NUMBER"
        val DATA_TO_ACCOUNT_NUMBER = "DATA_TO_ACCOUNT_NUMBER"
        val DATA_AMOUNT = "DATA_AMOUNT"
        val DATA_REFERENCE_NUMBER = "DATA_REFERENCE_NUMBER"
    }

    private var transferSuccessPresenter: TransferSuccessPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_success)

        val fromAccountNumber = getIntent().getStringExtra(DATA_FROM_ACCOUNT_NUMBER)
        val toAccountNumber = getIntent().getStringExtra(DATA_TO_ACCOUNT_NUMBER)
        val amount = getIntent().getDoubleExtra(DATA_AMOUNT, 0.0)
        val referenceNumber = getIntent().getStringExtra(DATA_REFERENCE_NUMBER)

        transferSuccessPresenter = TransferSuccessPresenter(this, fromAccountNumber, toAccountNumber, amount, referenceNumber)

        btnClose.setOnClickListener { it
            transferSuccessPresenter?.onCloseView()
        }
    }

    override fun onDestroy() {
        transferSuccessPresenter?.onDestroy()
        super.onDestroy()
    }

    // TransferSuccessContract.View functions

    override fun showTransferSuccess(fromAccountNumber: String, toAccountNumber: String, amount: Double, referenceNumber: String) {
        tvFromAccountNumber.setText(fromAccountNumber)
        tvToAccountNumber.setText(toAccountNumber)
        tvAmount.setText(amount.toString())
        tvReferenceNumber.setText(referenceNumber)
    }

    override fun dismissView() {
        finish()
    }
}