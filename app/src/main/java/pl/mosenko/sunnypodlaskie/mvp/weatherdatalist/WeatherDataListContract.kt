package pl.mosenko.sunnypodlaskie.mvp.weatherdatalist

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView
import pl.mosenko.sunnypodlaskie.persistence.entities.WeatherDataEntity

/**
 * Created by syk on 06.06.17.
 */
interface WeatherDataListContract {
    interface View : MvpLceView<MutableList<WeatherDataEntity?>?> {
        open fun showEmpty()
        open fun showError(throwable: Throwable?)
        open fun showDataWithoutInternetUpdatedMessage()
    }

    interface Presenter : MvpPresenter<View?> {
        open fun loadData(pullToRefresh: Boolean)
        open fun onResume()
        open fun onPause()
    }
}
