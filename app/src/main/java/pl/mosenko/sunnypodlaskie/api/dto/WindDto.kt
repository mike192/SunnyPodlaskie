package pl.mosenko.sunnypodlaskie.api.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WindDto(
        @SerializedName("speed")
        @Expose
        val speed: Double,

        @SerializedName("deg")
        @Expose
        val degree: Double
)
