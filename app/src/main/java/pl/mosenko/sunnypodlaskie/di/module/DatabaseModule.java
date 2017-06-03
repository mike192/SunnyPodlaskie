package pl.mosenko.sunnypodlaskie.di.module;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.j256.ormlite.android.DatabaseTableConfigUtil;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.mosenko.sunnypodlaskie.BuildConfig;
import pl.mosenko.sunnypodlaskie.persistence.DatabaseHelperPodlaskieWeather;
import pl.mosenko.sunnypodlaskie.persistence.dao.WeatherDataEntityDAO;
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity;
/**
 * Created by syk on 18.05.17.
 */

@Module
public class DatabaseModule {
    private static final String TAG = DatabaseModule.class.getSimpleName();

    @Provides
    @Singleton
    public DatabaseHelperPodlaskieWeather provideDatabaseHelperPodlaskieWeather(@NonNull final Context context) {
        return new DatabaseHelperPodlaskieWeather(context);
    }

    @Provides
    @Singleton
    public WeatherDataEntityDAO provideWeatherDataEntityDAO(@NonNull final DatabaseHelperPodlaskieWeather databaseHelperPodlaskieWeather) {
        try {
            ConnectionSource connectionSource = databaseHelperPodlaskieWeather.getConnectionSource();
            DatabaseTableConfig<WeatherDataEntity> tableConfig = DatabaseTableConfigUtil.fromClass(connectionSource, WeatherDataEntity.class);
            if (tableConfig != null) {
                return new WeatherDataEntityDAO(connectionSource, tableConfig);
            } else {
                return new WeatherDataEntityDAO(connectionSource);
            }
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, e.getMessage(), e);
            }
            return null;
        }
    }
}
