package pl.mosenko.sunnypodlaskie.mvp.weatherdatalist

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import org.koin.android.ext.android.inject
import pl.aprilapps.switcher.Switcher
import pl.mosenko.sunnypodlaskie.BuildConfig
import pl.mosenko.sunnypodlaskie.R
import pl.mosenko.sunnypodlaskie.databinding.FragmentWeatherDataListBinding
import pl.mosenko.sunnypodlaskie.mvp.weatherdatadetail.WeatherDataDetailFragment
import pl.mosenko.sunnypodlaskie.mvp.weatherdatalist.WeatherDataListAdaper.WeatherDataClickedListener
import pl.mosenko.sunnypodlaskie.mvp.weatherdatalist.WeatherDataListFragment.WeatherItemCallback
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity

/**
 * Created by syk on 20.05.17.
 */
class WeatherDataListFragment :
    Fragment(),
    WeatherDataListContract.View, OnRefreshListener, WeatherDataClickedListener {

    private val presenter: WeatherDataListContract.Presenter by inject()
    private val isTablet by lazy { resources.getBoolean(R.bool.is_tablet) }

    private var _binding: FragmentWeatherDataListBinding? = null
    private val binding get() = _binding!!

    private var weatherItemCallback: WeatherItemCallback? = null
    private var navigationCallback: NavigationCallback? = null
    private lateinit var swipeRefreshLayouts: List<SwipeRefreshLayout>
    private lateinit var switcher: Switcher
    private lateinit var weatherDataListAdaper: WeatherDataListAdaper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tryToInitializeCallbackFields()
    }

    private fun tryToInitializeCallbackFields() {
        weatherItemCallback = try {
            if (isTablet) {
                WeatherItemCallback { weatherDataId: Long -> replaceWeatherDataDetailsFragment(weatherDataId) }
            } else {
                activity as WeatherItemCallback
            }
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " must implement ${WeatherItemCallback::class.simpleName}")
        }
        navigationCallback = try {
            activity as NavigationCallback
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " must implement ${NavigationCallback::class.simpleName}")
        }
    }

    private fun replaceWeatherDataDetailsFragment(weatherDataId: Long) {
        val fragment = WeatherDataDetailFragment().apply {
            arguments = bundleOf(WeatherDataDetailFragment.ARG_WEATHER_DATA_ID to weatherDataId)
        }

        childFragmentManager.commit {
            replace(R.id.fl_container, fragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherDataListBinding.inflate(inflater, container, false)
        swipeRefreshLayouts = listOf(
            binding.incContent.srlContent, binding.incEmpty.srlEmpty,
            binding.incError.srlError
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        presenter.attachView(this)
        configureSwitcherView()
        customizeRecyclerView()
        customizeSwipeRefreshLayout()
    }

    private fun configureSwitcherView() {
        switcher = Switcher.Builder(requireContext())
            .addContentView(binding.incContent.srlContent)
            .addEmptyView(binding.incEmpty.srlEmpty)
            .addProgressView(binding.pbLoading)
            .addErrorView(binding.incError.srlError)
            .build()
    }

    private fun customizeRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.incContent.rvContent.let {
            it.layoutManager = layoutManager
            it.setHasFixedSize(true)
            weatherDataListAdaper = WeatherDataListAdaper(this)
            it.adapter = weatherDataListAdaper
        }
    }

    override fun showEmpty() {
        stopRefreshLayouts()
        switcher.showEmptyView()
    }

    private fun stopRefreshLayouts() {
        swipeRefreshLayouts.forEach {
            it.isRefreshing = false
        }
    }

    override fun showLoading(pullToRefresh: Boolean) {
        if (!pullToRefresh) {
            switcher.showProgressView()
        }
    }

    override fun showContent() {
        stopRefreshLayouts()
        switcher.showContentView()
    }

    override fun showError(throwable: Throwable) {
        showError(throwable, true)
    }

    override fun showDataWithoutInternetUpdatedMessage() {
        Toast.makeText(activity, R.string.message_no_connection, Toast.LENGTH_SHORT).show()
    }

    override fun showError(e: Throwable, pullToRefresh: Boolean) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, e.message, e)
        }
        if (pullToRefresh) {
            stopRefreshLayouts()
        }
        switcher.showErrorView()
    }

    override fun setData(data: MutableList<WeatherDataEntity>) {
        weatherDataListAdaper.swapWeatherList(data)
    }

    private fun customizeSwipeRefreshLayout() {
        setSwipeRefreshLayoutListener()
        for (swipeRefreshLayout in swipeRefreshLayouts) {
            swipeRefreshLayout.setColorSchemeResources(
                R.color.colorAccent,
                R.color.activated,
                R.color.colorPrimary,
                R.color.colorPrimaryDark
            )
        }
    }

    private fun setSwipeRefreshLayoutListener() {
        swipeRefreshLayouts.forEach {
            it.setOnRefreshListener(this)
        }
    }

    override fun onRefresh() {
        loadData(true)
    }

    override fun onWeatherDataItemClick(id: Long) {
        weatherItemCallback?.onItemSelected(id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        weatherItemCallback = null
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun loadData(pullToRefresh: Boolean) {
        presenter.loadData(pullToRefresh)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_settings) {
            navigationCallback?.navigateToSettings()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun interface WeatherItemCallback {
        fun onItemSelected(weatherDataId: Long)
    }

    interface NavigationCallback {
        fun navigateToSettings()
    }

    companion object {
        private val TAG = WeatherDataListFragment::class.java.simpleName
    }
}
