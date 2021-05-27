package pl.mosenko.sunnypodlaskie.util

import junit.framework.Assert
import org.junit.Test

/**
 * Created by syk on 10.06.17.
 */
class WeatherDataUtilTest {
    @Test
    fun getFormattedTemperatureTest() {
        val temperature = 23.12233
        val formattedTemperature = WeatherDataUtil.getFormattedTemperature(temperature)
        Assert.assertEquals("23.1\u00b0", formattedTemperature)
    }

    @Test
    fun getFormattedPressureTest() {
        val pressure = 1000.0
        val formattedPressure = WeatherDataUtil.getFormattedPressure(pressure)
        Assert.assertEquals("1000.0hPa", formattedPressure)
    }
}
