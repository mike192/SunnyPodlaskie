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
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity;
import pl.mosenko.sunnypodlaskie.repository.WeatherDataRepository;

/**
 * Created by syk on 03.06.17.
 */

public class WeatherDataListPresenterImpl extends MvpBasePresenter<WeatherDataListContract.View> implements WeatherDataListContract.Presenter, WeatherDataRepository.Callback {
    private static final String TAG = WeatherDataListPresenterImpl.class.getSimpleName();

    @Inject
    WeatherDataRepository mWeatherDataRepository;
    @Inject
    MerlinsBeard mMerlinsBeard;

    private CompositeDisposable mRepositoryDisposable;
    private Disposable mInternetSubscription;


    public WeatherDataListPresenterImpl() {
        injectFields();
        initializeCompositeDisposable();
    }

    private void injectFields() {
        ApplicationPodlaskieWeather.sharedApplication().getDIComponent().inject(this);
    }

    private void initializeCompositeDisposable() {
        mRepositoryDisposable = new CompositeDisposable();
    }

    @Override
    public void onResume() {
        observeInternetConnectivity();
    }

    private void observeInternetConnectivity() {
        mInternetSubscription = ReactiveNetwork.observeInternetConnectivity()
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
        if (mInternetSubscription != null && mInternetSubscription.isDisposed()) {
            mInternetSubscription.dispose();
        }
    }

    @Override
    public void loadData(boolean pullToRefresh) {
      loadData(pullToRefresh, mMerlinsBeard.isConnected());
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
        Disposable disposable = mWeatherDataRepository.loadCurrentWeatherData(isConnectedToInternet, this);
        mRepositoryDisposable.add(disposable);
    }

    private void safelyDisposeRepositorySubscription() {
        if (mRepositoryDisposable != null && mRepositoryDisposable.isDisposed()) {
            mRepositoryDisposable.dispose();
        }
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        safelyDisposeRepositorySubscription();
    }

    @Override
    public void onNextWeatherDataEntities(List<WeatherDataEntity> weatherDataEntityList, boolean isConnectedToInternet) {
        for (WeatherDataEntity weatherDataEntity : weatherDataEntityList) {
            Log.d(TAG, weatherDataEntity.toString());
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
