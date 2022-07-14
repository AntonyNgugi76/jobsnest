package com.example.jobsnest.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.fragment.app.DialogFragment
import com.example.jobsnest.AccountName
import com.example.jobsnest.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AddWorkDialogFragment : DialogFragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView : View = inflater.inflate(R.layout.fragment_add_work_dialog,container,false)
        rootView.findViewById<MaterialButton>(R.id.acc_input_cancel).setOnClickListener { dismiss() }
        rootView.findViewById<MaterialButton>(R.id.acc_input_ok).setOnClickListener {
           val accName  =  rootView.findViewById<TextInputLayout>(R.id.acc_name_input)
           val accNameText  =  accName.editText?.text
            if (accNameText?.isEmpty() == true){
                accName.error = "Account Name"
            }else{
                Toast.makeText(context, accName.editText?.text.toString(), Toast.LENGTH_SHORT).show()
                val accName = accName.editText?.text.toString()
                saveAccNameToDB(accName)
                dismiss()
            }

        }
        return rootView
    }

    private fun saveAccNameToDB(accName: String) {
        val auth = FirebaseAuth.getInstance()
        val dbref = FirebaseDatabase.getInstance().getReference("accounts")
        val accountName = AccountName(accName)
        dbref.child(auth.uid!!).push().setValue(accountName)

    }
}