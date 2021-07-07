package pl.mosenko.sunnypodlaskie.mvp.setting

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.appcompat.widget.SwitchCompat
import android.view.View
import org.hamcrest.core.AllOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.mosenko.sunnypodlaskie.R

/**
 * Created by syk on 10.06.17.
 */
@RunWith(AndroidJUnit4::class)
class SettingsActivityTest {
    @Rule
    var activityTestRule: ActivityTestRule<SettingsActivity?>? = ActivityTestRule(SettingsActivity::class.java)
    @Test
    fun notificationPreferenceSummary_shouldChangeAfterSwitchTurnOff() {
        Given@{
            val switchWidgetReal = activityTestRule.getActivity().findViewById<View?>(R.id.switchWidget) as SwitchCompat
            InstrumentationRegistry.getInstrumentation().runOnMainSync { switchWidgetReal.isChecked = true }
        }
        When@{
            val switchWidget = Espresso.onView(
                    ViewMatchers.withId(R.id.switchWidget))
            switchWidget.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            switchWidget.perform(ViewActions.click())
        }
        Then@{
            val textView = Espresso.onView(
                    AllOf.allOf(ViewMatchers.withId(android.R.id.summary), ViewMatchers.withText("Wyłączone")))
            textView.check(ViewAssertions.matches(ViewMatchers.withText("Wyłączone")))
        }
    }

    @Test
    fun notificationPreferenceSummary_shouldChangeAfterSwitchTurnOn() {
        Given@{
            val switchWidgetReal = activityTestRule.getActivity().findViewById<View?>(R.id.switchWidget) as SwitchCompat
            InstrumentationRegistry.getInstrumentation().runOnMainSync { switchWidgetReal.isChecked = false }
        }
        When@{
            val switchWidget = Espresso.onView(
                    ViewMatchers.withId(R.id.switchWidget))
            switchWidget.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            switchWidget.perform(ViewActions.click())
        }
        Then@{
            val textView = Espresso.onView(
                    AllOf.allOf(ViewMatchers.withId(android.R.id.summary), ViewMatchers.withText("Włączone")))
            textView.check(ViewAssertions.matches(ViewMatchers.withText("Włączone")))
        }
    }
}
