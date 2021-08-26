package pl.mosenko.sunnypodlaskie.ui.setting

import android.view.View
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import org.hamcrest.CoreMatchers.allOf
import org.junit.Test
import org.junit.runner.RunWith
import pl.mosenko.sunnypodlaskie.R

/**
 * Created by syk on 10.06.17.
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
class SettingsActivityTest {

    @Test
    fun notificationPreferenceSummary_shouldChangeAfterSwitchTurnOff() {
        val settingsScenario = launchFragmentInContainer<SettingsFragment>()
        settingsScenario.onFragment { fragment ->
            val switchCompat =
                fragment.requireView().findViewById<View>(R.id.switchWidget) as SwitchCompat
            switchCompat.isChecked = true
        }

        onView(withId(R.id.switchWidget)).perform(click())

        onView(allOf(withId(android.R.id.summary), withText("Wyłączone")))
            .check(matches(withText("Wyłączone")))
    }

    @Test
    fun notificationPreferenceSummary_shouldChangeAfterSwitchTurnOn() {
        val settingsScenario = launchFragmentInContainer<SettingsFragment>()
        settingsScenario.onFragment { fragment ->
            val switchCompat =
                fragment.requireView().findViewById<View>(R.id.switchWidget) as SwitchCompat
            switchCompat.isChecked = false
        }

        onView(withId(R.id.switchWidget)).perform(click())

        onView(allOf(withId(android.R.id.summary), withText("Włączone")))
            .check(matches(withText("Włączone")))
    }
}
