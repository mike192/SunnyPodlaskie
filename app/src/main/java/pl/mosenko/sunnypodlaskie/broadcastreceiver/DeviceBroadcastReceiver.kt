package pl.mosenko.sunnypodlaskie.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import pl.mosenko.sunnypodlaskie.util.WeatherDataAlarmSyncUtil

/**
 * Created by syk on 26.05.17.
 */
class DeviceBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
            WeatherDataAlarmSyncUtil.startAlarm(context)
        }
    }
}
