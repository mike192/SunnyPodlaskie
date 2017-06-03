package pl.mosenko.sunnypodlaskie.network.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherData {

    @SerializedName("cnt")
    @Expose
    private Integer cnt;
    @SerializedName("list")
    @Expose
    private java.util.List<pl.mosenko.sunnypodlaskie.network.dto.List> list = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public WeatherData() {
    }

    /**
     *
     * @param cnt
     * @param list
     */
    public WeatherData(Integer cnt, java.util.List<pl.mosenko.sunnypodlaskie.network.dto.List> list) {
        super();
        this.cnt = cnt;
        this.list = list;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public java.util.List<pl.mosenko.sunnypodlaskie.network.dto.List> getList() {
        return list;
    }

    public void setList(java.util.List<pl.mosenko.sunnypodlaskie.network.dto.List> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "cnt=" + cnt +
                ", list=" + list +
                '}';
    }
}