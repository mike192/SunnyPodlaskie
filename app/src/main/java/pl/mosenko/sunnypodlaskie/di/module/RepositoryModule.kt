package pl.mosenko.sunnypodlaskie.di.module

import dagger.Module
import dagger.Provides
import pl.mosenko.sunnypodlaskie.network.api.RxWeatherDataAPI
import pl.mosenko.sunnypodlaskie.persistence.dao.WeatherConditionEntityDAO
import pl.mosenko.sunnypodlaskie.persistence.dao.WeatherDataEntityDAO
import pl.mosenko.sunnypodlaskie.repository.WeatherDataRepository
import javax.inject.Singleton

/**
 * Created by syk on 03.06.17.
 */
@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideWeatherDataRepository(rxWeatherDataAPI: RxWeatherDataAPI?, weatherDataEntityDAO: WeatherDataEntityDAO?, weatherConditionEntities: WeatherConditionEntityDAO?): WeatherDataRepository? {
        return WeatherDataRepository(rxWeatherDataAPI, weatherDataEntityDAO, weatherConditionEntities)
    }
}
