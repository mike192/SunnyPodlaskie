package pl.mosenko.sunnypodlaskie.network.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CloudsDto(
        @SerializedName("all")
        @Expose
        val all: Int
)
