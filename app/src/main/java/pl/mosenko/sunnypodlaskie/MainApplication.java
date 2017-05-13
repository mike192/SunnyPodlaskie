package pl.mosenko.sunnypodlaskie;

import android.app.Application;

import pl.mosenko.sunnypodlaskie.component.DaggerMainActivityComponent;
import pl.mosenko.sunnypodlaskie.component.MainActivityComponent;

/**
 * Created by syk on 13.05.17.
 */

public class MainApplication extends Application {
    private MainActivityComponent mainActivityComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mainActivityComponent = DaggerMainActivityComponent.builder().build();
    }

    public MainActivityComponent getMainActivityComponent() {
        return mainActivityComponent;
    }
}
