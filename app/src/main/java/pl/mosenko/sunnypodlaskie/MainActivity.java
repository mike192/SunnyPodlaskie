package pl.mosenko.sunnypodlaskie;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import pl.mosenko.sunnypodlaskie.model.List;
import pl.mosenko.sunnypodlaskie.model.WeatherData;
import pl.mosenko.sunnypodlaskie.network.RxWeatherDataAPI;

import static android.R.color.holo_blue_dark;

public class MainActivity extends AppCompatActivity implements RxWeatherDataAPI.GetCurrentWeatherDataListCallback {

    private final String TAG = MainActivity.class.getSimpleName();

    @Inject
    RxWeatherDataAPI rxWeatherDataAPI;
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
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mCurrentWeatherRecycyler.setLayoutManager(layoutManager);
        mCurrentWeatherRecycyler.setHasFixedSize(true);
        mWeatherAdaper = new WeatherAdaper(this, new ArrayList<>());
        mCurrentWeatherRecycyler.setAdapter(mWeatherAdaper);
        mSwipeRefreshWeatherLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.activated),
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimaryDark));
        mSwipeRefreshWeatherLayout.setOnRefreshListener(() -> updateWeatherData());
        updateWeatherData();
    }

    private void updateWeatherData() {
        mSwipeRefreshWeatherLayout.setRefreshing(true);
        Disposable disposable = rxWeatherDataAPI.getCurrentWeatherData(this);
        mCompositeDisposable.add(disposable);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCompositeDisposable.dispose();
    }

    @Override
    public void onSuccess(WeatherData weatherDataList) {
        for (List list: weatherDataList.getList()) {
            Log.d(TAG, list.toString());
        }
        mSwipeRefreshWeatherLayout.setRefreshing(false);
       mWeatherAdaper.swapWeatherList(weatherDataList.getList());
    }

    @Override
    public void onError(Throwable networkError) {
        Log.e(TAG, networkError.getMessage());
    }
}
