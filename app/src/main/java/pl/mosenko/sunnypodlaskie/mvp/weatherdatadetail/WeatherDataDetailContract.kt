package pl.mosenko.sunnypodlaskie.mvp.weatherdatadetail

import android.os.Bundle
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

/**
 * Created by syk on 06.06.17.
 */
interface WeatherDataDetailContract {
    interface View : MvpView {
        open fun loadData(weatherDataDetailPresentationModel: WeatherDataDetailPresentationModel?)
        open fun showError(throwable: Throwable?)
    }

    interface Presenter : MvpPresenter<View?> {
        open fun onViewCreated(savedInstanceState: Bundle?)
    }
}
