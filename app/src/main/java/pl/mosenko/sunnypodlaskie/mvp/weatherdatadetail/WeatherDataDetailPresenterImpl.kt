package pl.mosenko.sunnypodlaskie.mvp.weatherdatadetail

import android.os.Bundle
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.runBlocking
import pl.mosenko.sunnypodlaskie.persistence.dao.WeatherDataEntityDao
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity
import pl.mosenko.sunnypodlaskie.util.WeatherDataDetailPresentationModelConverter

/**
 * Created by syk on 06.06.17.
 */
class WeatherDataDetailPresenterImpl(
    var weatherDataEntityDao: WeatherDataEntityDao,
) : MvpBasePresenter<WeatherDataDetailContract.View>(), WeatherDataDetailContract.Presenter {

    private var compositeDisposable: CompositeDisposable? = null

    private fun initializeCompositeDisposable() {
        compositeDisposable = CompositeDisposable()
    }

    override fun onViewCreated(savedInstanceState: Bundle) {
        if (savedInstanceState.containsKey(WeatherDataDetailFragment.ARG_WEATHER_DATA_ID)) {
            val weatherDataId = savedInstanceState.getLong(WeatherDataDetailFragment.ARG_WEATHER_DATA_ID)
            queryForWeatherDataById(weatherDataId)
        }
    }

    private fun queryForWeatherDataById(weatherDataId: Long) {
        if (!isViewNotNullAttached()) {
            return
        }
        safelyDisposeRepositorySubscription()
        val disposable =
            Observable.fromCallable {
                runBlocking {
                    weatherDataEntityDao.getWeatherDataById(weatherDataId)
                }
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { weatherDataEntity: WeatherDataEntity -> formatWeatherDataEntityToDisplay(weatherDataEntity) }
                .subscribe({ weatherDataDetailPresentationModel: WeatherDataDetailPresentationModel -> view.loadData(weatherDataDetailPresentationModel) }) { throwable: Throwable -> view.showError(throwable) }
        compositeDisposable?.add(disposable)
    }

    private fun isViewNotNullAttached(): Boolean {
        return view != null && isViewAttached
    }

    private fun formatWeatherDataEntityToDisplay(weatherDataEntity: WeatherDataEntity): WeatherDataDetailPresentationModel {
        return WeatherDataDetailPresentationModelConverter.convert(weatherDataEntity)
    }

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        safelyDisposeRepositorySubscription()
    }

    private fun safelyDisposeRepositorySubscription() {
        if (compositeDisposable?.isDisposed == true) {
            compositeDisposable!!.dispose()
        }
    }

    init {
        initializeCompositeDisposable()
    }
}
