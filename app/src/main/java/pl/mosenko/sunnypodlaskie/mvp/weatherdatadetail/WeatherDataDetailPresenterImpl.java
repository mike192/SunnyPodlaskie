package pl.mosenko.sunnypodlaskie.mvp.weatherdatadetail;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pl.mosenko.sunnypodlaskie.ApplicationPodlaskieWeather;
import pl.mosenko.sunnypodlaskie.persistence.dao.WeatherDataEntityDAO;
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity;
import pl.mosenko.sunnypodlaskie.util.WeatherDataDetailPresentationModelConverter;

import static pl.mosenko.sunnypodlaskie.mvp.weatherdatadetail.WeatherDataDetailFragment.ARG_WEATHER_DATA_ID;

/**
 * Created by syk on 06.06.17.
 */

public class WeatherDataDetailPresenterImpl extends MvpBasePresenter<WeatherDataDetailContract.View> implements  WeatherDataDetailContract.Presenter{

    @Inject
    WeatherDataEntityDAO weatherDataEntityDAO;
    private CompositeDisposable compositeDisposable;

    public WeatherDataDetailPresenterImpl() {
        injectFields();
        initializeCompositeDisposable();
    }

    private void injectFields() {
        ApplicationPodlaskieWeather.sharedApplication().getDIComponent().inject(this);
    }

    private void initializeCompositeDisposable() {
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onViewCreated(Bundle savedInstanceState) {
            if (savedInstanceState.containsKey(ARG_WEATHER_DATA_ID)) {
                Long weatherDataId = savedInstanceState.getLong(ARG_WEATHER_DATA_ID);
                queryForWeatherDataById(weatherDataId);
            }
    }

    private void queryForWeatherDataById(@NonNull Long weatherDataId) {
        if (!isViewNotNullAttached()) {
         return;
        }
        safelyDisposeRepositorySubscription();
        Disposable disposable = weatherDataEntityDAO.rxQueryForId(weatherDataId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(this::formatWeatherDataEntityToDisplay)
                .subscribe(getView()::loadData, getView()::showError);
        compositeDisposable.add(disposable);
    }

    private boolean isViewNotNullAttached() {
        return getView() != null && isViewAttached();
    }

    private WeatherDataDetailPresentationModel formatWeatherDataEntityToDisplay(WeatherDataEntity weatherDataEntity) {
        return WeatherDataDetailPresentationModelConverter.convert(weatherDataEntity);
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        safelyDisposeRepositorySubscription();
    }

    private void safelyDisposeRepositorySubscription() {
        if (compositeDisposable != null && compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}
