package pl.mosenko.sunnypodlaskie.testutils

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.util.concurrent.Callable

/**
 * Created by syk on 12.06.17.
 */
class TrampolineSchedulerRule : TestRule {
    override fun apply(base: Statement?, description: Description?): Statement? {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                RxJavaPlugins.setIoSchedulerHandler { scheduler: Scheduler? -> Schedulers.trampoline() }
                RxJavaPlugins.setComputationSchedulerHandler { scheduler: Scheduler? -> Schedulers.trampoline() }
                RxJavaPlugins.setNewThreadSchedulerHandler { scheduler: Scheduler? -> Schedulers.trampoline() }
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler: Callable<Scheduler?>? -> Schedulers.trampoline() }
                try {
                    base.evaluate()
                } catch (throwable: Throwable) {
                    throwable.printStackTrace()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
    }
}
