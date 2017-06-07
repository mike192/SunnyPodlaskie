package pl.mosenko.sunnypodlaskie.mvp.weatherdatadetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import pl.aprilapps.switcher.Switcher;
import pl.mosenko.sunnypodlaskie.ApplicationPodlaskieWeather;
import pl.mosenko.sunnypodlaskie.BuildConfig;
import pl.mosenko.sunnypodlaskie.R;
import pl.mosenko.sunnypodlaskie.persistence.dao.WeatherDataEntityDAO;
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity;
import pl.mosenko.sunnypodlaskie.util.WeatherDataUtil;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by syk on 20.05.17.
 */

public class WeatherDataDetailFragment extends MvpFragment<WeatherDataDetailContract.View, WeatherDataDetailContract.Presenter> implements WeatherDataDetailContract.View {
    public static final String TAG = WeatherDataDetailFragment.class.getSimpleName();
    public static final String ARG_WEATHER_DATA_ID = "weather_data_id";

    @BindView(R.id.city_detail)
    TextView mCityDetailTV;
    @BindView(R.id.weather_icon_detail)
    ImageView mIconDetailIV;
    @BindView(R.id.temperature_detail)
    TextView mTemperatureTV;
    @BindView(R.id.weather_description_detail)
    TextView mWeatherDescriptionTV;
    @BindView(R.id.pressure_detail)
    TextView mPressure;
    @BindView(R.id.wind_detail)
    TextView mWindDetails;
    @BindView(R.id.humidity_detail)
    TextView mHumidityDetail;
    @BindView(R.id.sunrise_detail)
    TextView mSunrise;
    @BindView(R.id.sunset_detail)
    TextView mSunset;

    private Unbinder mUnbinder;

    public WeatherDataDetailFragment() {
    }

    @Override
    public WeatherDataDetailContract.Presenter createPresenter() {
        return new WeatherDataDetailPresenterImpl();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflateFragment(inflater, container);
        bindGraphicalComponents(rootView);
        return rootView;
    }

    private View inflateFragment(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_weather_data_details, container, false);
    }

    private void bindGraphicalComponents(View rootView) {
        mUnbinder = ButterKnife.bind(this, rootView);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPresenter().onViewCreated(getArguments());
    }

    @Override
    public void loadData(WeatherDataDetailPresentationModel weatherDataDetailPresentationModel) {
        mCityDetailTV.setText(weatherDataDetailPresentationModel.getTitleDetails());
        mTemperatureTV.setText(weatherDataDetailPresentationModel.getTemperature());
        mWeatherDescriptionTV.setText(weatherDataDetailPresentationModel.getDescription());
        mIconDetailIV.setImageResource(weatherDataDetailPresentationModel.getGetIconResource());
        mPressure.setText(weatherDataDetailPresentationModel.getPressure());
        mWindDetails.setText(weatherDataDetailPresentationModel.getWindDetails());
        mHumidityDetail.setText(weatherDataDetailPresentationModel.getHumidity());
        mSunrise.setText(weatherDataDetailPresentationModel.getSunrise());
        mSunset.setText(weatherDataDetailPresentationModel.getSunset());
    }

    @Override
    public void showError(Throwable throwable) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, throwable.getMessage(), throwable);
        }
        Toast.makeText(getContext(), R.string.weather_details_error_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
