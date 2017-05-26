package pl.mosenko.sunnypodlaskie.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import pl.mosenko.sunnypodlaskie.util.WeatherDataAlarmSyncUtil;

/**
 * Created by syk on 26.05.17.
 */

public class DeviceBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
            WeatherDataAlarmSyncUtil.startAlarm(context);
        }
    }
}
