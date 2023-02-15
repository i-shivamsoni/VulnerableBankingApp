package com.example.vulnerablebankingapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_transaction.*
import java.time.LocalDateTime


class TransactionActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_transaction)
    }

    fun buttonSendMoneyOnClick(view: View) {
        text_field_account.error = ""
        text_field_amount.error = ""
        val user = mAuth.currentUser
        val accountNumber = text_field_account.editText?.text.toString()
        if (text_field_amount.editText?.text.toString() == "") {
            text_field_amount.error = "Amount cannot be blank."
        } else {
            val amountTemp = text_field_amount.editText?.text.toString().toFloat()
            val amount = MoneyConverter.poundsToPennies(amountTemp).toString()
            var amountDecimalPlaces = 0
            if (text_field_amount.editText?.text.toString().contains('.')) {
                val amountDecimalIndex = text_field_amount.editText?.text.toString().indexOf('.')
                amountDecimalPlaces = text_field_amount.editText?.text.toString().length - amountDecimalIndex - 1
            }
            if (accountNumber.length != 6) {
                text_field_account.error = "The account number must be 6 digits"
            } else if (amountDecimalPlaces > 2) {
                text_field_amount.error = "There can't be more than two decimal places!"
            } else if (amount.toInt() < 1) {
                text_field_amount.error = "The amount must be greater than 0.01!"
            } else if (user != null) {
                val intent = Intent(this, TransactionService::class.java)
                intent.putExtra("accountNumber", accountNumber)
                intent.putExtra("amount", amount)
                startService(intent)
            }
            val hideKeyboard = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            hideKeyboard.hideSoftInputFromWindow(LinerLayoutTransaction.windowToken, 0)
        }
    }
}