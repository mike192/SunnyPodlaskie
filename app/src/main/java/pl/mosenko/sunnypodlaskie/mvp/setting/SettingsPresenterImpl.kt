package pl.mosenko.sunnypodlaskie.mvp.setting

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import pl.mosenko.sunnypodlaskie.R
import pl.mosenko.sunnypodlaskie.util.PreferenceWeatherUtil
import pl.mosenko.sunnypodlaskie.util.WeatherDataAlarmSyncUtil

/**
 * Created by syk on 08.06.17.
 */
class SettingsPresenterImpl : MvpBasePresenter<SettingsContract.View>(), SettingsContract.Presenter, OnSharedPreferenceChangeListener {

    private lateinit var sharedPreferences: SharedPreferences

    override fun setSharedPreferences(sharedPreferences: SharedPreferences) {
        this.sharedPreferences = sharedPreferences
    }

    override fun onCreatePreferences() {
        if (isViewNotNullAttached()) {
            view.addInitialSyncTimeSummary(sharedPreferences)
        }
    }

    override fun onStart() {
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onStop() {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun validateNewSyncTime(syncTime: String): Boolean {
        if (PreferenceWeatherUtil.parseSyncTimeToPartiallyDate(syncTime) == null) {
            if (isViewNotNullAttached()) {
                view.showMessageBadSyncTimeFormat()
            }
            return false
        }
        return true
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (!isViewAttached) {
            return
        }
        val context = view.getContext()!!
        val changedSyncTime = key == context.getString(R.string.pref_sync_time_key)
        if (changedSyncTime) {
            view.addSyncTimePreferenceSummary(sharedPreferences, key)
            WeatherDataAlarmSyncUtil.startAlarm(context)
        }
    }

    private fun isViewNotNullAttached(): Boolean {
        return view != null && isViewAttached
    }
}
