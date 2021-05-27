package pl.mosenko.sunnypodlaskie.di.module

import android.content.Context
import android.util.Log
import com.j256.ormlite.android.DatabaseTableConfigUtil
import dagger.Module
import dagger.Provides
import pl.mosenko.sunnypodlaskie.BuildConfig
import pl.mosenko.sunnypodlaskie.persistence.DatabaseHelperPodlaskieWeather
import pl.mosenko.sunnypodlaskie.persistence.dao.WeatherConditionEntityDAO
import pl.mosenko.sunnypodlaskie.persistence.dao.WeatherDataEntityDAO
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherConditionEntity
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity
import java.sql.SQLException
import javax.inject.Singleton

/**
 * Created by syk on 18.05.17.
 */
@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabaseHelperPodlaskieWeather(context: Context): DatabaseHelperPodlaskieWeather? {
        return DatabaseHelperPodlaskieWeather(context)
    }

    @Provides
    @Singleton
    fun provideWeatherDataEntityDAO(databaseHelperPodlaskieWeather: DatabaseHelperPodlaskieWeather): WeatherDataEntityDAO? {
        return try {
            val connectionSource = databaseHelperPodlaskieWeather.connectionSource
            val tableConfig = DatabaseTableConfigUtil.fromClass(connectionSource, WeatherDataEntity::class.java)
            tableConfig?.let { WeatherDataEntityDAO(connectionSource, it) }
                    ?: WeatherDataEntityDAO(connectionSource)
        } catch (e: SQLException) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, e.message, e)
            }
            null
        }
    }

    @Provides
    @Singleton
    fun provideWeatherConditionEntityDAO(databaseHelperPodlaskieWeather: DatabaseHelperPodlaskieWeather): WeatherConditionEntityDAO? {
        return try {
            val connectionSource = databaseHelperPodlaskieWeather.connectionSource
            val tableConfig = DatabaseTableConfigUtil.fromClass(connectionSource, WeatherConditionEntity::class.java)
            tableConfig?.let { WeatherConditionEntityDAO(connectionSource, it) }
                    ?: WeatherConditionEntityDAO(connectionSource)
        } catch (e: SQLException) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, e.message, e)
            }
            null
        }
    }

    companion object {
        private val TAG = DatabaseModule::class.java.simpleName
    }
}
