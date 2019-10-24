package com.example.codetest.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.codetest.R
import com.example.codetest.contract.TransferSuccessContract
import com.example.codetest.presenter.TransferSuccessPresenter

class TransferSuccessFragment : Fragment(), TransferSuccessContract.View {

    companion object {
        val DATA_REFERENCE_NUMBER = "DATA_REFERENCE_NUMBER"
        val DATA_FROM_ACCOUNT_NUMBER = "DATA_FROM_ACCOUNT_NUMBER"
        val DATA_TO_ACCOUNT_NUMBER = "DATA_TO_ACCOUNT_NUMBER"
        val DATA_AMOUNT = "DATA_AMOUNT"

        fun newInstance(referenceNumber: String, fromAccountNumber: String, toAccountNumber: String, amount: Double): TransferSuccessFragment {
            val transferSuccessFragment = TransferSuccessFragment()

            val bundle = Bundle()
            bundle.putString(DATA_REFERENCE_NUMBER, referenceNumber)
            bundle.putString(DATA_FROM_ACCOUNT_NUMBER, fromAccountNumber)
            bundle.putString(DATA_TO_ACCOUNT_NUMBER, toAccountNumber)
            bundle.putDouble(DATA_AMOUNT, amount)

            transferSuccessFragment.arguments = bundle

            return transferSuccessFragment
        }
    }

    private var transferSuccessPresenter: TransferSuccessPresenter? = null

    private var tvReferenceNumber: TextView? = null
    private var tvFromAccountNumber: TextView? = null
    private var tvToAccountNumber: TextView? = null
    private var tvAmount: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_transfer_success, container, false)

        transferSuccessPresenter = TransferSuccessPresenter(this)

        val btnClose = view.findViewById<Button>(R.id.btnClose)

        tvReferenceNumber = view.findViewById(R.id.tvReferenceNumber)
        tvFromAccountNumber = view.findViewById(R.id.tvFromAccountNumber)
        tvToAccountNumber = view.findViewById(R.id.tvToAccountNumber)
        tvAmount = view.findViewById(R.id.tvAmount)

        btnClose.setOnClickListener { it
            transferSuccessPresenter?.onCloseView()
        }

        val bundle = this.arguments

        if (bundle != null) {
            val referenceNumber = bundle.getString(DATA_REFERENCE_NUMBER)
            val fromAccountNumber = bundle.getString(DATA_FROM_ACCOUNT_NUMBER)
            val toAccountNumber = bundle.getString(DATA_TO_ACCOUNT_NUMBER)
            val amount = bundle.getDouble(DATA_AMOUNT, 0.0)

            transferSuccessPresenter?.onReceiveData(referenceNumber, fromAccountNumber, toAccountNumber, amount)
        }

        return view
    }

    override fun onDestroy() {
        transferSuccessPresenter?.onDestroy()

        super.onDestroy()
    }

    // TransferSuccessContract.View functions

    override fun showTransferSuccess(referenceNumber: String, fromAccountNumber: String, toAccountNumber: String, amount: Double) {
        tvReferenceNumber?.text = referenceNumber
        tvFromAccountNumber?.text = fromAccountNumber
        tvToAccountNumber?.text = toAccountNumber
        tvAmount?.text = amount.toString()
    }

    override fun dismissView() {
        this.activity?.supportFragmentManager?.popBackStack()
    }
}