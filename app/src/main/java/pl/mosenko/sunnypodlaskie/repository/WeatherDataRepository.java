package pl.mosenko.sunnypodlaskie.repository;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pl.mosenko.sunnypodlaskie.network.api.RxWeatherDataAPI;
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
    WeatherDataEntityDAO weatherDataEntityDAO;

    public WeatherDataRepository(RxWeatherDataAPI mRxWeatherDataAPI, WeatherDataEntityDAO weatherDataEntityDAO) {
        this.mRxWeatherDataAPI = mRxWeatherDataAPI;
        this.weatherDataEntityDAO = weatherDataEntityDAO;
    }

    public Disposable loadCurrentWeatherData(final boolean isConnectedToInternet, Callback callback) {
        Observable<List<WeatherDataEntity>> weatherDataEntityObservable;

        if (isConnectedToInternet) {
            weatherDataEntityObservable = mRxWeatherDataAPI.getCurrentWeatherData()
                    .map(p -> WeatherDtoEntityConverter.convertToWeatherDataEntityList(p))
                    .doOnNext(p -> cacheCurrentWeatherData(p));

        } else {
            weatherDataEntityObservable = weatherDataEntityDAO.rxQueryForAll();
        }
        Disposable disposable = weatherDataEntityObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(p -> callback.onNextWeatherDataEntities(p, isConnectedToInternet), callback::onError);

        return disposable;
    }

    @NonNull
    private void cacheCurrentWeatherData(List<WeatherDataEntity> weatherDataEntityList) throws java.sql.SQLException {
        weatherDataEntityDAO.deleteBuilder().delete();
        weatherDataEntityDAO.create(weatherDataEntityList);
        //weatherDataEntityDAO.queryForAll();
    }

    public interface Callback {
        void onNextWeatherDataEntities(List<WeatherDataEntity> weatherDataEntityList, boolean isConnectedToInternet);
        void onError(Throwable throwable);
    }
}
