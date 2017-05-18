package pl.mosenko.sunnypodlaskie;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pl.mosenko.sunnypodlaskie.dto.List;
import pl.mosenko.sunnypodlaskie.dto.WeatherData;
import pl.mosenko.sunnypodlaskie.network.RxWeatherDataAPI;
import pl.mosenko.sunnypodlaskie.persistence.dao.WeatherDataEntityDAO;
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity;
import pl.mosenko.sunnypodlaskie.util.WeatherDtoEntityConverter;
import pl.mosenko.sunnypodlaskie.util.WeatherUtil;

import static pl.mosenko.sunnypodlaskie.util.WeatherDtoEntityConverter.convertToWeatherDataEntityList;

public class MainActivity extends AppCompatActivity implements RxWeatherDataAPI.GetCurrentWeatherDataListCallback {

    private final String TAG = MainActivity.class.getSimpleName();

    @Inject
    RxWeatherDataAPI rxWeatherDataAPI;
    @Inject
    WeatherDataEntityDAO weatherDataEntityDAO;

    @BindView(R.id.swipe_refresh_weather_layout)
    SwipeRefreshLayout mSwipeRefreshWeatherLayout;
    @BindView(R.id.recyclerview_current_weather)
    RecyclerView mCurrentWeatherRecycyler;
    private CompositeDisposable mCompositeDisposable;
    private WeatherAdaper mWeatherAdaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCompositeDisposable = new CompositeDisposable();
        getSupportActionBar().setElevation(0f);
        ButterKnife.bind(this);
        ((MainApplication) getApplication()).getMainActivityComponent().inject(this);
        customizeRecyclerView();
        mSwipeRefreshWeatherLayout.setOnRefreshListener(() -> updateWeatherData());
        updateWeatherData();
    }

    private void customizeRecyclerView() {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mCurrentWeatherRecycyler.setLayoutManager(layoutManager);
        mCurrentWeatherRecycyler.setHasFixedSize(true);
        mWeatherAdaper = new WeatherAdaper(this);
        mCurrentWeatherRecycyler.setAdapter(mWeatherAdaper);
        mSwipeRefreshWeatherLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.activated),
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimaryDark));
    }

    private void updateWeatherData() {
        mSwipeRefreshWeatherLayout.setRefreshing(true);
        //TODO firstly check if data are current
        Disposable disposableDao = weatherDataEntityDAO.rxQueryForAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(p -> showWeatherData(p), t -> onError(t));
        mCompositeDisposable.add(disposableDao);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCompositeDisposable.dispose();
    }

    private void showWeatherData(java.util.List<WeatherDataEntity> weatherDataEntityList) {
        if (weatherDataEntityList == null || weatherDataEntityList.isEmpty()) {
            synchronizeWeatherData();
        } else {
            updateWeatherDataRecyclerView(weatherDataEntityList);
        }
        mSwipeRefreshWeatherLayout.setRefreshing(false);
    }

    private void synchronizeWeatherData() {
        Disposable disposable = rxWeatherDataAPI.getCurrentWeatherData(this);
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void onSuccess(final WeatherData weatherDataList) {
        mSwipeRefreshWeatherLayout.setRefreshing(false);
        for (List list: weatherDataList.getList()) {
            Log.d(TAG, list.toString());
        }
       Disposable disposable = Observable.fromCallable(() -> updateWeatherDataOnDatabase(weatherDataList))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(p -> updateWeatherDataRecyclerView(p), e -> onError(e));

        mCompositeDisposable.add(disposable);
    }

    private void updateWeatherDataRecyclerView(java.util.List<WeatherDataEntity> weatherDataEntityList) {
        mWeatherAdaper.swapWeatherList(weatherDataEntityList);
    }

    @NonNull
    private java.util.List<WeatherDataEntity> updateWeatherDataOnDatabase(WeatherData weatherDataList) throws java.sql.SQLException {
        java.util.List<WeatherDataEntity> weatherDataEntityList = WeatherDtoEntityConverter.convertToWeatherDataEntityList(weatherDataList.getList());
        weatherDataEntityDAO.create(weatherDataEntityList);
        return weatherDataEntityList;
    }

    @Override
    public void onError(Throwable networkError) {
        Log.e(TAG, networkError.getMessage());
    }
}
