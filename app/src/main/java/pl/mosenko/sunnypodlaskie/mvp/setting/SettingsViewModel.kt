package pl.mosenko.sunnypodlaskie.mvp.setting

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import pl.mosenko.sunnypodlaskie.R
import pl.mosenko.sunnypodlaskie.util.WeatherPreferenceUtil
import pl.mosenko.sunnypodlaskie.util.WeatherAlarmSyncUtil

class SettingsViewModel(
    application: Application,
    private val weatherPreferenceUtil: WeatherPreferenceUtil,
    private val weatherAlarmSyncUtil: WeatherAlarmSyncUtil
) : AndroidViewModel(application),
    SharedPreferences.OnSharedPreferenceChangeListener {

    val syncTime: LiveData<String> = liveData(context = viewModelScope.coroutineContext) {
        emit(weatherPreferenceUtil.getStringSyncTime())
    }

    fun validateSyncTime(syncTime: String): Boolean {
        if (weatherPreferenceUtil.parseSyncTimeToPartiallyDate(syncTime) == null) {
            return false
        }
        return true
    }

    private fun startSyncTimeAlarm() {
        weatherAlarmSyncUtil.startAlarm()
    }

    private fun stopSyncTimeAlarm() {
        weatherAlarmSyncUtil.cancelAlarm()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        val context: Context = getApplication()
        val enableNotificationKey = context.getString(R.string.pref_enable_notifications_key)
        val enableNotificationValue = sharedPreferences.getBoolean(enableNotificationKey, false)
        if (enableNotificationValue) {
            startSyncTimeAlarm()
        } else {
            stopSyncTimeAlarm()
        }
    }
}
