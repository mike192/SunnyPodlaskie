package pl.mosenko.sunnypodlaskie.persistence

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import pl.mosenko.sunnypodlaskie.BuildConfig
import pl.mosenko.sunnypodlaskie.R
import pl.mosenko.sunnypodlaskie.persistence.dao.WeatherConditionEntityDAO
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherConditionEntity
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity
import pl.mosenko.sunnypodlaskie.util.RawResourceUtil
import java.sql.SQLException

/**
 * Created by syk on 16.05.17.
 */
class DatabaseHelperPodlaskieWeather(private val context: Context) : OrmLiteSqliteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(database: SQLiteDatabase, connectionSource: ConnectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, WeatherDataEntity::class.java)
            TableUtils.createTableIfNotExists(connectionSource, WeatherConditionEntity::class.java)
            addWeatherConditionsToDatabase(connectionSource)
        } catch (e: SQLException) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, e.message, e)
            }
        }
    }

    @Throws(SQLException::class)
    private fun addWeatherConditionsToDatabase(connectionSource: ConnectionSource) {
        val weatherConditionEntities = WeatherConditionEntityDAO(connectionSource)
        val weatherConditionsJson = RawResourceUtil.readRawTextFile(context, R.raw.weather_conditions)
        val conditionEntities = Gson().fromJson<MutableList<WeatherConditionEntity?>?>(weatherConditionsJson, object : TypeToken<MutableList<WeatherConditionEntity?>?>() {}.type)
        weatherConditionEntities.create(conditionEntities)
    }

    override fun onUpgrade(database: SQLiteDatabase, connectionSource: ConnectionSource, oldVersion: Int, newVersion: Int) {
        try {
            TableUtils.dropTable<WeatherDataEntity?, Any?>(connectionSource, WeatherDataEntity::class.java, true)
            TableUtils.dropTable<WeatherConditionEntity?, Any?>(connectionSource, WeatherConditionEntity::class.java, true)
        } catch (e: SQLException) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, e.message, e)
            }
        }
        onCreate(database, connectionSource)
    }

    companion object {
        private val TAG = DatabaseHelperPodlaskieWeather::class.java.simpleName
        private const val DATABASE_NAME: String = "weather_data.db"
        private const val DATABASE_VERSION = 10
    }
}
