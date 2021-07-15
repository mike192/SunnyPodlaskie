package pl.mosenko.sunnypodlaskie.mvp.weatherdatalist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import org.koin.android.ext.android.inject
import pl.aprilapps.switcher.Switcher
import pl.mosenko.sunnypodlaskie.BuildConfig
import pl.mosenko.sunnypodlaskie.R
import pl.mosenko.sunnypodlaskie.databinding.FragmentWeatherDataListBinding
import pl.mosenko.sunnypodlaskie.mvp.weatherdatalist.WeatherDataListAdaper.WeatherDataClickedListener
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity

/**
 * Created by syk on 20.05.17.
 */
class WeatherDataListFragment :
    Fragment(),
    WeatherDataListContract.View, OnRefreshListener, WeatherDataClickedListener {

    private val presenter: WeatherDataListContract.Presenter by inject()

    private var _binding: FragmentWeatherDataListBinding? = null
    private val binding get() = _binding!!

    private lateinit var swipeRefreshLayouts: List<SwipeRefreshLayout>
    private lateinit var switcher: Switcher
    private lateinit var weatherDataListAdaper: WeatherDataListAdaper

    private var callback = dummyCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tryToInitializeCallbackField(activity)
    }

    private fun tryToInitializeCallbackField(context: Context?) {
        callback = try {
            context as Callback
        } catch (e: ClassCastException) {
            throw ClassCastException(
                context.toString()
                        + " must implement Callback"
            )
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
        callback.onItemSelected(id)
    }

    override fun onDetach() {
        super.onDetach()
        callback = dummyCallback
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
        _binding = null
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

    interface Callback {
        fun onItemSelected(weatherDataId: Long)
    }

    companion object {
        private val TAG = WeatherDataListFragment::class.java.simpleName
        private val dummyCallback: Callback = object : Callback {
            override fun onItemSelected(weatherDataId: Long) {

            }
        }
    }
}
