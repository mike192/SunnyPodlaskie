package pl.mosenko.sunnypodlaskie.util

import android.content.Context
import android.support.v7.preference.PreferenceManager
import android.util.Log
import pl.mosenko.sunnypodlaskie.BuildConfig
import pl.mosenko.sunnypodlaskie.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by syk on 26.05.17.
 */
object PreferenceWeatherUtil {
    private val TAG = PreferenceWeatherUtil::class.java.simpleName
    fun areNotificationsEnabled(context: Context): Boolean {
        val displayNotificationsKey = context.getString(R.string.pref_enable_notifications_key)
        val showNotificationsDefaultValue = context.resources.getBoolean(R.bool.show_notifications_by_default)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getBoolean(displayNotificationsKey, showNotificationsDefaultValue)
    }

    fun getSyncDate(context: Context): Date? {
        val syncTimeString = getStringSyncTime(context)
        return parseSyncTime(syncTimeString)
    }

    private fun getStringSyncTime(context: Context?): String {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val syncTimeString: String?
        syncTimeString = if (sharedPreferences.contains(context.getString(R.string.pref_sync_time_key))) {
            sharedPreferences.getString(context.getString(R.string.pref_sync_time_key), "")
        } else {
            context.getString(R.string.pref_sync_time_default)
        }
        return syncTimeString
    }

    private fun parseSyncTime(syncTimeString: String?): Date? {
        val syncTimeDate = parseSyncTimeToPartiallyDate(syncTimeString)
        val syncDateCalendar = getCurrentSyncTimeCalendar(syncTimeDate)
        return syncDateCalendar.time
    }

    private fun getCurrentSyncTimeCalendar(syncTimeDate: Date?): Calendar {
        val syncTimeCalendar = Calendar.getInstance()
        syncTimeCalendar.time = syncTimeDate
        val syncDateCalendar = Calendar.getInstance()
        syncDateCalendar[Calendar.HOUR_OF_DAY] = syncTimeCalendar[Calendar.HOUR_OF_DAY]
        syncDateCalendar[Calendar.MINUTE] = syncTimeCalendar[Calendar.MINUTE]
        syncDateCalendar[Calendar.SECOND] = 0
        syncDateCalendar[Calendar.MILLISECOND] = 0
        return syncDateCalendar
    }

    fun parseSyncTimeToPartiallyDate(syncTimeString: String?): Date? {
        val simpleTimeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        try {
            val syncTimeDate = simpleTimeFormat.parse(syncTimeString)
            return if (isValidTime(syncTimeString)) syncTimeDate else null
        } catch (e: ParseException) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, e.message, e)
            }
        }
        return null
    }

    private fun isValidTime(syncTimeString: String?): Boolean {
        val hourPart = syncTimeString.substring(0, syncTimeString.indexOf(":"))
        val minutePart = syncTimeString.substring(syncTimeString.indexOf(":") + 1)
        val hours: Int?
        val minutes: Int?
        try {
            hours = Integer.valueOf(hourPart)
            minutes = Integer.valueOf(minutePart)
        } catch (ne: NumberFormatException) {
            return false
        }
        return hours >= 0 && hours <= 24 && minutes >= 0 && minutes <= 59
    }
}
