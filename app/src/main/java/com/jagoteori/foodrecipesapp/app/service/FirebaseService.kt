package com.jagoteori.foodrecipesapp.app.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.jagoteori.foodrecipesapp.MainActivity
import com.jagoteori.foodrecipesapp.R
import kotlin.random.Random

class FirebaseService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        showNotification(message)
    }

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
        token = newToken
    }

    private fun showNotification(message: RemoteMessage) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivities(
            this, 0, arrayOf(intent),
            FLAG_ONE_SHOT
        )

        val mNotificationManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationID = Random.nextInt()

        val mBuilder = this.let {
            NotificationCompat.Builder(it, "channel_like_recipe")
                .setSmallIcon(R.drawable.ic_favorite)
                .setContentTitle(message.data["title"])
                .setContentText("Menyukai resep ${message.data["message"]} anda!")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) createNotificationChannel(
            notificationManager = mNotificationManager
        )

        val notification = mBuilder.build()
        mNotificationManager.notify(notificationID, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(
            "channel_like_recipe",
            "food recipe channel",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "food recipe channel"
            enableLights(true)
            lightColor = Color.MAGENTA
            enableVibration(true)
        }
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        var sharePreferences: SharedPreferences? = null
        var token: String?
            get() = sharePreferences?.getString("token", "")
            set(value) {
                sharePreferences?.edit()?.putString("token", value)?.apply()
            }
    }

}