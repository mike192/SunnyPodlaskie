package pl.mosenko.sunnypodlaskie.mvp.weatherdatadetail

/**
 * Created by syk on 06.06.17.
 */
class WeatherDataDetailPresentationModel {
    private var titleDetails: String? = null
    private var temperature: String? = null
    private var description: String? = null
    private var iconResource = 0
    private var pressure: String? = null
    private var windDetails: String? = null
    private var humidity: String? = null
    private var sunrise: String? = null
    private var sunset: String? = null
    fun getTitleDetails(): String? {
        return titleDetails
    }

    fun setTitleDetails(titleDetails: String?) {
        this.titleDetails = titleDetails
    }

    fun getTemperature(): String? {
        return temperature
    }

    fun setTemperature(temperature: String?) {
        this.temperature = temperature
    }

    fun getDescription(): String? {
        return description
    }

    fun setDescription(description: String?) {
        this.description = description
    }

    fun getIconResource(): Int {
        return iconResource
    }

    fun setIconResource(iconResource: Int) {
        this.iconResource = iconResource
    }

    fun getPressure(): String? {
        return pressure
    }

    fun setPressure(pressure: String?) {
        this.pressure = pressure
    }

    fun getWindDetails(): String? {
        return windDetails
    }

    fun setWindDetails(windDetails: String?) {
        this.windDetails = windDetails
    }

    fun getHumidity(): String? {
        return humidity
    }

    fun setHumidity(humidity: String?) {
        this.humidity = humidity
    }

    fun getSunrise(): String? {
        return sunrise
    }

    fun setSunrise(sunrise: String?) {
        this.sunrise = sunrise
    }

    fun getSunset(): String? {
        return sunset
    }

    fun setSunset(sunset: String?) {
        this.sunset = sunset
    }
}
