package pl.mosenko.sunnypodlaskie.mvp.weatherdatalist;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity;

/**
 * Created by syk on 03.06.17.
 */

public interface WeatherDataListPresenter extends MvpPresenter<WeatherDataListView> {
    void loadData(boolean pullToRefresh);

    void onResume();

    void onPause();
}
