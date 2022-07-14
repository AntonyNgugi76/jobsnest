package com.example.jobsnest.Adpters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jobsnest.Income
import com.example.jobsnest.Job
import com.example.jobsnest.R

class NetIncomeAdapter(private var arr :List<Income>): RecyclerView.Adapter<NetIncomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.net_income_layout, parent, false)
        return ViewHolder(view)

    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = arr[position]
        holder.accountname.text = currentItem.accName
        holder.amount.text= currentItem.earning
    }
    override fun getItemCount(): Int {
        return arr.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val accountname: TextView = itemView.findViewById(R.id.textViewAccountname)
        val amount: TextView = itemView.findViewById(R.id.textViewAmt)

    }


}