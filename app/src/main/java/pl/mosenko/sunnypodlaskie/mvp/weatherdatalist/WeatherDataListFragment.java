package pl.mosenko.sunnypodlaskie.mvp.weatherdatalist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.util.List;

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

public class WeatherDataListFragment extends MvpFragment<WeatherDataListContract.View, WeatherDataListContract.Presenter>
        implements WeatherDataListContract.View, SwipeRefreshLayout.OnRefreshListener, WeatherDataListAdaper.WeatherDataClickedListener {

    private static final String TAG = WeatherDataListFragment.class.getSimpleName();

    @BindView(R.id.WeatherDataListFragment_TextView_Empty)
    TextView textViewEmpty;
    @BindView(R.id.WeatherDataListFragment_ProgressBar)
    ProgressBar progressBarLoading;
    @BindView(R.id.WeatherDataListFragment_RecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.FragmentWeatherDataList_TextView_Error)
    TextView textViewError;

    @BindView(R.id.WeatherDataListFragment_SwipeRefreshLayout_Empty)
    SwipeRefreshLayout swipeRefreshLayoutEmpty;
    @BindView(R.id.WeatherDataListFragment_SwipeRefreshLayout_Error)
    SwipeRefreshLayout swipeRefreshLayoutError;
    @BindView(R.id.FragmentWeatherDataList_SwipeRefreshLayout_Content)
    SwipeRefreshLayout swipeRefreshLayoutContent;
    @BindViews({
            R.id.WeatherDataListFragment_SwipeRefreshLayout_Empty,
            R.id.WeatherDataListFragment_SwipeRefreshLayout_Error,
            R.id.FragmentWeatherDataList_SwipeRefreshLayout_Content
    })
    List<SwipeRefreshLayout> swipeRefreshLayouts;

    static final ButterKnife.Setter<SwipeRefreshLayout, SwipeRefreshLayout.OnRefreshListener> SET_SWIPE_REFRESH_LAYOUT_LISTENER =
            (view, listener, index) -> view.setOnRefreshListener(listener);
    static final ButterKnife.Action<SwipeRefreshLayout>  STOP_REFRESHING = (view, index) -> view.setRefreshing(false);
    private static final Callback sDummyCallback = (id) -> {};
    private Switcher mSwitcher;
    private Callback mCallback = sDummyCallback;
    private WeatherDataListAdaper mWeatherDataListAdaper;
    private Unbinder mUnbinder;

    public WeatherDataListFragment() {
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
    public android.view.View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        android.view.View rootView = inflateFragmentLayout(inflater, container);
        bindGraphicalComponents(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(android.view.View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        configureSwitcherView();
        customizeRecyclerView();
        customizeSwipeRefreshLayout();
    }

    private void configureSwitcherView() {
        mSwitcher = new Switcher.Builder(getContext())
                .addContentView(swipeRefreshLayoutContent)
                .addEmptyView(swipeRefreshLayoutEmpty)
                .addProgressView(progressBarLoading)
                .addErrorView(swipeRefreshLayoutError)
                .build();
    }

    private void customizeRecyclerView() {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        mWeatherDataListAdaper = new WeatherDataListAdaper(getActivity(), this);
        recyclerView.setAdapter(mWeatherDataListAdaper);
    }

    private void bindGraphicalComponents(android.view.View rootView) {
        mUnbinder = ButterKnife.bind(this, rootView);
    }

    private android.view.View inflateFragmentLayout(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_weather_data_list, container, false);
    }

    @Override
    public void showEmpty() {
        ButterKnife.apply(swipeRefreshLayouts, STOP_REFRESHING);
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
        ButterKnife.apply(swipeRefreshLayouts, STOP_REFRESHING);
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
            ButterKnife.apply(swipeRefreshLayouts, STOP_REFRESHING);
        }
        mSwitcher.showErrorView();
}

    @Override
    public void setData(List<WeatherDataEntity> weatherDataEntityList) {
        mWeatherDataListAdaper.swapWeatherList(weatherDataEntityList);
    }

    private void customizeSwipeRefreshLayout() {
        ButterKnife.apply(swipeRefreshLayouts, SET_SWIPE_REFRESH_LAYOUT_LISTENER, this);
        for (SwipeRefreshLayout swipeRefreshLayout : swipeRefreshLayouts) {
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

    @NonNull
    @Override
    public WeatherDataListContract.Presenter createPresenter() {
        return new WeatherDataListPresenterImpl();
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

    public interface Callback {
        void onItemSelected(long weatherDataId);
    }
}