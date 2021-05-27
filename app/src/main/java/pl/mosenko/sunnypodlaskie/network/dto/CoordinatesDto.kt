package pl.mosenko.sunnypodlaskie.network.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CoordinatesDto {
    @SerializedName("lon")
    @Expose
    private var longitude: Double? = null

    @SerializedName("lat")
    @Expose
    private var latitude: Double? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param longitude
     * @param latitude
     */
    constructor(longitude: Double?, latitude: Double?) : super() {
        this.longitude = longitude
        this.latitude = latitude
    }

    fun getLongitude(): Double? {
        return longitude
    }

    fun setLongitude(longitude: Double?) {
        this.longitude = longitude
    }

    fun getLatitude(): Double? {
        return latitude
    }

    fun setLatitude(latitude: Double?) {
        this.latitude = latitude
    }

    override fun toString(): String {
        return "CoordinatesDto{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}'
    }
}
