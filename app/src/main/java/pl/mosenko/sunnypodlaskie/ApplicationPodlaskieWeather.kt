package pl.mosenko.sunnypodlaskie

import android.app.Application
import pl.mosenko.sunnypodlaskie.di.component.DIComponent
import pl.mosenko.sunnypodlaskie.di.component.DaggerDIComponent
import pl.mosenko.sunnypodlaskie.di.module.ContextModule
import pl.mosenko.sunnypodlaskie.di.module.DatabaseModule
import pl.mosenko.sunnypodlaskie.di.module.NetworkModule
import pl.mosenko.sunnypodlaskie.util.WeatherDataAlarmSyncUtil
import java.util.*

/**
 * Created by syk on 13.05.17.
 */
class ApplicationPodlaskieWeather : Application() {
    private var mDIComponent: DIComponent? = null
    override fun onCreate() {
        super.onCreate()
        initializeSharedApplication()
        setDefaultLocale()
        buildComponent()
        setupAlarmOnCreateApp()
    }

    private fun setupAlarmOnCreateApp() {
        WeatherDataAlarmSyncUtil.setupAlarmOnCreateApp(applicationContext)
    }

    private fun initializeSharedApplication() {
        sharedApplication = this
    }

    private fun setDefaultLocale() {
        Locale.setDefault(Locale("pl"))
    }

    private fun buildComponent() {
        mDIComponent = DaggerDIComponent.builder()
                .contextModule(ContextModule(applicationContext))
                .networkModule(NetworkModule())
                .databaseModule(DatabaseModule())
                .build()
    }

    override fun onTerminate() {
        super.onTerminate()
        sharedApplication = null
    }

    fun getDIComponent(): DIComponent? {
        return mDIComponent
    }

    companion object {
        private var sharedApplication: ApplicationPodlaskieWeather? = null
        fun sharedApplication(): ApplicationPodlaskieWeather? {
            return sharedApplication
        }
    }
}
