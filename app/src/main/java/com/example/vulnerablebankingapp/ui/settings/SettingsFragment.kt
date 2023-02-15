package com.example.vulnerablebankingapp.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vulnerablebankingapp.CreateAccountActivity
import com.example.vulnerablebankingapp.LoginActivity
import com.example.vulnerablebankingapp.R
import com.example.vulnerablebankingapp.ReauthenticateToChangePasswordActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_transaction_check_password.*

class SettingsFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        change_password_button.setOnClickListener {
            val intent = Intent(it.context, ReauthenticateToChangePasswordActivity::class.java)
            startActivity(intent)
        }

        log_out.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(it.context, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}