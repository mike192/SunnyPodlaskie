package pl.mosenko.sunnypodlaskie.mvp.weatherdatalist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hannesdorfmann.mosby3.mvp.lce.MvpLceFragment;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pl.aprilapps.switcher.Switcher;
import pl.mosenko.sunnypodlaskie.BuildConfig;
import pl.mosenko.sunnypodlaskie.R;
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity;

/**
 * Created by syk on 20.05.17.
 */

public class FragmentWeatherDataList extends MvpLceFragment<View, java.util.List<WeatherDataEntity>, WeatherDataListView, WeatherDataListPresenter>
        implements WeatherDataListView, SwipeRefreshLayout.OnRefreshListener, WeatherAdaper.WeatherDataClickedListener {

    private static final String TAG = FragmentWeatherDataList.class.getSimpleName();

    @BindView(R.id.FragmentWeatherDataList_TextView_Empty)
    TextView mTextViewEmpty;
    @BindView(R.id.FragmentWeatherDataList_ProgressBar)
    ProgressBar mProgressBarLoading;
    @BindView(R.id.FragmentWeatherDataList_RecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.FragmentWeatherDataList_TextView_Error)
    TextView mTextViewError;

    @BindView(R.id.FragmentWeatherDataList_SwipeRefreshLayout_Empty)
    SwipeRefreshLayout mSwipeRefreshLayoutEmpty;
    @BindView(R.id.FragmentWeatherDataList_SwipeRefreshLayout_Error)
    SwipeRefreshLayout mSwipeRefreshLayoutError;
    @BindView(R.id.FragmentWeatherDataList_SwipeRefreshLayout_Content)
    SwipeRefreshLayout mSwipeRefreshLayoutContent;
    @BindViews({
            R.id.FragmentWeatherDataList_SwipeRefreshLayout_Empty,
            R.id.FragmentWeatherDataList_SwipeRefreshLayout_Error,
            R.id.FragmentWeatherDataList_SwipeRefreshLayout_Content
    })
    java.util.List<SwipeRefreshLayout> mSwipeRefreshLayouts;

    static final ButterKnife.Setter<SwipeRefreshLayout, SwipeRefreshLayout.OnRefreshListener> SET_SWIPE_REFRESH_LAYOUT_LISTENER =
            (view, listener, index) -> view.setOnRefreshListener(listener);
    static final ButterKnife.Action<SwipeRefreshLayout>  STOP_REFRESHING = (view, index) -> view.setRefreshing(false);
    private static final Callback sDummyCallback = (id) -> {};
    private Switcher mSwitcher;
    private Callback mCallback = sDummyCallback;
    private WeatherAdaper mWeatherAdaper;
    private Unbinder mUnbinder;

    public FragmentWeatherDataList() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tryToInitializeCallbackField(getActivity());
    }

    private void tryToInitializeCallbackField(Context context) {
        try {
            mCallback = (Callback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement Callback");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflateFragmentLayout(inflater, container);
        bindGraphicalComponents(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        configureSwitcherView();
        customizeRecyclerView();
        customizeSwipeRefreshLayout();
    }

    private void configureSwitcherView() {
        mSwitcher = new Switcher.Builder(getContext())
                .addContentView(mSwipeRefreshLayoutContent)
                .addEmptyView(mSwipeRefreshLayoutEmpty)
                .addProgressView(mProgressBarLoading)
                .addErrorView(mSwipeRefreshLayoutError)
                .build();
    }

    private void customizeRecyclerView() {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mWeatherAdaper = new WeatherAdaper(getActivity(), this);
        mRecyclerView.setAdapter(mWeatherAdaper);
    }

    private void bindGraphicalComponents(View rootView) {
        mUnbinder = ButterKnife.bind(this, rootView);
    }

    private View inflateFragmentLayout(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_weather_data_list, container, false);
    }

    @Override
    public void showEmpty() {
        ButterKnife.apply(mSwipeRefreshLayouts, STOP_REFRESHING);
        mSwitcher.showEmptyView();
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        if (!pullToRefresh) {
            mSwitcher.showProgressView();
        }
    }

    @Override
    public void showContent() {
        ButterKnife.apply(mSwipeRefreshLayouts, STOP_REFRESHING);
        mSwitcher.showContentView();
    }

    @Override
    public void showError(Throwable throwable) {
        showError(throwable, true);
    }

    @Override
    public void showDataWithoutInternetUpdatedMessage() {
        Toast.makeText(getActivity(), R.string.message_no_connection, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, e.getMessage(), e);
        }
        if (pullToRefresh) {
            ButterKnife.apply(mSwipeRefreshLayouts, STOP_REFRESHING);
        }
        mSwitcher.showErrorView();
}

    @Override
    public void setData(java.util.List<WeatherDataEntity> weatherDataEntityList) {
        mWeatherAdaper.swapWeatherList(weatherDataEntityList);
    }


    private void customizeSwipeRefreshLayout() {
        ButterKnife.apply(mSwipeRefreshLayouts, SET_SWIPE_REFRESH_LAYOUT_LISTENER, this);
        for (SwipeRefreshLayout swipeRefreshLayout : mSwipeRefreshLayouts) {
            swipeRefreshLayout.setColorSchemeResources(
                    R.color.colorAccent,
                    R.color.activated,
                    R.color.colorPrimary,
                    R.color.colorPrimaryDark);
        }
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }

    @Override
    public WeatherDataListPresenter createPresenter() {
        return null;
    }

    @Override
    public void onWeatherDataItemClick(long id) {
            mCallback.onItemSelected(id);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = sDummyCallback;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        getPresenter().onPause();
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        getPresenter().loadData(pullToRefresh);
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null; //not used
    }

    public interface Callback {
        void onItemSelected(long weatherDataId);
    }
}
