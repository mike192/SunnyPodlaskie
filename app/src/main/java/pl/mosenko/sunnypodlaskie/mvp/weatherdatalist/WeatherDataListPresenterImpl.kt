package pl.mosenko.sunnypodlaskie.mvp.weatherdatalist

import android.util.Log
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import com.novoda.merlin.MerlinsBeard
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import pl.mosenko.sunnypodlaskie.BuildConfig
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity
import pl.mosenko.sunnypodlaskie.repository.WeatherDataRepository

/**
 * Created by syk on 03.06.17.
 */
class WeatherDataListPresenterImpl(
        var weatherDataRepository: WeatherDataRepository,
        var merlinsBeard: MerlinsBeard
) : MvpBasePresenter<WeatherDataListContract.View>(), WeatherDataListContract.Presenter, WeatherDataRepository.Callback {

    private var repositoryDisposable: CompositeDisposable = CompositeDisposable()
    private var internetSubscription: Disposable? = null

    override fun onResume() {
        observeInternetConnectivity()
    }

    private fun observeInternetConnectivity() {
        internetSubscription = ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { isConnectedToInternet: Boolean -> loadData(false, isConnectedToInternet) }
    }

    override fun onPause() {
        safelyDisposeInternetSubscription()
    }

    private fun safelyDisposeInternetSubscription() {
        if (internetSubscription?.isDisposed == true) {
            internetSubscription!!.dispose()
        }
    }

    override fun loadData(pullToRefresh: Boolean) {
        loadData(pullToRefresh, merlinsBeard.isConnected)
    }

    private fun loadData(pullToRefresh: Boolean, isConnectedToInternet: Boolean) {
        if (!isViewNotNullAttached()) {
            return
        }
        view.showLoading(pullToRefresh)
        safelyDisposeRepositorySubscription()
        loadCurrentWeatherData(isConnectedToInternet)
    }

    private fun loadCurrentWeatherData(isConnectedToInternet: Boolean) {
        val disposable = weatherDataRepository.loadCurrentWeatherData(isConnectedToInternet, this)
        repositoryDisposable.add(disposable)
    }

    private fun safelyDisposeRepositorySubscription() {
        if (repositoryDisposable.isDisposed) {
            repositoryDisposable.dispose()
        }
    }

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        safelyDisposeRepositorySubscription()
    }

    override fun onNextWeatherDataEntities(weatherDataEntityList: MutableList<WeatherDataEntity>, isConnectedToInternet: Boolean) {
        if (BuildConfig.DEBUG) {
            logFetchedData(weatherDataEntityList)
        }
        if (!isViewNotNullAttached()) {
            return
        }
        view.setData(weatherDataEntityList)
        if (weatherDataEntityList.isEmpty()) {
            view.showEmpty()
        } else {
            view.showContent()
        }
        if (!isConnectedToInternet) {
            view.showDataWithoutInternetUpdatedMessage()
        }
    }

    private fun logFetchedData(weatherDataEntityList: MutableList<WeatherDataEntity>) {
        for (weatherDataEntity in weatherDataEntityList) {
            Log.d(TAG, weatherDataEntity.toString())
        }
    }

    override fun onError(throwable: Throwable) {
        if (isViewNotNullAttached()) {
            view.showError(throwable)
        }
    }

    private fun isViewNotNullAttached(): Boolean {
        return view != null && isViewAttached
    }

    companion object {
        private val TAG = WeatherDataListPresenterImpl::class.java.simpleName
    }
}
