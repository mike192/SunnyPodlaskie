package pl.mosenko.sunnypodlaskie.mvp.setting

import android.content.Context
import android.content.SharedPreferences
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

/**
 * Created by syk on 08.06.17.
 */
interface SettingsContract {
    interface View : MvpView {
        fun addInitialSyncTimeSummary(sharedPreferences: SharedPreferences)
        fun getContext(): Context?
        fun addSyncTimePreferenceSummary(sharedPreferences: SharedPreferences, key: String)
        fun showMessageBadSyncTimeFormat()
    }

    interface Presenter : MvpPresenter<View> {
        fun setSharedPreferences(sharedPreferences: SharedPreferences)
        fun onCreatePreferences()
        fun onStart()
        fun onStop()
        fun validateNewSyncTime(syncTime: String): Boolean
    }
}
