package pl.mosenko.sunnypodlaskie.di

import android.util.Log
import com.j256.ormlite.android.DatabaseTableConfigUtil
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.novoda.merlin.MerlinsBeard
import org.koin.dsl.module
import pl.mosenko.sunnypodlaskie.BuildConfig
import pl.mosenko.sunnypodlaskie.mvp.setting.SettingsContract
import pl.mosenko.sunnypodlaskie.mvp.setting.SettingsPresenterImpl
import pl.mosenko.sunnypodlaskie.mvp.weatherdatadetail.WeatherDataDetailContract
import pl.mosenko.sunnypodlaskie.mvp.weatherdatadetail.WeatherDataDetailPresenterImpl
import pl.mosenko.sunnypodlaskie.mvp.weatherdatalist.WeatherDataListContract
import pl.mosenko.sunnypodlaskie.mvp.weatherdatalist.WeatherDataListPresenterImpl
import pl.mosenko.sunnypodlaskie.network.api.RxWeatherDataAPI
import pl.mosenko.sunnypodlaskie.network.api.WeatherDataAPI
import pl.mosenko.sunnypodlaskie.persistence.DatabaseHelperPodlaskieWeather
import pl.mosenko.sunnypodlaskie.persistence.dao.WeatherConditionEntityDAO
import pl.mosenko.sunnypodlaskie.persistence.dao.WeatherDataEntityDAO
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherConditionEntity
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity
import pl.mosenko.sunnypodlaskie.repository.WeatherDataRepository
import pl.mosenko.sunnypodlaskie.util.WeatherAPIKeyProvider
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.sql.SQLException

const val TAG = "AppModules"

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
    single { RxWeatherDataAPI(get(), get()) }
}

val databaseModule = module {
    single { DatabaseHelperPodlaskieWeather(get()) }
    single { provideWeatherDataEntityDAO(get()) }
    single { provideWeatherConditionEntityDAO(get()) }
}

val repositoryModule = module {
    single { WeatherDataRepository(get(), get(), get()) }
}

val presenterModules = module {
    factory<WeatherDataDetailContract.Presenter> { WeatherDataDetailPresenterImpl(get()) }
    factory<WeatherDataListContract.Presenter> { WeatherDataListPresenterImpl(get(), get()) }
    factory<SettingsContract.Presenter> { SettingsPresenterImpl() }
}

private fun provideWeatherDataEntityDAO(databaseHelperPodlaskieWeather: DatabaseHelperPodlaskieWeather): WeatherDataEntityDAO {
    return try {
        val connectionSource = databaseHelperPodlaskieWeather.connectionSource
        val tableConfig =
            DatabaseTableConfigUtil.fromClass(connectionSource, WeatherDataEntity::class.java)
        tableConfig?.let { WeatherDataEntityDAO(connectionSource, it) }
            ?: WeatherDataEntityDAO(connectionSource)
    } catch (e: SQLException) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, e.message, e)
        }
        throw IllegalStateException("WeatherDataEntityDAO creation error!")
    }
}

private fun provideWeatherConditionEntityDAO(databaseHelperPodlaskieWeather: DatabaseHelperPodlaskieWeather): WeatherConditionEntityDAO {
    return try {
        val connectionSource = databaseHelperPodlaskieWeather.connectionSource
        val tableConfig =
            DatabaseTableConfigUtil.fromClass(connectionSource, WeatherConditionEntity::class.java)
        tableConfig?.let { WeatherConditionEntityDAO(connectionSource, it) }
            ?: WeatherConditionEntityDAO(connectionSource)
    } catch (e: SQLException) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, e.message, e)
        }
        throw IllegalStateException("WeatherConditionEntityDAO creation error!")
    }
}

val appModules = listOf(networkModule, databaseModule, repositoryModule, presenterModules)


