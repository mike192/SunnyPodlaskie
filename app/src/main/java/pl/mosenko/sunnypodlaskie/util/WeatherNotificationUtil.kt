package pl.mosenko.sunnypodlaskie.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import pl.mosenko.sunnypodlaskie.R
import pl.mosenko.sunnypodlaskie.mvp.MainActivity
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by syk on 26.05.17.
 */

private const val WEATHER_NOTIFICATION_ID = 3011
private const val NOTIFICATION_CHANNEL_ID = "4011"

class WeatherNotificationUtil(private val context: Context) {

    fun notifyUserOfNewWeatherData(receivingTime: Date) {
        createNotificationChannel(context)
        val notification = buildNotification(receivingTime)
        notify(notification)
    }

    private fun notify(notification: Notification) {
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(WEATHER_NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.notification_channel_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance)
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun buildNotification(receivingTime: Date): Notification {
        val notificationTitle = context.getString(R.string.notification_title)
        val notificationContentText = context.getString(R.string.notification_content_text)
        val receivingTimeFormatted = getFormattedReceivingDate(receivingTime)
        val notificationBuilder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
            .setContentTitle(notificationTitle)
            .setContentText("$notificationContentText $receivingTimeFormatted")
            .setSmallIcon(R.drawable.ic_clear)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
        val mainActivityIntentPendingIntent = getPendingIntent()
        notificationBuilder.setContentIntent(mainActivityIntentPendingIntent)
        return notificationBuilder.build()
    }

    private fun getPendingIntent(): PendingIntent? {
        val mainActivityIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        return PendingIntent.getActivity(context, 0, mainActivityIntent, 0)
    }

    private fun getFormattedReceivingDate(receivingTime: Date): String? {
        val notificationDateFormat = SimpleDateFormat("HH:mm:ss dd.MM", Locale.getDefault())
        return notificationDateFormat.format(receivingTime)
    }
}
