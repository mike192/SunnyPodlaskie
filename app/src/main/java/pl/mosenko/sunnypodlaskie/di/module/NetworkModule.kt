package pl.mosenko.sunnypodlaskie.di.module

import android.content.Context
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.novoda.merlin.MerlinsBeard
import dagger.Module
import dagger.Provides
import pl.mosenko.sunnypodlaskie.BuildConfig
import pl.mosenko.sunnypodlaskie.network.api.RxWeatherDataAPI
import pl.mosenko.sunnypodlaskie.network.api.WeatherDataAPI
import pl.mosenko.sunnypodlaskie.util.WeatherAPIKeyProvider
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by syk on 12.05.17.
 */
@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit? {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASEURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun provideWeatherDataAPI(retrofit: Retrofit?): WeatherDataAPI? {
        return retrofit.create(WeatherDataAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideAPIKeyProvider(): WeatherAPIKeyProvider? {
        return WeatherAPIKeyProvider()
    }

    @Provides
    @Singleton
    fun provideRxWeatherDataAPI(watherDataAPI: WeatherDataAPI?, apiKeyProvider: WeatherAPIKeyProvider?): RxWeatherDataAPI? {
        return RxWeatherDataAPI(watherDataAPI, apiKeyProvider)
    }

    @Provides
    @Singleton
    fun provideMerlinsBeard(context: Context?): MerlinsBeard? {
        return MerlinsBeard.from(context)
    }
}
