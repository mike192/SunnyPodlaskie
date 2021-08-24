package pl.mosenko.sunnypodlaskie.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import pl.mosenko.sunnypodlaskie.util.WeatherAlarmSyncUtil

/**
 * Created by syk on 26.05.17.
 */
class DeviceBroadcastReceiver : BroadcastReceiver(), KoinComponent {

    private val weatherAlarmSyncUtil: WeatherAlarmSyncUtil by inject()

    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
            weatherAlarmSyncUtil.startAlarm()
        }
    }
}
