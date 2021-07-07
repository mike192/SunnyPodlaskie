package pl.mosenko.sunnypodlaskie.network.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MainDto(
        @SerializedName("temp")
        @Expose
        val temperature: Double,

        @SerializedName("pressure")
        @Expose
        val pressure: Double,

        @SerializedName("humidity")
        @Expose
        val humidity: Int,

        @SerializedName("temp_min")
        @Expose
        val tempMin: Double,

        @SerializedName("temp_max")
        @Expose
        val tempMax: Double,

        @SerializedName("sea_level")
        @Expose
        val seaLevel: Double,

        @SerializedName("grnd_level")
        @Expose
        val grndLevel: Double
)
