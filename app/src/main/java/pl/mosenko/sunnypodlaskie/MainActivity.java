package pl.mosenko.sunnypodlaskie;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedDelete;

import java.sql.SQLException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
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
    @BindView(R.id.pb_loading_indicator)
    ProgressBar mLoadingIndicator;

    private CompositeDisposable mCompositeDisposable;
    private WeatherAdaper mWeatherAdaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivityView();
        mCompositeDisposable = new CompositeDisposable();
        ((MainApplication) getApplication()).getMainActivityComponent().inject(this);
        customizeRecyclerView();
        customizeSwipeRefreshLayout();
        synchronizeCurrentWeatherData();
    }

    private void initializeActivityView() {
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0f);
        ButterKnife.bind(this);
    }

    private void showLoading() {
        if (!mSwipeRefreshWeatherLayout.isRefreshing()) {
            mCurrentWeatherRecycyler.setVisibility(View.INVISIBLE);
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }
    }

    private void showWeatherDataView() {
        if (!mSwipeRefreshWeatherLayout.isRefreshing()) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            mCurrentWeatherRecycyler.setVisibility(View.VISIBLE);
        } else {
            mSwipeRefreshWeatherLayout.setRefreshing(false);
        }
    }

    private void synchronizeCurrentWeatherData() {
        showLoading();
        Disposable disposable = rxWeatherDataAPI.getCurrentWeatherData(this);
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void onDownloadWeatherDataSuccess(final WeatherData weatherDataList) {
        for (List list : weatherDataList.getList()) {
            Log.d(TAG, list.toString());
        }
        Disposable disposable = Observable.fromCallable(() -> cacheCurrentWeatherDataOnDatabase(weatherDataList))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(p -> showCurrentWeatherDataRecyclerView(p), e -> onDownloadWeatherDataError(e));

        mCompositeDisposable.add(disposable);
    }

    @NonNull
    private java.util.List<WeatherDataEntity> cacheCurrentWeatherDataOnDatabase(WeatherData weatherDataList) throws java.sql.SQLException {
        java.util.List<WeatherDataEntity> weatherDataEntityList = WeatherDtoEntityConverter.convertToWeatherDataEntityList(weatherDataList.getList());
        weatherDataEntityDAO.deleteBuilder().delete();
        weatherDataEntityDAO.create(weatherDataEntityList);
        return weatherDataEntityList;
    }

    private void showCurrentWeatherDataRecyclerView(java.util.List<WeatherDataEntity> weatherDataEntityList) {
        mWeatherAdaper.swapWeatherList(weatherDataEntityList);
        showWeatherDataView();
    }

    @Override
    public void onDownloadWeatherDataError(Throwable networkError) {
        Log.e(TAG, networkError.getMessage());
    }

    private void customizeSwipeRefreshLayout() {
        mSwipeRefreshWeatherLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.activated),
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimaryDark));
        mSwipeRefreshWeatherLayout.setOnRefreshListener(() -> synchronizeCurrentWeatherData());
    }

    private void customizeRecyclerView() {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mCurrentWeatherRecycyler.setLayoutManager(layoutManager);
        mCurrentWeatherRecycyler.setHasFixedSize(true);
        mWeatherAdaper = new WeatherAdaper(this);
        mCurrentWeatherRecycyler.setAdapter(mWeatherAdaper);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCompositeDisposable.dispose();
    }
}
