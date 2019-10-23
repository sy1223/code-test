package com.example.codetest.helper

class NumberHelper {

    companion object {
        fun isValidDollarFormat(amount: String): Boolean {
            return amount.matches("\\d+([,.]\\d{1,2})?".toRegex())
        }
    }
}