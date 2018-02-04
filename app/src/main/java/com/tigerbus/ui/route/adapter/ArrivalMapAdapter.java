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
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.tigerbus.R;
import com.tigerbus.TigerApplication;
import com.tigerbus.base.log.TlogType;
import com.tigerbus.data.bus.BusShape;
import com.tigerbus.data.bus.BusStopOfRoute;
import com.tigerbus.data.detail.PointType;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public final class ArrivalMapAdapter implements OnMapReadyCallback {

    private Bitmap markerIcon;
    private BusStopOfRoute busStopOfRoute;
    private ArrayList<PointType> pointTypes;
    private GoogleMap googleMap;
    private MapFragment mapFragment;

    public ArrivalMapAdapter(
            @NonNull Context context,
            @NonNull BusStopOfRoute busStopOfRoute) {
        this.markerIcon = drawableToBitmap(context.getDrawable(R.drawable.ic_place));
        this.busStopOfRoute = busStopOfRoute;
        this.initMapFragment();
    }

    public void setBusShapePointType(ArrayList<PointType> pointTypes) {
        this.pointTypes = pointTypes;
        initBusShapePointType();
    }

    private void initBusShapePointType(){
        if (googleMap == null || pointTypes==null)
            return;
        final PolylineOptions  polylineOptions = new PolylineOptions ();
        Observable.fromIterable(pointTypes).subscribe(
                pointType -> polylineOptions.add(getLatLng(pointType.getPositionLat(), pointType.getPositionLon())),
                throwable -> {
                }, () -> googleMap.addPolyline(polylineOptions));
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

    private void updateMapMarks() {
        if (googleMap == null)
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
        initBusShapePointType();
    }
}
