package pl.mosenko.sunnypodlaskie;

import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherConditionEntity;
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
        WeatherConditionEntity condition = new WeatherConditionEntity();
        condition.setId(200l);
        condition.setDescription("Słoczecznie");
        WeatherConditionEntity condition2= new WeatherConditionEntity();
        condition2.setId(201l);
        condition2.setDescription("Mocno słonczecznie");
        List<WeatherConditionEntity> conditionEntities = new ArrayList<>();
        conditionEntities.add(condition);
        conditionEntities.add(condition2);

        Gson gson = new Gson();
       // System.out.println(gson.toJson(conditionEntities));

        String json = "[{\"id\":200,\"description\":\"Słoczecznie\"},{\"id\":201,\"description\":\"Mocno słonczecznie\"}]";
        List<WeatherConditionEntity> conditionEntities2 = gson.fromJson(json, new TypeToken<List<WeatherConditionEntity>>(){}.getType());
        conditionEntities2.forEach(System.out::println);

        assertEquals(4, 2 + 2);
    }
}