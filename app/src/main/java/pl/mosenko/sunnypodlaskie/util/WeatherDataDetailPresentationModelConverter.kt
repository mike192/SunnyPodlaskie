package pl.mosenko.sunnypodlaskie.util

import pl.mosenko.sunnypodlaskie.mvp.weatherdatadetail.WeatherDataDetailPresentationModel
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity

/**
 * Created by syk on 07.06.17.
 */
object WeatherDataDetailPresentationModelConverter {

    fun convert(weatherDataEntity: WeatherDataEntity): WeatherDataDetailPresentationModel {
        val presentationModel = WeatherDataDetailPresentationModel()
        presentationModel.titleDetails = WeatherDataUtil.getDetailsTitle(weatherDataEntity.city, weatherDataEntity.receivingTime)
        presentationModel.temperature = WeatherDataUtil.getFormattedTemperature(weatherDataEntity.temperature)
        presentationModel.description = weatherDataEntity.weatherCondition?.description
        presentationModel.iconResource = WeatherDataUtil.getWeatherIconResourceByCode(weatherDataEntity.iconKey)
        presentationModel.pressure = WeatherDataUtil.getFormattedPressure(weatherDataEntity.pressure)
        presentationModel.windDetails = WeatherDataUtil.getFormattedWindDetails(weatherDataEntity.windSpeed, weatherDataEntity.windDegree)
        presentationModel.humidity = WeatherDataUtil.getFormattedHumidity(weatherDataEntity.humidity)
        presentationModel.sunrise = WeatherDataUtil.getFormattedTime(weatherDataEntity.sunrise)
        presentationModel.sunset = WeatherDataUtil.getFormattedTime(weatherDataEntity.sunset)
        return presentationModel
    }
}
