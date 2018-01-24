package com.tigerbus.sqlite.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.tigerbus.sqlite.BriteApi;

import io.reactivex.annotations.NonNull;

@AutoValue
public abstract class CommonStopType implements Parcelable {
    public static final String TABLE = "COMMOD_STOP_TYPE";
    public static final String ID = "ID";
    public static final String TYPENAME = "TYPENAME";
    public static final String QUERY = "SELECT * FROM " + TABLE;

    public static final CommonStopType mapper(Cursor cursor) {
        int id = BriteApi.getInt(cursor, ID);
        String typeName = BriteApi.getString(cursor, TYPENAME);
        return CommonStopType.create(id, typeName);
    }

    public static final CommonStopType create(@NonNull int id, @NonNull String type) {
        return new AutoValue_CommonStopType(id, type);
    }

    public abstract int id();

    public abstract String type();

    public static final class SqlBuilder {
        private final ContentValues contentValues = new ContentValues();

        public SqlBuilder type(String type) {
            contentValues.put(TYPENAME, type);
            return this;
        }

        public ContentValues build() {
            return contentValues; // TODO defensive copy?
        }
    }
}
