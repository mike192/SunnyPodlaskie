package pl.mosenko.sunnypodlaskie.repository;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pl.mosenko.sunnypodlaskie.network.api.RxWeatherDataAPI;
import pl.mosenko.sunnypodlaskie.persistence.dao.WeatherConditionEntityDAO;
import pl.mosenko.sunnypodlaskie.persistence.dao.WeatherDataEntityDAO;
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity;
import pl.mosenko.sunnypodlaskie.util.WeatherDtoEntityConverter;

/**
 * Created by syk on 03.06.17.
 */

public class WeatherDataRepository {
    @Inject
    RxWeatherDataAPI mRxWeatherDataAPI;
    @Inject
    WeatherDataEntityDAO mWeatherDataEntityDAO;
    @Inject
    WeatherConditionEntityDAO mWeatherConditionEntities;

    public WeatherDataRepository(RxWeatherDataAPI rxWeatherDataAPI, WeatherDataEntityDAO weatherDataEntityDAO, WeatherConditionEntityDAO weatherConditionEntities) {
        this.mRxWeatherDataAPI = rxWeatherDataAPI;
        this.mWeatherDataEntityDAO = weatherDataEntityDAO;
        this.mWeatherConditionEntities = weatherConditionEntities;
    }

    public Disposable loadCurrentWeatherData(final boolean isConnectedToInternet, final Callback callback) {
       Observable<List<WeatherDataEntity>> weatherDataEntityObservable;
        if (isConnectedToInternet) {
            weatherDataEntityObservable = mRxWeatherDataAPI.getCurrentWeatherData()
                    .map(WeatherDtoEntityConverter::convertToWeatherDataEntityList)
                    .doOnNext(this::cacheCurrentWeatherData);
        } else {
            weatherDataEntityObservable = mWeatherDataEntityDAO.rxQueryForAll();
        }
        Disposable disposable = weatherDataEntityObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(p -> callback.onNextWeatherDataEntities(p, isConnectedToInternet), callback::onError);
        return disposable;
    }

    @NonNull
    private void cacheCurrentWeatherData(List<WeatherDataEntity> weatherDataEntityList) throws java.sql.SQLException {
        mWeatherDataEntityDAO.deleteBuilder().delete();
        mWeatherDataEntityDAO.create(weatherDataEntityList);
        for (WeatherDataEntity weatherEntity : weatherDataEntityList) {
            mWeatherConditionEntities.refresh(weatherEntity.getWeatherCondition());
        }
    }

    public interface Callback {
        void onNextWeatherDataEntities(List<WeatherDataEntity> weatherDataEntityList, boolean isConnectedToInternet);
        void onError(Throwable throwable);
    }
}
