package pl.mosenko.sunnypodlaskie.mvp.weatherdatadetail;

import android.os.Bundle;
import android.view.View;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity;

/**
 * Created by syk on 06.06.17.
 */

public interface WeatherDataDetailContract {
    interface View extends MvpView {

        void loadData(WeatherDataDetailPresentationModel weatherDataDetailPresentationModel);

        void showError(Throwable throwable);
    }
    interface Presenter extends MvpPresenter<View> {

        void onViewCreated(Bundle savedInstanceState);
    }
}
