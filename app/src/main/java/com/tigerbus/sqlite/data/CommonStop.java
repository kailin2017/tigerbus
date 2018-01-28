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
import java.util.UUID;

import io.reactivex.annotations.NonNull;

@AutoValue
public abstract class CommonStop implements Parcelable {

    public static final String TABLE = "common";
    public static final Collection<String> QUERY_TABLES =
            Arrays.asList(CommonStop.TABLE, RouteStop.TABLE, CommonStopType.TABLE);
    public static final String ID = TABLE + "_id";
    public static final String ROUTESTOP = "routestop";
    public static final String TYPE = "type";
    public static final String QUERY = BriteApi.SELECT_FROM + CommonStop.TABLE
            + BriteApi.INNER_JOIN + RouteStop.TABLE
            + " ON " + RouteStop.TABLE + BriteApi.DOT + RouteStop.ID + " = " + CommonStop.TABLE + "." + CommonStop.ROUTESTOP
            + BriteApi.INNER_JOIN + CommonStopType.TABLE
            + " ON " + CommonStopType.TABLE + BriteApi.DOT + CommonStopType.ID + " = " + CommonStop.TABLE + "." + CommonStop.TYPE;

    public static final CommonStop mapper(Cursor cursor) {
        String id = BriteApi.getString(cursor, ID);
        // RouteStop
        String rId = BriteApi.getString(cursor, ROUTESTOP);
        String rStopStr = BriteApi.getString(cursor, RouteStop.STOP);
        String rRouteStr = BriteApi.getString(cursor, RouteStop.ROUTE);
        String rSubRouteStr = BriteApi.getString(cursor, RouteStop.ROUTESUB);
        Stop rStop = TigerApplication.string2Object(Stop.class, rStopStr);
        BusRoute rRoute = TigerApplication.string2Object(BusRoute.class, rRouteStr);
        BusSubRoute rSubRoute = TigerApplication.string2Object(BusSubRoute.class, rSubRouteStr);
        RouteStop routeStop = RouteStop.create(rId, rStop, rRoute, rSubRoute);
        // CommonStopType
        int type = BriteApi.getInt(cursor, CommonStop.TYPE);
        String typeNeme = BriteApi.getString(cursor, CommonStopType.TYPENAME);
        CommonStopType commonStopType = CommonStopType.create(type, typeNeme);

        return create(id, routeStop, commonStopType);
    }


    public static final CommonStop create(
            @NonNull RouteStop routeStop, @NonNull CommonStopType commonStopType) {
        return create(UUID.randomUUID().toString(), routeStop, commonStopType);
    }

    public static final CommonStop create(
            @NonNull String id, @NonNull RouteStop routeStop, @NonNull CommonStopType commonStopType) {
        return new AutoValue_CommonStop(id, routeStop, commonStopType);
    }

    public abstract String id();

    public abstract RouteStop routeStop();

    public abstract CommonStopType commonStopType();

    public static final class SqlBuilder {
        private final ContentValues contentValues = new ContentValues();

        public SqlBuilder() {
            contentValues.put(ID, UUID.randomUUID().toString());
        }

        public SqlBuilder routeStop(String routeStop) {
            contentValues.put(ROUTESTOP, routeStop);
            return this;
        }

        public SqlBuilder type(int type) {
            contentValues.put(TYPE, type);
            return this;
        }

        public ContentValues build() {
            return contentValues;
        }
    }
}
