package pl.mosenko.sunnypodlaskie

import androidx.multidex.MultiDexApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import pl.mosenko.sunnypodlaskie.di.appModules
import java.util.*

/**
 * Created by syk on 13.05.17.
 */
class ApplicationPodlaskieWeather : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
        setDefaultLocale()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@ApplicationPodlaskieWeather)
            modules(appModules)
        }
    }

    private fun setDefaultLocale() {
        Locale.setDefault(Locale("pl"))
    }
}
