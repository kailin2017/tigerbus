package com.tigerbus.ui.route.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.location.Location;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tigerbus.R;
import com.tigerbus.TigerApplication;
import com.tigerbus.base.log.TlogType;
import com.tigerbus.data.bus.BusEstimateTime;
import com.tigerbus.data.bus.BusStopOfRoute;
import com.tigerbus.data.detail.PointType;
import com.tigerbus.data.detail.Stop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public final class ArrivalMapAdapter implements OnMapReadyCallback {

    private Map<String, BusEstimateTime> busEstimateTimeMap = new HashMap<>();
    private Bitmap markerIcon;
    private BusStopOfRoute busStopOfRoute;
    private GoogleMap googleMap;
    private MapFragment mapFragment;
    private Disposable estimateDisposable, locationDisposable;

    public ArrivalMapAdapter(
            @NonNull Context context,
            @NonNull BusStopOfRoute busStopOfRoute,
            @NonNull PublishSubject<ArrayList<BusEstimateTime>> publishSubject,
            @NonNull PublishSubject<Location> locationSubject) {
        this.markerIcon = drawableToBitmap(context.getDrawable(R.drawable.ic_nav_station));
        this.busStopOfRoute = busStopOfRoute;
        this.initMapFragment();
        this.estimateDisposable = publishSubject.subscribe(this::updateData);
        this.locationDisposable = locationSubject.subscribe(this::updateMapCamera);
    }


    private void initMapFragment() {
        PointType stopPosition = busStopOfRoute.getStops().get(0).getStopPosition();
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(getLatLng(stopPosition.getPositionLat(), stopPosition.getPositionLon())).zoom(15).build();
        GoogleMapOptions googleMapOptions = new GoogleMapOptions();
        googleMapOptions.camera(cameraPosition);
        mapFragment = MapFragment.newInstance(googleMapOptions);
        mapFragment.getMapAsync(this);
    }

    private void updateData(ArrayList<BusEstimateTime> busEstimateTimes) {
        Observable.fromIterable(busEstimateTimes)
                .subscribeOn(Schedulers.io())
                .filter(busEstimateTime -> busEstimateTime.getDirection().equalsIgnoreCase(busStopOfRoute.getDirection()))
                .filter(busEstimateTime -> busEstimateTime.getSubRouteUID() != null ?
                        busEstimateTime.getSubRouteUID().equalsIgnoreCase(busStopOfRoute.getSubRouteUID()) : true)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        busEstimateTime -> busEstimateTimeMap.put(busEstimateTime.getStopUID(), busEstimateTime),
                        throwable -> TigerApplication.printLog(TlogType.error, "", throwable.toString()),
                        this::updateMapMarks);
    }

    private void updateMapMarks() {
        if (googleMap == null || busEstimateTimeMap.size() == 0)
            return;
        Observable.fromIterable(busStopOfRoute.getStops())
                .doOnSubscribe(disposable -> googleMap.clear())
                .subscribe(stop -> {
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(getLatLng(stop.getStopPosition().getPositionLat(), stop.getStopPosition().getPositionLon()));
                    markerOptions.icon(BitmapDescriptorFactory.fromBitmap(markerIcon));
                    markerOptions.title(stop.getStopName().getZh_tw());
                    googleMap.addMarker(markerOptions);
                }, throwable -> TigerApplication.printLog(TlogType.error, "", throwable.toString()));
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap.Config c = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), c);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private void updateMapCamera(Location location) {
        TigerApplication.printLog(TlogType.error, "location", location.getLatitude() + "," + location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(getLatLng(location.getLatitude(), location.getLongitude()));
        googleMap.moveCamera(cameraUpdate);
    }

    public Disposable[] getDiaposables() {
        return new Disposable[]{estimateDisposable, locationDisposable};
    }

    public MapFragment getMapFragment() {
        return mapFragment;
    }

    private LatLng getLatLng(double latitude, double longitude) {
        return new LatLng(latitude, longitude);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        updateMapMarks();
    }
}
