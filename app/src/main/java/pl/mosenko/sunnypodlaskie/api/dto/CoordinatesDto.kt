package pl.mosenko.sunnypodlaskie.api.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoordinatesDto (
    @SerializedName("lon")
    @Expose
    val longitude: Double,

    @SerializedName("lat")
    @Expose
    val latitude: Double
)
