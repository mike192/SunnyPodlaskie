package pl.mosenko.sunnypodlaskie.service

import android.util.Log
import com.firebase.jobdispatcher.*
import io.reactivex.disposables.CompositeDisposable
import pl.mosenko.sunnypodlaskie.ApplicationPodlaskieWeather
import pl.mosenko.sunnypodlaskie.BuildConfig
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity
import pl.mosenko.sunnypodlaskie.repository.WeatherDataRepository
import pl.mosenko.sunnypodlaskie.util.PreferenceWeatherUtil
import pl.mosenko.sunnypodlaskie.util.WeatherNotificationUtil
import java.util.*
import javax.inject.Inject

/**
 * Created by syk on 26.05.17.
 */
class WeatherDataSyncJobService : JobService(), WeatherDataRepository.Callback {
    @kotlin.jvm.JvmField
    @Inject
    var weatherDataRepository: WeatherDataRepository? = null
    private var repositoryDisposable: CompositeDisposable? = null
    private var job: JobParameters? = null
    override fun onCreate() {
        super.onCreate()
        injectFields()
        initializeCompositeDisposable()
    }

    private fun initializeCompositeDisposable() {
        repositoryDisposable = CompositeDisposable()
    }

    override fun onStartJob(job: JobParameters?): Boolean {
        val disposable = weatherDataRepository.loadCurrentWeatherData(true, this)
        repositoryDisposable.add(disposable)
        this.job = job
        return true
    }

    private fun injectFields() {
        ApplicationPodlaskieWeather.Companion.sharedApplication().getDIComponent().inject(this)
    }

    override fun onStopJob(job: JobParameters?): Boolean {
        safelyDisposeRepositorySubscription()
        return true
    }

    private fun safelyDisposeRepositorySubscription() {
        if (repositoryDisposable != null && !repositoryDisposable.isDisposed()) {
            repositoryDisposable.dispose()
        }
    }

    private fun showNotification(weatherDataEntities: MutableList<WeatherDataEntity?>?) {
        val context = applicationContext
        if (job != null) {
            jobFinished(job, false)
        }
        val receivingTime = getWeatherDataReceivingTime(weatherDataEntities)
        if (PreferenceWeatherUtil.areNotificationsEnabled(context)) {
            WeatherNotificationUtil.notifyUserOfNewWeatherData(context, receivingTime)
        }
    }

    private fun getWeatherDataReceivingTime(weatherDataEntities: MutableList<WeatherDataEntity?>?): Date? {
        if (weatherDataEntities != null && !weatherDataEntities.isEmpty()) {
            return weatherDataEntities[0].getReceivingTime()
        }
        throw IllegalArgumentException("Downloaded weather data are incorrect.")
    }

    override fun onNextWeatherDataEntities(weatherDataEntityList: MutableList<WeatherDataEntity?>?, isConnectedToInternet: Boolean) {
        showNotification(weatherDataEntityList)
    }

    override fun onError(throwable: Throwable?) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, throwable.message, throwable)
        }
    }

    companion object {
        private val TAG = WeatherDataSyncJobService::class.java.simpleName
    }
}
