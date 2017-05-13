package pl.mosenko.sunnypodlaskie.component;

import javax.inject.Singleton;

import dagger.Component;
import pl.mosenko.sunnypodlaskie.MainActivity;
import pl.mosenko.sunnypodlaskie.network.NetworkModule;

/**
 * Created by syk on 13.05.17.
 */

@Singleton
@Component(modules = {NetworkModule.class})
public interface MainActivityComponent {
    void inject(MainActivity mainActivity);
}
