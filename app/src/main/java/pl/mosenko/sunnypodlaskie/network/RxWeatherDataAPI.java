package pl.mosenko.sunnypodlaskie.network;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pl.mosenko.sunnypodlaskie.model.WeatherData;
import pl.mosenko.sunnypodlaskie.util.APIKeyProvider;

/**
 * Created by syk on 13.05.17.
 */

public class RxWeatherDataAPI {
    private WeatherDataAPI weatherDataAPI;
    private APIKeyProvider apiKeyProvider;

    public RxWeatherDataAPI(WeatherDataAPI weatherDataAPI, APIKeyProvider apiKeyProvider) {
        this.weatherDataAPI = weatherDataAPI;
        this.apiKeyProvider = apiKeyProvider;
    }

    public Disposable getCurrentWeatherData(final GetCurrentWeatherDataListCallback weatherListCallback) {
        return weatherDataAPI.getCurrentWeatherData(apiKeyProvider.getDecodedAPIKey())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(p -> weatherListCallback.onSuccess(p), t -> weatherListCallback.onError(t));
    }


    public interface GetCurrentWeatherDataListCallback {
        void onSuccess(WeatherData weatherDataList);

        void onError(Throwable networkError);
    }
}
