package com.example.jobsnest.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobsnest.Adpters.NetIncomeAdapter
import com.example.jobsnest.Income
import com.example.jobsnest.Job
import com.example.jobsnest.R
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NetIncome : AppCompatActivity() {
    private lateinit var recyclerIncome: RecyclerView
    private lateinit var extendedFloatingActionButton: ExtendedFloatingActionButton
    private lateinit var arr: ArrayList<Income>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_net_income)

        getTransactionData()

        extendedFloatingActionButton = findViewById(R.id.extend)
        recyclerIncome = findViewById(R.id.recyclerNetIncome)
        recyclerIncome.layoutManager= LinearLayoutManager(this)
        recyclerIncome.setHasFixedSize(true)

        arr = arrayListOf()


    }

    private fun getTransactionData() {
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser?.uid
        val dbRef = FirebaseDatabase.getInstance().getReference("transactions").child(user!!)

        dbRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    var earningTotal = 0
                    for(dataSnapshot in snapshot.children){
                        val accName = dataSnapshot.child("accName").value.toString()
                        val amountPaid = dataSnapshot.child("amountPaid").value.toString().toInt()
                        val writerEarning = dataSnapshot.child("writerEarning").value.toString().toInt()
                        var earning = amountPaid - writerEarning
                        earningTotal += earning
                        extendedFloatingActionButton.text = "= $$earningTotal"
                        val transaction = Income(accName, earning.toString())
                        arr.add(transaction)
                        val adapter = NetIncomeAdapter(arr)
                        recyclerIncome.adapter= adapter
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}