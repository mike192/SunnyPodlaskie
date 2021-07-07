package pl.mosenko.sunnypodlaskie.util

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.content.ContextCompat
import pl.mosenko.sunnypodlaskie.R
import pl.mosenko.sunnypodlaskie.mvp.weatherdatalist.WeatherDataListActivity
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by syk on 26.05.17.
 */
object WeatherNotificationUtil {
    private const val WEATHER_NOTIFICATION_ID = 3011

    fun notifyUserOfNewWeatherData(context: Context, receivingTime: Date) {
        val notification = buildNotification(context, receivingTime)
        notify(context, notification)
    }

    private fun notify(context: Context, notification: Notification?) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(WEATHER_NOTIFICATION_ID, notification)
    }

    private fun buildNotification(context: Context, receivingTime: Date): Notification {
        val notificationTitle = context.getString(R.string.notification_title)
        val notificationContentText = context.getString(R.string.notification_content_text)
        val receivingTimeFormatted = getFormattedReceivingDate(receivingTime)
        val notificationBuilder = NotificationCompat.Builder(context)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setContentTitle(notificationTitle)
                .setContentText("$notificationContentText $receivingTimeFormatted")
                .setSmallIcon(R.drawable.ic_clear)
                .setAutoCancel(true)
        val mainAcitivityIntentPendingIntent = getPendingIntent(context)
        notificationBuilder.setContentIntent(mainAcitivityIntentPendingIntent)
        return notificationBuilder.build()
    }

    private fun getPendingIntent(context: Context): PendingIntent? {
        val mainAcitivityIntent = Intent(context, WeatherDataListActivity::class.java)
        val taskStackBuilder = TaskStackBuilder.create(context)
        taskStackBuilder.addNextIntent(mainAcitivityIntent)
        return taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun getFormattedReceivingDate(receivingTime: Date): String? {
        val notificationDateFormat = SimpleDateFormat("MMM, d HH:mm:ss", Locale.getDefault())
        return notificationDateFormat.format(receivingTime)
    }
}
