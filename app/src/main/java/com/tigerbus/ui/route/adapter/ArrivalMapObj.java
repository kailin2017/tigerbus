package com.tigerbus.ui.route.adapter;

import com.google.android.gms.maps.MapFragment;

public final class ArrivalMapObj {

    private MapFragment mapFragment;
    private String pagerTitle;

    public ArrivalMapObj(String pagerTitle, ArrivalMapAdapter arrivalMapAdapter) {
        setPagerTitle(pagerTitle);
        setView(arrivalMapAdapter.getMapFragment());
    }

    public MapFragment getView() {
        return mapFragment;
    }

    public void setView(MapFragment fragment) {
        this.mapFragment = fragment;
    }

    public String getPagerTitle() {
        return pagerTitle;
    }

    public void setPagerTitle(String pagerTitle) {
        this.pagerTitle = pagerTitle;
    }
}
