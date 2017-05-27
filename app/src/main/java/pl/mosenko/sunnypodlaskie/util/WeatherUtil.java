package pl.mosenko.sunnypodlaskie.util;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import pl.mosenko.sunnypodlaskie.R;

import static android.content.ContentValues.TAG;

/**
 * Created by syk on 15.05.17.
 */

public class WeatherUtil {
    private static final String PRESSURE_UNIT = "hPa";
    private static final String TEMPERATURE_UNIT = "\u00b0";
    private static final String WIND_SPEED_UNIT = "m/s";


    private WeatherUtil() {}

    public static int getWeatherIconResourceByCode(final String code) {
        switch (code) {
            case "01d":
                return R.drawable.art_01d;
            case "02d":
                return R.drawable.art_02d;
            case "03d":
                return R.drawable.art_03d;
            case "04d":
                return R.drawable.art_04d;
            case "09d":
                return R.drawable.art_09d;
            case "10d":
                return R.drawable.art_10d;
            case "11d":
                return R.drawable.art_11d;
            case "13d":
                return R.drawable.art_13d;
            case "50d":
                return R.drawable.art_50d;
            case "01n":
                return R.drawable.art_01n;
            case "02n":
                return R.drawable.art_02n;
            case "03n":
                return R.drawable.art_03d;
            case "04n":
                return R.drawable.art_04d;
            case "09n":
                return R.drawable.art_09d;
            case "10n":
                return R.drawable.art_10n;
            case "11n":
                return R.drawable.art_11d;
            case "13n":
                return R.drawable.art_13d;
            case "50n":
                return R.drawable.art_50d;
            default:
                return R.drawable.art_xx;
        }
    }

    public static String getFormattedTemperature(Double temperature) {
        return String.format("%2.1f", temperature)  + TEMPERATURE_UNIT;
    }

    public static String getFormattedPressure(Double pressure) {
        return String.valueOf(pressure) + PRESSURE_UNIT;
    }

    public static String getFormattedWindDetails(Double windSpeed, Double windDegree) {
        String windDegreeDescription = converWindDegreeToTextDescription(windDegree);
        return String.valueOf(windSpeed) + WIND_SPEED_UNIT + ", " + windDegreeDescription;
    }

    private static String converWindDegreeToTextDescription(Double windDegree) {
        if (windDegree == null) return "";
        if (windDegree > 337.5) return "N";
        if (windDegree > 292.5) return "NW";
        if (windDegree > 247.5) return "W";
        if (windDegree > 202.5) return "SW";
        if (windDegree > 157.5) return "S";
        if (windDegree > 122.5) return "SE";
        if (windDegree > 67.5) return "E";
        if (windDegree > 22.5) return "NE";
        return "N";
    }

    public static String getFormattedHumidity(Integer humidity) {
        String stringHumidity = String.valueOf(humidity);
        return addPercentPostfix(stringHumidity);
    }

    public static String addPercentPostfix(String string) {
        return string + "%";
    }

    public static String getFormattedTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
        return simpleDateFormat.format(date);
    }

    public static String getDetailsTitle(String city, Date receivingTime) {
        DateFormat dateFormat = new SimpleDateFormat("MM dd hh:mm:ss ");
        String formattedReceivingDate = dateFormat.format(receivingTime);
        return city + ", " + formattedReceivingDate;
    }
}
