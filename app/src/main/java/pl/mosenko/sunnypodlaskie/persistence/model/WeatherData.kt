package pl.mosenko.sunnypodlaskie.persistence.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import pl.mosenko.sunnypodlaskie.persistence.converters.DateConverter
import pl.mosenko.sunnypodlaskie.persistence.converters.WeatherConditionEntityConverter
import java.util.*

/**
 * Created by syk on 16.05.17.
 */
@Entity(tableName = "weather_data")
data class WeatherData(
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "city") var city: String,
    @field:TypeConverters(DateConverter::class)
    @ColumnInfo(name = "receiving_time") var receivingTime: Date,
    @ColumnInfo(name = "icon_key") var iconKey: String,
    @ColumnInfo(name = "temperature") var temperature: Double,
    @ColumnInfo(name = "pressure") var pressure: Double? = null,
    @ColumnInfo(name = "humidity") var humidity: Int? = null,
    @ColumnInfo(name = "wind_speed") var windSpeed: Double? = null,
    @ColumnInfo(name = "wind_degree") var windDegree: Double? = null,
    @field:TypeConverters(DateConverter::class)
    @ColumnInfo(name = "sunrise") var sunrise: Date? = null,
    @field:TypeConverters(DateConverter::class)
    @ColumnInfo(name = "sunset") var sunset: Date? = null,
    @field:TypeConverters(WeatherConditionEntityConverter::class)
    @ColumnInfo(name = "weather_condition")
    var weatherCondition: WeatherCondition? = null
)
