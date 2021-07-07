package pl.mosenko.sunnypodlaskie.mvp.weatherdatadetail

import android.os.Bundle
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

/**
 * Created by syk on 06.06.17.
 */
interface WeatherDataDetailContract {
    interface View : MvpView {
        fun loadData(weatherDataDetailPresentationModel: WeatherDataDetailPresentationModel)
        fun showError(throwable: Throwable)
    }

    interface Presenter : MvpPresenter<View> {
        fun onViewCreated(savedInstanceState: Bundle)
    }
}
