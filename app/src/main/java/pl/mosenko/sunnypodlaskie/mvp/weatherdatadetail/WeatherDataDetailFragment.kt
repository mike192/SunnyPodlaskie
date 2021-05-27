package pl.mosenko.sunnypodlaskie.mvp.weatherdatadetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import me.grantland.widget.AutofitTextView
import pl.mosenko.sunnypodlaskie.BuildConfig
import pl.mosenko.sunnypodlaskie.R

/**
 * Created by syk on 20.05.17.
 */
class WeatherDataDetailFragment : MvpFragment<WeatherDataDetailContract.View?, WeatherDataDetailContract.Presenter?>(), WeatherDataDetailContract.View {
    @kotlin.jvm.JvmField
    @BindView(R.id.WeatherDataDetailFragment_city_detail)
    var mCityDetailTV: TextView? = null

    @kotlin.jvm.JvmField
    @BindView(R.id.WeatherDataDetailFragment_weather_icon_detail)
    var mIconDetailIV: ImageView? = null

    @kotlin.jvm.JvmField
    @BindView(R.id.WeatherDataDetailFragment_temperature_detail)
    var mTemperatureTV: TextView? = null

    @kotlin.jvm.JvmField
    @BindView(R.id.WeatherDataDetailFragment_weather_description_detail)
    var mWeatherDescriptionTV: TextView? = null

    @kotlin.jvm.JvmField
    @BindView(R.id.WeatherDataDetailFragment_pressure_detail)
    var mPressure: AutofitTextView? = null

    @kotlin.jvm.JvmField
    @BindView(R.id.WeatherDataDetailFragment_wind_detail)
    var mWindDetails: AutofitTextView? = null

    @kotlin.jvm.JvmField
    @BindView(R.id.WeatherDataDetailFragment_humidity_detail)
    var mHumidityDetail: AutofitTextView? = null

    @kotlin.jvm.JvmField
    @BindView(R.id.WeatherDataDetailFragment_sunrise_detail)
    var mSunrise: AutofitTextView? = null

    @kotlin.jvm.JvmField
    @BindView(R.id.WeatherDataDetailFragment_sunset_detail)
    var mSunset: AutofitTextView? = null
    private var unbinder: Unbinder? = null
    override fun createPresenter(): WeatherDataDetailContract.Presenter? {
        return WeatherDataDetailPresenterImpl()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflateFragment(inflater, container)
        bindGraphicalComponents(rootView)
        return rootView
    }

    private fun inflateFragment(inflater: LayoutInflater?, container: ViewGroup?): View? {
        return inflater.inflate(R.layout.fragment_weather_data_details, container, false)
    }

    private fun bindGraphicalComponents(rootView: View?) {
        unbinder = ButterKnife.bind(this, rootView)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPresenter().onViewCreated(arguments)
    }

    override fun loadData(weatherDataDetailPresentationModel: WeatherDataDetailPresentationModel?) {
        mCityDetailTV.setText(weatherDataDetailPresentationModel.getTitleDetails())
        mTemperatureTV.setText(weatherDataDetailPresentationModel.getTemperature())
        mWeatherDescriptionTV.setText(weatherDataDetailPresentationModel.getDescription())
        mIconDetailIV.setImageResource(weatherDataDetailPresentationModel.getIconResource())
        mPressure.setText(weatherDataDetailPresentationModel.getPressure())
        mWindDetails.setText(weatherDataDetailPresentationModel.getWindDetails())
        mHumidityDetail.setText(weatherDataDetailPresentationModel.getHumidity())
        mSunrise.setText(weatherDataDetailPresentationModel.getSunrise())
        mSunset.setText(weatherDataDetailPresentationModel.getSunset())
    }

    override fun showError(throwable: Throwable?) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, throwable.message, throwable)
        }
        Toast.makeText(context, R.string.weather_details_error_message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder.unbind()
    }

    companion object {
        val TAG = WeatherDataDetailFragment::class.java.simpleName
        val ARG_WEATHER_DATA_ID: String? = "weather_data_id"
    }
}
