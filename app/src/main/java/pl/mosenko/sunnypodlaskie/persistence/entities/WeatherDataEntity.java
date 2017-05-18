package pl.mosenko.sunnypodlaskie.persistence.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

import pl.mosenko.sunnypodlaskie.persistence.dao.WeatherDataEntityDAO;

import static pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity.TABLE_NAME;

/**
 * Created by syk on 16.05.17.
 */

@DatabaseTable(tableName = TABLE_NAME, daoClass = WeatherDataEntityDAO.class)
public class WeatherDataEntity extends AbstractOrmLiteEntity {
    public static final String TABLE_NAME = "weather_data";
    public static final String CITY_COLUMN = "city";
    public static final String ICON_KEY_COLUMN = "icon_key";
    public static final String DESCRIPTION_COLUMN = "description";
    public static final String TEMPERATURE_COLUMN = "temperature";
    public static final String PRESSURE_COLUMN = "pressure";
    public static final String HUMIDITY_COLUMN = "humidity";
    public static final String WIND_SPEED_COLUMN = "wind_speed";
    public static final String WIND_DEGREE_COLUMN = "wind_degree";
    public static final String SUNRISE_COLUMN = "sunrise";
    public static final String SUNSET_COLUMN = "sunset";

    @DatabaseField(columnName = CITY_COLUMN, canBeNull = false)
    private String city;
    @DatabaseField(columnName = ICON_KEY_COLUMN, canBeNull = false)
    private String iconKey;
    @DatabaseField(columnName = DESCRIPTION_COLUMN)
    private String description;
    @DatabaseField(columnName = TEMPERATURE_COLUMN, canBeNull = false)
    private Double temperature;
    @DatabaseField(columnName = PRESSURE_COLUMN)
    private Double pressure;
    @DatabaseField(columnName = HUMIDITY_COLUMN)
    private Integer humidity;
    @DatabaseField(columnName = WIND_SPEED_COLUMN)
    private Double windSpeed;
    @DatabaseField(columnName = WIND_DEGREE_COLUMN)
    private Double windDegree;
    @DatabaseField(columnName = SUNRISE_COLUMN)
    private Date sunrise;
    @DatabaseField(columnName = SUNSET_COLUMN)
    private Date sunset;

    public WeatherDataEntity() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIconKey() {
        return iconKey;
    }

    public void setIconKey(String iconKey) {
        this.iconKey = iconKey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Double getWindDegree() {
        return windDegree;
    }

    public void setWindDegree(Double windDegree) {
        this.windDegree = windDegree;
    }

    public Date getSunrise() {
        return sunrise;
    }

    public void setSunrise(Date sunrise) {
        this.sunrise = sunrise;
    }

    public Date getSunset() {
        return sunset;
    }

    public void setSunset(Date sunset) {
        this.sunset = sunset;
    }
}
