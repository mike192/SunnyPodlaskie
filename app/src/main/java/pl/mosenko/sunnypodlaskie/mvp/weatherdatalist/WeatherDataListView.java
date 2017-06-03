package pl.mosenko.sunnypodlaskie.mvp.weatherdatalist;

import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;

import java.util.List;

import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity;

/**
 * Created by syk on 03.06.17.
 */

public interface WeatherDataListView extends MvpLceView<List<WeatherDataEntity>> {
    void showEmpty();

    void showError(Throwable throwable);

    void showDataWithoutInternetUpdatedMessage();
}
