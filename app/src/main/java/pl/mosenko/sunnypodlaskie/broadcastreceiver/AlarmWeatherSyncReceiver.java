package pl.mosenko.sunnypodlaskie.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import pl.mosenko.sunnypodlaskie.util.WeatherDataJobSyncUtils;

/**
 * Created by syk on 26.05.17.
 */

public class AlarmWeatherSyncReceiver extends BroadcastReceiver {

    public AlarmWeatherSyncReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        WeatherDataJobSyncUtils.scheduleFirebaseJobDispatcherSync(context);
    }
}
