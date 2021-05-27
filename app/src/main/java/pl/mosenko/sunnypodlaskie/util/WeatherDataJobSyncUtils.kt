package pl.mosenko.sunnypodlaskie.util

import android.content.Context
import com.firebase.jobdispatcher.*
import pl.mosenko.sunnypodlaskie.service.WeatherDataSyncJobService
import java.util.concurrent.TimeUnit

/**
 * Created by syk on 22.05.17.
 */
object WeatherDataJobSyncUtils {
    private val WEATHER_DATA_SYNC_TAG: String? = "weather_data_sync"
    private const val SYNC_INTERVAL_SECONDS = 5
    private val SYNC_FLEXTIME_SECONDS = TimeUnit.MINUTES.toSeconds(5) as Int
    fun scheduleFirebaseJobDispatcherSync(context: Context) {
        val driver: Driver = GooglePlayDriver(context)
        val firebaseJobDispatcher = FirebaseJobDispatcher(driver)
        val syncWeatherDataJob = firebaseJobDispatcher.newJobBuilder()
                .setService(WeatherDataSyncJobService::class.java)
                .setTag(WEATHER_DATA_SYNC_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                .setTrigger(Trigger.executionWindow(SYNC_INTERVAL_SECONDS, SYNC_FLEXTIME_SECONDS))
                .setRecurring(false)
                .setReplaceCurrent(true)
                .build()
        firebaseJobDispatcher.schedule(syncWeatherDataJob)
    }
}
