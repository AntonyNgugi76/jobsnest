package com.example.jobsnest.Activities
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobsnest.Adpters.RecyclerAdapter
import com.example.jobsnest.Fragments.AddWorkDialogFragment
import com.example.jobsnest.Job
import com.example.jobsnest.R
import com.example.jobsnest.Utils.ConnectivityStatus
import com.example.jobsnest.Utils.CustomSnackBar
import com.example.jobsnest.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var dbref: DatabaseReference
    private lateinit var recyclerViewhome: RecyclerView
    private lateinit var jobArrayList: ArrayList<Job>

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        recyclerViewhome=findViewById(R.id.rViewHome)
        recyclerViewhome.layoutManager=LinearLayoutManager(this)
        recyclerViewhome.setHasFixedSize(true)

        jobArrayList= arrayListOf<Job>()

        checkNetworkConnection()

        mainBinding.addAccountName.setOnClickListener {
            val accDialog = AddWorkDialogFragment()
            accDialog.show(supportFragmentManager,"AccountDialog")
        }
        mainBinding.addNewJob.setOnClickListener {
            startActivity(Intent(this,AddWorkActivity::class.java))
            finish()
        }

    }

    private fun getJobsdata() {
        auth= FirebaseAuth.getInstance()
        dbref= FirebaseDatabase.getInstance().getReference("jobs").child(auth.currentUser?.uid!!)
        dbref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (jobSnapshot in snapshot.children) {
                        val key = jobSnapshot.key
                        val accountname= jobSnapshot.child("acc_name").value.toString()
                        val jobdesc = jobSnapshot.child("job_desc").value.toString()
                        val duedate = jobSnapshot.child("deadline_date").value.toString().toInt()
                        val writername = jobSnapshot.child("writer_name").value.toString()
                        val amt = jobSnapshot.child("amt").value.toString().toInt()
                        val writerearning = jobSnapshot.child("amt").value.toString().toInt()
                        val jobDetails = Job(key!!, accountname, jobdesc, duedate, 0L, amt, writerearning, writername)

                        jobArrayList.add(jobDetails)
                        recyclerViewhome.adapter=RecyclerAdapter(jobArrayList, this@MainActivity)

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    private fun checkNetworkConnection() {
        val mainConnectivity = ConnectivityStatus(this)
        mainConnectivity.observe(this) { isConnected ->
            if (!isConnected) {
                val message = " No Internet Connection!!! "
                val view = mainBinding.root
                val customSnackBar = CustomSnackBar()
                customSnackBar.showCustomSnackBar(this,view,message)
                mainBinding.floatingActionsMenu.isEnabled = false
                mainBinding.noConnection.visibility = View.VISIBLE
                mainBinding.noConnectionImage.visibility = View.VISIBLE
            } else {
                mainBinding.floatingActionsMenu.isEnabled = true
                mainBinding.noConnection.visibility = View.GONE
                mainBinding.noConnectionImage.visibility = View.GONE
                getJobsdata()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return  true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId){
            R.id.logout->{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
                true
            }
            R.id.addaccount->{
                true
            }
            R.id.netIncome->{
                startActivity(Intent(this, NetIncome::class.java))
                finish()
                true
            }
            else-> super.onOptionsItemSelected(item)
        }
    }


}