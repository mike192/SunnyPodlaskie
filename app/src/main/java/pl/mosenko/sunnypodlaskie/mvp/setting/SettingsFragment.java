package pl.mosenko.sunnypodlaskie.mvp.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.widget.Toast;

import pl.mosenko.sunnypodlaskie.R;
import pl.mosenko.sunnypodlaskie.util.PreferenceWeatherUtil;
import pl.mosenko.sunnypodlaskie.util.WeatherDataAlarmSyncUtil;

/**
 * Created by syk on 23.05.17.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceChangeListener {

    private SharedPreferences mSharedPreferences;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_general);
        initializeSharedPreferences();
        addInitialSyncTimeSummary();
    }

    private void initializeSharedPreferences() {
        mSharedPreferences = getPreferenceScreen().getSharedPreferences();
    }

    private void addInitialSyncTimeSummary() {
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        int count = preferenceScreen.getPreferenceCount();
        for (int i = 0; i < count; i++) {
            Preference preference = preferenceScreen.getPreference(i);
            if (preference instanceof EditTextPreference) {
                addSyncTimePreferenceSummary(mSharedPreferences, preference.getKey());
                preference.setOnPreferenceChangeListener(this);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mSharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        final Context context = getContext();
        boolean changedSyncTime = key.equals(getString(R.string.pref_sync_time_key));

        if (changedSyncTime) {
            addSyncTimePreferenceSummary(sharedPreferences, key);
            WeatherDataAlarmSyncUtil.startAlarm(context);
        }
    }

    private boolean validateNewSyncTime(String syncTimeString) {
        if (PreferenceWeatherUtil.parseSyncTimeToPartiallyDate(syncTimeString) == null) {
            Toast.makeText(getContext(), R.string.bad_sync_time_format_message, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void addSyncTimePreferenceSummary(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        preference.setSummary(sharedPreferences.getString(key, getString(R.string.pref_sync_time_default)));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
            return validateNewSyncTime(newValue.toString());
    }
}
