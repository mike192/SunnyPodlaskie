package pl.mosenko.sunnypodlaskie.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.mosenko.sunnypodlaskie.network.api.RxWeatherDataAPI;
import pl.mosenko.sunnypodlaskie.persistence.dao.WeatherDataEntityDAO;
import pl.mosenko.sunnypodlaskie.repository.WeatherDataRepository;

/**
 * Created by syk on 03.06.17.
 */

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    public WeatherDataRepository provideWeatherDataRepository(RxWeatherDataAPI rxWeatherDataAPI, WeatherDataEntityDAO weatherDataEntityDAO) {
        return new WeatherDataRepository(rxWeatherDataAPI, weatherDataEntityDAO);
    }
}
