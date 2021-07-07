package pl.mosenko.sunnypodlaskie.network.api

import io.reactivex.Observable
import pl.mosenko.sunnypodlaskie.network.dto.WeatherDataDto
import pl.mosenko.sunnypodlaskie.util.WeatherAPIKeyProvider

/**
 * Created by syk on 13.05.17.
 */
class RxWeatherDataAPI(private val weatherDataAPI: WeatherDataAPI, private val apiKeyProvider: WeatherAPIKeyProvider) {
    fun getCurrentWeatherData(): Observable<WeatherDataDto> {
        return weatherDataAPI.getCurrentWeatherData(apiKeyProvider.getDecodedAPIKey())
    }
}
