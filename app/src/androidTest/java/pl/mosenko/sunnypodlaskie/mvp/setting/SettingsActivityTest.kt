package pl.mosenko.sunnypodlaskie.mvp.setting

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.SwitchCompat
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
