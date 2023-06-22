package com.example.vulnerablebankingapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.text_field_password

class ChangePasswordActivity : AppCompatActivity() 
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_change_password)
    }

    fun buttonChangePasswordOnClick(view: View) {
        val password = text_field_password.editText?.text.toString()
        if (password != "") {
            mAuth.currentUser?.updatePassword(password)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Snackbar.make(view, "Password changed!", Snackbar.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Snackbar.make(view, task.exception?.message.toString(), Snackbar.LENGTH_SHORT).show()
                }
            }
        } else {
            Snackbar.make(view, "Password cannot be blank!", Snackbar.LENGTH_SHORT).show()
        }
        val hideKeyboard = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        hideKeyboard.hideSoftInputFromWindow(LinerLayoutChangePassword.windowToken, 0)
    }
}
