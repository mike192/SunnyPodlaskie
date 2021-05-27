package pl.mosenko.sunnypodlaskie.network.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WeatherDataListDto {
    @SerializedName("coord")
    @Expose
    private var coordinatesDto: CoordinatesDto? = null

    @SerializedName("sys")
    @Expose
    private var sysDto: SysDto? = null

    @SerializedName("weather")
    @Expose
    private var weatherDto: MutableList<WeatherDto?>? = null

    @SerializedName("main")
    @Expose
    private var mainDto: MainDto? = null

    @SerializedName("wind")
    @Expose
    private var windDto: WindDto? = null

    @SerializedName("clouds")
    @Expose
    private var cloudsDto: CloudsDto? = null

    @SerializedName("dt")
    @Expose
    private var dt: Long? = null

    @SerializedName("id")
    @Expose
    private var id: Int? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param id
     * @param dt
     * @param cloudsDto
     * @param coordinatesDto
     * @param windDto
     * @param sysDto
     * @param name
     * @param weatherDto
     * @param mainDto
     */
    constructor(coordinatesDto: CoordinatesDto?, sysDto: SysDto?, weatherDto: MutableList<WeatherDto?>?, mainDto: MainDto?, windDto: WindDto?, cloudsDto: CloudsDto?, dt: Long?, id: Int?, name: String?) : super() {
        this.coordinatesDto = coordinatesDto
        this.sysDto = sysDto
        this.weatherDto = weatherDto
        this.mainDto = mainDto
        this.windDto = windDto
        this.cloudsDto = cloudsDto
        this.dt = dt
        this.id = id
        this.name = name
    }

    fun getCoordinatesDto(): CoordinatesDto? {
        return coordinatesDto
    }

    fun setCoordinatesDto(coordinatesDto: CoordinatesDto?) {
        this.coordinatesDto = coordinatesDto
    }

    fun getSysDto(): SysDto? {
        return sysDto
    }

    fun setSysDto(sysDto: SysDto?) {
        this.sysDto = sysDto
    }

    fun getWeatherDto(): MutableList<WeatherDto?>? {
        return weatherDto
    }

    fun setWeatherDto(weatherDto: MutableList<WeatherDto?>?) {
        this.weatherDto = weatherDto
    }

    fun getMainDto(): MainDto? {
        return mainDto
    }

    fun setMainDto(mainDto: MainDto?) {
        this.mainDto = mainDto
    }

    fun getWindDto(): WindDto? {
        return windDto
    }

    fun setWindDto(windDto: WindDto?) {
        this.windDto = windDto
    }

    fun getCloudsDto(): CloudsDto? {
        return cloudsDto
    }

    fun setCloudsDto(cloudsDto: CloudsDto?) {
        this.cloudsDto = cloudsDto
    }

    fun getDt(): Long? {
        return dt
    }

    fun setDt(dt: Long?) {
        this.dt = dt
    }

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    override fun toString(): String {
        return "WeatherDataListDto{" +
                "coordinatesDto=" + coordinatesDto +
                ", sysDto=" + sysDto +
                ", weatherDto=" + weatherDto +
                ", mainDto=" + mainDto +
                ", windDto=" + windDto +
                ", cloudsDto=" + cloudsDto +
                ", dt=" + dt +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}'
    }
}
