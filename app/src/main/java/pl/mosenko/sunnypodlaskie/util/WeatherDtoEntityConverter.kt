package pl.mosenko.sunnypodlaskie.util

import pl.mosenko.sunnypodlaskie.network.dto.WeatherDataDto
import pl.mosenko.sunnypodlaskie.network.dto.WeatherDataListDto
import pl.mosenko.sunnypodlaskie.persistence.converters.WeatherConditionEntityConverter
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity
import java.util.*

/**
 * Created by syk on 18.05.17.
 */
object WeatherDtoEntityConverter {
    private val CORRECT_NAMES_OF_CITIES: MutableMap<String, String> = HashMap()

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

    private fun convertToWeatherDataEntity(weatherInfo: WeatherDataListDto) =
        WeatherDataEntity(
            receivingTime = convertToDate(weatherInfo.dt),
            iconKey = weatherInfo.weatherDto[0].icon,
            city = mapCityToCorrectCity(weatherInfo.name),
            temperature = weatherInfo.mainDto.temperature,
            weatherCondition = getWeatherConditionFromId(weatherInfo),
            pressure = weatherInfo.mainDto.pressure,
            humidity = weatherInfo.mainDto.humidity,
            windSpeed = weatherInfo.windDto.speed,
            windDegree = weatherInfo.windDto.degree,
            sunrise = convertToDate(weatherInfo.sysDto.sunrise),
            sunset = convertToDate(weatherInfo.sysDto.sunset)
        )

    private fun getWeatherConditionFromId(weatherInfo: WeatherDataListDto) =
        WeatherConditionEntityConverter.fromWeatherConditionId(weatherInfo.weatherDto[0].id)

    private fun mapCityToCorrectCity(name: String): String {
        val cityCorrectName = CORRECT_NAMES_OF_CITIES[name]
        return cityCorrectName ?: name
    }

    fun convertToWeatherDataEntityList(weatherDataDto: WeatherDataDto): MutableList<WeatherDataEntity> {
        val weatherDataWeatherDataListDto = weatherDataDto.weatherDataListDto
        val weatherDataEntityList: MutableList<WeatherDataEntity> =
            ArrayList(weatherDataWeatherDataListDto.size)
        for (weatherInfo in weatherDataWeatherDataListDto) {
            val weatherDataEntity = convertToWeatherDataEntity(weatherInfo)
            weatherDataEntityList.add(weatherDataEntity)
        }
        return weatherDataEntityList
    }

    private fun convertToDate(unixTimestamp: Long) = Date(unixTimestamp * 1000)
}
