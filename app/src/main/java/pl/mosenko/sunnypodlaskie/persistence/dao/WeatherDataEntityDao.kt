package pl.mosenko.sunnypodlaskie.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.mosenko.sunnypodlaskie.persistence.model.WeatherData

/**
 * Created by syk on 16.05.17.
 */
@Dao
interface WeatherDataEntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(weatherDataList: List<WeatherData>)

    @Query("SELECT * FROM weather_data")
    suspend fun getAllWeatherData(): List<WeatherData>?

    @Query("DELETE FROM weather_data")
    suspend fun clearAllWeatherData()

    @Query("SELECT * FROM weather_data where city = :city")
    fun getWeatherDataByCityName(city: String): Flow<WeatherData>
}
