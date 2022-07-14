package com.example.jobsnest.Utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferences {
    fun saveToSharedPreferences(context:Context, key:String, value:Long){
        val sharedPreferences: SharedPreferences? =
            context.getSharedPreferences("com.example.jobsnest", Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor = sharedPreferences?.edit() as SharedPreferences.Editor
        editor.putLong(key,value)
        editor.apply()
    }
    fun retrieveFromSharedPreferences(context: Context,key: String): Long? {
        val sharedPreferences: SharedPreferences? =
            context.getSharedPreferences("com.example.jobsnest", Context.MODE_PRIVATE)
        val deadlineTime = sharedPreferences?.getLong(key,0)
        return deadlineTime
    }
}