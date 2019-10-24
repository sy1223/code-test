package com.example.codetest.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.codetest.R
import com.example.codetest.contract.TransferContract
import com.example.codetest.presenter.TransferPresenter

class TransferFragment : Fragment(), TransferContract.View {

    companion object {
        fun newInstance(): TransferFragment {
            return TransferFragment()
        }
    }

    private var transferPresenter: TransferPresenter? = null

    private var tvFromAccountNumber: TextView? = null
    private var tvToAccountNumber: TextView? = null
    private var etAmount: EditText? = null
    private var tvErrorMessage: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_transfer, container, false)

        transferPresenter = TransferPresenter(this)

        val rlFromAccount = view.findViewById<RelativeLayout>(R.id.rlFromAccount)
        val rlToAccount = view.findViewById<RelativeLayout>(R.id.rlToAccount)
        val btnConfirm = view.findViewById<Button>(R.id.btnConfirm)

        tvFromAccountNumber = view.findViewById(R.id.tvFromAccountNumber)
        tvToAccountNumber = view.findViewById(R.id.tvToAccountNumber)
        etAmount = view.findViewById(R.id.etAmount)
        tvErrorMessage = view.findViewById(R.id.tvErrorMessage)

        rlFromAccount.setOnClickListener { it
            transferPresenter?.onShowFromAccounts()
        }

        rlToAccount.setOnClickListener { it
            transferPresenter?.onShowToAccounts()
        }

        btnConfirm.setOnClickListener { it
            transferPresenter?.onTransfer(tvFromAccountNumber?.text.toString(), tvToAccountNumber?.text.toString(), etAmount?.text.toString())
        }

        return view
    }

    override fun onDestroy() {
        transferPresenter?.onDestroy()

        super.onDestroy()
    }

    // TransferContract.View functions

    override fun showFromAccounts(accounts: Array<String>) {
        showAccountSelectionDialog(accounts, true)
    }

    override fun showToAccounts(accounts: Array<String>) {
        showAccountSelectionDialog(accounts, false)
    }

    override fun showTransferSuccess(fromAccountNumber: String, toAccountNumber: String, amount: Double, referenceNumber: String) {
        val activity = getActivity()
        val transferSuccessFragment = TransferSuccessFragment.newInstance(referenceNumber, fromAccountNumber, toAccountNumber, amount)

        if (activity != null) {
            val transaction = activity.supportFragmentManager.beginTransaction()

            transaction.replace(R.id.container, transferSuccessFragment, "TransferSuccessFragment")
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    override fun showTransferError(errorMessageRedId: Int) {
        tvErrorMessage?.setText(errorMessageRedId)
    }

    override fun clearErrorMessage() {
        tvErrorMessage?.text = ""
    }

    override fun resetViewData() {
        tvFromAccountNumber?.text = ""
        tvToAccountNumber?.text = ""
        etAmount?.setText("")
        tvErrorMessage?.text = ""
    }

    //

    private fun showAccountSelectionDialog(accounts: Array<String>, isFromAccount: Boolean) {
        val activity = this.activity

        if (activity != null) {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle(getString(R.string.choose_an_account))
            builder.setItems(accounts) { _, which ->
                val selectedAccountNumber = accounts[which]

                if (isFromAccount) {
                    tvFromAccountNumber?.setText(selectedAccountNumber)
                } else {
                    tvToAccountNumber?.setText(selectedAccountNumber)
                }
            }

            val dialog = builder.create()
            dialog.show()
        }
    }
}