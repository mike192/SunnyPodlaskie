package pl.mosenko.sunnypodlaskie.mvp.setting;

import android.content.Context;
import android.content.SharedPreferences;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import pl.mosenko.sunnypodlaskie.R;
import pl.mosenko.sunnypodlaskie.util.PreferenceWeatherUtil;
import pl.mosenko.sunnypodlaskie.util.WeatherDataAlarmSyncUtil;

/**
 * Created by syk on 08.06.17.
 */

public class SettingsPresenterImpl extends MvpBasePresenter<SettingsContract.View> implements SettingsContract.Presenter, SharedPreferences.OnSharedPreferenceChangeListener {

    private SharedPreferences sharedPreferences;

    @Override
    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void onCreatePreferences() {
        if (isViewNotNullAttached()) {
            getView().addInitialSyncTimeSummary(sharedPreferences);
        }
    }

    @Override
    public void onStart() {
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop() {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean validateNewSyncTime(String syncTime) {
        if (PreferenceWeatherUtil.parseSyncTimeToPartiallyDate(syncTime) == null) {
            if (isViewNotNullAttached()) {
                getView().showMessageBadSyncTimeFormat();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (!isViewAttached()) {
            return;
        }

        final Context context = getView().getContext();
        boolean changedSyncTime = key.equals(context.getString(R.string.pref_sync_time_key));

        if (changedSyncTime) {
            getView().addSyncTimePreferenceSummary(sharedPreferences, key);
            WeatherDataAlarmSyncUtil.startAlarm(context);
        }
    }

    private boolean isViewNotNullAttached() {
        return getView() != null && isViewAttached();
    }
}
