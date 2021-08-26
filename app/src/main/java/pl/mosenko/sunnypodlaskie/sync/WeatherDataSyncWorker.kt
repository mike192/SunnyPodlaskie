package pl.mosenko.sunnypodlaskie.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.flow.collect
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import pl.mosenko.sunnypodlaskie.persistence.model.WeatherData
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
        var repositoryResult: RepositoryResult<List<WeatherData>>? = null
        weatherDataRepository.loadWeatherData(true)
            .collect { result ->
                if (result is Success) {
                    showNotification(result.data)
                }
                repositoryResult = result
            }
        if (runAttemptCount >= RUN_ATTEMPT_COUNT_LIMIT) {
            return Result.failure()
        }

        return when (repositoryResult) {
            is Success -> {
                Result.success()
            }
            else -> Result.retry()
        }
    }

    private fun showNotification(weatherData: List<WeatherData>) {
        val receivingTime = getWeatherDataReceivingTime(weatherData)
        weatherNotificationUtil.notifyUserOfNewWeatherData(receivingTime)
    }

    private fun getWeatherDataReceivingTime(weatherData: List<WeatherData>): Date {
        if (weatherData.isNotEmpty()) {
            return weatherData[0].receivingTime
        }
        throw IllegalArgumentException("Downloaded weather data are incorrect.")
    }
}
