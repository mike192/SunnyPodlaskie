package pl.mosenko.sunnypodlaskie.persistence.entities

import android.provider.BaseColumns
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import pl.mosenko.sunnypodlaskie.persistence.dao.WeatherDataEntityDAO
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity
import java.util.*

/**
 * Created by syk on 16.05.17.
 */
@DatabaseTable(tableName = WeatherDataEntity.TABLE_NAME, daoClass = WeatherDataEntityDAO::class)
class WeatherDataEntity : AbstractOrmLiteEntity() {
    @DatabaseField(columnName = CITY_COLUMN, canBeNull = false)
    private var city: String? = null

    @DatabaseField(columnName = RECEIVING_TIME_COLUMN, canBeNull = false)
    private var receivingTime: Date? = null

    @DatabaseField(columnName = ICON_KEY_COLUMN, canBeNull = false)
    private var iconKey: String? = null

    @DatabaseField(columnName = WEATHER_CONDITION, canBeNull = false, foreignColumnName = BaseColumns._ID, foreign = true, foreignAutoRefresh = true)
    private var weatherCondition: WeatherConditionEntity? = null

    @DatabaseField(columnName = TEMPERATURE_COLUMN, canBeNull = false)
    private var temperature: Double? = null

    @DatabaseField(columnName = PRESSURE_COLUMN)
    private var pressure: Double? = null

    @DatabaseField(columnName = HUMIDITY_COLUMN)
    private var humidity: Int? = null

    @DatabaseField(columnName = WIND_SPEED_COLUMN)
    private var windSpeed: Double? = null

    @DatabaseField(columnName = WIND_DEGREE_COLUMN)
    private var windDegree: Double? = null

    @DatabaseField(columnName = SUNRISE_COLUMN)
    private var sunrise: Date? = null

    @DatabaseField(columnName = SUNSET_COLUMN)
    private var sunset: Date? = null
    fun getCity(): String? {
        return city
    }

    fun setCity(city: String?) {
        this.city = city
    }

    fun getReceivingTime(): Date? {
        return receivingTime
    }

    fun setReceivingTime(receivingTime: Date?) {
        this.receivingTime = receivingTime
    }

    fun getIconKey(): String? {
        return iconKey
    }

    fun setIconKey(iconKey: String?) {
        this.iconKey = iconKey
    }

    fun getWeatherCondition(): WeatherConditionEntity? {
        return weatherCondition
    }

    fun setWeatherCondition(weatherCondition: WeatherConditionEntity?) {
        this.weatherCondition = weatherCondition
    }

    fun getTemperature(): Double? {
        return temperature
    }

    fun setTemperature(temperature: Double?) {
        this.temperature = temperature
    }

    fun getPressure(): Double? {
        return pressure
    }

    fun setPressure(pressure: Double?) {
        this.pressure = pressure
    }

    fun getHumidity(): Int? {
        return humidity
    }

    fun setHumidity(humidity: Int?) {
        this.humidity = humidity
    }

    fun getWindSpeed(): Double? {
        return windSpeed
    }

    fun setWindSpeed(windSpeed: Double?) {
        this.windSpeed = windSpeed
    }

    fun getWindDegree(): Double? {
        return windDegree
    }

    fun setWindDegree(windDegree: Double?) {
        this.windDegree = windDegree
    }

    fun getSunrise(): Date? {
        return sunrise
    }

    fun setSunrise(sunrise: Date?) {
        this.sunrise = sunrise
    }

    fun getSunset(): Date? {
        return sunset
    }

    fun setSunset(sunset: Date?) {
        this.sunset = sunset
    }

    override fun toString(): String {
        return "WeatherDataEntity{" +
                "city='" + city + '\'' +
                ", receivingTime=" + receivingTime +
                ", iconKey='" + iconKey + '\'' +
                ", weatherCondition=" + weatherCondition +
                ", temperature=" + temperature +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", windSpeed=" + windSpeed +
                ", windDegree=" + windDegree +
                ", sunrise=" + sunrise +
                ", sunset=" + sunset +
                '}'
    }

    companion object {
        val TABLE_NAME: String? = "weather_data"
        val CITY_COLUMN: String? = "city"
        val RECEIVING_TIME_COLUMN: String? = "receiving_time"
        val ICON_KEY_COLUMN: String? = "icon_key"
        val WEATHER_CONDITION: String? = "weather_condition"
        val TEMPERATURE_COLUMN: String? = "temperature"
        val PRESSURE_COLUMN: String? = "pressure"
        val HUMIDITY_COLUMN: String? = "humidity"
        val WIND_SPEED_COLUMN: String? = "wind_speed"
        val WIND_DEGREE_COLUMN: String? = "wind_degree"
        val SUNRISE_COLUMN: String? = "sunrise"
        val SUNSET_COLUMN: String? = "sunset"
    }
}
