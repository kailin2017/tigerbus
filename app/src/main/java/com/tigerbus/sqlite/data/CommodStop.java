package com.tigerbus.sqlite.data;

import android.content.ContentValues;
import android.database.Cursor;

import com.google.auto.value.AutoValue;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.data.bus.BusSubRoute;
import com.tigerbus.data.detail.Stop;
import com.tigerbus.sqlite.BriteApi;
import com.tigerbus.sqlite.api.SqlDataUtil;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

@AutoValue
public abstract class CommodStop implements Serializable {
    public static final String TABLE = "COMMOD_STOP";
    public static final Collection<String> QUERY_TABLES =
            Arrays.asList(CommodStop.TABLE, CommodStopType.TABLE);

    public static final String ID = "ID";
    public static final String STOP = "STOP";
    public static final String ROUTE = "ROUTE";
    public static final String ROUTESUB = "ROUTESUB";
    public static final String TYPE = "TYPE";

    public static final String QUERY = ""
            + "SELECT * FROM " + CommodStop.TABLE + " INNER JOIN " + CommodStopType.TABLE
            + " ON " + CommodStopType.TABLE + "." + CommodStopType.ID + " = " + CommodStop.TABLE + "." + CommodStop.TYPE;

    public static final CommodStop mapper(Cursor cursor) {
        String id = BriteApi.getString(cursor, ID);
        String stopString = BriteApi.getString(cursor, STOP);
        String routeString = BriteApi.getString(cursor, ROUTE);
        String subRouteString = BriteApi.getString(cursor, ROUTESUB);
        String typeName = BriteApi.getString(cursor, CommodStopType.TYPENAME);

        Stop stop = SqlDataUtil.string2Object(Stop.class, stopString);
        BusRoute busRoute = SqlDataUtil.string2Object(BusRoute.class, routeString);
        BusSubRoute busSubRoute = SqlDataUtil.string2Object(BusSubRoute.class, subRouteString);

        return create(id, stop, busRoute, busSubRoute, typeName);
    }

    public static final String getKey(CommodStop commodStop) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(commodStop.stop().getStopUID());
        stringBuffer.append(commodStop.busRoute().getRouteUID());
        stringBuffer.append(commodStop.busSubRoute().getSubRouteUID());
        stringBuffer.append(commodStop.busSubRoute().getDirection());
        return stringBuffer.toString();
    }

    public static final CommodStop create(String id, Stop stop, BusRoute busRoute, BusSubRoute busSubRoute, String type) {
        return new AutoValue_CommodStop(id, stop, busRoute, busSubRoute, type);
    }

    public abstract String id();

    public abstract Stop stop();

    public abstract BusRoute busRoute();

    public abstract BusSubRoute busSubRoute();

    public abstract String type();

    public static final class SqlBuilder {
        private final ContentValues contentValues = new ContentValues();

        public SqlBuilder init(CommodStop commodStop) {
            contentValues.put(ID, getKey(commodStop));
            contentValues.put(STOP, SqlDataUtil.object2String(commodStop.stop()));
            contentValues.put(ROUTE, SqlDataUtil.object2String(commodStop.busRoute()));
            contentValues.put(ROUTESUB, SqlDataUtil.object2String(commodStop.busSubRoute()));
            return this;
        }

        public SqlBuilder type(String type) {
            contentValues.put(TYPE, type);
            return this;
        }

        public ContentValues build() {
            return contentValues; // TODO defensive copy?
        }
    }
}
