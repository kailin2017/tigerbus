package com.tigerbus.sqlite.data;

import android.database.Cursor;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.tigerbus.TigerApplication;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.data.bus.BusSubRoute;
import com.tigerbus.data.detail.Stop;
import com.tigerbus.sqlite.BriteApi;

@AutoValue
public abstract class RouteStop implements Parcelable {

    public static final String TABLE = "ROUTE_STOP";
    public static final String ID = "ID";
    public static final String STOP = "STOP";
    public static final String ROUTE = "ROUTE";
    public static final String ROUTESUB = "ROUTESUB";
    public static final String QUERY = BriteApi.SELECT_FROM + TABLE;

    public static final RouteStop mapper(Cursor cursor) {
        String id = BriteApi.getString(cursor, ID);
        String stopString = BriteApi.getString(cursor, STOP);
        String routeString = BriteApi.getString(cursor, ROUTE);
        String subRouteString = BriteApi.getString(cursor, ROUTESUB);

        Stop stop = TigerApplication.string2Object(Stop.class, stopString);
        BusRoute busRoute = TigerApplication.string2Object(BusRoute.class, routeString);
        BusSubRoute busSubRoute = TigerApplication.string2Object(BusSubRoute.class, subRouteString);
        return create(id, stop, busRoute, busSubRoute);
    }

    public static final RouteStop create(
            @NonNull String id, @NonNull Stop stop, @NonNull BusRoute busRoute, @NonNull BusSubRoute busSubRoute) {
        return new AutoValue_RouteStop(id, stop, busRoute, busSubRoute);
    }

    public abstract String id();

    public abstract Stop stop();

    public abstract BusRoute busRoute();

    public abstract BusSubRoute busSubRoute();

}
