package pl.mosenko.sunnypodlaskie.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherData {

    @SerializedName("cnt")
    @Expose
    private Integer cnt;
    @SerializedName("list")
    @Expose
    private java.util.List<pl.mosenko.sunnypodlaskie.model.List> list = null;

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
    public WeatherData(Integer cnt, java.util.List<pl.mosenko.sunnypodlaskie.model.List> list) {
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

    public java.util.List<pl.mosenko.sunnypodlaskie.model.List> getList() {
        return list;
    }

    public void setList(java.util.List<pl.mosenko.sunnypodlaskie.model.List> list) {
        this.list = list;
    }

}