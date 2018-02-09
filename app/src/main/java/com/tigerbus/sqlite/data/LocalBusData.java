package com.tigerbus.sqlite.data;


import android.content.ContentValues;
import android.database.Cursor;

import com.google.auto.value.AutoValue;
import com.tigerbus.data.bus.BusRoute;
import com.tigerbus.sqlite.BriteApi;

import java.util.ArrayList;

@AutoValue
public abstract class LocalBusData {

    public final static String TABLE = "local_bus_data";
    public final static String ID = TABLE + "_id";
    public final static String VERSIONID = "version_id";
    public final static String CITYNAME = "city_name";
    public final static String BUSROURES = "bus_routes";

    public final static LocalBusData create(String cityName, int versionId, ArrayList<BusRoute> busRoutes) {
        return new AutoValue_LocalBusData(cityName, versionId, busRoutes);
    }

    public final static LocalBusData mapper(Cursor cursor) {
        String cityName = BriteApi.getString(cursor, CITYNAME);
        int versionId = BriteApi.getInt(cursor, VERSIONID);
        String busRoutesString = BriteApi.getString(cursor, BUSROURES);
        ArrayList<BusRoute> busRoutes = BriteApi.string2Object(ArrayList.class, busRoutesString);
        return create(cityName, versionId, busRoutes);
    }

    public final static String getQueryString() {
        return String.format(BriteApi.SELECT_FROM_S, TABLE);
    }

    public abstract String cityName();

    public abstract int versionId();

    public abstract ArrayList<BusRoute> busRoutes();

    public final static class SqlBuilder {
        private final ContentValues contentValues = new ContentValues();

        public SqlBuilder cityName(String cityName) {
            contentValues.put(CITYNAME, cityName);
            return this;
        }

        public SqlBuilder versionId(int versionId) {
            contentValues.put(VERSIONID, versionId);
            return this;
        }

        public SqlBuilder busRoutes(ArrayList<BusRoute> busRoutes) {
            contentValues.put(BUSROURES, BriteApi.object2String(busRoutes));
            return this;
        }

        public ContentValues build() {
            return contentValues;
        }
    }
}
