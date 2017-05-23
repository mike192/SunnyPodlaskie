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

/**
 * Created by syk on 22.05.17.
 */

public class WeatherDataSyncUtils {
    private static boolean sInitialized;
    private static final String WEATHER_DATA_SYNC_TAG = "weather_data_sync";
    private static final int SYNC_INTERVAL_HOURS = 24;
    private static final int SYNC_INTERVAL_SECONDS = (int) TimeUnit.HOURS.toSeconds(SYNC_INTERVAL_HOURS);
    private static final int SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS + (int) TimeUnit.MINUTES.toSeconds(15);

    private static void scheduleFirebaseJobDispatcherSync(@NonNull Context context) {
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher firebaseJobDispatcher = new FirebaseJobDispatcher(driver);

        Job syncWeatherDataJob = firebaseJobDispatcher.newJobBuilder()
                .setService(null) //TODO setSERVICE!!!
                .setTag(WEATHER_DATA_SYNC_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setTrigger(Trigger.executionWindow(SYNC_INTERVAL_SECONDS, SYNC_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                .setRecurring(true)
                .setReplaceCurrent(true)
                .build();
        firebaseJobDispatcher.schedule(syncWeatherDataJob);
    }

    synchronized public static void initialize(@NonNull final Context context) {
        if (sInitialized) {
            return;
        } else {
            sInitialized = true;
        }
        scheduleFirebaseJobDispatcherSync(context);
    }


}
