package com.example.codetest

import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.codetest.presenter.TransferPresenter
import com.example.codetest.view.TransferView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TransferView {

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

    // TransferView functions

    override fun onShowFromAccounts(accounts: Array<String>) {
        showAccountSelectionDialog(accounts, true)
    }

    override fun onShowToAccounts(accounts: Array<String>) {
        showAccountSelectionDialog(accounts, false)
    }

    override fun onTransferSuccess(referenceNumber: String) {
        val intent = Intent(this, TransferSuccessActivity::class.java)
        intent.putExtra(TransferSuccessActivity.DATA_REFERENCE_NUMBER, referenceNumber)
        startActivity(intent)
    }

    override fun onTransferError(errorMessageRedId: Int) {
        val toast = Toast.makeText(applicationContext, getString(errorMessageRedId), Toast.LENGTH_LONG)
        toast.show()
    }
}
