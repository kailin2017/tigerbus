package com.tigerbus.sqlite.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.squareup.sqlbrite3.SqlBrite;
import com.tigerbus.TigerApplication;
import com.tigerbus.sqlite.BriteApi;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.UUID;

@AutoValue
public abstract class WeekStatus implements Parcelable ,BriteApi{
    public static final String TABLE = " WEEK_STATUS";
    public static final String ID = "ID";
    public static final String SUN = "SUN";
    public static final String MON = "MON";
    public static final String TUE = "TUE";
    public static final String WED = "WED";
    public static final String THU = "THU";
    public static final String FRI = "FRI";
    public static final String SAT = "SAT";
    public static final String QUERY = BriteApi.SELECT_FROM + TABLE;

    public static final WeekStatus mapper(Cursor cursor) {
        String id = BriteApi.getString(cursor, ID);
        boolean sun = BriteApi.getBoolean(cursor, SUN);
        boolean mon = BriteApi.getBoolean(cursor, MON);
        boolean tue = BriteApi.getBoolean(cursor, TUE);
        boolean wed = BriteApi.getBoolean(cursor, WED);
        boolean thu = BriteApi.getBoolean(cursor, THU);
        boolean fri = BriteApi.getBoolean(cursor, FRI);
        boolean sat = BriteApi.getBoolean(cursor, SAT);
        return create(id, sun, mon, tue, wed, thu, fri, sat);
    }

    public static final WeekStatus create(
            @NonNull String id, @NonNull boolean sun, @NonNull boolean mon, @NonNull boolean tue,
            @NonNull boolean wed, @NonNull boolean thu, @NonNull boolean fri, @NonNull boolean sat) {
        return new AutoValue_WeekStatus(id, sun, mon, tue, wed, thu, fri, sat);
    }

    public abstract String id();

    public abstract boolean sun();

    public abstract boolean mon();

    public abstract boolean tue();

    public abstract boolean wed();

    public abstract boolean thu();

    public abstract boolean fri();

    public abstract boolean sat();

    public static final class SqlBuilder {
        private final ContentValues contentValues = new ContentValues();

        public SqlBuilder(){
            contentValues.put(ID, UUID.randomUUID().toString());
        }

        public void sun(boolean b) {
            put(SUN, b);
        }

        public void mon(boolean b) {
            put(MON, b);
        }

        public void tue(boolean b) {
            put(TUE, b);
        }

        public void wed(boolean b) {
            put(WED, b);
        }

        public void thu(boolean b) {
            put(THU, b);
        }

        public void fri(boolean b) {
            put(FRI, b);
        }

        public void sat(boolean b) {
            put(SAT, b);
        }

        private void put(String key, boolean b) {
            contentValues.put(key, BriteApi.putBoolean(b));
        }

        public ContentValues build() {
            return contentValues; // TODO defensive copy?
        }
    }
}
