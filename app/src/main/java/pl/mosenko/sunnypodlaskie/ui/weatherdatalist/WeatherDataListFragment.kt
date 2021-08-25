package pl.mosenko.sunnypodlaskie.ui.weatherdatalist

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.mosenko.sunnypodlaskie.BuildConfig
import pl.mosenko.sunnypodlaskie.R
import pl.mosenko.sunnypodlaskie.databinding.FragmentWeatherDataListBinding
import pl.mosenko.sunnypodlaskie.ui.EventObserver
import pl.mosenko.sunnypodlaskie.ui.weatherdatadetail.WeatherDataDetailFragment
import pl.mosenko.sunnypodlaskie.ui.weatherdatalist.WeatherDataListAdapter.WeatherDataClickedListener
import pl.mosenko.sunnypodlaskie.persistence.model.WeatherData
import pl.mosenko.sunnypodlaskie.repository.Result

/**
 * Created by syk on 20.05.17.
 */
class WeatherDataListFragment :
    Fragment(), OnRefreshListener, WeatherDataClickedListener {

    val viewModel: WeatherDataListViewModel by viewModel()
    private val isTablet by lazy { resources.getBoolean(R.bool.is_tablet) }

    private var _binding: FragmentWeatherDataListBinding? = null
    private val binding get() = _binding!!

    private lateinit var weatherDataListAdapter: WeatherDataListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherDataListBinding.inflate(inflater, container, false)
        showEmpty()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        customizeRecyclerView()
        customizeSwipeRefreshLayout()
        setupObservers()
        viewModel.loadWeatherData(true)
    }

    private fun setupObservers() {
        viewModel.weatherDataList.observe(viewLifecycleOwner, {
            when (it) {
                is Result.Success -> {
                    if (it.data.isEmpty()) {
                        showEmpty()
                    } else {
                        showContent(it.data)
                    }
                }
                is Result.Error -> {
                    showError(it.throwable)
                }
                is Result.Loading -> {
                    showLoading()
                }
            }
        })
        viewModel.snackbarMessage.observe(viewLifecycleOwner, EventObserver {
            stopRefreshLayouts()
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
        })
    }

    private fun customizeRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.incContent.rvContent.let {
            it.layoutManager = layoutManager
            it.setHasFixedSize(true)
            weatherDataListAdapter = WeatherDataListAdapter(this)
            it.adapter = weatherDataListAdapter
        }
    }

    private fun showEmpty() {
        stopRefreshLayouts()
        configureViews(DataListViewsConfiguration.EMPTY)
    }

    private fun configureViews(configuration: DataListViewsConfiguration) {
        if (!configuration.loading) {
            binding.incContent.rvContent.isVisible = configuration.content
            binding.incContent.tvEmpty.isVisible = configuration.empty
            binding.incContent.tvErrorMessage.isVisible = configuration.error
        }
        binding.incContent.srlContent.isRefreshing = configuration.loading
    }

    private fun stopRefreshLayouts() {
        binding.incContent.srlContent.isRefreshing = false
    }

    private fun showLoading() {
        configureViews(DataListViewsConfiguration.LOADING)
    }

    private fun showContent(data: List<WeatherData>) {
        weatherDataListAdapter.swapWeatherList(data)
        stopRefreshLayouts()
        configureViews(DataListViewsConfiguration.CONTENT)
    }

    private fun showError(e: Throwable) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, e.message, e)
        }
        stopRefreshLayouts()
        configureViews(DataListViewsConfiguration.ERROR)
    }

    private fun customizeSwipeRefreshLayout() {
        binding.incContent.srlContent.setOnRefreshListener(this)
        binding.incContent.srlContent.setColorSchemeResources(
            R.color.colorAccent,
            R.color.activated,
            R.color.colorPrimary,
            R.color.colorPrimaryDark
        )
    }

    override fun onRefresh() {
        viewModel.loadWeatherData(true)
    }

    override fun onWeatherDataItemClick(city: String) {
        if (isTablet) {
            val navHostFragment = childFragmentManager.findFragmentById(
                R.id.fcv_container
            ) as NavHostFragment
            navHostFragment.navController.navigate(
                R.id.weatherDataDetailFragmentTablet,
                bundleOf(WeatherDataDetailFragment.ARG_WEATHER_DATA_CITY to city)
            )
        } else {
            Navigation.findNavController(binding.root).navigate(
                WeatherDataListFragmentDirections.navigateToWeatherDataDetailFragment(city)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_settings) {
            Navigation.findNavController(binding.root).navigate(R.id.navigateToSettingsFragment)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private val TAG = WeatherDataListFragment::class.java.simpleName
    }
}
