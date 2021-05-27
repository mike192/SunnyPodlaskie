package pl.mosenko.sunnypodlaskie.mvp.weatherdatalist

import android.content.Context
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.BindViews
import butterknife.ButterKnife
import butterknife.Unbinder
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import pl.aprilapps.switcher.Switcher
import pl.mosenko.sunnypodlaskie.BuildConfig
import pl.mosenko.sunnypodlaskie.R
import pl.mosenko.sunnypodlaskie.mvp.weatherdatalist.WeatherDataListAdaper.WeatherDataClickedListener
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity

/**
 * Created by syk on 20.05.17.
 */
class WeatherDataListFragment : MvpFragment<WeatherDataListContract.View?, WeatherDataListContract.Presenter?>(), WeatherDataListContract.View, OnRefreshListener, WeatherDataClickedListener {
    @kotlin.jvm.JvmField
    @BindView(R.id.WeatherDataListFragment_TextView_Empty)
    var textViewEmpty: TextView? = null

    @kotlin.jvm.JvmField
    @BindView(R.id.WeatherDataListFragment_ProgressBar)
    var progressBarLoading: ProgressBar? = null

    @kotlin.jvm.JvmField
    @BindView(R.id.WeatherDataListFragment_RecyclerView)
    var recyclerView: RecyclerView? = null

    @kotlin.jvm.JvmField
    @BindView(R.id.FragmentWeatherDataList_TextView_Error)
    var textViewError: TextView? = null

    @kotlin.jvm.JvmField
    @BindView(R.id.WeatherDataListFragment_SwipeRefreshLayout_Empty)
    var swipeRefreshLayoutEmpty: SwipeRefreshLayout? = null

    @kotlin.jvm.JvmField
    @BindView(R.id.WeatherDataListFragment_SwipeRefreshLayout_Error)
    var swipeRefreshLayoutError: SwipeRefreshLayout? = null

    @kotlin.jvm.JvmField
    @BindView(R.id.FragmentWeatherDataList_SwipeRefreshLayout_Content)
    var swipeRefreshLayoutContent: SwipeRefreshLayout? = null

    @kotlin.jvm.JvmField
    @BindViews(R.id.WeatherDataListFragment_SwipeRefreshLayout_Empty, R.id.WeatherDataListFragment_SwipeRefreshLayout_Error, R.id.FragmentWeatherDataList_SwipeRefreshLayout_Content)
    var swipeRefreshLayouts: MutableList<SwipeRefreshLayout?>? = null
    private var mSwitcher: Switcher? = null
    private var mCallback = sDummyCallback
    private var mWeatherDataListAdaper: WeatherDataListAdaper? = null
    private var mUnbinder: Unbinder? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tryToInitializeCallbackField(activity)
    }

    private fun tryToInitializeCallbackField(context: Context?) {
        mCallback = try {
            context as Callback?
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString()
                    + " must implement Callback")
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflateFragmentLayout(inflater, container)
        bindGraphicalComponents(rootView)
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureSwitcherView()
        customizeRecyclerView()
        customizeSwipeRefreshLayout()
    }

    private fun configureSwitcherView() {
        mSwitcher = Builder(context)
                .addContentView(swipeRefreshLayoutContent)
                .addEmptyView(swipeRefreshLayoutEmpty)
                .addProgressView(progressBarLoading)
                .addErrorView(swipeRefreshLayoutError)
                .build()
    }

    private fun customizeRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.setLayoutManager(layoutManager)
        recyclerView.setHasFixedSize(true)
        mWeatherDataListAdaper = WeatherDataListAdaper(activity, this)
        recyclerView.setAdapter(mWeatherDataListAdaper)
    }

    private fun bindGraphicalComponents(rootView: View?) {
        mUnbinder = ButterKnife.bind(this, rootView)
    }

    private fun inflateFragmentLayout(inflater: LayoutInflater?, container: ViewGroup?): View? {
        return inflater.inflate(R.layout.fragment_weather_data_list, container, false)
    }

    override fun showEmpty() {
        ButterKnife.apply(swipeRefreshLayouts, STOP_REFRESHING)
        mSwitcher.showEmptyView()
    }

    override fun showLoading(pullToRefresh: Boolean) {
        if (!pullToRefresh) {
            mSwitcher.showProgressView()
        }
    }

    override fun showContent() {
        ButterKnife.apply(swipeRefreshLayouts, STOP_REFRESHING)
        mSwitcher.showContentView()
    }

    override fun showError(throwable: Throwable?) {
        showError(throwable, true)
    }

    override fun showDataWithoutInternetUpdatedMessage() {
        Toast.makeText(activity, R.string.message_no_connection, Toast.LENGTH_SHORT).show()
    }

    override fun showError(e: Throwable?, pullToRefresh: Boolean) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, e.message, e)
        }
        if (pullToRefresh) {
            ButterKnife.apply(swipeRefreshLayouts, STOP_REFRESHING)
        }
        mSwitcher.showErrorView()
    }

    override fun setData(weatherDataEntityList: MutableList<WeatherDataEntity?>?) {
        mWeatherDataListAdaper.swapWeatherList(weatherDataEntityList)
    }

    private fun customizeSwipeRefreshLayout() {
        ButterKnife.apply(swipeRefreshLayouts, SET_SWIPE_REFRESH_LAYOUT_LISTENER, this)
        for (swipeRefreshLayout in swipeRefreshLayouts) {
            swipeRefreshLayout.setColorSchemeResources(
                    R.color.colorAccent,
                    R.color.activated,
                    R.color.colorPrimary,
                    R.color.colorPrimaryDark)
        }
    }

    override fun onRefresh() {
        loadData(true)
    }

    override fun createPresenter(): WeatherDataListContract.Presenter {
        return WeatherDataListPresenterImpl()
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
        open fun onItemSelected(weatherDataId: Long)
    }

    companion object {
        private val TAG = WeatherDataListFragment::class.java.simpleName
        val SET_SWIPE_REFRESH_LAYOUT_LISTENER: ButterKnife.Setter<SwipeRefreshLayout?, OnRefreshListener?>? = ButterKnife.Setter { view: SwipeRefreshLayout?, listener: OnRefreshListener?, index: Int -> view.setOnRefreshListener(listener) }
        val STOP_REFRESHING: ButterKnife.Action<SwipeRefreshLayout?>? = ButterKnife.Action { view: SwipeRefreshLayout?, index: Int -> view.setRefreshing(false) }
        private val sDummyCallback: Callback? = Callback { id: Long -> }
    }
}
