package pl.mosenko.sunnypodlaskie.network.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MainDto {
    @SerializedName("temp")
    @Expose
    private var temperature: Double? = null

    @SerializedName("pressure")
    @Expose
    private var pressure: Double? = null

    @SerializedName("humidity")
    @Expose
    private var humidity: Int? = null

    @SerializedName("temp_min")
    @Expose
    private var tempMin: Double? = null

    @SerializedName("temp_max")
    @Expose
    private var tempMax: Double? = null

    @SerializedName("sea_level")
    @Expose
    private var seaLevel: Double? = null

    @SerializedName("grnd_level")
    @Expose
    private var grndLevel: Double? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param seaLevel
     * @param humidity
     * @param pressure
     * @param grndLevel
     * @param tempMax
     * @param temperature
     * @param tempMin
     */
    constructor(temperature: Double?, pressure: Double?, humidity: Int?, tempMin: Double?, tempMax: Double?, seaLevel: Double?, grndLevel: Double?) : super() {
        this.temperature = temperature
        this.pressure = pressure
        this.humidity = humidity
        this.tempMin = tempMin
        this.tempMax = tempMax
        this.seaLevel = seaLevel
        this.grndLevel = grndLevel
    }

    fun getTemperature(): Double? {
        return temperature
    }

    fun setTemperature(temperature: Double?) {
        this.temperature = temperature
    }

    fun getPressure(): Double? {
        return pressure
    }

    fun setPressure(pressure: Double?) {
        this.pressure = pressure
    }

    fun getHumidity(): Int? {
        return humidity
    }

    fun setHumidity(humidity: Int?) {
        this.humidity = humidity
    }

    fun getTempMin(): Double? {
        return tempMin
    }

    fun setTempMin(tempMin: Double?) {
        this.tempMin = tempMin
    }

    fun getTempMax(): Double? {
        return tempMax
    }

    fun setTempMax(tempMax: Double?) {
        this.tempMax = tempMax
    }

    fun getSeaLevel(): Double? {
        return seaLevel
    }

    fun setSeaLevel(seaLevel: Double?) {
        this.seaLevel = seaLevel
    }

    fun getGrndLevel(): Double? {
        return grndLevel
    }

    fun setGrndLevel(grndLevel: Double?) {
        this.grndLevel = grndLevel
    }

    override fun toString(): String {
        return "MainDto{" +
                "temperature=" + temperature +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", tempMin=" + tempMin +
                ", tempMax=" + tempMax +
                ", seaLevel=" + seaLevel +
                ", grndLevel=" + grndLevel +
                '}'
    }
}
