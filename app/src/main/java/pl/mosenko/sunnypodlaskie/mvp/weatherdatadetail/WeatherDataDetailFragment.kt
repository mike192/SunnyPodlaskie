package pl.mosenko.sunnypodlaskie.mvp.weatherdatadetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import org.koin.android.ext.android.inject
import pl.mosenko.sunnypodlaskie.BuildConfig
import pl.mosenko.sunnypodlaskie.R
import pl.mosenko.sunnypodlaskie.databinding.FragmentWeatherDataDetailsBinding

/**
 * Created by syk on 20.05.17.
 */
class WeatherDataDetailFragment : Fragment(), WeatherDataDetailContract.View {

    private val args: WeatherDataDetailFragmentArgs by navArgs()
    private var _binding: FragmentWeatherDataDetailsBinding? = null
    private val binding get() = _binding!!

    private val presenter: WeatherDataDetailContract.Presenter by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherDataDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        if (arguments == null || requireArguments().isEmpty) {
            presenter.onViewCreated(null)
        } else {
            presenter.onViewCreated(args.weatherDataId)
        }
    }

    override fun loadData(weatherDataDetailPresentationModel: WeatherDataDetailPresentationModel) {
        binding.incPrimaryInfo.apply {
            tvCityDetail.text = weatherDataDetailPresentationModel.titleDetails
            tvTemperatureDetail.text = weatherDataDetailPresentationModel.temperature
            tvWeatherDescriptionDetail.text = weatherDataDetailPresentationModel.description
            ivWeatherIconDetail.setImageResource(weatherDataDetailPresentationModel.iconResource)
        }
        binding.incExtraInfo.apply {
            tvPressureDetail.text = weatherDataDetailPresentationModel.pressure
            tvWindDetail.text = weatherDataDetailPresentationModel.windDetails
            tvHumidityDetail.text = weatherDataDetailPresentationModel.humidity
            tvSunriseDetail.text = weatherDataDetailPresentationModel.sunrise
            tvSunsetDetail.text = weatherDataDetailPresentationModel.sunset
        }
    }

    override fun showError(throwable: Throwable) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, throwable.message, throwable)
        }
        Toast.makeText(context, R.string.weather_details_error_message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
        _binding = null
    }

    companion object {
        val TAG: String = WeatherDataDetailFragment::class.java.simpleName
        const val ARG_WEATHER_DATA_ID: String = "weatherDataId"
    }
}
