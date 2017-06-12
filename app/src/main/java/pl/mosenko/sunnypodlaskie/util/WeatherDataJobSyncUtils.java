package pl.mosenko.sunnypodlaskie.util;

import android.content.Context;
import java.util.concurrent.TimeUnit;

import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import pl.mosenko.sunnypodlaskie.service.WeatherDataSyncJobService;

/**
 * Created by syk on 22.05.17.
 */

public class WeatherDataJobSyncUtils {

    private static final String WEATHER_DATA_SYNC_TAG = "weather_data_sync";
    private static final int SYNC_INTERVAL_SECONDS = 5;
    private static final int SYNC_FLEXTIME_SECONDS = (int) TimeUnit.MINUTES.toSeconds(5);

    public static void scheduleFirebaseJobDispatcherSync(@NonNull Context context) {
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher firebaseJobDispatcher = new FirebaseJobDispatcher(driver);

        Job syncWeatherDataJob = firebaseJobDispatcher.newJobBuilder()
                .setService(WeatherDataSyncJobService.class)
                .setTag(WEATHER_DATA_SYNC_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                .setTrigger(Trigger.executionWindow(SYNC_INTERVAL_SECONDS, SYNC_FLEXTIME_SECONDS))
                .setRecurring(false)
                .setReplaceCurrent(true)
                .build();
        firebaseJobDispatcher.schedule(syncWeatherDataJob);
    }
}
