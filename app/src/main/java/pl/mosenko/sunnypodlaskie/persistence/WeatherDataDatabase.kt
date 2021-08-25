package pl.mosenko.sunnypodlaskie.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.mosenko.sunnypodlaskie.persistence.dao.WeatherDataEntityDao
import pl.mosenko.sunnypodlaskie.persistence.model.WeatherData

/**
 * Created by syk on 16.05.17.
 */
@Database(
    entities = [WeatherData::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherDataDatabase : RoomDatabase() {

    abstract fun weatherDataDao(): WeatherDataEntityDao
}
