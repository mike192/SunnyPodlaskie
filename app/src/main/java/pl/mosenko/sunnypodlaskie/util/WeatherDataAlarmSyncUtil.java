package pl.mosenko.sunnypodlaskie.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.preference.PreferenceManager;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import pl.mosenko.sunnypodlaskie.broadcastreceiver.AlarmWeatherSyncReceiver;
import pl.mosenko.sunnypodlaskie.R;

/**
 * Created by syk on 26.05.17.
 */

public class WeatherDataAlarmSyncUtil {
    private static final int ALARM_INTERVAL = (int) (int) TimeUnit.HOURS.toMillis(24);
    private static final int REQUEST_CODE = 1001;

    private WeatherDataAlarmSyncUtil() {
    }

    public static void startAlarmOnRunApp(@NonNull Context context) {
        if (!isAlarmScheduled(context)) {
            startAlarm(context);
        }
    }

    private static boolean isAlarmScheduled(@NonNull Context context) {
        Intent alarmIntent = new Intent(context, AlarmWeatherSyncReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, alarmIntent, PendingIntent.FLAG_NO_CREATE);
        return pendingIntent != null;
    }

    public static void startAlarm(@NonNull Context context) {
        PendingIntent pendingIntent = getPendingIntent(context);
        Date syncTimeDate = getSyncDate(context);
        initializeSyncAlarm(context, pendingIntent, syncTimeDate);
    }

    private static void initializeSyncAlarm(@NonNull Context context, PendingIntent pendingIntent, Date syncTimeDate) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, syncTimeDate.getTime(),
                ALARM_INTERVAL, pendingIntent);
    }

    private static Date getSyncDate(@NonNull Context context) {
        return PreferenceWeatherUtil.getSyncDate(context);
    }

    private static PendingIntent getPendingIntent(@NonNull Context context) {
        Intent alarmIntent = new Intent(context, AlarmWeatherSyncReceiver.class);
        return PendingIntent.getBroadcast(context, REQUEST_CODE, alarmIntent, 0);
    }

    public static void cancelAlarm(@NonNull Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = getPendingIntent(context);
        alarmManager.cancel(pendingIntent);
        WeatherDataJobSyncUtils.cancelFirebaseJobDispatcherSync(context);
    }
}
