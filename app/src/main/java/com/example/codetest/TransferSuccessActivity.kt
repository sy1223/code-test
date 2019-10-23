package com.example.codetest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.codetest.presenter.TransferSuccessPresenter
import com.example.codetest.view.TransferSuccessView
import kotlinx.android.synthetic.main.activity_transfer_success.*

class TransferSuccessActivity : AppCompatActivity(), TransferSuccessView {

    companion object {
        val DATA_REFERENCE_NUMBER = "DATA_REFERENCE_NUMBER"
    }

    private var transferSuccessPresenter: TransferSuccessPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_success)

        val referenceNumber = getIntent().getStringExtra(DATA_REFERENCE_NUMBER)

        transferSuccessPresenter = TransferSuccessPresenter(this, referenceNumber)

        btnClose.setOnClickListener { it
            transferSuccessPresenter?.onCloseView()
        }
    }

    override fun onDestroy() {
        transferSuccessPresenter?.onDestroy()
        super.onDestroy()
    }

    //

    override fun onShowTransferSuccess(referenceNumber: String) {
        tvReferenceNumber.setText(referenceNumber)
    }

    override fun onDismissView() {
        finish()
    }
}