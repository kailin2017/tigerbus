package com.tigerbus.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.tigerbus.TigerApplication;
import com.tigerbus.base.log.TlogType;

public final class RemindService extends Service {

    private final static String TAG = RemindService.class.getSimpleName();
    private LocalBinder localBinder = new LocalBinder();

    @Override
    public IBinder onBind(Intent arg0) {
        TigerApplication.printLog(TlogType.debug, TAG, "onBind");
        return localBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        TigerApplication.printLog(TlogType.debug, TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        TigerApplication.printLog(TlogType.debug, TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        TigerApplication.printLog(TlogType.debug, TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        TigerApplication.printLog(TlogType.debug, TAG, "onDestroy");
        super.onDestroy();
    }

    public class LocalBinder extends Binder {
        RemindService getService() {
            return RemindService.this;
        }
    }
}
