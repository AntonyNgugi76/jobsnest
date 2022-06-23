package com.example.jobsnest.Activities

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.jobsnest.Job
import com.example.jobsnest.R
import com.example.jobsnest.databinding.ActivityAddworkBinding
import com.example.jobsnest.databinding.ActivityMainBinding
import com.example.jobsnest.databinding.ActivityMainBinding.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AddWorkActivity : AppCompatActivity(){
    val workBinding by lazy {
        ActivityAddworkBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?){
    super.onCreate(savedInstanceState)
        setContentView(workBinding.root)

        val user =FirebaseAuth.getInstance().currentUser

        workBinding.addWorkButton.setOnClickListener {

            //val acc_name=  workBinding.writingAccount.text.toString()
            val job_desc=  workBinding.jobDescription.text.toString()
           // val deadline_date=  workBinding.deadline.text.toString().toInt()
            val writer_name=  workBinding.writerName.text.toString()
            val writer_earning=  workBinding.writerEarning.text.toString().toInt()
            val amt=  workBinding.amountPaid.text.toString().toInt()

            user?.uid?.let { workDetails(it,acc_name="fiverr",job_desc, deadline_date=5,writer_name,writer_earning,amt) }
        }
    }

    private fun workDetails(uid:String,
                            acc_name: String,
                            job_desc: String,
                            deadline_date: Int,
                            writer_name: String,
                            writer_earning:Int,
                            amt: Int) {
        val database = Firebase.database
        val myRef = database.getReference("jobs")
        val job =Job(acc_name,job_desc,deadline_date,writer_name,writer_earning,amt)
        myRef.child(uid).setValue(job)

    }

}