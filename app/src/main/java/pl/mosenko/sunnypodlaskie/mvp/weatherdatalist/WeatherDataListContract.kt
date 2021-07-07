package pl.mosenko.sunnypodlaskie.mvp.weatherdatalist

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity

/**
 * Created by syk on 06.06.17.
 */
interface WeatherDataListContract {
    interface View : MvpLceView<MutableList<WeatherDataEntity>> {
        fun showEmpty()
        fun showError(throwable: Throwable)
        fun showDataWithoutInternetUpdatedMessage()
    }

    interface Presenter : MvpPresenter<View> {
        fun loadData(pullToRefresh: Boolean)
        fun onResume()
        fun onPause()
    }
}
