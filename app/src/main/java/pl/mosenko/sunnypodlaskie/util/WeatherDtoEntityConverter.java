package pl.mosenko.sunnypodlaskie.util;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.mosenko.sunnypodlaskie.network.dto.WeatherDataDto;
import pl.mosenko.sunnypodlaskie.network.dto.WeatherDataListDto;
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

    private static WeatherDataEntity convertToWeatherDataEntity(WeatherDataListDto weatherInfo) {
        WeatherDataEntity weatherDataEntity = new WeatherDataEntity();
        weatherDataEntity.setReceivingTime(convertToDate(weatherInfo.getDt()));
        weatherDataEntity.setIconKey(weatherInfo.getWeatherDto().get(0).getIcon());
        weatherDataEntity.setCity(mapCityToCorrectCity(weatherInfo.getName()));
        weatherDataEntity.setTemperature(weatherInfo.getMainDto().getTemperature());
        weatherDataEntity.setWeatherCondition(getWeatherConditionFromId(weatherInfo));
        weatherDataEntity.setPressure(weatherInfo.getMainDto().getPressure());
        weatherDataEntity.setHumidity(weatherInfo.getMainDto().getHumidity());
        weatherDataEntity.setWindSpeed(weatherInfo.getWindDto().getSpeed());
        weatherDataEntity.setWindDegree(weatherInfo.getWindDto().getDegree());
        weatherDataEntity.setSunrise(convertToDate(weatherInfo.getSysDto().getSunrise()));
        weatherDataEntity.setSunset(convertToDate(weatherInfo.getSysDto().getSunset()));
        return weatherDataEntity;
    }

    @NonNull
    private static WeatherConditionEntity getWeatherConditionFromId(WeatherDataListDto weatherInfo) {
        return new WeatherConditionEntity(Long.valueOf(weatherInfo.getWeatherDto().get(0).getId()));
    }

    private static String mapCityToCorrectCity(String name) {
        String cityCorrectName = CORRECT_NAMES_OF_CITIES.get(name);
        return cityCorrectName != null ? cityCorrectName : name;
    }

    public static List<WeatherDataEntity> convertToWeatherDataEntityList(WeatherDataDto weatherDataDto) {
        List<WeatherDataListDto> weatherDataWeatherDataListDto = weatherDataDto.getWeatherDataListDto();
        List<WeatherDataEntity> weatherDataEntityList = new ArrayList<>(weatherDataWeatherDataListDto.size());
        for (WeatherDataListDto weatherInfo : weatherDataWeatherDataListDto) {
            WeatherDataEntity weatherDataEntity = convertToWeatherDataEntity(weatherInfo);
            weatherDataEntityList.add(weatherDataEntity);
        }
        return weatherDataEntityList;
    }

    private static Date convertToDate(Long unixTimestamp) {
        return new Date(unixTimestamp * 1000);
    }
}
