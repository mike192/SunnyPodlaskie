package pl.mosenko.sunnypodlaskie.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import pl.mosenko.sunnypodlaskie.util.WeatherDataJobSyncUtils

/**
 * Created by syk on 26.05.17.
 */
class AlarmWeatherSyncReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        WeatherDataJobSyncUtils.scheduleFirebaseJobDispatcherSync(context)
    }
}
