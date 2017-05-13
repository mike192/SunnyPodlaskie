package pl.mosenko.sunnypodlaskie.network;

import java.util.List;

import io.reactivex.Observable;
import pl.mosenko.sunnypodlaskie.model.WeatherData;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static android.R.attr.apiKey;

/**
 * Created by syk on 11.05.17.
 */

public interface WeatherDataAPI {
    @GET("data/2.5/group?id=757718,759503,776597,771506,768785,766027,764421,758651,753895,754479,775986,759320,759320,759320,771158,776069&units=metric")
    Observable<WeatherData> getCurrentWeatherData(@Query("appid") String apiKey);
}
