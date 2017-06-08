package pl.mosenko.sunnypodlaskie.network.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoordinatesDto {

    @SerializedName("lon")
    @Expose
    private Double longitude;
    @SerializedName("lat")
    @Expose
    private Double latitude;

    /**
     * No args constructor for use in serialization
     *
     */
    public CoordinatesDto() {
    }

    /**
     *
     * @param longitude
     * @param latitude
     */
    public CoordinatesDto(Double longitude, Double latitude) {
        super();
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "CoordinatesDto{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}