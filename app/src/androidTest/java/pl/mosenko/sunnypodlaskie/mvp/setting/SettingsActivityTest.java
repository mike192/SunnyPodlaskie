package pl.mosenko.sunnypodlaskie.mvp.setting;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.SwitchCompat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import pl.mosenko.sunnypodlaskie.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by syk on 10.06.17.
 */
@RunWith(AndroidJUnit4.class)
public class SettingsActivityTest {

    @Rule
    public ActivityTestRule<SettingsActivity> activityTestRule = new ActivityTestRule<>(SettingsActivity.class);

    @Test
    public void notificationPreferenceSummary_shouldChangeAfterSwitchTurnOff() {
        Given : {
            final SwitchCompat switchWidgetReal = (SwitchCompat) activityTestRule.getActivity().findViewById(R.id.switchWidget);
            InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> switchWidgetReal.setChecked(true));
        }
        When : {
            ViewInteraction switchWidget = onView(
                    withId(R.id.switchWidget));
            switchWidget.check(matches(isDisplayed()));
            switchWidget.perform(click());
        }
        Then : {
            ViewInteraction textView = onView(
                    allOf(withId(android.R.id.summary), withText("Wyłączone")));
            textView.check(matches(withText("Wyłączone")));
        }
    }

    @Test
    public void notificationPreferenceSummary_shouldChangeAfterSwitchTurnOn() {
        Given : {
            final SwitchCompat switchWidgetReal = (SwitchCompat) activityTestRule.getActivity().findViewById(R.id.switchWidget);
            InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> switchWidgetReal.setChecked(false));
        }
        When : {
            ViewInteraction switchWidget = onView(
                    withId(R.id.switchWidget));
            switchWidget.check(matches(isDisplayed()));
            switchWidget.perform(click());
        }
        Then : {
            ViewInteraction textView = onView(
                    allOf(withId(android.R.id.summary), withText("Włączone")));
            textView.check(matches(withText("Włączone")));
        }
    }
}