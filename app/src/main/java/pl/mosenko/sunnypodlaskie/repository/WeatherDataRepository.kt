package pl.mosenko.sunnypodlaskie.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.room.withTransaction
import kotlinx.coroutines.Dispatchers
import pl.mosenko.sunnypodlaskie.network.api.DefaultWeatherDataApi
import pl.mosenko.sunnypodlaskie.persistence.WeatherDataDatabase
import pl.mosenko.sunnypodlaskie.persistence.dao.WeatherDataEntityDao
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity
import pl.mosenko.sunnypodlaskie.repository.Result.*
import pl.mosenko.sunnypodlaskie.util.WeatherDtoEntityConverter

/**
 * Created by syk on 03.06.17.
 */
class WeatherDataRepository(
    private var database: WeatherDataDatabase,
    private var defaultWeatherDataApi: DefaultWeatherDataApi,
    private var weatherDataEntityDao: WeatherDataEntityDao,
) {

    fun loadWeatherData(forceUpdate: Boolean) =
        liveData(Dispatchers.IO) {
            emit(Loading)
            try {
                val weatherDataList: List<WeatherDataEntity> = if (forceUpdate) {
                    val currentWeatherData = defaultWeatherDataApi.getCurrentWeatherData()
                    val weatherDataEntityList =
                        WeatherDtoEntityConverter.convertToWeatherDataEntityList(currentWeatherData)
                    database.withTransaction {
                        weatherDataEntityDao.clearAllWeatherData()
                        weatherDataEntityDao.insertAll(weatherDataEntityList)
                    }
                    weatherDataEntityList
                } else {
                    weatherDataEntityDao.getAllWeatherData() ?: emptyList()
                }
                emit(Success(weatherDataList))
            } catch (throwable: Throwable) {
                emit(Error(throwable))
            }
        }

    fun observeWeatherDataByCity(city: String): LiveData<Result<WeatherDataEntity>> {
        return weatherDataEntityDao.observeWeatherDataByCityName(city).map {
            Success(it)
        }
    }
}
