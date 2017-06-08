package pl.mosenko.sunnypodlaskie.network.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CloudsDto {

    @SerializedName("all")
    @Expose
    private Integer all;

    /**
     * No args constructor for use in serialization
     *
     */
    public CloudsDto() {
    }

    /**
     *
     * @param all
     */
    public CloudsDto(Integer all) {
        super();
        this.all = all;
    }

    public Integer getAll() {
        return all;
    }

    public void setAll(Integer all) {
        this.all = all;
    }

    @Override
    public String toString() {
        return "Clouds{" +
                "all=" + all +
                '}';
    }
}