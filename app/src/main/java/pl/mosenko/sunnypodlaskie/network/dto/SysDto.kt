package pl.mosenko.sunnypodlaskie.network.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SysDto {
    @SerializedName("type")
    @Expose
    private var type: Int? = null

    @SerializedName("id")
    @Expose
    private var id: Int? = null

    @SerializedName("message")
    @Expose
    private var message: Double? = null

    @SerializedName("country")
    @Expose
    private var country: String? = null

    @SerializedName("sunrise")
    @Expose
    private var sunrise: Long? = null

    @SerializedName("sunset")
    @Expose
    private var sunset: Long? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param message
     * @param id
     * @param sunset
     * @param sunrise
     * @param type
     * @param country
     */
    constructor(type: Int?, id: Int?, message: Double?, country: String?, sunrise: Long?, sunset: Long?) : super() {
        this.type = type
        this.id = id
        this.message = message
        this.country = country
        this.sunrise = sunrise
        this.sunset = sunset
    }

    fun getType(): Int? {
        return type
    }

    fun setType(type: Int?) {
        this.type = type
    }

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id
    }

    fun getMessage(): Double? {
        return message
    }

    fun setMessage(message: Double?) {
        this.message = message
    }

    fun getCountry(): String? {
        return country
    }

    fun setCountry(country: String?) {
        this.country = country
    }

    fun getSunrise(): Long? {
        return sunrise
    }

    fun setSunrise(sunrise: Long?) {
        this.sunrise = sunrise
    }

    fun getSunset(): Long? {
        return sunset
    }

    fun setSunset(sunset: Long?) {
        this.sunset = sunset
    }

    override fun toString(): String {
        return "SysDto{" +
                "type=" + type +
                ", id=" + id +
                ", message=" + message +
                ", country='" + country + '\'' +
                ", sunrise=" + sunrise +
                ", sunset=" + sunset +
                '}'
    }
}
