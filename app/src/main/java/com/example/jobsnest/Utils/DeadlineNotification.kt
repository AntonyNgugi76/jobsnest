package com.example.jobsnest.Utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.jobsnest.R

class DeadlineNotification {
    fun notification(context: Context,title: String, message: String){
        val channelID = "com.example.jobsnest.utils"
        val channelName = "DEADLINE_NOTIFICATION"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mNotificationChannel = NotificationChannel(channelID,channelName,importance)
            val notificationBuilder = Notification.Builder(context, channelID)
                .setSmallIcon(R.drawable.ic_baseline_check_24)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mNotificationChannel)
            notificationManager.notify(0,notificationBuilder.build())
        }
        else{
            val notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_baseline_check_24)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(0,notificationBuilder.build())
        }
    }
}