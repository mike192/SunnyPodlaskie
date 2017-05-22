package pl.mosenko.sunnypodlaskie.util;

import android.text.format.DateUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import pl.mosenko.sunnypodlaskie.dto.List;
import pl.mosenko.sunnypodlaskie.dto.WeatherData;
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity;

/**
 * Created by syk on 18.05.17.
 */

public class WeatherDtoEntityConverter {
    private WeatherDtoEntityConverter() {
    }

    private static WeatherDataEntity convertToWeatherDataEntity(List weatherInfo) {
        WeatherDataEntity weatherDataEntity = new WeatherDataEntity();
        weatherDataEntity.setReceivingTime(convertToDate(weatherInfo.getDt()));
        weatherDataEntity.setIconKey(weatherInfo.getWeather().get(0).getIcon());
        weatherDataEntity.setCity(weatherInfo.getName());
        weatherDataEntity.setTemperature(weatherInfo.getMain().getTemp());
        weatherDataEntity.setDescription(weatherInfo.getWeather().get(0).getDescription());
        weatherDataEntity.setPressure(weatherInfo.getMain().getPressure());
        weatherDataEntity.setHumidity(weatherInfo.getMain().getHumidity());
        weatherDataEntity.setWindSpeed(weatherInfo.getWind().getSpeed());
        weatherDataEntity.setWindDegree(weatherInfo.getWind().getDeg());
        weatherDataEntity.setSunrise(convertToDate(weatherInfo.getSys().getSunrise()));
        weatherDataEntity.setSunset(convertToDate(weatherInfo.getSys().getSunset()));
        return weatherDataEntity;
    }

    public static java.util.List<WeatherDataEntity> convertToWeatherDataEntityList(java.util.List<List> weatherDataList) {
        java.util.List<WeatherDataEntity> weatherDataEntityList = new ArrayList<>(weatherDataList.size());
        for (List weatherInfo : weatherDataList) {
            WeatherDataEntity weatherDataEntity = convertToWeatherDataEntity(weatherInfo);
            weatherDataEntityList.add(weatherDataEntity);
        }
        return weatherDataEntityList;
    }

    private static Date convertToDate(Long unixTimestamp) {
        return new Date(unixTimestamp * 1000);
    }
}
