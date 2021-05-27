package pl.mosenko.sunnypodlaskie.di.component

import dagger.Component
import pl.mosenko.sunnypodlaskie.di.module.ContextModule
import pl.mosenko.sunnypodlaskie.di.module.DatabaseModule
import pl.mosenko.sunnypodlaskie.di.module.NetworkModule
import pl.mosenko.sunnypodlaskie.di.module.RepositoryModule
import pl.mosenko.sunnypodlaskie.mvp.weatherdatadetail.WeatherDataDetailPresenterImpl
import pl.mosenko.sunnypodlaskie.mvp.weatherdatalist.WeatherDataListPresenterImpl
import pl.mosenko.sunnypodlaskie.service.WeatherDataSyncJobService
import javax.inject.Singleton

/**
 * Created by syk on 13.05.17.
 */
@Singleton
@Component(modules = [NetworkModule::class, ContextModule::class, DatabaseModule::class, RepositoryModule::class])
interface DIComponent {
    open fun inject(weatherDataSyncJobService: WeatherDataSyncJobService?)
    open fun inject(weatherDataListPresenter: WeatherDataListPresenterImpl?)
    open fun inject(weatherDataDetailPresenter: WeatherDataDetailPresenterImpl?)
}
