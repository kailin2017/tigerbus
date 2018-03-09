package com.tigerbus;

import com.tigerbus.app.BaseActivity;

import android.content.Intent;
import android.os.Handler;

import junit.framework.Assert;

import org.robolectric.Shadows;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

public final class UnitTestUtil {

    public static UnitTestUtil unitTestUtil;

    public static UnitTestUtil getInstance() {
        if (unitTestUtil == null) {
            synchronized (UnitTestUtil.class) {
                if (unitTestUtil == null) {
                    unitTestUtil = new UnitTestUtil();
                }
            }
        }
        return unitTestUtil;
    }

    public void async2sync() {
        Function<Scheduler, Scheduler> function = s -> Schedulers.trampoline();
        RxAndroidPlugins.reset();
        RxAndroidPlugins.setMainThreadSchedulerHandler(function);
        RxJavaPlugins.reset();
        RxJavaPlugins.setIoSchedulerHandler(function);
    }

    public <T> TestObserver<T> getTestObserver(Observable<T> observable) {
        TestObserver<T> testObserver = new TestObserver<>();
        observable.subscribe(testObserver);
        return testObserver;
    }


}
