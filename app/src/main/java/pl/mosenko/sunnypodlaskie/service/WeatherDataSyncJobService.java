package pl.mosenko.sunnypodlaskie.service;

import android.content.Context;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import pl.mosenko.sunnypodlaskie.ApplicationPodlaskieWeather;
import pl.mosenko.sunnypodlaskie.BuildConfig;
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity;
import pl.mosenko.sunnypodlaskie.repository.WeatherDataRepository;
import pl.mosenko.sunnypodlaskie.util.PreferenceWeatherUtil;
import pl.mosenko.sunnypodlaskie.util.WeatherNotificationUtil;

import static android.media.CamcorderProfile.get;

/**
 * Created by syk on 26.05.17.
 */

public class WeatherDataSyncJobService extends JobService implements WeatherDataRepository.Callback {
    private static final String TAG = WeatherDataSyncJobService.class.getSimpleName();

    @Inject
    WeatherDataRepository mWeatherDataRepository;
    private CompositeDisposable mRepositoryDisposable;
    private JobParameters mJob;

    @Override
    public void onCreate() {
        super.onCreate();
        injectFields();
        initializeCompositeDisposable();
    }

    private void initializeCompositeDisposable() {
        mRepositoryDisposable = new CompositeDisposable();
    }

    @Override
    public boolean onStartJob(JobParameters job) {
        Disposable disposable = mWeatherDataRepository.loadCurrentWeatherData(true, this);
        mRepositoryDisposable.add(disposable);
        mJob = job;
        return true;
    }

    private void injectFields() {
        ApplicationPodlaskieWeather.sharedApplication().getDIComponent().inject(this);
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        safelyDisposeRepositorySubscription();
        return true;
    }

    private void safelyDisposeRepositorySubscription() {
        if (mRepositoryDisposable != null && !mRepositoryDisposable.isDisposed()) {
            mRepositoryDisposable.dispose();
        }
    }

    private void showNotification(List<WeatherDataEntity> weatherDataEntities) {
        Context context = getApplicationContext();
        if (mJob != null) {
            jobFinished(mJob, false);
        }
        Date receivingTime = getWeatherDataReceivingTime(weatherDataEntities);
        if (PreferenceWeatherUtil.areNotificationsEnabled(context)) {
            WeatherNotificationUtil.notifyUserOfNewWeatherData(context, receivingTime);
        }
    }

    private Date getWeatherDataReceivingTime(List<WeatherDataEntity> weatherDataEntities) {
        if (weatherDataEntities != null && !weatherDataEntities.isEmpty()) {
            return weatherDataEntities.get(0).getReceivingTime();
        }
        throw new IllegalArgumentException("Downloaded weather data are incorrect.");
    }

    @Override
    public void onNextWeatherDataEntities(List<WeatherDataEntity> weatherDataEntityList, boolean isConnectedToInternet) {
        showNotification(weatherDataEntityList);
    }

    @Override
    public void onError(Throwable throwable) {
       if (BuildConfig.DEBUG) {
           Log.e(TAG, throwable.getMessage(), throwable);
       }
    }
}
