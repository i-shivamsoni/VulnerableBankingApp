package com.example.vulnerablebankingapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vulnerablebankingapp.R
import com.example.vulnerablebankingapp.TransactionData
import kotlinx.android.synthetic.main.transaction_list_item.view.*

class RecycleViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var items: List<TransactionData> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transaction_list_item, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ArticleViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(transactionList: List<TransactionData>) {
        items = transactionList
    }

    class ArticleViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val date = itemView.date_text_view
        private val amount = itemView.amount_text_view

        fun bind(transactionData: TransactionData) {
            date.text = transactionData.date
            amount.text = transactionData.amount

        }
    }
}