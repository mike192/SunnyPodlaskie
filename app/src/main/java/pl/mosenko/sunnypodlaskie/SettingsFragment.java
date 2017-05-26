package pl.mosenko.sunnypodlaskie;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import pl.mosenko.sunnypodlaskie.util.PreferenceWeatherUtil;
import pl.mosenko.sunnypodlaskie.util.WeatherDataAlarmSyncUtil;
import pl.mosenko.sunnypodlaskie.util.WeatherDataJobSyncUtils;

/**
 * Created by syk on 23.05.17.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private SharedPreferences mSharedPreferences;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_general);
        mSharedPreferences = getPreferenceScreen().getSharedPreferences();

    }

    @Override
    public void onStart() {
        super.onStart();
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);
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

    private void addSyncTimePreferenceSummary(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        preference.setSummary(sharedPreferences.getString(key, ""));
    }
}
