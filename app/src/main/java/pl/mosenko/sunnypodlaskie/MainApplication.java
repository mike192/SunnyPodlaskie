package pl.mosenko.sunnypodlaskie;

import android.app.Application;

import pl.mosenko.sunnypodlaskie.component.DaggerMainActivityComponent;
import pl.mosenko.sunnypodlaskie.component.MainActivityComponent;
import pl.mosenko.sunnypodlaskie.module.ContextModule;
import pl.mosenko.sunnypodlaskie.module.DatabaseModule;
import pl.mosenko.sunnypodlaskie.module.NetworkModule;


/**
 * Created by syk on 13.05.17.
 */

public class MainApplication extends Application {
    private MainActivityComponent mainActivityComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        buildComponent();
    }

    private void buildComponent() {
        mainActivityComponent = DaggerMainActivityComponent.builder()
                .contextModule(new ContextModule(getApplicationContext()))
                .networkModule(new NetworkModule())
                .databaseModule(new DatabaseModule())
                .build();
    }

    public MainActivityComponent getMainActivityComponent() {
        return mainActivityComponent;
    }
}
