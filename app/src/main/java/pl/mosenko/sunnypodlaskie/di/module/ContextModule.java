package pl.mosenko.sunnypodlaskie.di.module;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by syk on 18.05.17.
 */

@Module
public class ContextModule {

    private Context context;

    public ContextModule(@NonNull Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    public Context provideContext() {
        return context;
    }
}

