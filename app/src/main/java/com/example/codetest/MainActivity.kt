package com.example.codetest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.codetest.fragment.TransferFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, TransferFragment.newInstance())
                    .commitNow()
        }
    }
}
