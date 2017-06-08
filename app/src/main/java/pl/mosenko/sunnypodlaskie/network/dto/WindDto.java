package pl.mosenko.sunnypodlaskie.network.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WindDto {

    @SerializedName("speed")
    @Expose
    private Double speed;
    @SerializedName("deg")
    @Expose
    private Double degree;

    /**
     * No args constructor for use in serialization
     *
     */
    public WindDto() {
    }

    /**
     *
     * @param speed
     * @param degree
     */
    public WindDto(Double speed, Double degree) {
        super();
        this.speed = speed;
        this.degree = degree;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getDegree() {
        return degree;
    }

    public void setDegree(Double degree) {
        this.degree = degree;
    }

    @Override
    public String toString() {
        return "WindDto{" +
                "speed=" + speed +
                ", degree=" + degree +
                '}';
    }
}