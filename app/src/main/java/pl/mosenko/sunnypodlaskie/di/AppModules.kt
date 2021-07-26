package pl.mosenko.sunnypodlaskie.di

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.novoda.merlin.MerlinsBeard
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.mosenko.sunnypodlaskie.BuildConfig
import pl.mosenko.sunnypodlaskie.mvp.setting.SettingsContract
import pl.mosenko.sunnypodlaskie.mvp.setting.SettingsPresenterImpl
import pl.mosenko.sunnypodlaskie.mvp.weatherdatadetail.WeatherDataDetailViewModel
import pl.mosenko.sunnypodlaskie.mvp.weatherdatalist.WeatherDataListContract
import pl.mosenko.sunnypodlaskie.mvp.weatherdatalist.WeatherDataListPresenterImpl
import pl.mosenko.sunnypodlaskie.network.api.RxWeatherDataApi
import pl.mosenko.sunnypodlaskie.network.api.WeatherDataAPI
import pl.mosenko.sunnypodlaskie.persistence.WeatherDataDatabase
import pl.mosenko.sunnypodlaskie.repository.WeatherDataRepository
import pl.mosenko.sunnypodlaskie.util.WeatherAPIKeyProvider
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASEURL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single<WeatherDataAPI> { get<Retrofit>().create(WeatherDataAPI::class.java) }
    single { WeatherAPIKeyProvider() }
    single { MerlinsBeard.from(get()) }
    single { RxWeatherDataApi(get(), get()) }
}

val databaseModule = module {
    single { createWeatherDataDb(get()) }
    single { get<WeatherDataDatabase>().weatherDataDao() }
}

private fun createWeatherDataDb(context: Context) = Room.databaseBuilder(
    context.applicationContext,
    WeatherDataDatabase::class.java,
    "weather_data.db"
).build()

val repositoryModule = module {
    single { WeatherDataRepository(get(), get(), get()) }
}

val presenterModules = module {
    factory<WeatherDataListContract.Presenter> { WeatherDataListPresenterImpl(get(), get()) }
    factory<SettingsContract.Presenter> { SettingsPresenterImpl() }
}

val viewModelModels = module {
    viewModel { WeatherDataDetailViewModel(get()) }
}

val appModules =
    listOf(networkModule, databaseModule, repositoryModule, presenterModules, viewModelModels)


