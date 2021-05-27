package pl.mosenko.sunnypodlaskie.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by syk on 18.05.17.
 */
@Module
class ContextModule(context: Context) {
    private val context: Context?
    @Singleton
    @Provides
    fun provideContext(): Context? {
        return context
    }

    init {
        this.context = context
    }
}
