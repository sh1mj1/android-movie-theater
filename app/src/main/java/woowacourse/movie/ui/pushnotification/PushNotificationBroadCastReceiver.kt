package woowacourse.movie.ui.pushnotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import woowacourse.movie.R
import java.util.Date

class PushNotificationBroadCastReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel("channelId", "channelName", NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)

        val notification =
            NotificationCompat.Builder(context, "channelId")
                .setContentTitle("Movie Reservation")
                .setContentText("Your movie will start in 1 hour.")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build()

        notificationManager.notify(1, notification)

        Log.d("Alarm", "Alarm triggered at ${Date()}")
    }
}
