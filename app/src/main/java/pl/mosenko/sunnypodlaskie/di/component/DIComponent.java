package pl.mosenko.sunnypodlaskie.di.component;

import javax.inject.Singleton;

import dagger.Component;
import pl.mosenko.sunnypodlaskie.di.module.RepositoryModule;
import pl.mosenko.sunnypodlaskie.mvp.weatherdatalist.WeatherDataListActivity;
import pl.mosenko.sunnypodlaskie.mvp.weatherdatadetail.WeatherDataDetailFragment;
import pl.mosenko.sunnypodlaskie.mvp.weatherdatalist.FragmentWeatherDataList;
import pl.mosenko.sunnypodlaskie.di.module.ContextModule;
import pl.mosenko.sunnypodlaskie.di.module.DatabaseModule;
import pl.mosenko.sunnypodlaskie.di.module.NetworkModule;
import pl.mosenko.sunnypodlaskie.mvp.weatherdatalist.WeatherDataListPresenterImpl;
import pl.mosenko.sunnypodlaskie.service.WeatherDataSyncJobService;

/**
 * Created by syk on 13.05.17.
 */

@Singleton
@Component(modules = {NetworkModule.class, ContextModule.class, DatabaseModule.class, RepositoryModule.class})
public interface DIComponent {
    void inject(WeatherDataListActivity weatherDataListActivity);
    void inject(FragmentWeatherDataList fragmentWeatherDataList);
    void inject(WeatherDataDetailFragment weatherDataDetailFragment);
    void inject(WeatherDataSyncJobService weatherDataSyncJobService);
    void inject(WeatherDataListPresenterImpl weatherDataListPresenter);
}
