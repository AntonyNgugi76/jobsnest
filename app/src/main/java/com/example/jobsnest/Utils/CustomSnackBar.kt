package com.example.jobsnest.Utils

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.example.jobsnest.R
import com.google.android.material.snackbar.Snackbar

class CustomSnackBar {
    fun showCustomSnackBar(context: Context, view: View, message: String){
        Snackbar.make(view,message,Snackbar.LENGTH_LONG)
            .setBackgroundTint(ContextCompat.getColor(context, R.color.green))
            .setTextColor(ContextCompat.getColor(context,R.color.white))
            .show()
    }
}