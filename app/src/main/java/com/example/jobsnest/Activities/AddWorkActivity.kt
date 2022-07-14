package com.example.jobsnest.Activities

import android.accounts.Account
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.jobsnest.AccountName
import com.example.jobsnest.Job
import com.example.jobsnest.R
import com.example.jobsnest.databinding.ActivityAddworkBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AddWorkActivity : AppCompatActivity(){
    val user =FirebaseAuth.getInstance().currentUser?.uid!!
    var accountList: ArrayList<String> = ArrayList()
    val workBinding by lazy {
        ActivityAddworkBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?){
    super.onCreate(savedInstanceState)
        setContentView(workBinding.root)
        supportActionBar?.hide()

        getAccountNames()

        workBinding.addWorkButton.setOnClickListener {

            val acc_name=  workBinding.writingAccount.text.toString()
            val job_desc=  workBinding.jobDescription.text.toString()
            val deadline_date=  workBinding.days.text.toString().toInt()
            val writer_name=  workBinding.writerName.text.toString()
            val writer_earning=  workBinding.writerEarning.text.toString().toInt()
            val amt=  workBinding.amountPaid.text.toString().toInt()
            val daysToMillis = deadline_date*24*3600*1000
            val deadline_time = daysToMillis + System.currentTimeMillis()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
            workDetails(user, acc_name, job_desc, deadline_date, deadline_time, writer_name, writer_earning, amt)
        }
    }

    private fun getAccountNames() {
        val dbref = FirebaseDatabase.getInstance().getReference("accounts").child(user)
        dbref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(dataSnashot in snapshot.children){
                    val accountData = dataSnashot.child("acc_name").value
                    Log.e("accountData", "onDataChange: $accountData", )
                    accountList.add(accountData!! as String)
                    Log.e("list", "onDataChange: $accountList", )
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        //val accounts = listOf("Fiverr", "CourseHero", "Turing")
        val days = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15")
        val deadlineAdapter = ArrayAdapter(this, R.layout.account_list_item,days)
        val workAdapter = ArrayAdapter(this, R.layout.account_list_item,accountList)
        workBinding.writingAccount.setAdapter(workAdapter)
        workBinding.days.setAdapter(deadlineAdapter)
    }

    private fun workDetails(uid:String,
                            acc_name: String,
                            job_desc: String,
                            deadline_date: Int,
                            deadline_time: Long,
                            writer_name: String,
                            writer_earning:Int,
                            amt: Int) {
        val database = Firebase.database
        val myRef = database.getReference("jobs").child(uid).push()
        val job =Job("",acc_name, job_desc, deadline_date, deadline_time, amt, writer_earning, writer_name)
        myRef.setValue(job)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

}