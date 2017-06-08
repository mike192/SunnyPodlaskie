package pl.mosenko.sunnypodlaskie.mvp.weatherdatalist;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;

import java.util.List;

import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity;

/**
 * Created by syk on 06.06.17.
 */

public interface WeatherDataListContract {

    interface View extends MvpLceView<List<WeatherDataEntity>> {
        void showEmpty();

        void showError(Throwable throwable);

        void showDataWithoutInternetUpdatedMessage();
    }

    interface Presenter extends MvpPresenter<View> {
        void loadData(boolean pullToRefresh);

        void onResume();

        void onPause();
    }
}
