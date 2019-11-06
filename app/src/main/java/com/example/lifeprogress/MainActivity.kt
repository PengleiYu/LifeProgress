package com.example.lifeprogress

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        btn_notify.setOnClickListener { createNotification() }
        scheduleJob()
    }

    private fun scheduleJob() {
        val request = PeriodicWorkRequest.Builder(FunnyWork::class.java, 1, TimeUnit.MINUTES).build()
        WorkManager.getInstance(this).enqueue(request)
    }

    class FunnyWork(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
        override fun doWork(): Result {
            createNotification(applicationContext)
            return Result.success()
        }

        private fun createNotification(context: Context) {
            // 通知跳转
            val intent = Intent(context, ResultActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_NO_CREATE)

            // 创建通知
            val builder = NotificationCompat.Builder(context, App.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.funny))
                .setContentTitle("Current Time")
                .setContentText(Date().toString())
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            with(NotificationManagerCompat.from(context)) {
                notify(NOTIFICATION_ID, builder.build())
            }
        }
    }

    companion object {
        private const val NOTIFICATION_ID = 1
    }
}
