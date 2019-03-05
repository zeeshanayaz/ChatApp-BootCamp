package com.zeeshan.chatapp.firebaseMessaging

import android.app.NotificationManager
import android.content.ContentValues.TAG
import android.content.Context
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.zeeshan.chatapp.R
import java.lang.NullPointerException

class MyFirebaseMessagingService: FirebaseMessagingService() {


    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)

        if (remoteMessage != null){
            showNotification(remoteMessage)
        }

    }

    private fun showNotification(remoteMessage: RemoteMessage) {
        if(remoteMessage.data != null){
            Log.d("RemoteMessage", remoteMessage.data.toString())

            var channelId = "Channel-1"

            val name = resources.getResourceName(R.string.app_name)

            val notificationBuilder = NotificationCompat.Builder(this, channelId)
                .setContentTitle(name)
                .setContentText("New Message")
                .setSmallIcon(R.mipmap.ic_launcher)
                .build()

            val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder)
        }

    }


    override fun onNewToken(token: String?) {
//        super.onNewToken(token)
        Log.d("TokenFirebase", "Token Refreshed : $token")
    }

    companion object {
        fun addTokenToFirebase(newRegistrationToken: String?){
            if (newRegistrationToken == null ) throw NullPointerException("FCM is Null")
        }
    }
}