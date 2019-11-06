package com.example.lifeprogress

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_notify.setOnClickListener { createNotification() }
    }

    private fun createNotification() {
        val intent = Intent(this, ResultActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 1,
            intent, PendingIntent.FLAG_NO_CREATE
        )

        val builder = NotificationCompat.Builder(this, App.CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_notification_overlay)
            .setContentTitle("Hello")
            .setContentText("world")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    companion object {
        private const val NOTIFICATION_ID = 1
    }
}
