package com.virgendelosdoloreslacarlota.dep.services

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.virgendelosdoloreslacarlota.dep.R
import com.virgendelosdoloreslacarlota.dep.feature.main.MainActivity
import com.virgendelosdoloreslacarlota.dep.usecase.SaveTokenUseCase
import com.virgendelosdoloreslacarlota.domain.token.TokenRepository
import com.virgendelosdoloreslacarlota.domain.token.TokenRequest
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

@AndroidEntryPoint
class MessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var saveTokenRequest: SaveTokenUseCase

    override fun onNewToken(token: String) {
        Timber.d("FirebaseToken: $token")
        super.onNewToken(token)
        saveTokenRequest(TokenRequest(token))
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Timber.d("Notification: ${remoteMessage.from}")
        if (remoteMessage.notification != null) {
            showNotification(remoteMessage.notification?.title, remoteMessage.notification?.body)
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun showNotification(title: String?, body: String?) {
        val channelId = getString(R.string.default_notification_channel_id)
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getActivity(
                this, 0, intent,
                PendingIntent.FLAG_IMMUTABLE
                        or PendingIntent.FLAG_UPDATE_CURRENT
            )
        } else {
            PendingIntent.getActivity(
                this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        val notificationBuilder =
            NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_notification)
                .setAutoCancel(true)
                .setLights(Color.BLUE, 500, 500)
                .setVibrate(longArrayOf(500, 500, 500))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentIntent(pendingIntent)
                .setContentTitle(title)
                .setContentText(body)

        val notificationManager = NotificationManagerCompat.from(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "D.E.P. La Carlota",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(NotificationID.iD, notificationBuilder.build())
    }

    internal object NotificationID {
        private val c = AtomicInteger(100)
        val iD: Int
            get() = c.incrementAndGet()
    }
}