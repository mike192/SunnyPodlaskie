package pl.mosenko.sunnypodlaskie.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

import pl.mosenko.sunnypodlaskie.mvp.weatherdatalist.MainActivity;
import pl.mosenko.sunnypodlaskie.R;

/**
 * Created by syk on 26.05.17.
 */

public class WeatherNotificationUtil {
    private static final int WEATHER_NOTIFICATION_ID = 3011;

    public static void notifyUserOfNewWeatherData(@NonNull Context context, Date receivingTime) {
        Notification notification = buildNotification(context, receivingTime);
        notify(context, notification);
    }

    private static void notify(@NonNull Context context, Notification notification) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(WEATHER_NOTIFICATION_ID, notification);
    }

    @NonNull
    private static Notification buildNotification(@NonNull Context context, Date receivingTime) {
        String notificationTitle = context.getString(R.string.notification_title);
        String notificationContentText = context.getString(R.string.notification_content_text);
        String receivingTimeFormatted = getFormattedReceivingDate(receivingTime);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setContentTitle(notificationTitle)
                .setContentText(notificationContentText + " " + receivingTimeFormatted)
                .setSmallIcon(R.drawable.ic_clear) //app's icon
                .setAutoCancel(true);

        PendingIntent mainAcitivityIntentPendingIntent = getPendingIntent(context);
        notificationBuilder.setContentIntent(mainAcitivityIntentPendingIntent);
        return notificationBuilder.build();
    }

    private static PendingIntent getPendingIntent(@NonNull Context context) {
        Intent mainAcitivityIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addNextIntent(mainAcitivityIntent);
        return taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static String getFormattedReceivingDate(Date receivingTime) {
        SimpleDateFormat notificationDateFormat = new SimpleDateFormat("MMM, dd HH:mm:ss");
        return notificationDateFormat.format(receivingTime);
    }


}
