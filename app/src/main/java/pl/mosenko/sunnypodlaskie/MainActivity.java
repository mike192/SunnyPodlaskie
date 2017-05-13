package pl.mosenko.sunnypodlaskie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import pl.mosenko.sunnypodlaskie.model.List;
import pl.mosenko.sunnypodlaskie.model.WeatherData;
import pl.mosenko.sunnypodlaskie.network.RxWeatherDataAPI;

public class MainActivity extends AppCompatActivity implements RxWeatherDataAPI.GetCurrentWeatherDataListCallback {

    private final String TAG = MainActivity.class.getSimpleName();

    @Inject
    RxWeatherDataAPI rxWeatherDataAPI;
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((MainApplication) getApplication()).getMainActivityComponent().inject(this);
        compositeDisposable = new CompositeDisposable();
        Disposable disposable = rxWeatherDataAPI.getCurrentWeatherData(this);
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.dispose();
    }

    @Override
    public void onSuccess(WeatherData weatherDataList) {
        for (List list: weatherDataList.getList()) {
            Log.d(TAG, list.toString());
        }
    }

    @Override
    public void onError(Throwable networkError) {
        Log.e(TAG, networkError.getMessage());
    }
}
