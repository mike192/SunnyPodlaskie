package pl.mosenko.sunnypodlaskie.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import android.support.annotation.NonNull;
import android.util.Log;

import java.sql.SQLException;

import pl.mosenko.sunnypodlaskie.BuildConfig;
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity;

/**
 * Created by syk on 16.05.17.
 */

public class DatabaseHelperPodlaskieWeather extends OrmLiteSqliteOpenHelper {
    private static final String TAG = DatabaseHelperPodlaskieWeather.class.getSimpleName();
    private static final String DATABASE_NAME = "weather_data.db";
    private static final int DATABASE_VERSION = 1;


    public DatabaseHelperPodlaskieWeather(@NonNull Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase database, @NonNull ConnectionSource connectionSource) {
        try {
            TableUtils.clearTable(connectionSource, WeatherDataEntity.class);
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, e.getMessage(), e);
            }
        }
    }

    @Override
    public void onUpgrade(@NonNull SQLiteDatabase database, @NonNull ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, WeatherDataEntity.class, true);
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, e.getMessage(), e);
            }
        }
        onCreate(database, connectionSource);
    }
}
