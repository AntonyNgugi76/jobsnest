package com.example.jobsnest.Activities

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.Observer
import com.example.jobsnest.Utils.ConnectivityStatus
import com.example.jobsnest.Utils.CustomSnackBar
import com.example.jobsnest.databinding.ActivitySplashScreenBinding
import com.google.firebase.auth.FirebaseAuth

class SplashScreenActivity : AppCompatActivity() {
    val binding by lazy {
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        checkConnectivity()
    }

    private fun checkConnectivity() {
        val connectivity = ConnectivityStatus(this)
        connectivity.observe(this, Observer { isConnected ->
            if(isConnected){
                binding.message = "Internet Connected"
                Handler(Looper.myLooper()!!).postDelayed({
                    binding.networkStatus = true
                    val auth = FirebaseAuth.getInstance()
                    if (auth.uid!=null){
                        startMainActivity()
                    }
                    else{
                        startNextActivity()
                        finish()
                    }

                },2000)
            }else{
                Log.d(TAG, "checkConnectivity: no connection")
                binding.networkStatus = false
                binding.message = "No internet connection!!!"
                CustomSnackBar().showCustomSnackBar(this,binding.root,"Check your internet connection!!")
               /* startNextActivity()
                finish()*/
            }
        })
    }

    private fun startMainActivity() {
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

    private fun startNextActivity() {
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }
}