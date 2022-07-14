package com.example.jobsnest.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.jobsnest.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var emailText:TextInputEditText
    private lateinit var passwordText:TextInputEditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val textView:TextView= findViewById(R.id.registerTview)
        val button: Button= findViewById(R.id.buttonLogin)

        auth= FirebaseAuth.getInstance()

        textView.setOnClickListener(){
            val intent1 = Intent(this, Register::class.java)
            startActivity(intent1)
        }
        button.setOnClickListener(){
            signIn()
        }
    }

    private fun signIn() {
        emailText = findViewById(R.id.edEmail)
        passwordText = findViewById(R.id.edPassword)

        val email =emailText.text.toString()
        val password = passwordText.text.toString()

        if(email.isNotEmpty()&& password.isNotEmpty()){
            auth.signInWithEmailAndPassword(email, password) .addOnCompleteListener{
                    task->
                if (task.isSuccessful){
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    val intent2= Intent(this, MainActivity::class.java)
                    startActivity(intent2)
                    finish()
                }
            } .addOnFailureListener{
                    Exception->
                Toast.makeText(applicationContext, Exception.localizedMessage, Toast.LENGTH_LONG).show()
            }

        }else
        {
            Toast.makeText(this, "An email and password is Required", Toast.LENGTH_SHORT).show()
        }



    }
}