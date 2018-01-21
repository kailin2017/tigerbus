package com.tigerbus.data.bus;

import android.os.Parcel;
import android.os.Parcelable;

import com.tigerbus.data.detail.Stop;

public final class RouteStop implements Parcelable {

    private BusRoute busRoute;
    private BusSubRoute busSubRoute;
    private Stop stop;

    public RouteStop(BusRoute busRoute, BusSubRoute busSubRoute, Stop stop) {
        this.busRoute = busRoute;
        this.busSubRoute = busSubRoute;
        this.stop = stop;
    }

    protected RouteStop(Parcel in) {
        busRoute = in.readParcelable(BusRoute.class.getClassLoader());
        busSubRoute = in.readParcelable(BusSubRoute.class.getClassLoader());
        stop = in.readParcelable(Stop.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(busRoute, flags);
        dest.writeParcelable(busSubRoute, flags);
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

    public BusRoute getBusRoute() {
        return busRoute;
    }

    public void setBusRoute(BusRoute busRoute) {
        this.busRoute = busRoute;
    }

    public BusSubRoute getBusSubRoute() {
        return busSubRoute;
    }

    public void setBusSubRoute(BusSubRoute busSubRoute) {
        this.busSubRoute = busSubRoute;
    }

    public Stop getStop() {
        return stop;
    }

    public void setStop(Stop stop) {
        this.stop = stop;
    }
}
