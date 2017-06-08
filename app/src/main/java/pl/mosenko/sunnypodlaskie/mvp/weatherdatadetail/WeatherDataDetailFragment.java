package pl.mosenko.sunnypodlaskie.mvp.weatherdatadetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pl.mosenko.sunnypodlaskie.BuildConfig;
import pl.mosenko.sunnypodlaskie.R;

/**
 * Created by syk on 20.05.17.
 */

public class WeatherDataDetailFragment extends MvpFragment<WeatherDataDetailContract.View, WeatherDataDetailContract.Presenter> implements WeatherDataDetailContract.View {

    public static final String TAG = WeatherDataDetailFragment.class.getSimpleName();
    public static final String ARG_WEATHER_DATA_ID = "weather_data_id";

    @BindView(R.id.WeatherDataDetailFragment_city_detail)
    TextView mCityDetailTV;
    @BindView(R.id.WeatherDataDetailFragment_weather_icon_detail)
    ImageView mIconDetailIV;
    @BindView(R.id.WeatherDataDetailFragment_temperature_detail)
    TextView mTemperatureTV;
    @BindView(R.id.WeatherDataDetailFragment_weather_description_detail)
    TextView mWeatherDescriptionTV;
    @BindView(R.id.WeatherDataDetailFragment_pressure_detail)
    TextView mPressure;
    @BindView(R.id.WeatherDataDetailFragment_wind_detail)
    TextView mWindDetails;
    @BindView(R.id.WeatherDataDetailFragment_humidity_detail)
    TextView mHumidityDetail;
    @BindView(R.id.WeatherDataDetailFragment_sunrise_detail)
    TextView mSunrise;
    @BindView(R.id.WeatherDataDetailFragment_sunset_detail)
    TextView mSunset;

    private Unbinder unbinder;

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
        unbinder = ButterKnife.bind(this, rootView);
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
        mIconDetailIV.setImageResource(weatherDataDetailPresentationModel.getIconResource());
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
        unbinder.unbind();
    }
}
