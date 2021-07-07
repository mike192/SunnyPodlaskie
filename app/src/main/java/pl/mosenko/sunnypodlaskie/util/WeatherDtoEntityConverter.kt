package pl.mosenko.sunnypodlaskie.util

import pl.mosenko.sunnypodlaskie.network.dto.WeatherDataDto
import pl.mosenko.sunnypodlaskie.network.dto.WeatherDataListDto
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherConditionEntity
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity
import java.util.*

/**
 * Created by syk on 18.05.17.
 */
object WeatherDtoEntityConverter {
    private val CORRECT_NAMES_OF_CITIES: MutableMap<String, String> = HashMap()

    private fun convertToWeatherDataEntity(weatherInfo: WeatherDataListDto): WeatherDataEntity {
        val weatherDataEntity = WeatherDataEntity()
        weatherDataEntity.setReceivingTime(convertToDate(weatherInfo.dt))
        weatherDataEntity.setIconKey(weatherInfo.weatherDto[0].icon)
        weatherDataEntity.setCity(mapCityToCorrectCity(weatherInfo.name))
        weatherDataEntity.setTemperature(weatherInfo.mainDto.temperature)
        weatherDataEntity.setWeatherCondition(getWeatherConditionFromId(weatherInfo))
        weatherDataEntity.setPressure(weatherInfo.mainDto.pressure)
        weatherDataEntity.setHumidity(weatherInfo.mainDto.humidity)
        weatherDataEntity.setWindSpeed(weatherInfo.windDto.speed)
        weatherDataEntity.setWindDegree(weatherInfo.windDto.degree)
        weatherDataEntity.setSunrise(convertToDate(weatherInfo.sysDto.sunrise))
        weatherDataEntity.setSunset(convertToDate(weatherInfo.sysDto.sunset))
        return weatherDataEntity
    }

    private fun getWeatherConditionFromId(weatherInfo: WeatherDataListDto): WeatherConditionEntity {
        return WeatherConditionEntity(java.lang.Long.valueOf(weatherInfo.weatherDto[0].id.toLong()))
    }

    private fun mapCityToCorrectCity(name: String): String {
        val cityCorrectName = CORRECT_NAMES_OF_CITIES[name]
        return cityCorrectName ?: name
    }

    fun convertToWeatherDataEntityList(weatherDataDto: WeatherDataDto): MutableList<WeatherDataEntity> {
        val weatherDataWeatherDataListDto = weatherDataDto.weatherDataListDto
        val weatherDataEntityList: MutableList<WeatherDataEntity> = ArrayList(weatherDataWeatherDataListDto.size)
        for (weatherInfo in weatherDataWeatherDataListDto) {
            val weatherDataEntity = convertToWeatherDataEntity(weatherInfo)
            weatherDataEntityList.add(weatherDataEntity)
        }
        return weatherDataEntityList
    }

    private fun convertToDate(unixTimestamp: Long): Date {
        return Date(unixTimestamp * 1000)
    }

    init {
        CORRECT_NAMES_OF_CITIES["Suwalki"] = "Suwałki"
        CORRECT_NAMES_OF_CITIES["Augustow"] = "Augustów"
        CORRECT_NAMES_OF_CITIES["Lomza"] = "Łomża"
        CORRECT_NAMES_OF_CITIES["Monki"] = "Mońki"
        CORRECT_NAMES_OF_CITIES["Sokolka"] = "Sokółka"
        CORRECT_NAMES_OF_CITIES["Zambrow"] = "Zambrów"
        CORRECT_NAMES_OF_CITIES["Hajnowka"] = "Hajnówka"
        CORRECT_NAMES_OF_CITIES["Bialystok"] = "Białystok"
    }
}
