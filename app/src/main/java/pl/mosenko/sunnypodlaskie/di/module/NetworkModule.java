package pl.mosenko.sunnypodlaskie.di.module;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.novoda.merlin.MerlinsBeard;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.mosenko.sunnypodlaskie.BuildConfig;
import pl.mosenko.sunnypodlaskie.network.api.RxWeatherDataAPI;
import pl.mosenko.sunnypodlaskie.network.api.WeatherDataAPI;
import pl.mosenko.sunnypodlaskie.util.WeatherAPIKeyProvider;
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
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public WeatherDataAPI provideWeatherDataAPI(Retrofit retrofit) {
        return retrofit.create(WeatherDataAPI.class);
    }

    @Provides
    @Singleton
    public WeatherAPIKeyProvider provideAPIKeyProvider() {
        return new WeatherAPIKeyProvider();
    }

    @Provides
    @Singleton
    public RxWeatherDataAPI provideRxWeatherDataAPI(WeatherDataAPI watherDataAPI, WeatherAPIKeyProvider apiKeyProvider) {
        return new RxWeatherDataAPI(watherDataAPI, apiKeyProvider);
    }

    @Provides
    @Singleton
    public MerlinsBeard provideMerlinsBeard(Context context) {
        return MerlinsBeard.from(context);
    }
}
