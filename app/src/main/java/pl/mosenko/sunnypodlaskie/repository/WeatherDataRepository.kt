package pl.mosenko.sunnypodlaskie.repository

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import pl.mosenko.sunnypodlaskie.network.api.RxWeatherDataAPI
import pl.mosenko.sunnypodlaskie.network.dto.WeatherDataDto
import pl.mosenko.sunnypodlaskie.persistence.dao.WeatherConditionEntityDAO
import pl.mosenko.sunnypodlaskie.persistence.dao.WeatherDataEntityDAO
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity
import pl.mosenko.sunnypodlaskie.util.WeatherDtoEntityConverter
import java.sql.SQLException

/**
 * Created by syk on 03.06.17.
 */
class WeatherDataRepository(var rxWeatherDataAPI: RxWeatherDataAPI, var weatherDataEntityDAO: WeatherDataEntityDAO, var weatherConditionEntities: WeatherConditionEntityDAO) {

    fun loadCurrentWeatherData(isConnectedToInternet: Boolean, callback: Callback): Disposable {
        val weatherDataEntityObservable: Observable<MutableList<WeatherDataEntity>> = if (isConnectedToInternet) {
            rxWeatherDataAPI.getCurrentWeatherData()
                    .map { obj: WeatherDataDto -> WeatherDtoEntityConverter.convertToWeatherDataEntityList(obj) }
                    .doOnNext { weatherDataEntityList: MutableList<WeatherDataEntity> -> cacheCurrentWeatherData(weatherDataEntityList) }
        } else {
            weatherDataEntityDAO.rxQueryForAll()
        }
        return weatherDataEntityObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ p: MutableList<WeatherDataEntity> -> callback.onNextWeatherDataEntities(p, isConnectedToInternet) }) { throwable: Throwable -> callback.onError(throwable) }
    }

    @Throws(SQLException::class)
    private fun cacheCurrentWeatherData(weatherDataEntityList: MutableList<WeatherDataEntity>) {
        weatherDataEntityDAO.deleteBuilder().delete()
        weatherDataEntityDAO.create(weatherDataEntityList.toList())
        for (weatherEntity in weatherDataEntityList) {
            weatherConditionEntities.refresh(weatherEntity.getWeatherCondition())
        }
    }

    interface Callback {
        fun onNextWeatherDataEntities(weatherDataEntityList: MutableList<WeatherDataEntity>, isConnectedToInternet: Boolean)
        fun onError(throwable: Throwable)
    }
}
