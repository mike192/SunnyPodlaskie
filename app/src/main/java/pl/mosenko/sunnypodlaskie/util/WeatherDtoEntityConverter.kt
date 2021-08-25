package pl.mosenko.sunnypodlaskie.util

import pl.mosenko.sunnypodlaskie.api.dto.WeatherDataDto
import pl.mosenko.sunnypodlaskie.api.dto.WeatherDataListDto
import pl.mosenko.sunnypodlaskie.persistence.converters.WeatherConditionEntityConverter
import pl.mosenko.sunnypodlaskie.persistence.model.WeatherData
import java.util.*

/**
 * Created by syk on 18.05.17.
 */
class WeatherDtoEntityConverter {
    private val polishNamesOfCities: MutableMap<String, String> = HashMap()

    init {
        polishNamesOfCities["Suwalki"] = "Suwałki"
        polishNamesOfCities["Augustow"] = "Augustów"
        polishNamesOfCities["Lomza"] = "Łomża"
        polishNamesOfCities["Monki"] = "Mońki"
        polishNamesOfCities["Sokolka"] = "Sokółka"
        polishNamesOfCities["Zambrow"] = "Zambrów"
        polishNamesOfCities["Hajnowka"] = "Hajnówka"
        polishNamesOfCities["Bialystok"] = "Białystok"
    }

    private fun convertToWeatherDataEntity(weatherInfo: WeatherDataListDto) =
        WeatherData(
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
        val cityCorrectName = polishNamesOfCities[name]
        return cityCorrectName ?: name
    }

    fun convertToWeatherDataEntityList(weatherDataDto: WeatherDataDto): MutableList<WeatherData> {
        val weatherDataWeatherDataListDto = weatherDataDto.weatherDataListDto
        val weatherDataList: MutableList<WeatherData> =
            ArrayList(weatherDataWeatherDataListDto.size)
        for (weatherInfo in weatherDataWeatherDataListDto) {
            val weatherDataEntity = convertToWeatherDataEntity(weatherInfo)
            weatherDataList.add(weatherDataEntity)
        }
        return weatherDataList
    }

    private fun convertToDate(unixTimestamp: Long) = Date(unixTimestamp * 1000)
}
