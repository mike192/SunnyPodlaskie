package pl.mosenko.sunnypodlaskie.di.component;

import javax.inject.Singleton;

import dagger.Component;
import pl.mosenko.sunnypodlaskie.mvp.weatherdatalist.MainActivity;
import pl.mosenko.sunnypodlaskie.mvp.weatherdatadetail.WeatherDataDetailFragment;
import pl.mosenko.sunnypodlaskie.mvp.weatherdatalist.WeatherDataListFragment;
import pl.mosenko.sunnypodlaskie.di.module.ContextModule;
import pl.mosenko.sunnypodlaskie.di.module.DatabaseModule;
import pl.mosenko.sunnypodlaskie.di.module.NetworkModule;
import pl.mosenko.sunnypodlaskie.service.WeatherDataSyncJobService;

/**
 * Created by syk on 13.05.17.
 */

@Singleton
@Component(modules = {NetworkModule.class, ContextModule.class, DatabaseModule.class})
public interface DIComponent {
    void inject(MainActivity mainActivity);
    void inject(WeatherDataListFragment weatherDataListFragment);
    void inject(WeatherDataDetailFragment weatherDataDetailFragment);
    void inject(WeatherDataSyncJobService weatherDataSyncJobService);
}
