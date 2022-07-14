package com.example.jobsnest.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.jobsnest.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Register : AppCompatActivity() {
    private lateinit var  auth: FirebaseAuth
    lateinit var emailText:TextInputEditText
    lateinit var passwordText:TextInputEditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

         auth= FirebaseAuth.getInstance()

        val buttonreg:Button= findViewById(R.id.buttonregister)
        buttonreg.setOnClickListener(){

            register()

        }
    }

    private fun register() {
        emailText=findViewById(R.id.edEmail)
        passwordText=findViewById(R.id.edPassword)

        val email =emailText.text.toString()
        val password = passwordText.text.toString()
        if (email.isNotEmpty()&& password.isNotEmpty()){
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{task->

                if(task.isSuccessful){
                    saveUserDetails()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }.addOnFailureListener{ Exception->
                Toast.makeText(applicationContext, Exception.localizedMessage, Toast.LENGTH_LONG).show()

            }

        } else{
            Toast.makeText(this, "Fill in all the fields", Toast.LENGTH_LONG).show()
        }







    }

    private fun saveUserDetails() {
        // Write a message to the database
        val database = Firebase.database
        val myRef = database.getReference("users")

    }
}