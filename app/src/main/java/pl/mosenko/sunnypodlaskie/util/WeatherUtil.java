package pl.mosenko.sunnypodlaskie.util;

import android.graphics.drawable.Drawable;

import pl.mosenko.sunnypodlaskie.R;

/**
 * Created by syk on 15.05.17.
 */

public class WeatherUtil {
    private WeatherUtil() {}

    public static int getWeatherIconResourceByCode(final String code) {
        switch (code) {
            case "01d":
                return R.drawable.art_clear;
            case "02d":
                return R.drawable.art_fog; //cloudy fog
            case "03d":
                return R.drawable.art_clouds; //cloud down
            case "10d":
                return R.drawable.art_rain;
            case "11d":
                return R.drawable.art_storm;
            case "13d":
                return R.drawable.art_snow;
            case "01n":
                return R.drawable.art_clear;
            case "04d":
                return R.drawable.art_clouds;
            case "04n":
                return R.drawable.art_clouds;
            case "02n":
                return R.drawable.art_clouds;
            case "03n":
                return R.drawable.art_fog;
            case "10n":
                return R.drawable.art_fog;
            case "11n":
                return R.drawable.art_rain;
            case "13n":
                return R.drawable.art_snow;
            default:
                throw new IllegalArgumentException("There's no icon for provided icon code.");
        }
    }

    public static String getFormattedTemperature(Double temperature) {
        return String.format("%2.1f", temperature)  + "\u00b0";
    }
}
