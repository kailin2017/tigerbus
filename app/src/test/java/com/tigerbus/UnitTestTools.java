package com.tigerbus;

import com.tigerbus.base.BaseActivity;

import android.content.Intent;
import android.os.Handler;

import junit.framework.Assert;

import org.robolectric.Shadows;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

public final class UnitTestTools {

    private final static String TAG = UnitTestTools.class.getSimpleName();
    private static Handler handler;

    /**
     * 非同步替換為同步
     */
    public static void async2sync() {
        Function<Scheduler, Scheduler> function = s -> Schedulers.trampoline();
        RxAndroidPlugins.reset();
        RxAndroidPlugins.setMainThreadSchedulerHandler(function);
        RxJavaPlugins.reset();
        RxJavaPlugins.setIoSchedulerHandler(function);
    }

    public static <T> void rxJavaTest(Consumer<T> consumer, Observable<T>... observables) {
        for (Observable<T> o : observables) {
            o.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(consumer, throwable -> UnitTestTools.printLog(throwable.toString()));
        }
    }

    public static void printLog(String message) {
        System.out.println(message);
    }

    public static <T extends BaseActivity>void startActivityTest(T startAcitvity, Class aimsAcitvity) {
        printLog("startAcitvity:" + startAcitvity.getLocalClassName() + ", aimsAcitvity:" + aimsAcitvity.getSimpleName());
        Intent expectedIntent = new Intent(startAcitvity, aimsAcitvity);
        Intent actualIntent = Shadows.shadowOf(startAcitvity).getNextStartedActivity();
        Assert.assertEquals(expectedIntent, actualIntent);
    }

    public static void delay(int s, Runnable... runnables) throws InterruptedException {
        Thread.sleep(s * 1000);
        for (Runnable r : runnables) r.run();
    }

}
