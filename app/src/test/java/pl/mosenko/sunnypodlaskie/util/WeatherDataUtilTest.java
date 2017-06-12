package pl.mosenko.sunnypodlaskie.util;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by syk on 10.06.17.
 */

public class WeatherDataUtilTest {
    @Test
    public void getFormattedTemperatureTest() {
        double temperature = 23.12233;
        String formattedTemperature = WeatherDataUtil.getFormattedTemperature(temperature);
        Assert.assertEquals("23.1\u00b0", formattedTemperature);
    }

    @Test
    public void getFormattedPressureTest() {
        double pressure = 1000;
        String formattedPressure = WeatherDataUtil.getFormattedPressure(pressure);
        Assert.assertEquals("1000.0hPa", formattedPressure);
    }

}
