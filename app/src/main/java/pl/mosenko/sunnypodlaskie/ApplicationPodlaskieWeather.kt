package pl.mosenko.sunnypodlaskie

import androidx.multidex.MultiDexApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import pl.mosenko.sunnypodlaskie.di.appModules
import pl.mosenko.sunnypodlaskie.util.WeatherDataAlarmSyncUtil
import java.util.*

/**
 * Created by syk on 13.05.17.
 */
class ApplicationPodlaskieWeather : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
        setDefaultLocale()
        setupAlarmOnCreateApp()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@ApplicationPodlaskieWeather)
            modules(appModules)
        }
    }

    private fun setupAlarmOnCreateApp() {
        WeatherDataAlarmSyncUtil.setupAlarmOnCreateApp(applicationContext)
    }

    private fun setDefaultLocale() {
        Locale.setDefault(Locale("pl"))
    }
}
