package pl.mosenko.sunnypodlaskie.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import pl.mosenko.sunnypodlaskie.broadcastreceiver.AlarmWeatherSyncReceiver
import java.util.*
import java.util.concurrent.TimeUnit

private const val REQUEST_CODE = 1001

/**
 * Created by syk on 26.05.17.
 */
class WeatherAlarmSyncUtil(
    private val context: Context,
    private val weatherPreferenceUtil: WeatherPreferenceUtil
) {
    private val alarmInterval = TimeUnit.HOURS.toMillis(24)

    private fun isAlarmScheduled(context: Context): Boolean {
        val alarmIntent = Intent(context, AlarmWeatherSyncReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            alarmIntent,
            PendingIntent.FLAG_NO_CREATE
        )
        return pendingIntent != null
    }

    fun startAlarm() {
        if (!isAlarmScheduled(context)) {
            val pendingIntent = getPendingIntent(context)
            val syncTimeDate = getSyncDate()
            initializeSyncAlarm(pendingIntent, syncTimeDate)
        }
    }

    private fun initializeSyncAlarm(
        pendingIntent: PendingIntent,
        syncTimeDate: Date
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP, syncTimeDate.time,
            alarmInterval, pendingIntent
        )
    }

    private fun getSyncDate(): Date {
        return weatherPreferenceUtil.getSyncDate()
    }

    private fun getPendingIntent(context: Context): PendingIntent {
        val alarmIntent = Intent(context, AlarmWeatherSyncReceiver::class.java)
        return PendingIntent.getBroadcast(context, REQUEST_CODE, alarmIntent, 0)
    }

    fun cancelAlarm() {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(getPendingIntent(context))
    }
}
