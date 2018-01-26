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

import java.util.Arrays;
import java.util.Collection;

@AutoValue
public abstract class RemindStop implements Parcelable {
    public static final String TABLE = " REMIND_STOP";
    public static final Collection<String> QUERY_TABLES =
            Arrays.asList(RemindStop.TABLE, RouteStop.TABLE, WeekStatus.TABLE);
    public static final String ID = "ID";
    public static final String ISONE = "ISONE";
    public static final String ISRUN = "ISRUN";
    public static final String REMIND_MINUTE = "REMIND_MINUTE";
    public static final String DURATION_START = "DURATION_START";
    public static final String DURATION_END = "DURATION_END";
    public static final String ROUTESTOP = "ROUTESTOP";
    public static final String RUNWEEK = "RUNWEEK";

    private static final String QUERY = BriteApi.SELECT_FROM + TABLE
            + BriteApi.INNER_JOIN + RouteStop.TABLE
            + " ON " + RemindStop.TABLE + BriteApi.DOT + RemindStop.ROUTESTOP + "=" + RouteStop.TABLE + BriteApi.DOT + RouteStop.ID
            + BriteApi.INNER_JOIN + WeekStatus.TABLE
            + " ON " + RemindStop.TABLE + BriteApi.DOT + RemindStop.RUNWEEK + "=" + WeekStatus.TABLE + BriteApi.DOT + WeekStatus.ID
            + BriteApi.WHERE + RemindStop.TABLE + BriteApi.DOT + RemindStop.ISRUN + "=" + BriteApi.BOOLEAN_TRUE;

    public static final String QUERY1 = QUERY + BriteApi.AND + ISONE + "=" + BriteApi.BOOLEAN_FALSE;
    public static final String QUERY2 = QUERY + BriteApi.AND + ISONE + "=" + BriteApi.BOOLEAN_TRUE + BriteApi.AND + " %s = %d ";

    public static final RemindStop mapper(Cursor cursor) {
        int id = BriteApi.getInt(cursor, TABLE + BriteApi.DOT + ID);


        boolean isOne = BriteApi.getBoolean(cursor, ISONE);
        boolean isRun = BriteApi.getBoolean(cursor, ISRUN);
        int remindMinute = BriteApi.getInt(cursor, REMIND_MINUTE);
        long durationStart = BriteApi.getLong(cursor, DURATION_START);
        long durationEnd = BriteApi.getLong(cursor, DURATION_END);

        // RouteStop
        String rId = BriteApi.getString(cursor, RouteStop.ID);
        String rStopStr = BriteApi.getString(cursor, RouteStop.STOP);
        String rRouteStr = BriteApi.getString(cursor, RouteStop.ROUTE);
        String rSubRouteStr = BriteApi.getString(cursor, RouteStop.ROUTESUB);
        Stop rStop = TigerApplication.string2Object(Stop.class, rStopStr);
        BusRoute rRoute = TigerApplication.string2Object(BusRoute.class, rRouteStr);
        BusSubRoute rSubRoute = TigerApplication.string2Object(BusSubRoute.class, rSubRouteStr);
        RouteStop routeStop = RouteStop.create(rId, rStop, rRoute, rSubRoute);
        // WeekStatus
        String weekId = BriteApi.getString(cursor, WeekStatus.TABLE + BriteApi.DOT + WeekStatus.ID);
        boolean weekSUN = BriteApi.getBoolean(cursor, WeekStatus.SUN);
        boolean weekMON = BriteApi.getBoolean(cursor, WeekStatus.MON);
        boolean weekTUE = BriteApi.getBoolean(cursor, WeekStatus.TUE);
        boolean weekWED = BriteApi.getBoolean(cursor, WeekStatus.WED);
        boolean weekTHU = BriteApi.getBoolean(cursor, WeekStatus.THU);
        boolean weekFRI = BriteApi.getBoolean(cursor, WeekStatus.FRI);
        boolean weekSAT = BriteApi.getBoolean(cursor, WeekStatus.SAT);
        WeekStatus runWeek = WeekStatus.create(weekId, weekSUN, weekMON, weekTUE, weekWED, weekTHU, weekFRI, weekSAT);

        return create(id, isOne, isRun, remindMinute, durationStart, durationEnd, routeStop, runWeek);
    }

    public static final RemindStop create(
            @NonNull int id, @NonNull boolean isOne, @NonNull boolean isRun, @NonNull int remindMinute,
            @NonNull long start, @NonNull long end, @NonNull RouteStop routeStop, @NonNull WeekStatus weekStatus) {
        return new AutoValue_RemindStop(id, isOne, isRun, remindMinute, start, end, routeStop, weekStatus);
    }

    public abstract int id();

    public abstract boolean isOne();

    public abstract boolean isRun();

    public abstract int remindMinute();

    public abstract long durationStart();

    public abstract long durationEnd();

    public abstract RouteStop routeStop();

    public abstract WeekStatus weekStatus();

    public static final class SqlBuilder{
        private ContentValues contentValues = new ContentValues();

        public SqlBuilder isOne(boolean isOne){
            contentValues.put(ISONE,BriteApi.putBoolean(isOne));
            return this;
        }

        public SqlBuilder isRun(boolean isRun){
            contentValues.put(ISRUN,BriteApi.putBoolean(isRun));
            return this;
        }

        public SqlBuilder remindMinute(int remindMinute){
            contentValues.put(REMIND_MINUTE, remindMinute);
            return this;
        }

        public SqlBuilder durationStart(long durationStart){
            contentValues.put(DURATION_START,durationStart);
            return this;
        }

        public SqlBuilder durationEnd(long durationEnd){
            contentValues.put(DURATION_END,durationEnd);
            return this;
        }

        public SqlBuilder routeStop(String routeStop){
            contentValues.put(ROUTESTOP,routeStop);
            return this;
        }

        public SqlBuilder weekStatus(String weekStatus){
            contentValues.put(RUNWEEK,weekStatus);
            return this;
        }

        public ContentValues build(){
            return contentValues;
        }
    }
}
