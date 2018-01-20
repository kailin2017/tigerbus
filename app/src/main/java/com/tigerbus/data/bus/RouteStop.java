package com.tigerbus.data.bus;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.tigerbus.data.detail.Stop;

import java.io.Serializable;

public final class RouteStop implements Parcelable{

    private  BusRoute route;
    private Stop stop;

    public RouteStop(BusRoute route, Stop stop) {
        this.route = route;
        this.stop = stop;
    }

    protected RouteStop(Parcel in) {
        route = in.readParcelable(BusRoute.class.getClassLoader());
        stop = in.readParcelable(Stop.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(route, flags);
        dest.writeParcelable(stop, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RouteStop> CREATOR = new Creator<RouteStop>() {
        @Override
        public RouteStop createFromParcel(Parcel in) {
            return new RouteStop(in);
        }

        @Override
        public RouteStop[] newArray(int size) {
            return new RouteStop[size];
        }
    };

    public BusRoute getRoute() {
        return route;
    }

    public void setRoute(BusRoute route) {
        this.route = route;
    }

    public Stop getStop() {
        return stop;
    }

    public void setStop(Stop stop) {
        this.stop = stop;
    }
}
