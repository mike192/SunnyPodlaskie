package pl.mosenko.sunnypodlaskie.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import pl.mosenko.sunnypodlaskie.R;

/**
 * Created by syk on 26.05.17.
 */

public class PreferenceWeatherUtil {
    private static final String TAG = PreferenceWeatherUtil.class.getSimpleName();

    private PreferenceWeatherUtil(){}

    public static boolean areNotificationsEnabled(@NonNull Context context) {
        String displayNotificationsKey = context.getString(R.string.pref_enable_notifications_key);
        boolean showNotificationsDefaultValue = context.getResources().getBoolean(R.bool.show_notifications_by_default);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(displayNotificationsKey, showNotificationsDefaultValue);
    }

    public static Date getSyncDate(@NonNull Context context) {
        String syncTimeString = getStringSyncTime(context);
        return parseSyncTime(syncTimeString);
    }

    @NonNull
    private static String getStringSyncTime(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String syncTimeString;
        if (sharedPreferences.contains(context.getString(R.string.pref_sync_time_key))) {
            syncTimeString = sharedPreferences.getString(context.getString(R.string.pref_sync_time_key), "");
        } else {
            syncTimeString = context.getString(R.string.pref_sync_time_default);
        }
        return syncTimeString;
    }

    private static Date parseSyncTime(String syncTimeString) {
        Date syncTimeDate = parseSyncTimeToPartiallyDate(syncTimeString);
        Calendar syncDateCalendar = getCurrentSyncTimeCalendar(syncTimeDate);
        return syncDateCalendar.getTime();
    }

    @NonNull
    private static Calendar getCurrentSyncTimeCalendar(Date syncTimeDate) {
        Calendar syncTimeCalendar = Calendar.getInstance();
        syncTimeCalendar.setTime(syncTimeDate);

        Calendar syncDateCalendar = Calendar.getInstance();
        syncDateCalendar.set(Calendar.HOUR_OF_DAY, syncTimeCalendar.get(Calendar.HOUR_OF_DAY));
        syncDateCalendar.set(Calendar.MINUTE, syncTimeCalendar.get(Calendar.MINUTE));
        syncDateCalendar.set(Calendar.SECOND, 0);
        syncDateCalendar.set(Calendar.MILLISECOND, 0);
        return syncDateCalendar;
    }

    @Nullable
    public static Date parseSyncTimeToPartiallyDate(String syncTimeString) {
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        try {
            Date syncTimeDate = simpleTimeFormat.parse(syncTimeString);
            return isValidTime(syncTimeString) ? syncTimeDate : null;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static boolean isValidTime(String syncTimeString) {
        String hourPart = syncTimeString.substring(0, syncTimeString.indexOf(":"));
        String minutePart = syncTimeString.substring(syncTimeString.indexOf(":") + 1);

        Integer hours;
        Integer minutes;
        try {
            hours = Integer.valueOf(hourPart);
            minutes = Integer.valueOf(minutePart);
        } catch (NumberFormatException ne) {
            return false;
        }

        return hours >= 0 && hours <= 24 && minutes >= 0 && minutes <= 59;
    }
}
