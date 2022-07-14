package com.example.jobsnest.Adpters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.jobsnest.Activities.WorkDetailsActivity
import com.example.jobsnest.Job
import com.example.jobsnest.R
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.core.Context

class RecyclerAdapter(
    private var arrJob: List<Job>,
    val activity: AppCompatActivity)
    :RecyclerView.Adapter<RecyclerAdapter.ViewHolder> (){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.r_layout, parent, false)
        return ViewHolder(view)


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem= arrJob[position]
        holder.acc_name.text= currentItem.acc_name
        holder.description.text=currentItem.job_desc
        holder.deadline.text=currentItem.deadline_date.toString()
        holder.writer_name.text= currentItem.writer_name.toString()
        holder.amt.text=currentItem.amt.toString()
        holder.cardLayout.setOnClickListener {
            val intent = Intent(activity,WorkDetailsActivity::class.java)
            intent.putExtra("pushKey", currentItem.push_id)
            activity.startActivity(intent)
            activity.finish()
        }


    }

    override fun getItemCount(): Int {
        return arrJob.size

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val acc_name: TextView = itemView.findViewById(R.id.tViewAccName)
        val description:TextView= itemView.findViewById(R.id.tviewDesc)
        val deadline:TextView= itemView.findViewById(R.id.tViewDeadline)
        val writer_name:TextView= itemView.findViewById(R.id.tviewWritername)
        val amt :TextView= itemView.findViewById(R.id.tViewAmt)
        val cardLayout: ConstraintLayout = itemView.findViewById(R.id.card_view_layout)

    }
}
