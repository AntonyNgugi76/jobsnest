package com.example.jobsnest.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jobsnest.Transactions
import com.example.jobsnest.Utils.DeadlineNotification
import com.example.jobsnest.Utils.SharedPreferences
import com.example.jobsnest.databinding.ActivityWorkDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class WorkDetailsActivity : AppCompatActivity() {
    val auth= FirebaseAuth.getInstance()
    val dbref= FirebaseDatabase.getInstance()
    private val workDetailBinding by lazy {
        ActivityWorkDetailsBinding.inflate(layoutInflater)
    }
    private val start = 86400000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(workDetailBinding.root)
        supportActionBar?.hide()

        getWorkData()
        val sharedPref = SharedPreferences()
        val key = "millis"
        val deadlineTime = sharedPref.retrieveFromSharedPreferences(this,key)
        Toast.makeText(this, "$deadlineTime", Toast.LENGTH_SHORT).show()
        val remainingTime = deadlineTime?.minus(System.currentTimeMillis())
        remainingTime?.let { workDetailBinding.countdownTimerView.start(it) }

        workDetailBinding.confirmWorkButton.setOnClickListener {
            /*val title = "Ring Ring!!"
            val message = "You have 12 hours left!"
            val notificationMessage = DeadlineNotification()
            notificationMessage.notification(this, title, message)*/
            val accText = workDetailBinding.accountNameTextView.text.toString()
            val amountPaid = workDetailBinding.amountPaidTextView.text.toString().toInt()
            val writerEarning = workDetailBinding.writerEarningTextView.text.toString().toInt()
            addToDb(accText, amountPaid, writerEarning)
        }



    }

    private fun addToDb(accText: String, amountPaid: Int,writerEarning: Int) {
        val dbref= FirebaseDatabase.getInstance().getReference("transactions")
            .child(auth.currentUser?.uid!!)
            .push()
        val transaction = Transactions(accText, amountPaid, writerEarning)
        dbref.setValue(transaction)
        deleteDataFromDb()
    }

    private fun deleteDataFromDb() {
        val pushKey = intent.getStringExtra("pushKey")
        val dbref= FirebaseDatabase.getInstance().getReference("jobs")
            .child(auth.currentUser?.uid!!)
            .child(pushKey!!)
        dbref.removeValue()
        backToMainActivity()
    }

    private fun backToMainActivity() {
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

    private fun getWorkData() {
        val pushKey = intent.getStringExtra("pushKey")
        val dbref= FirebaseDatabase.getInstance().getReference("jobs")
            .child(auth.currentUser?.uid!!)
            .child(pushKey!!)
        dbref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val acc_name = snapshot.child("acc_name").value.toString()
                    val job_desc = snapshot.child("job_desc").value.toString()
                    val amt_paid = snapshot.child("amt").value.toString()
                    val writer_name = snapshot.child("writer_name").value.toString()
                    val writer_pay = snapshot.child("writer_earning").value.toString()
                    val deadline_date = snapshot.child("deadline_date").value
                    val deadline_millis = snapshot.child("deadline_millis").value.toString().toLong()
                    val remainingTime = deadline_millis?.minus(System.currentTimeMillis())
                    remainingTime?.let { workDetailBinding.countdownTimerView.start(it) }
                    workDetailBinding.accountNameTextView.text = acc_name
                    workDetailBinding.jobDescriptionTextView.text = job_desc
                    workDetailBinding.amountPaidTextView.text = amt_paid
                    workDetailBinding.writerNameTextView.text = writer_name
                    workDetailBinding.writerEarningTextView.text = writer_pay


                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    private fun convertToMillis(selectedDays: Long) {
        val daysToMillis = selectedDays*24*3600*1000
        val deadlineTime = daysToMillis + System.currentTimeMillis()
        val remainingTime = deadlineTime?.minus(System.currentTimeMillis())
        remainingTime?.let { workDetailBinding.countdownTimerView.start(it) }
        /*val key = "millis"
        val sharedPref = SharedPreferences()
        sharedPref.saveToSharedPreferences(this,key,deadlineTime)
        startActivity(Intent(this,WorkDetailsActivity::class.java))
        finish()*/
    }

    override fun onBackPressed() {
        super.onBackPressed()
        backToMainActivity()
    }


}