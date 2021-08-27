package pl.mosenko.sunnypodlaskie.ui.weatherdatadetail

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
import pl.mosenko.sunnypodlaskie.ui.common.EventObserver

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
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val TAG: String = WeatherDataDetailFragment::class.java.simpleName
        const val ARG_WEATHER_DATA_CITY: String = "city"
    }
}
