package com.example.vulnerablebankingapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlinx.android.synthetic.main.activity_transaction.*

class CreateAccountActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        mAuth = FirebaseAuth.getInstance()
    }

    fun buttonCreateAccountOnClick(view: View) {
        val email = text_field_email.editText?.text.toString()
        val password = text_field_password.editText?.text.toString()
        if (email != "" && password != "") {
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Success", "createUserWithEmail:success")
                        val user = mAuth.currentUser
                        if (user != null) {
                            addAccountToDatabase(user)
                        }
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Error", "createUserWithEmail:failure", task.exception)
                        Snackbar.make(view, task.exception?.message.toString(), Snackbar.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }
        } else {
            Snackbar.make(view, "Email or password cannot be blank!", Snackbar.LENGTH_SHORT).show()
        }
        val hideKeyboard = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        hideKeyboard.hideSoftInputFromWindow(LinerLayoutCreateAccount.windowToken, 0)
    }

    private fun addAccountToDatabase(user: FirebaseUser) {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference(user.uid)
        val accountNumber = (100000..999999).random()
        val balance = 10000
        myRef.child("account_number").setValue(accountNumber)
        myRef.child("balance").setValue(balance)
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
