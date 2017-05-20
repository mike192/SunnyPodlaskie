package pl.mosenko.sunnypodlaskie;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import pl.mosenko.sunnypodlaskie.persistence.dao.WeatherDataEntityDAO;
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity;

/**
 * Created by syk on 20.05.17.
 */

public class WeatherDataDetailFragment extends Fragment {
    public static final String TAG = WeatherDataDetailFragment.class.getSimpleName();
    public static final String ARG_WEATHER_DATA_ID = "weather_data_id";
    @Inject
    WeatherDataEntityDAO weatherDataEntityDAO;
    private Unbinder mUnbinder;
    private Long weatherDataId;
    private CompositeDisposable mCompositeDisposable;

    public WeatherDataDetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectFields();
        initializeCompositeDisposable();
        setWeatherDataIdFromFragmentArguments();
    }

    private void initializeCompositeDisposable() {
        mCompositeDisposable = new CompositeDisposable();
    }

    private void setWeatherDataIdFromFragmentArguments() {
        if (getArguments().containsKey(ARG_WEATHER_DATA_ID)) {
            weatherDataId = getArguments().getLong(ARG_WEATHER_DATA_ID);
        }
    }

    private void injectFields() {
        ((MainApplication) getActivity().getApplication()).getMainActivityComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflateFragment(inflater, container);
        bindGraphicalComponents(rootView);
        fillGraphicalComponents();
        return rootView;
    }

    private void fillGraphicalComponents() {
        if (weatherDataId != null) {
            weatherDataEntityDAO.rxQueryForId(weatherDataId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(p -> fillGraphicalComponents(p), e -> logError(e));
        }
    }

    private void fillGraphicalComponents(WeatherDataEntity weatherDataEntity) {
        Toast.makeText(getActivity(), weatherDataEntity.getCity(), Toast.LENGTH_SHORT).show();
        //TODO: fill all components
    }

    private void logError(Throwable throwable) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, throwable.getMessage(), throwable);
        }
        Log.e(TAG, throwable.getMessage());
    }

    private void bindGraphicalComponents(View rootView) {
        mUnbinder = ButterKnife.bind(this, rootView);
    }

    private View inflateFragment(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_weather_data_details, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onStop() {
        super.onStop();
        mCompositeDisposable.dispose();
    }
}
