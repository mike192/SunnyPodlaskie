package pl.mosenko.sunnypodlaskie.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import pl.mosenko.sunnypodlaskie.broadcastreceiver.AlarmWeatherSyncReceiver
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by syk on 26.05.17.
 */
object WeatherDataAlarmSyncUtil {
    private val ALARM_INTERVAL = TimeUnit.HOURS.toMillis(24) as Int as Int
    private const val REQUEST_CODE = 1001
    fun setupAlarmOnCreateApp(context: Context) {
        if (!isAlarmScheduled(context)) {
            startAlarm(context)
        }
    }

    private fun isAlarmScheduled(context: Context): Boolean {
        val alarmIntent = Intent(context, AlarmWeatherSyncReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, alarmIntent, PendingIntent.FLAG_NO_CREATE)
        return pendingIntent != null
    }

    fun startAlarm(context: Context) {
        val pendingIntent = getPendingIntent(context)
        val syncTimeDate = getSyncDate(context)
        initializeSyncAlarm(context, pendingIntent, syncTimeDate)
    }

    private fun initializeSyncAlarm(context: Context, pendingIntent: PendingIntent?, syncTimeDate: Date?) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, syncTimeDate.getTime(),
                ALARM_INTERVAL.toLong(), pendingIntent)
    }

    private fun getSyncDate(context: Context): Date? {
        return PreferenceWeatherUtil.getSyncDate(context)
    }

    private fun getPendingIntent(context: Context): PendingIntent? {
        val alarmIntent = Intent(context, AlarmWeatherSyncReceiver::class.java)
        return PendingIntent.getBroadcast(context, REQUEST_CODE, alarmIntent, 0)
    }
}
