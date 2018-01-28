package com.tigerbus.sqlite.data;

import android.content.ContentValues;
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

    public static final String TABLE = "route_stop";
    public static final String ID = TABLE + "_id";
    public static final String STOP = "stop";
    public static final String ROUTE = "route";
    public static final String ROUTESUB = "routesub";
    public static final String QUERY = BriteApi.SELECT_FROM + TABLE;
    public static final String QUQRYID = QUERY + BriteApi.WHERE + ID + "='%s'";

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

    public static final String getKey(@NonNull RouteStop routeStop) {
        return getKey(routeStop.stop(), routeStop.busRoute(), routeStop.busSubRoute());
    }

    private static final String getKey(
            @NonNull Stop stop, @NonNull BusRoute busRoute, @NonNull BusSubRoute busSubRoute) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(stop.getStopUID());
        stringBuffer.append(busRoute.getRouteUID());
        stringBuffer.append(busSubRoute.getSubRouteUID());
        stringBuffer.append(busSubRoute.getDirection());
        return stringBuffer.toString();
    }

    public static final RouteStop create(
            @NonNull Stop stop, @NonNull BusRoute busRoute, @NonNull BusSubRoute busSubRoute) {
        String key = getKey(stop, busRoute, busSubRoute);
        return create(key, stop, busRoute, busSubRoute);
    }

    public static final RouteStop create(
            @NonNull String id, @NonNull Stop stop, @NonNull BusRoute busRoute, @NonNull BusSubRoute busSubRoute) {
        return new AutoValue_RouteStop(id, stop, busRoute, busSubRoute);
    }

    public abstract String id();

    public abstract Stop stop();

    public abstract BusRoute busRoute();

    public abstract BusSubRoute busSubRoute();

    public static final class SqlBuilder {
        private ContentValues contentValues = new ContentValues();

        public SqlBuilder id(String string) {
            contentValues.put(ID, string);
            return this;
        }

        public SqlBuilder stop(Stop stop) {
            contentValues.put(STOP, BriteApi.object2String(stop));
            return this;
        }

        public SqlBuilder busRoute(BusRoute busRoute) {
            contentValues.put(ROUTE, BriteApi.object2String(busRoute));
            return this;
        }


        public SqlBuilder busSubRoute(BusSubRoute busSubRoute) {
            contentValues.put(ROUTESUB, BriteApi.object2String(busSubRoute));
            return this;
        }

        public SqlBuilder routeStop(RouteStop routeStop) {
            contentValues.put(ID, routeStop.id());
            contentValues.put(STOP, BriteApi.object2String(routeStop.stop()));
            contentValues.put(ROUTE, BriteApi.object2String(routeStop.busRoute()));
            contentValues.put(ROUTESUB, BriteApi.object2String(routeStop.busSubRoute()));
            return this;
        }

        public ContentValues Build() {
            return contentValues;
        }
    }
}
