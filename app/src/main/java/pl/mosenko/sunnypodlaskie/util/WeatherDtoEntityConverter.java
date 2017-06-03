package pl.mosenko.sunnypodlaskie.util;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import pl.mosenko.sunnypodlaskie.network.dto.List;
import pl.mosenko.sunnypodlaskie.network.dto.Weather;
import pl.mosenko.sunnypodlaskie.network.dto.WeatherData;
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherConditionEntity;
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity;

/**
 * Created by syk on 18.05.17.
 */

public class WeatherDtoEntityConverter {
    private final static Map<String, String> CORRECT_NAMES_OF_CITIES = new HashMap<>();
    static {
        CORRECT_NAMES_OF_CITIES.put("Suwalki", "Suwałki");
        CORRECT_NAMES_OF_CITIES.put("Augustow", "Augustów");
        CORRECT_NAMES_OF_CITIES.put("Lomza", "Łomża");
        CORRECT_NAMES_OF_CITIES.put("Monki", "Mońki");
        CORRECT_NAMES_OF_CITIES.put("Sokolka", "Sokółka");
        CORRECT_NAMES_OF_CITIES.put("Zambrow", "Zambrów");
        CORRECT_NAMES_OF_CITIES.put("Hajnowka", "Hajnówka");
        CORRECT_NAMES_OF_CITIES.put("Bialystok", "Białystok");
    }

    private WeatherDtoEntityConverter() {
    }

    private static WeatherDataEntity convertToWeatherDataEntity(List weatherInfo) {
        WeatherDataEntity weatherDataEntity = new WeatherDataEntity();
        weatherDataEntity.setReceivingTime(convertToDate(weatherInfo.getDt()));
        weatherDataEntity.setIconKey(weatherInfo.getWeather().get(0).getIcon());
        weatherDataEntity.setCity(mapCityToCorrectCity(weatherInfo.getName()));
        weatherDataEntity.setTemperature(weatherInfo.getMain().getTemp());
        weatherDataEntity.setWeatherCondition(getWeatherConditionFromId(weatherInfo));
        weatherDataEntity.setPressure(weatherInfo.getMain().getPressure());
        weatherDataEntity.setHumidity(weatherInfo.getMain().getHumidity());
        weatherDataEntity.setWindSpeed(weatherInfo.getWind().getSpeed());
        weatherDataEntity.setWindDegree(weatherInfo.getWind().getDeg());
        weatherDataEntity.setSunrise(convertToDate(weatherInfo.getSys().getSunrise()));
        weatherDataEntity.setSunset(convertToDate(weatherInfo.getSys().getSunset()));
        return weatherDataEntity;
    }

    @NonNull
    private static WeatherConditionEntity getWeatherConditionFromId(List weatherInfo) {
        return new WeatherConditionEntity(Long.valueOf(weatherInfo.getWeather().get(0).getId()));
    }

    private static String mapCityToCorrectCity(String name) {
        String cityCorrectName = CORRECT_NAMES_OF_CITIES.get(name);
        return cityCorrectName != null ? cityCorrectName : name;
    }

    public static java.util.List<WeatherDataEntity> convertToWeatherDataEntityList(WeatherData weatherData) {
        java.util.List<List> weatherDataList = weatherData.getList();
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
