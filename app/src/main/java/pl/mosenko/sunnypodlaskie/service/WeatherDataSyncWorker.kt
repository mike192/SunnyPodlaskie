package pl.mosenko.sunnypodlaskie.service

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity
import pl.mosenko.sunnypodlaskie.repository.Result.Success
import pl.mosenko.sunnypodlaskie.repository.WeatherDataRepository
import pl.mosenko.sunnypodlaskie.util.WeatherNotificationUtil
import java.util.*
import pl.mosenko.sunnypodlaskie.repository.Result as RepositoryResult

private const val RUN_ATTEMPT_COUNT_LIMIT = 10

class WeatherDataSyncWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params),
    KoinComponent {

    private val weatherDataRepository: WeatherDataRepository by inject()
    private val weatherNotificationUtil: WeatherNotificationUtil by inject()

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            var repositoryResult: RepositoryResult<List<WeatherDataEntity>>? = null
            weatherDataRepository.loadWeatherData(true)
                .collect { result ->
                    if (result is Success) {
                        showNotification(result.data)
                    }
                    repositoryResult = result
                }
            if (runAttemptCount >= RUN_ATTEMPT_COUNT_LIMIT) {
                return@withContext Result.failure()
            }

            return@withContext when (repositoryResult) {
                is Success -> {
                    Result.success()
                }
                else -> Result.retry()
            }
        }
    }

    private fun showNotification(weatherDataEntities: List<WeatherDataEntity>) {
        val receivingTime = getWeatherDataReceivingTime(weatherDataEntities)
        weatherNotificationUtil.notifyUserOfNewWeatherData(receivingTime)
    }

    private fun getWeatherDataReceivingTime(weatherDataEntities: List<WeatherDataEntity>): Date {
        if (weatherDataEntities.isNotEmpty()) {
            return weatherDataEntities[0].receivingTime
        }
        throw IllegalArgumentException("Downloaded weather data are incorrect.")
    }
}
