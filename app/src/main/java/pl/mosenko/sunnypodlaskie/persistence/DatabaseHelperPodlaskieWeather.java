package pl.mosenko.sunnypodlaskie.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

import android.support.annotation.NonNull;

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
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
