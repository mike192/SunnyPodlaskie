package pl.mosenko.sunnypodlaskie.network.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WeatherDataDto {
    @SerializedName("cnt")
    @Expose
    private var counter: Int? = null

    @SerializedName("list")
    @Expose
    private var weatherDataListDto: MutableList<WeatherDataListDto?>? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param counter
     * @param weatherDataListDto
     */
    constructor(counter: Int?, weatherDataListDto: MutableList<WeatherDataListDto?>?) : super() {
        this.counter = counter
        this.weatherDataListDto = weatherDataListDto
    }

    fun getCounter(): Int? {
        return counter
    }

    fun setCounter(counter: Int?) {
        this.counter = counter
    }

    fun getWeatherDataListDto(): MutableList<WeatherDataListDto?>? {
        return weatherDataListDto
    }

    fun setWeatherDataListDto(weatherDataListDto: MutableList<WeatherDataListDto?>?) {
        this.weatherDataListDto = weatherDataListDto
    }

    override fun toString(): String {
        return "WeatherDataDto{" +
                "counter=" + counter +
                ", weatherDataListDto=" + weatherDataListDto +
                '}'
    }
}
