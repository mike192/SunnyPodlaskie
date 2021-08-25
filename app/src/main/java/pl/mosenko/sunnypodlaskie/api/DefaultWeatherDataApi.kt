package pl.mosenko.sunnypodlaskie.api

import pl.mosenko.sunnypodlaskie.api.dto.WeatherDataDto
import pl.mosenko.sunnypodlaskie.util.WeatherApiKeyProvider

/**
 * Created by syk on 13.05.17.
 */
class DefaultWeatherDataApi(
    private val weatherDataApi: WeatherDataApi,
    private val apiKeyProvider: WeatherApiKeyProvider
) {

    suspend fun getCurrentWeatherData(): WeatherDataDto {
        return weatherDataApi.getCurrentWeatherData(apiKeyProvider.getDecodedAPIKey())
    }
}
