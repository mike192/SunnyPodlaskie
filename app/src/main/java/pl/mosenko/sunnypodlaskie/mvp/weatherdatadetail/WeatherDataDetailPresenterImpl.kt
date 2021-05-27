package pl.mosenko.sunnypodlaskie.mvp.weatherdatadetail

import android.os.Bundle
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import pl.mosenko.sunnypodlaskie.ApplicationPodlaskieWeather
import pl.mosenko.sunnypodlaskie.persistence.dao.WeatherDataEntityDAO
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity
import pl.mosenko.sunnypodlaskie.util.WeatherDataDetailPresentationModelConverter
import javax.inject.Inject

/**
 * Created by syk on 06.06.17.
 */
class WeatherDataDetailPresenterImpl : MvpBasePresenter<WeatherDataDetailContract.View?>(), WeatherDataDetailContract.Presenter {
    @kotlin.jvm.JvmField
    @Inject
    var weatherDataEntityDAO: WeatherDataEntityDAO? = null
    private var compositeDisposable: CompositeDisposable? = null
    private fun injectFields() {
        ApplicationPodlaskieWeather.Companion.sharedApplication().getDIComponent().inject(this)
    }

    private fun initializeCompositeDisposable() {
        compositeDisposable = CompositeDisposable()
    }

    override fun onViewCreated(savedInstanceState: Bundle?) {
        if (savedInstanceState.containsKey(WeatherDataDetailFragment.Companion.ARG_WEATHER_DATA_ID)) {
            val weatherDataId = savedInstanceState.getLong(WeatherDataDetailFragment.Companion.ARG_WEATHER_DATA_ID)
            queryForWeatherDataById(weatherDataId)
        }
    }

    private fun queryForWeatherDataById(weatherDataId: Long) {
        if (!isViewNotNullAttached()) {
            return
        }
        safelyDisposeRepositorySubscription()
        val disposable = weatherDataEntityDAO.rxQueryForId(weatherDataId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { weatherDataEntity: WeatherDataEntity? -> formatWeatherDataEntityToDisplay(weatherDataEntity) }
                .subscribe({ weatherDataDetailPresentationModel: WeatherDataDetailPresentationModel? -> view.loadData(weatherDataDetailPresentationModel) }) { throwable: Throwable? -> view.showError(throwable) }
        compositeDisposable.add(disposable)
    }

    private fun isViewNotNullAttached(): Boolean {
        return view != null && isViewAttached
    }

    private fun formatWeatherDataEntityToDisplay(weatherDataEntity: WeatherDataEntity?): WeatherDataDetailPresentationModel? {
        return WeatherDataDetailPresentationModelConverter.convert(weatherDataEntity)
    }

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        safelyDisposeRepositorySubscription()
    }

    private fun safelyDisposeRepositorySubscription() {
        if (compositeDisposable != null && compositeDisposable.isDisposed()) {
            compositeDisposable.dispose()
        }
    }

    init {
        injectFields()
        initializeCompositeDisposable()
    }
}
