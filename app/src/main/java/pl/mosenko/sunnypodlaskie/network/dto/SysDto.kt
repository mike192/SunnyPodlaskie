package pl.mosenko.sunnypodlaskie.network.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SysDto(
        @SerializedName("type")
        @Expose
        val type: Int,

        @SerializedName("id")
        @Expose
        val id: Int,

        @SerializedName("message")
        @Expose
        val message: Double,

        @SerializedName("country")
        @Expose
        val country: String,

        @SerializedName("sunrise")
        @Expose
        val sunrise: Long,

        @SerializedName("sunset")
        @Expose
        val sunset: Long
)
