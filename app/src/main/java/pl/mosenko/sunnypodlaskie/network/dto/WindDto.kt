package pl.mosenko.sunnypodlaskie.network.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WindDto {
    @SerializedName("speed")
    @Expose
    private var speed: Double? = null

    @SerializedName("deg")
    @Expose
    private var degree: Double? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param speed
     * @param degree
     */
    constructor(speed: Double?, degree: Double?) : super() {
        this.speed = speed
        this.degree = degree
    }

    fun getSpeed(): Double? {
        return speed
    }

    fun setSpeed(speed: Double?) {
        this.speed = speed
    }

    fun getDegree(): Double? {
        return degree
    }

    fun setDegree(degree: Double?) {
        this.degree = degree
    }

    override fun toString(): String {
        return "WindDto{" +
                "speed=" + speed +
                ", degree=" + degree +
                '}'
    }
}
