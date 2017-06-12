package testutils;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by syk on 12.06.17.
 */

public class TrampolineSchedulerRule implements TestRule{
    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
                RxJavaPlugins.setComputationSchedulerHandler(scheduler -> Schedulers.trampoline());
                RxJavaPlugins.setNewThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
                RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
                try {
                    base.evaluate();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                } finally {
                    RxJavaPlugins.reset();
                    RxAndroidPlugins.reset();
                }
            }
        };
    }
}
