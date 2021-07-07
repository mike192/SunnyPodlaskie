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
import org.koin.android.ext.android.inject
import pl.mosenko.sunnypodlaskie.BuildConfig
import pl.mosenko.sunnypodlaskie.R

/**
 * Created by syk on 20.05.17.
 */
class WeatherDataDetailFragment : MvpFragment<WeatherDataDetailContract.View, WeatherDataDetailContract.Presenter>(), WeatherDataDetailContract.View {

    @BindView(R.id.WeatherDataDetailFragment_city_detail)
    lateinit var mCityDetailTV: TextView

    @BindView(R.id.WeatherDataDetailFragment_weather_icon_detail)
    lateinit var mIconDetailIV: ImageView

    @BindView(R.id.WeatherDataDetailFragment_temperature_detail)
    lateinit var mTemperatureTV: TextView

    @BindView(R.id.WeatherDataDetailFragment_weather_description_detail)
    lateinit var mWeatherDescriptionTV: TextView

    @BindView(R.id.WeatherDataDetailFragment_pressure_detail)
    lateinit var mPressure: AutofitTextView

    @BindView(R.id.WeatherDataDetailFragment_wind_detail)
    lateinit var mWindDetails: AutofitTextView

    @BindView(R.id.WeatherDataDetailFragment_humidity_detail)
    lateinit var mHumidityDetail: AutofitTextView

    @BindView(R.id.WeatherDataDetailFragment_sunrise_detail)
    lateinit var mSunrise: AutofitTextView

    @BindView(R.id.WeatherDataDetailFragment_sunset_detail)
    lateinit var mSunset: AutofitTextView

    private var unbinder: Unbinder? = null

    private val fragmentPresenter: WeatherDataDetailContract.Presenter by inject()

    override fun createPresenter(): WeatherDataDetailContract.Presenter {
        return fragmentPresenter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflateFragment(inflater, container)
        bindGraphicalComponents(rootView)
        return rootView
    }

    private fun inflateFragment(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(R.layout.fragment_weather_data_details, container, false)
    }

    private fun bindGraphicalComponents(rootView: View) {
        unbinder = ButterKnife.bind(this, rootView)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPresenter().onViewCreated(requireArguments())
    }

    override fun loadData(weatherDataDetailPresentationModel: WeatherDataDetailPresentationModel) {
        mCityDetailTV.text = weatherDataDetailPresentationModel.titleDetails
        mTemperatureTV.text = weatherDataDetailPresentationModel.temperature
        mWeatherDescriptionTV.text = weatherDataDetailPresentationModel.description
        mIconDetailIV.setImageResource(weatherDataDetailPresentationModel.iconResource)
        mPressure.text = weatherDataDetailPresentationModel.pressure
        mWindDetails.text = weatherDataDetailPresentationModel.windDetails
        mHumidityDetail.text = weatherDataDetailPresentationModel.humidity
        mSunrise.text = weatherDataDetailPresentationModel.sunrise
        mSunset.text = weatherDataDetailPresentationModel.sunset
    }

    override fun showError(throwable: Throwable) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, throwable.message, throwable)
        }
        Toast.makeText(context, R.string.weather_details_error_message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder?.unbind()
    }

    companion object {
        val TAG: String = WeatherDataDetailFragment::class.java.simpleName
        const val ARG_WEATHER_DATA_ID: String = "weather_data_id"
    }
}
