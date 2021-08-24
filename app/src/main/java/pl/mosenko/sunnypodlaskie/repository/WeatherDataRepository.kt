package pl.mosenko.sunnypodlaskie.repository

import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
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
    private var weatherDtoEntityConverter: WeatherDtoEntityConverter
) {

    fun loadWeatherData(forceUpdate: Boolean) =
        flow {
            emit(Loading)
            try {
                val weatherDataList: List<WeatherDataEntity> = if (forceUpdate) {
                    val currentWeatherData = defaultWeatherDataApi.getCurrentWeatherData()
                    val weatherDataEntityList =
                        weatherDtoEntityConverter.convertToWeatherDataEntityList(currentWeatherData)
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

    fun getWeatherDataByCity(city: String): Flow<Result<WeatherDataEntity>> {
        return weatherDataEntityDao.getWeatherDataByCityName(city).map {
            Success(it)
        }
    }
}
