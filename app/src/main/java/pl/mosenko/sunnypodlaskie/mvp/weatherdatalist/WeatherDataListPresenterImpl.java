package pl.mosenko.sunnypodlaskie.mvp.weatherdatalist;

import android.util.Log;

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.novoda.merlin.MerlinsBeard;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pl.mosenko.sunnypodlaskie.ApplicationPodlaskieWeather;
import pl.mosenko.sunnypodlaskie.BuildConfig;
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity;
import pl.mosenko.sunnypodlaskie.repository.WeatherDataRepository;

/**
 * Created by syk on 03.06.17.
 */

public class WeatherDataListPresenterImpl extends MvpBasePresenter<WeatherDataListContract.View> implements WeatherDataListContract.Presenter, WeatherDataRepository.Callback {

    private static final String TAG = WeatherDataListPresenterImpl.class.getSimpleName();
    @Inject
    WeatherDataRepository weatherDataRepository;
    @Inject
    MerlinsBeard merlinsBeard;
    private CompositeDisposable repositoryDisposable;
    private Disposable internetSubscription;


    public WeatherDataListPresenterImpl() {
        injectFields();
        initializeCompositeDisposable();
    }

    private void injectFields() {
        ApplicationPodlaskieWeather.sharedApplication().getDIComponent().inject(this);
    }

    private void initializeCompositeDisposable() {
        repositoryDisposable = new CompositeDisposable();
    }

    @Override
    public void onResume() {
        observeInternetConnectivity();
    }

    private void observeInternetConnectivity() {
        internetSubscription = ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isConnectedToInternet -> {
                    loadData(false, isConnectedToInternet);
                });
    }

    @Override
    public void onPause() {
        safelyDisposeInternetSubscription();
    }

    private void safelyDisposeInternetSubscription() {
        if (internetSubscription != null && internetSubscription.isDisposed()) {
            internetSubscription.dispose();
        }
    }

    @Override
    public void loadData(boolean pullToRefresh) {
      loadData(pullToRefresh, merlinsBeard.isConnected());
    }

    private void loadData(boolean pullToRefresh, boolean isConnectedToInternet) {
        if (!isViewNotNullAttached()) {
            return;
        }
        getView().showLoading(pullToRefresh);
        safelyDisposeRepositorySubscription();
        loadCurrentWeatherData(isConnectedToInternet);
    }

    private void loadCurrentWeatherData(boolean isConnectedToInternet) {
        Disposable disposable = weatherDataRepository.loadCurrentWeatherData(isConnectedToInternet, this);
        repositoryDisposable.add(disposable);
    }

    private void safelyDisposeRepositorySubscription() {
        if (repositoryDisposable != null && repositoryDisposable.isDisposed()) {
            repositoryDisposable.dispose();
        }
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        safelyDisposeRepositorySubscription();
    }

    @Override
    public void onNextWeatherDataEntities(List<WeatherDataEntity> weatherDataEntityList, boolean isConnectedToInternet) {
        if (BuildConfig.DEBUG) {
            logFetchedData(weatherDataEntityList);
        }
        if (!isViewNotNullAttached()) {
            return;
        }
        getView().setData(weatherDataEntityList);
        if (weatherDataEntityList == null || weatherDataEntityList.isEmpty()) {
            getView().showEmpty();
        } else {
            getView().showContent();
        }
        if (!isConnectedToInternet) {
            getView().showDataWithoutInternetUpdatedMessage();
        }
    }

    private void logFetchedData(List<WeatherDataEntity> weatherDataEntityList) {
        for (WeatherDataEntity weatherDataEntity : weatherDataEntityList) {
            Log.d(TAG, weatherDataEntity.toString());
        }
    }

    @Override
    public void onError(Throwable throwable) {
        if (isViewNotNullAttached()) {
            getView().showError(throwable);
        }
    }

    private boolean isViewNotNullAttached() {
        return getView() != null && isViewAttached();
    }
}
