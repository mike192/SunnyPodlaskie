package pl.mosenko.sunnypodlaskie;

import android.app.Application;

import java.util.Locale;

import pl.mosenko.sunnypodlaskie.di.component.DIComponent;
import pl.mosenko.sunnypodlaskie.di.component.DaggerDIComponent;
import pl.mosenko.sunnypodlaskie.di.module.ContextModule;
import pl.mosenko.sunnypodlaskie.di.module.DatabaseModule;
import pl.mosenko.sunnypodlaskie.di.module.NetworkModule;
import pl.mosenko.sunnypodlaskie.util.WeatherDataAlarmSyncUtil;


/**
 * Created by syk on 13.05.17.
 */

public class ApplicationPodlaskieWeather extends Application {
    private static ApplicationPodlaskieWeather sSharedApplication;
    private DIComponent mDIComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeSharedApplication();
        setDefaultLocale();
        buildComponent();
        setupAlarmOnCreateApp();
    }

    private void setupAlarmOnCreateApp() {
        WeatherDataAlarmSyncUtil.setupAlarmOnCreateApp(getApplicationContext());
    }

    private void initializeSharedApplication() {
        sSharedApplication = this;
    }

    private void setDefaultLocale() {
        Locale.setDefault(new Locale("pl"));
    }

    private void buildComponent() {
        mDIComponent = DaggerDIComponent.builder()
                .contextModule(new ContextModule(getApplicationContext()))
                .networkModule(new NetworkModule())
                .databaseModule(new DatabaseModule())
                .build();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        sSharedApplication = null;
    }

    public static ApplicationPodlaskieWeather sharedApplication() {
        return sSharedApplication;
    }

    public DIComponent getDIComponent() {
        return mDIComponent;
    }
}
