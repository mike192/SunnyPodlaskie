package pl.mosenko.sunnypodlaskie.mvp.weatherdatalist;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

import pl.mosenko.sunnypodlaskie.R;
import pl.mosenko.sunnypodlaskie.mvp.setting.SettingsActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertTrue;

/**
 * Created by syk on 10.06.17.
 */

@RunWith(AndroidJUnit4.class)
public class WeatherDataListActivityTest {

    @Rule
    public ActivityTestRule<WeatherDataListActivity> listActivityActivityTestRule =
            new ActivityTestRule<WeatherDataListActivity>(WeatherDataListActivity.class);

    @Test
    public void clickOnSetting_shouldStartSettingActivity() {
        onView(withId(R.id.WeatherDataListActivity_action_settings)).perform(click());
        Activity activity = getActivityInstance();
        assertTrue(activity instanceof SettingsActivity);
    }

    public Activity getActivityInstance() {
        final Activity[] activity = new Activity[1];

        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            Activity currentActivity = null;
            Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
            if (resumedActivities.iterator().hasNext()) {
                currentActivity = (Activity) resumedActivities.iterator().next();
                activity[0] = currentActivity;
            }
        });
        return activity[0];
    }
}
