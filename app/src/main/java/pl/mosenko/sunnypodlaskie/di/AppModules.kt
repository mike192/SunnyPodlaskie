package pl.mosenko.sunnypodlaskie.di

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.mosenko.sunnypodlaskie.BuildConfig
import pl.mosenko.sunnypodlaskie.ui.setting.SettingsViewModel
import pl.mosenko.sunnypodlaskie.ui.weatherdatadetail.WeatherDataDetailViewModel
import pl.mosenko.sunnypodlaskie.ui.weatherdatalist.WeatherDataListViewModel
import pl.mosenko.sunnypodlaskie.api.DefaultWeatherDataApi
import pl.mosenko.sunnypodlaskie.api.WeatherDataApi
import pl.mosenko.sunnypodlaskie.persistence.WeatherDataDatabase
import pl.mosenko.sunnypodlaskie.repository.WeatherDataRepository
import pl.mosenko.sunnypodlaskie.util.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val networkModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single<WeatherDataApi> { get<Retrofit>().create(WeatherDataApi::class.java) }
    single { WeatherApiKeyProvider() }
    single { DefaultWeatherDataApi(get(), get()) }
    single { ConnectivityUtil(get()) }
}

private val databaseModule = module {
    single { createWeatherDataDb(get()) }
    single { get<WeatherDataDatabase>().weatherDataDao() }
}

private fun createWeatherDataDb(context: Context) = Room.databaseBuilder(
    context.applicationContext,
    WeatherDataDatabase::class.java,
    "weather_data.db"
).build()

private val repositoryModule = module {
    single { WeatherDataRepository(get(), get(), get(), get()) }
}

private val viewModelModels = module {
    viewModel { WeatherDataDetailViewModel(get()) }
    viewModel { WeatherDataListViewModel(get(), get()) }
    viewModel { SettingsViewModel(get(), get(), get()) }
}

private val utilsModule = module {
    factory { WeatherNotificationUtil(get()) }
    factory { WeatherPreferenceUtil(get()) }
    factory { WeatherAlarmSyncUtil(get(), get()) }
    factory { WeatherDtoEntityConverter() }
    factory { WeatherDataJobSyncUtils() }
}

val appModules =
    listOf(networkModule, databaseModule, repositoryModule, viewModelModels, utilsModule)


