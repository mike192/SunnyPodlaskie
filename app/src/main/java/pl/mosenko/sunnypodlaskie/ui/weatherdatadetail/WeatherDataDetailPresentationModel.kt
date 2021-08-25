package pl.mosenko.sunnypodlaskie.ui.weatherdatadetail

import pl.mosenko.sunnypodlaskie.persistence.model.WeatherData
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
    constructor(weatherData: WeatherData) : this(
        titleDetails = WeatherDataUtil.getDetailsTitle(
            weatherData.city,
            weatherData.receivingTime
        ),
        temperature = WeatherDataUtil.getFormattedTemperature(weatherData.temperature),
        description = weatherData.weatherCondition?.description ?: DEFAULT_PLACEHOLDER,
        iconResource = WeatherDataUtil.getWeatherIconResourceByCode(weatherData.iconKey),
        pressure = WeatherDataUtil.getFormattedPressure(weatherData.pressure),
        windDetails = WeatherDataUtil.getFormattedWindDetails(
            weatherData.windSpeed,
            weatherData.windDegree
        ),
        humidity = WeatherDataUtil.getFormattedHumidity(weatherData.humidity),
        sunrise = WeatherDataUtil.getFormattedTime(weatherData.sunrise),
        sunset = WeatherDataUtil.getFormattedTime(weatherData.sunset)
    )
}
