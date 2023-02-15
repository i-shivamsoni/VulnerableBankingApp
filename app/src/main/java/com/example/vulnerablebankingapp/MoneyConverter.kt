package com.example.vulnerablebankingapp

import android.icu.util.UniversalTimeScale.toBigDecimal
import android.util.Log
import java.math.BigDecimal

class MoneyConverter {

    companion object {
        fun penniesToPounds(amount: Int): BigDecimal {
            return  BigDecimal(amount).divide(BigDecimal("100"))
        }

        fun poundsToPennies(amount: Float): Int {
            return (amount * 100).toInt()
        }
    }
}