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
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_transaction.*

class LoginActivity : AppCompatActivity(){
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_login)
        updateUI(mAuth.currentUser)
    }

    fun buttonLogInOnClick(view: View) {
        val email = text_field_email.editText?.text.toString()
        val password = text_field_password.editText?.text.toString()
        if (email != "" && password != "") {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Success", "signInWithEmail:success")
                        val user = mAuth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Error", "signInWithEmail:failure", task.exception)
                        Snackbar.make(view, "Email or password is wrong!", Snackbar.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }
        } else {
            Snackbar.make(view, "Email or password cannot be blank!", Snackbar.LENGTH_SHORT).show()
        }
        val hideKeyboard = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        hideKeyboard.hideSoftInputFromWindow(LinerLayoutLogin.windowToken, 0)
    }

    fun buttonCreateAccountOnClick(view: View) {
        val intent = Intent(this, CreateAccountActivity::class.java)
        startActivity(intent)
        val hideKeyboard = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        hideKeyboard.hideSoftInputFromWindow(LinerLayoutLogin.windowToken, 0)
    }

    fun updateUI(user : FirebaseUser?) {
        if (user != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
