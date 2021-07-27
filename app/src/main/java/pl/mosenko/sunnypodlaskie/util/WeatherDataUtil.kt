package pl.mosenko.sunnypodlaskie.util

import pl.mosenko.sunnypodlaskie.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by syk on 15.05.17.
 */
object WeatherDataUtil {
    private const val PRESSURE_UNIT: String = "hPa"
    private const val TEMPERATURE_UNIT: String = "\u00b0"
    private const val WIND_SPEED_UNIT: String = "m/s"
    private const val HUMIDITY_MARK: String = "%"
    const val DEFAULT_PLACEHOLDER: String = "-"

    fun getWeatherIconResourceByCode(code: String): Int {
        return when (code) {
            "01d" -> R.drawable.art_01d
            "02d" -> R.drawable.art_02d
            "03d" -> R.drawable.art_03d
            "04d" -> R.drawable.art_04d
            "09d" -> R.drawable.art_09d
            "10d" -> R.drawable.art_10d
            "11d" -> R.drawable.art_11d
            "13d" -> R.drawable.art_13d
            "50d" -> R.drawable.art_50d
            "01n" -> R.drawable.art_01n
            "02n" -> R.drawable.art_02n
            "03n" -> R.drawable.art_03d
            "04n" -> R.drawable.art_04d
            "09n" -> R.drawable.art_09d
            "10n" -> R.drawable.art_10n
            "11n" -> R.drawable.art_11d
            "13n" -> R.drawable.art_13d
            "50n" -> R.drawable.art_50d
            else -> R.drawable.art_xx
        }
    }

    fun getFormattedTemperature(temperature: Double?): String {
        return String.format("%2.1f", temperature) + TEMPERATURE_UNIT
    }

    fun getFormattedPressure(pressure: Double?): String {
        return "${pressure ?: DEFAULT_PLACEHOLDER} $PRESSURE_UNIT"
    }

    fun getFormattedWindDetails(windSpeed: Double?, windDegree: Double?): String {
        val windDegreeDescription = convertWindDegreeToTextDescription(windDegree)
        return "${windSpeed ?: DEFAULT_PLACEHOLDER} $WIND_SPEED_UNIT, $windDegreeDescription"
    }

    private fun convertWindDegreeToTextDescription(windDegree: Double?): String {
        if (windDegree == null) return ""
        if (windDegree > 337.5) return "N"
        if (windDegree > 292.5) return "NW"
        if (windDegree > 247.5) return "W"
        if (windDegree > 202.5) return "SW"
        if (windDegree > 157.5) return "S"
        if (windDegree > 122.5) return "SE"
        if (windDegree > 67.5) return "E"
        return if (windDegree > 22.5) "NE" else "N"
    }

    fun getFormattedHumidity(humidity: Int?): String {
        return "${humidity ?: DEFAULT_PLACEHOLDER} $HUMIDITY_MARK"
    }

    fun getFormattedTime(date: Date?): String? {
        if (date == null) return DEFAULT_PLACEHOLDER
        val currentFormat: DateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        currentFormat.timeZone = TimeZone.getTimeZone(getCurrentTimeZone())
        return currentFormat.format(date)
    }

    private fun getCurrentTimeZone(): String? {
        val timeZone = Calendar.getInstance().timeZone
        return timeZone.id
    }

    fun getDetailsTitle(city: String, receivingTime: Date): String {
        val dateFormat: DateFormat = SimpleDateFormat("d MMMM HH:mm:ss ", Locale.getDefault())
        val formattedReceivingDate = dateFormat.format(receivingTime)
        return "$city, $formattedReceivingDate"
    }
}
