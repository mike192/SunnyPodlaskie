package pl.mosenko.sunnypodlaskie.mvp.weatherdatadetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.mosenko.sunnypodlaskie.BuildConfig
import pl.mosenko.sunnypodlaskie.R
import pl.mosenko.sunnypodlaskie.databinding.FragmentWeatherDataDetailsBinding
import pl.mosenko.sunnypodlaskie.mvp.EventObserver

/**
 * Created by syk on 20.05.17.
 */
class WeatherDataDetailFragment : Fragment() {

    private val args: WeatherDataDetailFragmentArgs by navArgs()
    private var _binding: FragmentWeatherDataDetailsBinding? = null
    private val binding get() = _binding!!
    val viewModel: WeatherDataDetailViewModel by viewModel()

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
        if (arguments == null || requireArguments().isEmpty) {
            viewModel.loadWeatherDataDetails(null)
        } else {
            viewModel.loadWeatherDataDetails(args.city)
        }
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.weatherDataDetails.observe(viewLifecycleOwner, {
            if (it != null) {
                showData(it)
            }
        })
        viewModel.errorEvent.observe(viewLifecycleOwner, EventObserver {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, it.message, it)
            }
            Snackbar.make(
                binding.root,
                R.string.weather_details_error_message,
                Snackbar.LENGTH_LONG
            ).show()
        })
    }

    private fun showData(weatherDataDetailPresentationModel: WeatherDataDetailPresentationModel) {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val TAG: String = WeatherDataDetailFragment::class.java.simpleName
        const val ARG_WEATHER_DATA_CITY: String = "weatherDataId"
    }
}
