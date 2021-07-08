package pl.mosenko.sunnypodlaskie.repository

import androidx.room.withTransaction
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.runBlocking
import pl.mosenko.sunnypodlaskie.network.api.RxWeatherDataApi
import pl.mosenko.sunnypodlaskie.network.dto.WeatherDataDto
import pl.mosenko.sunnypodlaskie.persistence.WeatherDataDatabase
import pl.mosenko.sunnypodlaskie.persistence.dao.WeatherDataEntityDao
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity
import pl.mosenko.sunnypodlaskie.util.WeatherDtoEntityConverter
import java.sql.SQLException

/**
 * Created by syk on 03.06.17.
 */
class WeatherDataRepository(
    var database: WeatherDataDatabase,
    var rxWeatherDataApi: RxWeatherDataApi,
    var weatherDataEntityDao: WeatherDataEntityDao
) {

    fun loadCurrentWeatherData(isConnectedToInternet: Boolean, callback: Callback): Disposable {
        val weatherDataEntityObservable: Observable<MutableList<WeatherDataEntity>> =
            if (isConnectedToInternet) {
                rxWeatherDataApi.getCurrentWeatherData()
                    .map { obj: WeatherDataDto ->
                        WeatherDtoEntityConverter.convertToWeatherDataEntityList(obj)
                    }
                    .doOnNext { weatherDataEntityList: MutableList<WeatherDataEntity> ->
                        cacheCurrentWeatherData(weatherDataEntityList)
                    }
            } else {
                Observable.fromCallable {
                    runBlocking {
                        weatherDataEntityDao.getAllWeatherData().toMutableList()
                    }
                }
            }
        return weatherDataEntityObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ weatherData: MutableList<WeatherDataEntity> ->
                callback.onNextWeatherDataEntities(
                    weatherData,
                    isConnectedToInternet
                )
            }) { throwable: Throwable -> callback.onError(throwable) }
    }

    @Throws(SQLException::class)
    private fun cacheCurrentWeatherData(weatherDataEntityList: MutableList<WeatherDataEntity>): MutableList<WeatherDataEntity> {
        return runBlocking {
            database.withTransaction {
                weatherDataEntityDao.clearAllWeatherData()
                weatherDataEntityDao.insertAll(weatherDataEntityList)
                val savedWeatherDataList = weatherDataEntityDao.getAllWeatherData().toMutableList()
                weatherDataEntityList.forEach { fetchedEntity ->
                    val savedEntity = savedWeatherDataList.find { it.city == fetchedEntity.city }
                    fetchedEntity.id = savedEntity!!.id
                }
                return@withTransaction savedWeatherDataList
            }
        }
    }

    interface Callback {
        fun onNextWeatherDataEntities(
            weatherDataEntityList: MutableList<WeatherDataEntity>,
            isConnectedToInternet: Boolean
        )

        fun onError(throwable: Throwable)
    }
}
