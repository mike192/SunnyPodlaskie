package pl.mosenko.sunnypodlaskie.network.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CloudsDto {
    @SerializedName("all")
    @Expose
    private var all: Int? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param all
     */
    constructor(all: Int?) : super() {
        this.all = all
    }

    fun getAll(): Int? {
        return all
    }

    fun setAll(all: Int?) {
        this.all = all
    }

    override fun toString(): String {
        return "Clouds{" +
                "all=" + all +
                '}'
    }
}
