package pl.mosenko.sunnypodlaskie.mvp.weatherdatalist

import android.app.Activity
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.mosenko.sunnypodlaskie.R
import pl.mosenko.sunnypodlaskie.mvp.setting.SettingsActivity

/**
 * Created by syk on 10.06.17.
 */
@RunWith(AndroidJUnit4::class)
class WeatherDataListActivityTest {
    @Rule
    var listActivityActivityTestRule: ActivityTestRule<WeatherDataListActivity?>? = ActivityTestRule(WeatherDataListActivity::class.java)
    @Test
    fun clickOnSetting_shouldStartSettingActivity() {
        Espresso.onView(ViewMatchers.withId(R.id.WeatherDataListActivity_action_settings)).perform(ViewActions.click())
        val activity = getActivityInstance()
        Assert.assertTrue(activity is SettingsActivity)
    }

    fun getActivityInstance(): Activity? {
        val activity = arrayOfNulls<Activity?>(1)
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            var currentActivity: Activity? = null
            val resumedActivities: MutableCollection<*>? = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
            if (resumedActivities.iterator().hasNext()) {
                currentActivity = resumedActivities.iterator().next() as Activity?
                activity[0] = currentActivity
            }
        }
        return activity[0]
    }
}
