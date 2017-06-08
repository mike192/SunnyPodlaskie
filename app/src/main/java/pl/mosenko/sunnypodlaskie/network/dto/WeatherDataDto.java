package pl.mosenko.sunnypodlaskie.network.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherDataDto {

    @SerializedName("cnt")
    @Expose
    private Integer counter;
    @SerializedName("list")
    @Expose
    private List<WeatherDataListDto> weatherDataListDto = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public WeatherDataDto() {
    }

    /**
     *
     * @param counter
     * @param weatherDataListDto
     */
    public WeatherDataDto(Integer counter, List<WeatherDataListDto> weatherDataListDto) {
        super();
        this.counter = counter;
        this.weatherDataListDto = weatherDataListDto;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public List<WeatherDataListDto> getWeatherDataListDto() {
        return weatherDataListDto;
    }

    public void setWeatherDataListDto(List<WeatherDataListDto> weatherDataListDto) {
        this.weatherDataListDto = weatherDataListDto;
    }

    @Override
    public String toString() {
        return "WeatherDataDto{" +
                "counter=" + counter +
                ", weatherDataListDto=" + weatherDataListDto +
                '}';
    }
}