package pl.mosenko.sunnypodlaskie.network.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WeatherDataDto(
        @SerializedName("cnt")
        @Expose
        val counter: Int,

        @SerializedName("list")
        @Expose
        val weatherDataListDto: MutableList<WeatherDataListDto>
)
