package pl.mosenko.sunnypodlaskie.network.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WeatherDataListDto(
        @SerializedName("coord")
        @Expose
        val coordinatesDto: CoordinatesDto,

        @SerializedName("sys")
        @Expose
        val sysDto: SysDto,

        @SerializedName("weather")
        @Expose
        val weatherDto: MutableList<WeatherDto>,

        @SerializedName("main")
        @Expose
        val mainDto: MainDto,

        @SerializedName("wind")
        @Expose
        val windDto: WindDto,

        @SerializedName("clouds")
        @Expose
        val cloudsDto: CloudsDto,

        @SerializedName("dt")
        @Expose
        val dt: Long,

        @SerializedName("id")
        @Expose
        val id: Int,

        @SerializedName("name")
        @Expose
        val name: String
)
