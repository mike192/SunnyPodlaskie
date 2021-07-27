package pl.mosenko.sunnypodlaskie.mvp.weatherdatadetail

import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity
import pl.mosenko.sunnypodlaskie.util.WeatherDataUtil
import pl.mosenko.sunnypodlaskie.util.WeatherDataUtil.DEFAULT_PLACEHOLDER

/**
 * Created by syk on 06.06.17.
 */
class WeatherDataDetailPresentationModel
   private constructor (
    val titleDetails: String,
    val temperature: String,
    val description: String? = null,
    val iconResource: Int,
    val pressure: String? = null,
    val windDetails: String? = null,
    val humidity: String? = null,
    val sunrise: String? = null,
    val sunset: String? = null
) {
    constructor(weatherDataEntity: WeatherDataEntity) : this(
        titleDetails = WeatherDataUtil.getDetailsTitle(
            weatherDataEntity.city,
            weatherDataEntity.receivingTime
        ),
        temperature = WeatherDataUtil.getFormattedTemperature(weatherDataEntity.temperature),
        description = weatherDataEntity.weatherCondition?.description ?: DEFAULT_PLACEHOLDER,
        iconResource = WeatherDataUtil.getWeatherIconResourceByCode(weatherDataEntity.iconKey),
        pressure = WeatherDataUtil.getFormattedPressure(weatherDataEntity.pressure),
        windDetails = WeatherDataUtil.getFormattedWindDetails(
            weatherDataEntity.windSpeed,
            weatherDataEntity.windDegree
        ),
        humidity = WeatherDataUtil.getFormattedHumidity(weatherDataEntity.humidity),
        sunrise = WeatherDataUtil.getFormattedTime(weatherDataEntity.sunrise),
        sunset = WeatherDataUtil.getFormattedTime(weatherDataEntity.sunset)
    )
}
