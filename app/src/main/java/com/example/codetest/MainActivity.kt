package com.example.codetest

import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.codetest.contract.TransferContract
import com.example.codetest.presenter.TransferPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TransferContract.View {

    private var transferPresenter: TransferPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        transferPresenter = TransferPresenter(this)

        rlFromAccount.setOnClickListener { it
            transferPresenter?.onShowFromAccounts()
        }

        rlToAccount.setOnClickListener { it
            transferPresenter?.onShowToAccounts()
        }

        btnConfirm.setOnClickListener { it
            transferPresenter?.onTransfer(tvFromAccountNumber.text.toString(), tvToAccountNumber.text.toString(), etAmount.text.toString())
        }
    }

    override fun onDestroy() {
        transferPresenter?.onDestroy()
        super.onDestroy()
    }

    private fun showAccountSelectionDialog(accounts: Array<String>, isFromAccount: Boolean) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.choose_an_account))
        builder.setItems(accounts) { _, which ->
            val selectedAccountNumber = accounts[which]

            if (isFromAccount) {
                tvFromAccountNumber.setText(selectedAccountNumber)
            } else {
                tvToAccountNumber.setText(selectedAccountNumber)
            }
        }

        val dialog = builder.create()
        dialog.show()
    }

    // TransferContract.View functions

    override fun showFromAccounts(accounts: Array<String>) {
        showAccountSelectionDialog(accounts, true)
    }

    override fun showToAccounts(accounts: Array<String>) {
        showAccountSelectionDialog(accounts, false)
    }

    override fun clearErrorMessage() {
        tvErrorMessage.setText("")
    }

    override fun showTransferSuccess(fromAccountNumber: String, toAccountNumber: String, amount: Double, referenceNumber: String) {
        val intent = Intent(this, TransferSuccessActivity::class.java)
        intent.putExtra(TransferSuccessActivity.DATA_FROM_ACCOUNT_NUMBER, fromAccountNumber)
        intent.putExtra(TransferSuccessActivity.DATA_TO_ACCOUNT_NUMBER, toAccountNumber)
        intent.putExtra(TransferSuccessActivity.DATA_AMOUNT, amount)
        intent.putExtra(TransferSuccessActivity.DATA_REFERENCE_NUMBER, referenceNumber)
        startActivity(intent)
    }

    override fun showTransferError(errorMessageRedId: Int) {
        tvErrorMessage.setText(errorMessageRedId)
    }
}
