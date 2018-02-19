package com.tigerbus.ui.route.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.tigerbus.R;
import com.tigerbus.TigerApplication;
import com.tigerbus.app.log.TlogType;
import com.tigerbus.data.bus.BusA1Data;
import com.tigerbus.data.bus.BusStopOfRoute;
import com.tigerbus.data.bus.BusSubRoute;
import com.tigerbus.data.detail.PointType;
import com.tigerbus.sqlite.data.RouteStop;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public final class ArrivalMapAdapter implements OnMapReadyCallback {

    private static final String TAG = ArrivalMapAdapter.class.getSimpleName();
    private Bitmap stationIcon;
    private Bitmap busIcon;
    private int lineColor;
    private BusSubRoute busSubRoute;
    private BusStopOfRoute busStopOfRoute;
    private ArrayList<PointType> pointTypes = new ArrayList<>();
    private GoogleMap googleMap;
    private MapFragment mapFragment;

    public ArrivalMapAdapter(
            RouteStop routeStop,
            @NonNull Context context,
            @NonNull BusSubRoute busSubRoute,
            @NonNull BusStopOfRoute busStopOfRoute,
            @NonNull PublishSubject<ArrayList<BusA1Data>> busA1DataSubject) {
        this.lineColor = context.getColor(R.color.colorAccent);
        this.stationIcon = drawableToBitmap(context.getDrawable(R.drawable.ic_place));
        this.busIcon = drawableToBitmap(context.getDrawable(R.drawable.ic_bus));
        this.busSubRoute = busSubRoute;
        this.busStopOfRoute = busStopOfRoute;
        this.initMapFragment(routeStop);
        busA1DataSubject.filter(busA1Data -> googleMap != null)
                .subscribe(busA1Data -> {
                    googleMap.clear();
                    initBusStation();
                    initBusShape();
                    updateBusLocation(busA1Data);
                });
    }

    private void initMapFragment(RouteStop routeStop) {
        PointType stopPosition = routeStop == null ?
                busStopOfRoute.getStops().get(0).getStopPosition() : routeStop.stop().getStopPosition();
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(getLatLng(stopPosition.getPositionLat(), stopPosition.getPositionLon())).zoom(15).build();
        GoogleMapOptions googleMapOptions = new GoogleMapOptions();
        googleMapOptions.camera(cameraPosition);
        mapFragment = MapFragment.newInstance(googleMapOptions);
        mapFragment.getMapAsync(this);
    }

    public void setBusShapePointType(ArrayList<PointType> pointTypes) {
        this.pointTypes.clear();
        this.pointTypes.addAll(pointTypes);
    }

    private void initBusShape() {
        final PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(lineColor);
        Observable.fromIterable(pointTypes)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        pointType -> polylineOptions.add(getLatLng(pointType.getPositionLat(), pointType.getPositionLon())),
                        throwable -> {
                        }, () -> googleMap.addPolyline(polylineOptions));
    }

    private void initBusStation() {
        Observable.fromIterable(busStopOfRoute.getStops())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stop -> addMarker(stop.getStopName().getZh_tw(), stationIcon,
                        stop.getStopPosition().getPositionLat(), stop.getStopPosition().getPositionLon())
                        , throwable -> TigerApplication.printLog(TlogType.error, TAG, throwable.toString()));
    }

    private void updateBusLocation(ArrayList<BusA1Data> busA1Datas) {
        Observable.fromIterable(busA1Datas)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(busA1Data -> busA1Data.getDirection().equalsIgnoreCase(busSubRoute.getDirection()) &&
                        busA1Data.getSubRouteUID().equalsIgnoreCase(busSubRoute.getSubRouteUID()))
                .subscribe(busA1Data -> addMarker(busA1Data.getPlateNumb(), busIcon,
                        busA1Data.getBusPosition().getPositionLat(), busA1Data.getBusPosition().getPositionLon())
                        , throwable -> TigerApplication.printLog(TlogType.error, TAG, throwable.toString()));
    }

    private void addMarker(String title, Bitmap bitmap, double latitude, double longitude) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title(title);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
        markerOptions.position(getLatLng(latitude, longitude));
        googleMap.addMarker(markerOptions);
    }


    private Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap.Config c = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), c);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
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
    }
}
