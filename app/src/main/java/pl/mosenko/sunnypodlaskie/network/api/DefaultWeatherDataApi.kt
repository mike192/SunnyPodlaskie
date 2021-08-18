package pl.mosenko.sunnypodlaskie.network.api

import pl.mosenko.sunnypodlaskie.network.dto.WeatherDataDto
import pl.mosenko.sunnypodlaskie.util.WeatherApiKeyProvider

/**
 * Created by syk on 13.05.17.
 */
class DefaultWeatherDataApi(private val weatherDataApi: WeatherDataApi, private val apiKeyProvider: WeatherApiKeyProvider) {

    suspend fun getCurrentWeatherData(): WeatherDataDto {
        return weatherDataApi.getCurrentWeatherData(apiKeyProvider.getDecodedAPIKey())
    }
}
