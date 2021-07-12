package pl.mosenko.sunnypodlaskie.mvp.setting

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.hannesdorfmann.mosby3.mvp.delegate.FragmentMvpDelegateImpl
import com.hannesdorfmann.mosby3.mvp.delegate.MvpDelegateCallback
import org.koin.android.ext.android.inject
import pl.mosenko.sunnypodlaskie.R

/**
 * Created by syk on 23.05.17.
 */
class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener,
    MvpDelegateCallback<SettingsContract.View, SettingsContract.Presenter>, SettingsContract.View {

    private val fragmentPresenter: SettingsContract.Presenter by inject()
    private val fragmentDelegate: FragmentMvpDelegateImpl<SettingsContract.View, SettingsContract.Presenter> =
        FragmentMvpDelegateImpl(this, this, false, false)

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.pref_general)
    }

    override fun onStart() {
        super.onStart()
        fragmentPresenter.onStart()
    }

    override fun onStop() {
        super.onStop()
        fragmentPresenter.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        fragmentDelegate.onSaveInstanceState(outState)
    }

    override fun addInitialSyncTimeSummary(sharedPreferences: SharedPreferences) {
        val preferenceScreen = preferenceScreen
        val count = preferenceScreen.preferenceCount
        for (i in 0 until count) {
            val preference = preferenceScreen.getPreference(i)
            if (preference is EditTextPreference) {
                addSyncTimePreferenceSummary(sharedPreferences, preference.getKey())
                preference.setOnPreferenceChangeListener(this)
            }
        }
    }

    override fun addSyncTimePreferenceSummary(sharedPreferences: SharedPreferences, key: String) {
        val preference: Preference = findPreference(key)
        preference.summary =
            sharedPreferences.getString(key, getString(R.string.pref_sync_time_default))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentDelegate.onViewCreated(view, savedInstanceState)
        presenter.setSharedPreferences(preferenceScreen.sharedPreferences)
        presenter.onCreatePreferences()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentDelegate.onDestroyView()
    }

    override fun showMessageBadSyncTimeFormat() {
        Toast.makeText(context, R.string.bad_sync_time_format_message, Toast.LENGTH_SHORT).show()
    }

    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        return fragmentPresenter.validateNewSyncTime(newValue.toString())
    }

    override fun createPresenter(): SettingsContract.Presenter {
        return fragmentPresenter
    }

    override fun getMvpView(): SettingsContract.View {
        return this
    }

    override fun getPresenter(): SettingsContract.Presenter {
        return fragmentPresenter
    }

    override fun setPresenter(presenter: SettingsContract.Presenter?) {
        // NOP
    }
}
