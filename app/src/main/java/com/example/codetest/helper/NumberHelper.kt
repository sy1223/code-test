package com.example.codetest.helper

class NumberHelper {

    companion object {
        fun isValidDollarAmount(amount: String): Boolean {
            return amount.matches("\\d+([,.]\\d{1,2})?".toRegex())
        }
    }
}