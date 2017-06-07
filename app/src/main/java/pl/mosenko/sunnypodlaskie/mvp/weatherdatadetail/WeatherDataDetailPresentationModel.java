package pl.mosenko.sunnypodlaskie.mvp.weatherdatadetail;

import pl.mosenko.sunnypodlaskie.util.WeatherDataUtil;

/**
 * Created by syk on 06.06.17.
 */

public class WeatherDataDetailPresentationModel {
    private String titleDetails;
    private String temperature;
    private String description;
    private int getIconResource;
    private String pressure;
    private String windDetails;
    private String humidity;
    private String sunrise;
    private String sunset;

    public String getTitleDetails() {
        return titleDetails;
    }

    public void setTitleDetails(String titleDetails) {
        this.titleDetails = titleDetails;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGetIconResource() {
        return getIconResource;
    }

    public void setGetIconResource(int getIconResource) {
        this.getIconResource = getIconResource;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getWindDetails() {
        return windDetails;
    }

    public void setWindDetails(String windDetails) {
        this.windDetails = windDetails;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }
}
