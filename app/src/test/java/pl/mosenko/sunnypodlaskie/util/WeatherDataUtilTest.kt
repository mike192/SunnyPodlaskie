package pl.mosenko.sunnypodlaskie.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * Created by syk on 10.06.17.
 */
class WeatherDataUtilTest {
    @Test
    fun getFormattedTemperatureTest() {
        val temperature = 23.12233
        val formattedTemperature = WeatherDataUtil.getFormattedTemperature(temperature)
        assertThat(formattedTemperature).isEqualTo("23.1\u00b0")
    }

    @Test
    fun getFormattedPressureTest() {
        val pressure = 1000.0
        val formattedPressure = WeatherDataUtil.getFormattedPressure(pressure)
        assertThat(formattedPressure).isEqualTo("1000.0 hPa")
    }
}
