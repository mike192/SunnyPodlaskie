package pl.mosenko.sunnypodlaskie.mvp

import androidx.annotation.UiThread

interface MvpPresenter<V : MvpView?> {
    @UiThread
    fun attachView(view: V)

    @UiThread
    fun detachView()
}
