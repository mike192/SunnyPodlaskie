package pl.mosenko.sunnypodlaskie.component;

import javax.inject.Singleton;

import dagger.Component;
import pl.mosenko.sunnypodlaskie.MainActivity;
import pl.mosenko.sunnypodlaskie.WeatherDataDetailFragment;
import pl.mosenko.sunnypodlaskie.WeatherDataListFragment;
import pl.mosenko.sunnypodlaskie.module.ContextModule;
import pl.mosenko.sunnypodlaskie.module.DatabaseModule;
import pl.mosenko.sunnypodlaskie.module.NetworkModule;

/**
 * Created by syk on 13.05.17.
 */

@Singleton
@Component(modules = {NetworkModule.class, ContextModule.class, DatabaseModule.class})
public interface DIComponent {
    void inject(MainActivity mainActivity);
    void inject(WeatherDataListFragment weatherDataListFragment);
    void inject(WeatherDataDetailFragment weatherDataDetailFragment);
}
