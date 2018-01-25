package com.tigerbus.sqlite.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.tigerbus.TigerApplication;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.data.bus.BusSubRoute;
import com.tigerbus.data.detail.Stop;
import com.tigerbus.sqlite.BriteApi;

import java.util.Arrays;
import java.util.Collection;

import io.reactivex.annotations.NonNull;

@AutoValue
public abstract class CommonStop implements Parcelable {

    public static final Collection<String> QUERY_TABLES = Arrays.asList(CommonStop.TABLE, CommonStopType.TABLE);
    public static final String TABLE = "COMMOD_STOP";
    public static final String ID = "ID";
    public static final String STOP = "STOP";
    public static final String ROUTE = "ROUTE";
    public static final String ROUTESUB = "ROUTESUB";
    public static final String TYPE = "TYPE";
    public static final String QUERY = ""
            + BriteApi.SELECT_FROM + CommonStop.TABLE + BriteApi.INNER_JOIN + CommonStopType.TABLE
            + " ON " + CommonStopType.TABLE + BriteApi.DOT + CommonStopType.ID + " = " + CommonStop.TABLE + "." + CommonStop.TYPE;

    public static final CommonStop mapper(Cursor cursor) {
        String id = BriteApi.getString(cursor, ID);
        String stopString = BriteApi.getString(cursor, STOP);
        String routeString = BriteApi.getString(cursor, ROUTE);
        String subRouteString = BriteApi.getString(cursor, ROUTESUB);

        Stop stop = TigerApplication.string2Object(Stop.class, stopString);
        BusRoute busRoute = TigerApplication.string2Object(BusRoute.class, routeString);
        BusSubRoute busSubRoute = TigerApplication.string2Object(BusSubRoute.class, subRouteString);

        int type = BriteApi.getInt(cursor, CommonStop.TYPE);
        String typeNeme = BriteApi.getString(cursor, CommonStopType.TYPENAME);
        CommonStopType commonStopType = CommonStopType.create(type,typeNeme);

        return create(id, stop, busRoute, busSubRoute, commonStopType);
    }

    public static final String getKey(CommonStop commonStop) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(commonStop.stop().getStopUID());
        stringBuffer.append(commonStop.busRoute().getRouteUID());
        stringBuffer.append(commonStop.busSubRoute().getSubRouteUID());
        stringBuffer.append(commonStop.busSubRoute().getDirection());
        return stringBuffer.toString();
    }

    public static final CommonStop create(
            @NonNull String id, @NonNull Stop stop, @NonNull BusRoute busRoute,
            @NonNull BusSubRoute busSubRoute, @NonNull CommonStopType commonStopType) {
        return new AutoValue_CommonStop(id, stop, busRoute, busSubRoute, commonStopType);
    }

    public abstract String id();

    public abstract Stop stop();

    public abstract BusRoute busRoute();

    public abstract BusSubRoute busSubRoute();

    public abstract CommonStopType commonStopType();

    public static final class SqlBuilder {
        private final ContentValues contentValues = new ContentValues();

        public SqlBuilder init(CommonStop commonStop) {
            contentValues.put(ID, getKey(commonStop));
            contentValues.put(STOP, TigerApplication.object2String(commonStop.stop()));
            contentValues.put(ROUTE, TigerApplication.object2String(commonStop.busRoute()));
            contentValues.put(ROUTESUB, TigerApplication.object2String(commonStop.busSubRoute()));
            return this;
        }

        public SqlBuilder type(int type) {
            contentValues.put(TYPE, type);
            return this;
        }

        public ContentValues build() {
            return contentValues; // TODO defensive copy?
        }
    }
}
