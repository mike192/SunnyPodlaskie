package pl.mosenko.sunnypodlaskie.mvp.weatherdatalist

import pl.mosenko.sunnypodlaskie.mvp.MvpPresenter
import pl.mosenko.sunnypodlaskie.mvp.MvpView
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity

/**
 * Created by syk on 06.06.17.
 */
interface WeatherDataListContract {
    interface View : MvpView {
        fun showLoading(pullToRefresh: Boolean)
        fun showContent()
        fun showError(e: Throwable, pullToRefresh: Boolean)
        fun setData(data: MutableList<WeatherDataEntity>)
        fun loadData(pullToRefresh: Boolean)
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
