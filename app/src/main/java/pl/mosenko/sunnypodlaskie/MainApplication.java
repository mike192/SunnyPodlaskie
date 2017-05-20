package pl.mosenko.sunnypodlaskie;

import android.app.Application;

import pl.mosenko.sunnypodlaskie.component.DIComponent;
import pl.mosenko.sunnypodlaskie.component.DaggerDIComponent;
import pl.mosenko.sunnypodlaskie.module.ContextModule;
import pl.mosenko.sunnypodlaskie.module.DatabaseModule;
import pl.mosenko.sunnypodlaskie.module.NetworkModule;


/**
 * Created by syk on 13.05.17.
 */

public class MainApplication extends Application {
    private DIComponent mainActivityComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        buildComponent();
    }

    private void buildComponent() {
        mainActivityComponent = DaggerDIComponent.builder()
                .contextModule(new ContextModule(getApplicationContext()))
                .networkModule(new NetworkModule())
                .databaseModule(new DatabaseModule())
                .build();
    }

    public DIComponent getMainActivityComponent() {
        return mainActivityComponent;
    }
}
