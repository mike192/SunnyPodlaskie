package pl.mosenko.sunnypodlaskie.mvp.setting;

import android.content.Context;
import android.content.SharedPreferences;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by syk on 08.06.17.
 */

public interface SettingsContract {

    public interface View extends MvpView {
        void addInitialSyncTimeSummary(SharedPreferences sharedPreferences);

        Context getContext();

        void addSyncTimePreferenceSummary(SharedPreferences sharedPreferences, String key);

        void showMessageBadSyncTimeFormat();
    }

    public interface Presenter extends MvpPresenter<View> {

        void setSharedPreferences(SharedPreferences sharedPreferences);

        void onCreatePreferences();

        void onStart();

        void onStop();

        boolean validateNewSyncTime(String syncTime);
    }
}
