package pl.mosenko.sunnypodlaskie;

import android.util.Base64;
import android.util.Log;

import org.junit.Test;

import java.util.Date;

import io.reactivex.Observable;
import pl.mosenko.sunnypodlaskie.util.PreferenceWeatherUtil;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        Date date = PreferenceWeatherUtil.parseSyncTime("07:13");
        System.out.println(date);
        assertEquals(4, 2 + 2);
    }
}