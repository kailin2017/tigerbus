package com.tigerbus.sqlite.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.tigerbus.sqlite.BriteApi;

@AutoValue
public abstract class CommonStopType implements Comparable<CommonStopType>, Parcelable {
    public static final String TABLE = "common_type";
    public static final String ID = TABLE + "_id";
    public static final String TYPENAME = "typename";
    public static final String QUERY = BriteApi.SELECT_FROM + TABLE;

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

    @Override
    public int compareTo(@NonNull CommonStopType commonStopType) {
        return this.id() - commonStopType.id();
    }

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
