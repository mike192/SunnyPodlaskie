package pl.mosenko.sunnypodlaskie.broadcastreceiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import pl.mosenko.sunnypodlaskie.util.WeatherDataJobSyncUtils

/**
 * Created by syk on 26.05.17.
 */
class AlarmWeatherSyncReceiver : BroadcastReceiver(), KoinComponent {

    private val weatherDataJobSyncUtils: WeatherDataJobSyncUtils by inject()

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        weatherDataJobSyncUtils.scheduleWeatherDataSync(context)
    }
}
