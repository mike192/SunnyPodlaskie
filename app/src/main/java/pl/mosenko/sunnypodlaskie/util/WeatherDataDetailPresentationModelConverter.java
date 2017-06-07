package pl.mosenko.sunnypodlaskie.util;

import pl.mosenko.sunnypodlaskie.mvp.weatherdatadetail.WeatherDataDetailPresentationModel;
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity;

/**
 * Created by syk on 07.06.17.
 */

public class WeatherDataDetailPresentationModelConverter {
    private WeatherDataDetailPresentationModelConverter() {
    }

    public static WeatherDataDetailPresentationModel convert(WeatherDataEntity weatherDataEntity) {
        WeatherDataDetailPresentationModel presentationModel = new WeatherDataDetailPresentationModel();
        presentationModel.setTitleDetails(WeatherDataUtil.getDetailsTitle(weatherDataEntity.getCity(), weatherDataEntity.getReceivingTime()));
        presentationModel.setTemperature(WeatherDataUtil.getFormattedTemperature(weatherDataEntity.getTemperature()));
        presentationModel.setDescription(weatherDataEntity.getWeatherCondition().getDescription());
        presentationModel.setGetIconResource(WeatherDataUtil.getWeatherIconResourceByCode(weatherDataEntity.getIconKey()));
        presentationModel.setPressure(WeatherDataUtil.getFormattedPressure(weatherDataEntity.getPressure()));
        presentationModel.setWindDetails(WeatherDataUtil.getFormattedWindDetails(weatherDataEntity.getWindSpeed(), weatherDataEntity.getWindDegree()));
        presentationModel.setHumidity(WeatherDataUtil.getFormattedHumidity(weatherDataEntity.getHumidity()));
        presentationModel.setSunrise(WeatherDataUtil.getFormattedTime(weatherDataEntity.getSunrise()));
        presentationModel.setSunset(WeatherDataUtil.getFormattedTime(weatherDataEntity.getSunset()));
        return presentationModel;
    }
}
