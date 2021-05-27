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
        open fun addInitialSyncTimeSummary(sharedPreferences: SharedPreferences?)
        open fun getContext(): Context?
        open fun addSyncTimePreferenceSummary(sharedPreferences: SharedPreferences?, key: String?)
        open fun showMessageBadSyncTimeFormat()
    }

    interface Presenter : MvpPresenter<View?> {
        open fun setSharedPreferences(sharedPreferences: SharedPreferences?)
        open fun onCreatePreferences()
        open fun onStart()
        open fun onStop()
        open fun validateNewSyncTime(syncTime: String?): Boolean
    }
}
