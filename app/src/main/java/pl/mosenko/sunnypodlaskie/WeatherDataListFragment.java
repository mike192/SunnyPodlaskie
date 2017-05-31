package pl.mosenko.sunnypodlaskie;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.novoda.merlin.Merlin;
import com.novoda.merlin.MerlinsBeard;
import com.novoda.merlin.registerable.connection.Connectable;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pl.mosenko.sunnypodlaskie.dto.List;
import pl.mosenko.sunnypodlaskie.dto.WeatherData;
import pl.mosenko.sunnypodlaskie.network.RxWeatherDataAPI;
import pl.mosenko.sunnypodlaskie.persistence.dao.WeatherDataEntityDAO;
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity;
import pl.mosenko.sunnypodlaskie.util.WeatherDtoEntityConverter;

import static android.content.ContentValues.TAG;

/**
 * Created by syk on 20.05.17.
 */

public class WeatherDataListFragment extends Fragment implements RxWeatherDataAPI.GetCurrentWeatherDataListCallback, WeatherAdaper.WeatherAdapterOnClickHandler {
    @Inject
    RxWeatherDataAPI rxWeatherDataAPI;
    @Inject
    WeatherDataEntityDAO weatherDataEntityDAO;
    @Inject
    MerlinsBeard merlinsBeard;
    @Inject
    Merlin merlin;

    @BindView(R.id.swipe_refresh_weather_layout)
    SwipeRefreshLayout mSwipeRefreshWeatherLayout;
    @BindView(R.id.recyclerview_current_weather)
    RecyclerView mCurrentWeatherRecycyler;
    @BindView(R.id.pb_loading_indicator)
    ProgressBar mLoadingIndicator;
    @BindView(R.id.no_cached_data)
    TextView noDataFoundTextView;

    private CompositeDisposable mCompositeDisposable;
    private WeatherAdaper mWeatherAdaper;
    private Unbinder mUnbinder;
    private OnWeatherDataItemClickListener mCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        injectFields();
        tryToInitializeCallbackField(context);
    }

    private void tryToInitializeCallbackField(Context context) {
        try {
            mCallback = (OnWeatherDataItemClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnWeatherDataItemClickListener");
        }
    }

    private void injectFields() {
        ((MainApplication) getActivity().getApplication()).getMainActivityComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflateFragmentLayout(inflater, container);
        bindGraphicalComponents(rootView);
        customizeRecyclerView();
        customizeSwipeRefreshLayout();
        initializeCompositeDisposable();
        synchronizeCurrentWeatherData();
        registerMerlinCallbacks();
        return rootView;
    }

    private void registerMerlinCallbacks() {
        merlin.registerConnectable(() -> {
            if (mSwipeRefreshWeatherLayout != null && !mLoadingIndicator.isShown()) {
                synchronizeCurrentWeatherData();
            }
        });
    }

    private void bindGraphicalComponents(View rootView) {
        mUnbinder = ButterKnife.bind(this, rootView);
    }

    private View inflateFragmentLayout(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_weather_data_list, container, false);
    }

    private void initializeCompositeDisposable() {
        mCompositeDisposable = new CompositeDisposable();
    }


    private void showLoading() {
        if (!mSwipeRefreshWeatherLayout.isRefreshing()) {
            mCurrentWeatherRecycyler.setVisibility(View.INVISIBLE);
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }
    }

    private void showWeatherDataView() {
        if (!mSwipeRefreshWeatherLayout.isRefreshing()) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            mCurrentWeatherRecycyler.setVisibility(View.VISIBLE);
        } else {
            mSwipeRefreshWeatherLayout.setRefreshing(false);
        }
    }

    private void synchronizeCurrentWeatherData() {
        showLoading();
        Disposable disposable = null;
        if (merlinsBeard.isConnected()) {
            disposable = rxWeatherDataAPI.getCurrentWeatherData(this);
        } else {
            disposable = weatherDataEntityDAO.rxQueryForAll()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(p -> showCurrentWeatherDataRecyclerView(p), e -> onDownloadWeatherDataError(e));
        }
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void onDownloadWeatherDataSuccess(final WeatherData weatherDataList) {
        for (List list : weatherDataList.getList()) {
            Log.d(TAG, list.toString());
        }
        Disposable disposable = Observable.fromCallable(() -> cacheCurrentWeatherDataOnDatabase(weatherDataList))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(p -> showCurrentWeatherDataRecyclerView(p), e -> onDownloadWeatherDataError(e));

        mCompositeDisposable.add(disposable);
    }

    @NonNull
    private java.util.List<WeatherDataEntity> cacheCurrentWeatherDataOnDatabase(WeatherData weatherDataList) throws java.sql.SQLException {
        java.util.List<WeatherDataEntity> weatherDataEntityList = WeatherDtoEntityConverter.convertToWeatherDataEntityList(weatherDataList.getList());
        weatherDataEntityDAO.deleteBuilder().delete();
        weatherDataEntityDAO.create(weatherDataEntityList);
        weatherDataEntityList = weatherDataEntityDAO.queryForAll();
        return weatherDataEntityList;
    }

    private void showCurrentWeatherDataRecyclerView(java.util.List<WeatherDataEntity> weatherDataEntityList) {
        if (!weatherDataEntityList.isEmpty()) {
            noDataFoundTextView.setVisibility(View.GONE);
            mWeatherAdaper.swapWeatherList(weatherDataEntityList);
            showWeatherDataView();
        } else {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            noDataFoundTextView.setVisibility(View.VISIBLE);
            mSwipeRefreshWeatherLayout.setRefreshing(false);
        }
    }

    @Override
    public void onDownloadWeatherDataError(Throwable networkError) {
        showWeatherDataView();
        Log.e(TAG, networkError.getMessage());
    }

    private void customizeSwipeRefreshLayout() {
        mSwipeRefreshWeatherLayout.setColorSchemeResources(
                R.color.colorAccent,
                R.color.activated,
                R.color.colorPrimary,
                R.color.colorPrimaryDark);
        mSwipeRefreshWeatherLayout.setOnRefreshListener(() ->
        {
            if (!mLoadingIndicator.isShown()) {
             synchronizeCurrentWeatherData();
            }
       //     mSwipeRefreshWeatherLayout.setRefreshing(false);
        });
    }

    private void customizeRecyclerView() {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mCurrentWeatherRecycyler.setLayoutManager(layoutManager);
        mCurrentWeatherRecycyler.setHasFixedSize(true);
        mWeatherAdaper = new WeatherAdaper(getActivity(), this);
        mCurrentWeatherRecycyler.setAdapter(mWeatherAdaper);
    }

    @Override
    public void onClick(long id) {
        if (!mSwipeRefreshWeatherLayout.isRefreshing()) {
            mCallback.onWeatherDataItemSelected(id);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onStop() {
        super.onStop();
        mCompositeDisposable.dispose();
    }

    @Override
    public void onResume() {
        super.onResume();
        merlin.bind();
    }

    @Override
    public void onPause() {
        merlin.unbind();
        super.onPause();
    }

    public interface OnWeatherDataItemClickListener {
        void onWeatherDataItemSelected(long weatherDataId);
    }
}
