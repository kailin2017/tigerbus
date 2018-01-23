package com.tigerbus.sqlite.data;

import android.content.ContentValues;
import android.database.Cursor;

import com.google.auto.value.AutoValue;
import com.tigerbus.sqlite.BriteApi;

import java.io.Serializable;

@AutoValue
public abstract class CommodStopType implements Serializable {
    public static final String TABLE = "COMMOD_STOP_TYPE";
    public static final String ID = "ID";
    public static final String TYPENAME = "TYPENAME";
    public static final String QUERY = "SELECT * FROM " + TABLE;

    public static final CommodStopType mapper(Cursor cursor) {
        long id = BriteApi.getInt(cursor, ID);
        String typeName = BriteApi.getString(cursor, TYPENAME);
        cursor.close();
        return CommodStopType.create(id, typeName);
    }

    public static final CommodStopType create(long id, String type) {
        return new AutoValue_CommodStopType(id, type);

    }

    public abstract long id();

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
