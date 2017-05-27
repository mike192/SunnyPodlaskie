package pl.mosenko.sunnypodlaskie.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.query.Raw;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import pl.mosenko.sunnypodlaskie.BuildConfig;
import pl.mosenko.sunnypodlaskie.R;
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherConditionEntity;
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity;
import pl.mosenko.sunnypodlaskie.util.RawResourceUtil;

/**
 * Created by syk on 16.05.17.
 */

public class DatabaseHelperPodlaskieWeather extends OrmLiteSqliteOpenHelper {
    private static final String TAG = DatabaseHelperPodlaskieWeather.class.getSimpleName();
    private static final String DATABASE_NAME = "weather_data.db";
    private static final int DATABASE_VERSION = 7;
    private Context mContext;

    public DatabaseHelperPodlaskieWeather(@NonNull Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase database, @NonNull ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, WeatherDataEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, WeatherConditionEntity.class);
            addWeatherConditionsToDatabase();
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, e.getMessage(), e);
            }
        }
    }

    private void addWeatherConditionsToDatabase() throws SQLException {
        Dao<WeatherConditionEntity, Long> weatherConditionEntityDao = getDao(WeatherConditionEntity.class);
        String weatherConditionsJson = RawResourceUtil.readRawTextFile(mContext, R.raw.weather_conditions);
        List<WeatherConditionEntity> conditionEntities = new Gson().fromJson(weatherConditionsJson, new TypeToken<List<WeatherConditionEntity>>(){}.getType());
        weatherConditionEntityDao.create(conditionEntities);
    }

    @Override
    public void onUpgrade(@NonNull SQLiteDatabase database, @NonNull ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, WeatherDataEntity.class, true);
            TableUtils.dropTable(connectionSource, WeatherConditionEntity.class, true);
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, e.getMessage(), e);
            }
        }
        onCreate(database, connectionSource);
    }
}
