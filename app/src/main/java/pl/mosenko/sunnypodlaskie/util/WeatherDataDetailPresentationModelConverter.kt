package pl.mosenko.sunnypodlaskie.util

import pl.mosenko.sunnypodlaskie.mvp.weatherdatadetail.WeatherDataDetailPresentationModel
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity

/**
 * Created by syk on 07.06.17.
 */
object WeatherDataDetailPresentationModelConverter {

    fun convert(weatherDataEntity: WeatherDataEntity): WeatherDataDetailPresentationModel {
        val presentationModel = WeatherDataDetailPresentationModel()
        presentationModel.titleDetails = WeatherDataUtil.getDetailsTitle(weatherDataEntity.getCity(), weatherDataEntity.getReceivingTime())
        presentationModel.temperature = WeatherDataUtil.getFormattedTemperature(weatherDataEntity.getTemperature())
        presentationModel.description = weatherDataEntity.getWeatherCondition().getDescription()
        presentationModel.iconResource = WeatherDataUtil.getWeatherIconResourceByCode(weatherDataEntity.getIconKey())
        presentationModel.pressure = WeatherDataUtil.getFormattedPressure(weatherDataEntity.getPressure())
        presentationModel.windDetails = WeatherDataUtil.getFormattedWindDetails(weatherDataEntity.getWindSpeed(), weatherDataEntity.getWindDegree())
        presentationModel.humidity = WeatherDataUtil.getFormattedHumidity(weatherDataEntity.getHumidity())
        presentationModel.sunrise = WeatherDataUtil.getFormattedTime(weatherDataEntity.getSunrise())
        presentationModel.sunset = WeatherDataUtil.getFormattedTime(weatherDataEntity.getSunset())
        return presentationModel
    }
}
