package com.tigerbus.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.tigerbus.R;

public final class LocationUtil {

    private volatile static LocationUtil uniqueInstance;
    private LocationStatusListener locationStatusListener;
    private LocationManager locationManager;
    private Context context;

    private LocationUtil(Context context) {
        this.context = context;
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public static LocationUtil getInstance(Context context) {
        if (uniqueInstance == null) {
            synchronized (LocationUtil.class) {
                uniqueInstance = new LocationUtil(context);
            }
        }
        return uniqueInstance;
    }

    @SuppressLint("MissingPermission")
    public void initLocation(LocationStatusListener locationStatusListener) {
        this.locationStatusListener = locationStatusListener;
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.getBestProvider(getCriteria(), true);
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 3000, 0, new PrivateLocationListener());
        } else {
            Toast.makeText(context, context.getString(R.string.google_map_error1), Toast.LENGTH_LONG).show();
            context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));    //開啟設定頁面
        }
    }

    private Criteria getCriteria() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return criteria;
    }

    public interface LocationStatusListener {
        void onLocationChanged(Location location);

        //status=OUT_OF_SERVICE 供應商停止服務
        //status=TEMPORARILY_UNAVAILABLE 供應商暫停服務
        default void onStatusChanged(String s, int status, Bundle bundle){}

        default void onProviderEnabled(String s){}

        default void onProviderDisabled(String s){}
    }

    private final class PrivateLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            locationStatusListener.onLocationChanged(location);
        }

        @Override
        public void onStatusChanged(String s, int status, Bundle bundle) {
            locationStatusListener.onStatusChanged(s, status, bundle);
        }

        @Override
        public void onProviderEnabled(String s) {
            locationStatusListener.onProviderEnabled(s);
        }

        @Override
        public void onProviderDisabled(String s) {
            locationStatusListener.onProviderDisabled(s);
        }
    }

}
