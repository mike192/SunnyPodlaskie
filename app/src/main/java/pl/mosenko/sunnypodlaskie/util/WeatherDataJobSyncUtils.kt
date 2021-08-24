package pl.mosenko.sunnypodlaskie.util

import android.content.Context
import androidx.work.*
import pl.mosenko.sunnypodlaskie.service.WeatherDataSyncWorker
import java.util.concurrent.TimeUnit

/**
 * Created by syk on 22.05.17.
 */
private const val WEATHER_DATA_SYNC_WORK_NAME: String = "weather_data_sync"
private const val SYNC_INTERVAL_SECONDS = 10L

class WeatherDataJobSyncUtils {

    fun scheduleWeatherDataSync(context: Context) {
        WorkManager.getInstance(context)
            .enqueueUniqueWork(
                WEATHER_DATA_SYNC_WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                prepareOneTimeWorkRequest()
            )
    }

    private fun prepareOneTimeWorkRequest(): OneTimeWorkRequest {
        val constraints: Constraints = Constraints.Builder().apply {
            setRequiredNetworkType(NetworkType.CONNECTED)
            setRequiresCharging(true)
        }.build()
        return OneTimeWorkRequestBuilder<WeatherDataSyncWorker>()
            .setConstraints(constraints)
            .setBackoffCriteria(BackoffPolicy.LINEAR, SYNC_INTERVAL_SECONDS, TimeUnit.SECONDS)
            .build()
    }
}
