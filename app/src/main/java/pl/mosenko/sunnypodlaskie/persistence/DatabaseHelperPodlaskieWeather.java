package pl.mosenko.sunnypodlaskie.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import android.support.annotation.NonNull;
import android.util.Log;

import java.sql.SQLException;
import java.util.List;

import pl.mosenko.sunnypodlaskie.BuildConfig;
import pl.mosenko.sunnypodlaskie.R;
import pl.mosenko.sunnypodlaskie.persistence.dao.WeatherConditionEntityDAO;
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherConditionEntity;
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity;
import pl.mosenko.sunnypodlaskie.util.RawResourceUtil;

/**
 * Created by syk on 16.05.17.
 */

public class DatabaseHelperPodlaskieWeather extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DatabaseHelperPodlaskieWeather.class.getSimpleName();
    private static final String DATABASE_NAME = "weather_data.db";
    private static final int DATABASE_VERSION = 10;
    private Context context;

    public DatabaseHelperPodlaskieWeather(@NonNull Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase database, @NonNull ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, WeatherDataEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, WeatherConditionEntity.class);
            addWeatherConditionsToDatabase(connectionSource);
        } catch (SQLException e) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, e.getMessage(), e);
            }
        }
    }

    private void addWeatherConditionsToDatabase(ConnectionSource connectionSource) throws SQLException {
        WeatherConditionEntityDAO weatherConditionEntities = new WeatherConditionEntityDAO(connectionSource);
        String weatherConditionsJson = RawResourceUtil.readRawTextFile(context, R.raw.weather_conditions);
        List<WeatherConditionEntity> conditionEntities = new Gson().fromJson(weatherConditionsJson, new TypeToken<List<WeatherConditionEntity>>(){}.getType());
        weatherConditionEntities.create(conditionEntities);
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
