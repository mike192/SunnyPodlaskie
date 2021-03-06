package pl.mosenko.sunnypodlaskie.mvp.setting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.view.View;
import android.widget.Toast;

import com.hannesdorfmann.mosby3.mvp.delegate.FragmentMvpDelegateImpl;
import com.hannesdorfmann.mosby3.mvp.delegate.MvpDelegateCallback;

import pl.mosenko.sunnypodlaskie.R;

/**
 * Created by syk on 23.05.17.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener, MvpDelegateCallback<SettingsContract.View, SettingsContract.Presenter>, SettingsContract.View {

    private SettingsContract.Presenter presenter;
    private FragmentMvpDelegateImpl<SettingsContract.View, SettingsContract.Presenter> fragmentDelegate = new FragmentMvpDelegateImpl<>(this, this, false, false);

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_general);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        fragmentDelegate.onSaveInstanceState(outState);
    }

    public void addInitialSyncTimeSummary(SharedPreferences sharedPreferences) {
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        int count = preferenceScreen.getPreferenceCount();
        for (int i = 0; i < count; i++) {
            Preference preference = preferenceScreen.getPreference(i);
            if (preference instanceof EditTextPreference) {
                addSyncTimePreferenceSummary(sharedPreferences, preference.getKey());
                preference.setOnPreferenceChangeListener(this);
            }
        }
    }

    public void addSyncTimePreferenceSummary(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        preference.setSummary(sharedPreferences.getString(key, getString(R.string.pref_sync_time_default)));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentDelegate.onViewCreated(view, savedInstanceState);
        presenter.setSharedPreferences(getPreferenceScreen().getSharedPreferences());
        presenter.onCreatePreferences();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentDelegate.onDestroyView();
    }

    @Override
    public void showMessageBadSyncTimeFormat() {
        Toast.makeText(getContext(), R.string.bad_sync_time_format_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return getPresenter().validateNewSyncTime(newValue.toString());
    }

    @NonNull
    @Override
    public SettingsContract.Presenter createPresenter() {
        return new SettingsPresenterImpl();
    }

    @Override
    public SettingsContract.Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void setPresenter(SettingsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public SettingsContract.View getMvpView() {
        return this;
    }
}
