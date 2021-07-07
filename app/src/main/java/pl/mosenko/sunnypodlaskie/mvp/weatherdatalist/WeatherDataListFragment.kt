package pl.mosenko.sunnypodlaskie.mvp.weatherdatalist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import butterknife.BindView
import butterknife.BindViews
import butterknife.ButterKnife
import butterknife.Unbinder
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import org.koin.android.ext.android.inject
import pl.aprilapps.switcher.Switcher
import pl.mosenko.sunnypodlaskie.BuildConfig
import pl.mosenko.sunnypodlaskie.R
import pl.mosenko.sunnypodlaskie.mvp.weatherdatadetail.WeatherDataDetailContract
import pl.mosenko.sunnypodlaskie.mvp.weatherdatalist.WeatherDataListAdaper.WeatherDataClickedListener
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity

/**
 * Created by syk on 20.05.17.
 */
class WeatherDataListFragment : MvpFragment<WeatherDataListContract.View, WeatherDataListContract.Presenter>(), WeatherDataListContract.View, OnRefreshListener, WeatherDataClickedListener {

    @BindView(R.id.WeatherDataListFragment_TextView_Empty)
    lateinit var textViewEmpty: TextView

    @BindView(R.id.WeatherDataListFragment_ProgressBar)
    lateinit var progressBarLoading: ProgressBar

    @BindView(R.id.WeatherDataListFragment_RecyclerView)
    lateinit var recyclerView: RecyclerView

    @BindView(R.id.FragmentWeatherDataList_TextView_Error)
    lateinit var textViewError: TextView

    @BindView(R.id.WeatherDataListFragment_SwipeRefreshLayout_Empty)
    lateinit var swipeRefreshLayoutEmpty: SwipeRefreshLayout

    @BindView(R.id.WeatherDataListFragment_SwipeRefreshLayout_Error)
    lateinit var swipeRefreshLayoutError: SwipeRefreshLayout

    @BindView(R.id.FragmentWeatherDataList_SwipeRefreshLayout_Content)
    lateinit var swipeRefreshLayoutContent: SwipeRefreshLayout

    @BindViews(R.id.WeatherDataListFragment_SwipeRefreshLayout_Empty, R.id.WeatherDataListFragment_SwipeRefreshLayout_Error, R.id.FragmentWeatherDataList_SwipeRefreshLayout_Content)

    lateinit var swipeRefreshLayouts: MutableList<SwipeRefreshLayout>
    lateinit var mSwitcher: Switcher
    lateinit var mWeatherDataListAdaper: WeatherDataListAdaper
    private lateinit var mUnbinder: Unbinder

    private val fragmentPresenter: WeatherDataListContract.Presenter by inject()

    private var mCallback = sDummyCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tryToInitializeCallbackField(activity)
    }

    private fun tryToInitializeCallbackField(context: Context?) {
        mCallback = try {
            context as Callback
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString()
                    + " must implement Callback")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflateFragmentLayout(inflater, container)
        bindGraphicalComponents(rootView)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureSwitcherView()
        customizeRecyclerView()
        customizeSwipeRefreshLayout()
    }

    private fun configureSwitcherView() {
        mSwitcher = Switcher.Builder(requireContext())
                .addContentView(swipeRefreshLayoutContent)
                .addEmptyView(swipeRefreshLayoutEmpty)
                .addProgressView(progressBarLoading)
                .addErrorView(swipeRefreshLayoutError)
                .build()
    }

    private fun customizeRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        mWeatherDataListAdaper = WeatherDataListAdaper(activity, this)
        recyclerView.adapter = mWeatherDataListAdaper
    }

    private fun bindGraphicalComponents(rootView: View) {
        mUnbinder = ButterKnife.bind(this, rootView)
    }

    private fun inflateFragmentLayout(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(R.layout.fragment_weather_data_list, container, false)
    }

    override fun showEmpty() {
        stopRefreshLayouts()
        mSwitcher.showEmptyView()
    }

    private fun stopRefreshLayouts() {
        swipeRefreshLayouts.forEach {
            it.isRefreshing = false
        }
    }


    override fun showLoading(pullToRefresh: Boolean) {
        if (!pullToRefresh) {
            mSwitcher.showProgressView()
        }
    }

    override fun showContent() {
        stopRefreshLayouts()
        mSwitcher.showContentView()
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
        mSwitcher.showErrorView()
    }

    override fun setData(weatherDataEntityList: MutableList<WeatherDataEntity>) {
        mWeatherDataListAdaper.swapWeatherList(weatherDataEntityList)
    }

    private fun customizeSwipeRefreshLayout() {
        setSwipeRefreshLayoutListener()
        for (swipeRefreshLayout in swipeRefreshLayouts) {
            swipeRefreshLayout.setColorSchemeResources(
                    R.color.colorAccent,
                    R.color.activated,
                    R.color.colorPrimary,
                    R.color.colorPrimaryDark)
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

    override fun createPresenter(): WeatherDataListContract.Presenter {
        return fragmentPresenter
    }

    override fun onWeatherDataItemClick(id: Long) {
        mCallback.onItemSelected(id)
    }

    override fun onDetach() {
        super.onDetach()
        mCallback = sDummyCallback
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mUnbinder.unbind()
    }

    override fun onResume() {
        super.onResume()
        getPresenter().onResume()
    }

    override fun onPause() {
        super.onPause()
        getPresenter().onPause()
    }

    override fun loadData(pullToRefresh: Boolean) {
        getPresenter().loadData(pullToRefresh)
    }

    interface Callback {
        fun onItemSelected(weatherDataId: Long)
    }

    companion object {
        private val TAG = WeatherDataListFragment::class.java.simpleName
        private val sDummyCallback: Callback = object : Callback {
            override fun onItemSelected(weatherDataId: Long) {

            }
        }
    }
}
