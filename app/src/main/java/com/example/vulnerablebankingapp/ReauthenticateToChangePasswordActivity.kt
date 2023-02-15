package com.example.vulnerablebankingapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_reauthenticate_to_change_password.*

import kotlinx.android.synthetic.main.fragment_transaction_check_password.text_field_email

class ReauthenticateToChangePasswordActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reauthenticate_to_change_password)
        mAuth = FirebaseAuth.getInstance()
    }

    fun buttonReauthenticateOnClick(view: View) {
        val email = text_field_email.editText?.text.toString()
        val password = text_field_password.editText?.text.toString()
        if (email != "" && password != "") {
            val credentials = EmailAuthProvider.getCredential(email, password)
            mAuth.currentUser?.reauthenticate(credentials)?.addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("Reauthorise", "Success")
                    val intent = Intent(this, ChangePasswordActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.d("Reauthorise", "Error")
                    Snackbar.make(view, "Email or password is wrong!", Snackbar.LENGTH_SHORT).show()
                }
            }
        } else {
            Snackbar.make(view, "Email or password cannot be blank!", Snackbar.LENGTH_SHORT).show()
        }

    }
}