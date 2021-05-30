package com.library.dailyactivityplanner

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SessionManager.init(applicationContext)
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channel =
                createChannelImportance("channel", "Channel Notification", "Notification for Alarm")

            val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            mNotificationManager.createNotificationChannel(channel)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannelImportance(
        idChannel: String,
        nameChannel: String,
        descriptionChannel: String
    ): NotificationChannel {
        val channel = NotificationChannel(
            idChannel,
            nameChannel,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            val attr = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build()

            setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), attr)
            lightColor = Color.GRAY
            enableLights(true)
            vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        }
        channel.description = descriptionChannel
        return channel
    }
}