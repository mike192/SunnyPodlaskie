package pl.mosenko.sunnypodlaskie.network.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherDataListDto {

    @SerializedName("coord")
    @Expose
    private CoordinatesDto coordinatesDto;
    @SerializedName("sys")
    @Expose
    private SysDto sysDto;
    @SerializedName("weather")
    @Expose
    private List<WeatherDto> weatherDto = null;
    @SerializedName("main")
    @Expose
    private MainDto mainDto;
    @SerializedName("wind")
    @Expose
    private WindDto windDto;
    @SerializedName("clouds")
    @Expose
    private CloudsDto cloudsDto;
    @SerializedName("dt")
    @Expose
    private Long dt;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * No args constructor for use in serialization
     *
     */
    public WeatherDataListDto() {
    }

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
    public WeatherDataListDto(CoordinatesDto coordinatesDto, SysDto sysDto, java.util.List<WeatherDto> weatherDto, MainDto mainDto, WindDto windDto, CloudsDto cloudsDto, Long dt, Integer id, String name) {
        super();
        this.coordinatesDto = coordinatesDto;
        this.sysDto = sysDto;
        this.weatherDto = weatherDto;
        this.mainDto = mainDto;
        this.windDto = windDto;
        this.cloudsDto = cloudsDto;
        this.dt = dt;
        this.id = id;
        this.name = name;
    }

    public CoordinatesDto getCoordinatesDto() {
        return coordinatesDto;
    }

    public void setCoordinatesDto(CoordinatesDto coordinatesDto) {
        this.coordinatesDto = coordinatesDto;
    }

    public SysDto getSysDto() {
        return sysDto;
    }

    public void setSysDto(SysDto sysDto) {
        this.sysDto = sysDto;
    }

    public java.util.List<WeatherDto> getWeatherDto() {
        return weatherDto;
    }

    public void setWeatherDto(java.util.List<WeatherDto> weatherDto) {
        this.weatherDto = weatherDto;
    }

    public MainDto getMainDto() {
        return mainDto;
    }

    public void setMainDto(MainDto mainDto) {
        this.mainDto = mainDto;
    }

    public WindDto getWindDto() {
        return windDto;
    }

    public void setWindDto(WindDto windDto) {
        this.windDto = windDto;
    }

    public CloudsDto getCloudsDto() {
        return cloudsDto;
    }

    public void setCloudsDto(CloudsDto cloudsDto) {
        this.cloudsDto = cloudsDto;
    }

    public Long getDt() {
        return dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
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
                '}';
    }
}