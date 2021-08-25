package pl.mosenko.sunnypodlaskie.ui.setting

import android.os.Bundle
import android.view.View
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.mosenko.sunnypodlaskie.R

/**
 * Created by syk on 23.05.17.
 */
class SettingsFragment : PreferenceFragmentCompat() {

    val viewModel: SettingsViewModel by viewModel()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.pref_general)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureSyncTimePreference()
    }

    override fun onStart() {
        super.onStart()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(viewModel)
    }

    override fun onStop() {
        super.onStop()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(viewModel)
    }

    private fun configureSyncTimePreference() {
        val preference: Preference = findPreference(getString(R.string.pref_sync_time_key))!!
        preference.setOnPreferenceChangeListener { _, newValue ->
            preference.summary = newValue.toString()
            val success: Boolean = viewModel.validateSyncTime(newValue.toString())
            if (!success) {
                Snackbar.make(
                    requireView(),
                    R.string.bad_sync_time_format_message,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            success
        }
        viewModel.syncTime.observe(viewLifecycleOwner, {
            preference.summary = it
        })
    }
}
