package pl.mosenko.sunnypodlaskie.service;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pl.mosenko.sunnypodlaskie.MainApplication;
import pl.mosenko.sunnypodlaskie.dto.WeatherData;
import pl.mosenko.sunnypodlaskie.network.RxWeatherDataAPI;
import pl.mosenko.sunnypodlaskie.persistence.dao.WeatherDataEntityDAO;
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity;
import pl.mosenko.sunnypodlaskie.util.PreferenceWeatherUtil;
import pl.mosenko.sunnypodlaskie.util.WeatherDtoEntityConverter;
import pl.mosenko.sunnypodlaskie.util.WeatherNotificationUtil;

import static android.media.CamcorderProfile.get;

/**
 * Created by syk on 26.05.17.
 */

public class WeatherDataSyncJobService extends JobService implements RxWeatherDataAPI.GetCurrentWeatherDataListCallback {
    private static final String TAG = WeatherDataSyncJobService.class.getSimpleName();

    @Inject
    RxWeatherDataAPI mRxWeatherDataAPI;
    @Inject
    WeatherDataEntityDAO mWeatherDataEntityDAO;
    private CompositeDisposable mCompositeDisposable;
    private JobParameters mJob;

    @Override
    public void onCreate() {
        super.onCreate();
        injectFields();
        initializeCompositeDisposable();
    }

    private void initializeCompositeDisposable() {
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public boolean onStartJob(JobParameters job) {
        Disposable disposable = mRxWeatherDataAPI.getCurrentWeatherData(this);
        mCompositeDisposable.add(disposable);
        mJob = job;
        return true;
    }

    private void injectFields() {
        ((MainApplication) getApplication()).getMainActivityComponent().inject(this);
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        mCompositeDisposable.dispose();
        return true;
    }

    @Override
    public void onDownloadWeatherDataSuccess(WeatherData weatherDataList) {
        Disposable disposable = Observable.fromCallable(() -> cacheCurrentWeatherDataOnDatabase(weatherDataList))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(p -> showNotification(p), e -> logError(e));

        mCompositeDisposable.add(disposable);
    }

    @NonNull
    private java.util.List<WeatherDataEntity> cacheCurrentWeatherDataOnDatabase(WeatherData weatherDataList) throws java.sql.SQLException {
        java.util.List<WeatherDataEntity> weatherDataEntityList = WeatherDtoEntityConverter.convertToWeatherDataEntityList(weatherDataList.getList());
        mWeatherDataEntityDAO.deleteBuilder().delete();
        mWeatherDataEntityDAO.create(weatherDataEntityList);
        return weatherDataEntityList;
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
    public void onDownloadWeatherDataError(Throwable networkError) {
        logError(networkError);
    }

    private void logError(Throwable e) {
        Log.e(TAG, e.getMessage(), e);
    }
}
