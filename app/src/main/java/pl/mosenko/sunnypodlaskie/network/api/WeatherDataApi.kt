package pl.mosenko.sunnypodlaskie.network.api

import io.reactivex.Observable
import pl.mosenko.sunnypodlaskie.network.dto.WeatherDataDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by syk on 11.05.17.
 */
interface WeatherDataApi {
    @GET("data/2.5/group?id=757718,759503,776597,771506,768785,766027,764421,758651,753895,754479,775986,759320,771158,776069&units=metric")
    suspend fun getCurrentWeatherData(@Query("appid") apiKey: String): WeatherDataDto
}
