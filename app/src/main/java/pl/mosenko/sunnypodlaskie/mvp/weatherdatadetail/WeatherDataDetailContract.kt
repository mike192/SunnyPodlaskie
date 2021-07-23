package pl.mosenko.sunnypodlaskie.mvp.weatherdatadetail

import pl.mosenko.sunnypodlaskie.mvp.MvpPresenter
import pl.mosenko.sunnypodlaskie.mvp.MvpView

/**
 * Created by syk on 06.06.17.
 */
interface WeatherDataDetailContract {
    interface View : MvpView {
        fun loadData(weatherDataDetailPresentationModel: WeatherDataDetailPresentationModel)
        fun showError(throwable: Throwable)
    }

    interface Presenter : MvpPresenter<View> {
        fun onViewCreated(weatherDataId: Long?)
    }
}
