package pl.mosenko.sunnypodlaskie.mvp

import androidx.annotation.UiThread
import java.lang.ref.WeakReference

open class MvpBasePresenter<V : MvpView?> : MvpPresenter<V> {

    private var viewRef: WeakReference<V?>? = null

    @UiThread
    override fun attachView(view: V) {
        viewRef = WeakReference(view)
    }

    @get:UiThread
    val view: V?
        get() = if (viewRef == null) null else viewRef!!.get()

    @get:UiThread
    val isViewAttached: Boolean
        get() = viewRef != null && viewRef!!.get() != null

    @UiThread
    override fun detachView() {
        if (viewRef != null) {
            viewRef!!.clear()
            viewRef = null
        }
    }
}
