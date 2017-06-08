package pl.mosenko.sunnypodlaskie.network.api;

import io.reactivex.Observable;
import pl.mosenko.sunnypodlaskie.network.dto.WeatherDataDto;
import pl.mosenko.sunnypodlaskie.util.WeatherAPIKeyProvider;

/**
 * Created by syk on 13.05.17.
 */

public class RxWeatherDataAPI {

    private WeatherDataAPI weatherDataAPI;
    private WeatherAPIKeyProvider apiKeyProvider;

    public RxWeatherDataAPI(WeatherDataAPI weatherDataAPI, WeatherAPIKeyProvider apiKeyProvider) {
        this.weatherDataAPI = weatherDataAPI;
        this.apiKeyProvider = apiKeyProvider;
    }

    public Observable<WeatherDataDto> getCurrentWeatherData() {
        return weatherDataAPI.getCurrentWeatherData(apiKeyProvider.getDecodedAPIKey());
    }
}
