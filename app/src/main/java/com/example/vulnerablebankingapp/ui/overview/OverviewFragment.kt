package com.example.vulnerablebankingapp.ui.overview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vulnerablebankingapp.MoneyConverter
import com.example.vulnerablebankingapp.R
import com.example.vulnerablebankingapp.TopSpacingItemDecoration
import com.example.vulnerablebankingapp.TransactionData
import com.example.vulnerablebankingapp.adapters.RecycleViewAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_overview.*

class OverviewFragment : Fragment() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var recycleViewAdapter: RecycleViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            val topSpacingItemDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingItemDecoration)
            recycleViewAdapter = RecycleViewAdapter()
            adapter = recycleViewAdapter
        }

        val databaseListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val accountNumber = dataSnapshot.child("account_number").value
                val balance = MoneyConverter.penniesToPounds((dataSnapshot.child("balance").value).toString().toInt())
                Log.e("balanceError", (dataSnapshot.child("balance").value).toString())
                //Error account number is sometimes null, it crashes because i switch fragment before the account number and balance have been feteched so the text views no longer exist.
                account_number_text_view.text = "Account Number: $accountNumber"
                balance_text_view.text = "Balance: $balance"
                Log.e("account", accountNumber.toString())
            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        mAuth.uid?.let { database.getReference(it) }?.addListenerForSingleValueEvent(databaseListener)

        var list: ArrayList<TransactionData> = ArrayList()
        val transactionListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var i = 0
                for (transaction in dataSnapshot.children) {
                    i+=1
                    list.add(
                        TransactionData(
                            "Date: " + transaction.key.toString().split("T")[0],
                            "Amount: " + transaction.value.toString()
                    )
                    )
                }
                list.reverse()
                recycleViewAdapter.submitList(list)
                recycleViewAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        mAuth.uid?.let { database.getReference(it).child("transactions") }?.addListenerForSingleValueEvent(transactionListener)

    }

}