package pl.mosenko.sunnypodlaskie.ui.weatherdatalist

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import pl.mosenko.sunnypodlaskie.R
import pl.mosenko.sunnypodlaskie.ui.MainActivity

/**
 * Created by syk on 10.06.17.
 */
@RunWith(AndroidJUnit4::class)
class WeatherDataListActivityTest {

    @Test
    fun clickOnWeatherDataItem_shouldStartWeatherDataDetails() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.rv_content)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_content))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
            )
        onView(withId(R.id.ll_weather_data_details)).check(matches(isDisplayed()))
    }

    @Test
    fun clickOnSetting_shouldStartSettingFragment() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.action_settings)).perform(click())
        onView(withText(R.string.pref_sync_time_label)).check(matches(isDisplayed()))
    }
}
