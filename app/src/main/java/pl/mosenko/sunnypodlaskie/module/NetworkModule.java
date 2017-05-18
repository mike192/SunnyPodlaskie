package pl.mosenko.sunnypodlaskie.module;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.mosenko.sunnypodlaskie.BuildConfig;
import pl.mosenko.sunnypodlaskie.network.RxWeatherDataAPI;
import pl.mosenko.sunnypodlaskie.network.WeatherDataAPI;
import pl.mosenko.sunnypodlaskie.util.APIKeyProvider;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by syk on 12.05.17.
 */

@Module
public class NetworkModule {

    @Provides
    @Singleton
    public Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public WeatherDataAPI provideWeatherDataAPI(Retrofit retrofit) {
        return retrofit.create(WeatherDataAPI.class);
    }

    @Provides
    @Singleton
    public APIKeyProvider provideAPIKeyProvider() {
        return new APIKeyProvider();
    }

    @Provides
    @Singleton
    public RxWeatherDataAPI provideRxWeatherDataAPI(WeatherDataAPI watherDataAPI, APIKeyProvider apiKeyProvider) {
        return new RxWeatherDataAPI(watherDataAPI, apiKeyProvider);
    }
}
