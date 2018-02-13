package com.tigerbus.sqlite;

import com.tigerbus.sqlite.data.CommonStop;

/**
 * Created by sinopac on 2018/2/13.
 */

public interface DBString {

    String COMMA = ",";
    String CREATE_TABLE = "CREATE TABLE ";
    String PRIMARY_KEY = " PRIMARY KEY ";
    String AUTOINCREMENT = " AUTOINCREMENT ";
    String TEXT_NOT_NULL = " TEXT NOT NULL ";
    String INTEGER_NOT_NULL = " INTEGER NOT NULL ";
    String INTEGER_DEFAULT_0 = " INTEGER DEFAULT 0 ";
    String BIGINT_NOT_NULL = " BIGINT NOT NULL ";
    String BIGINT_DEFAULT_0 = " BIGINT DEFAULT 0 ";

    default String getCreateTableString() {
        return "CREATE TABLE %s ( %s )";
    }

    default String getCreateCommodStopTypeTable() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(CommonStop.ID);
        stringBuffer.append(TEXT_NOT_NULL);
        stringBuffer.append(PRIMARY_KEY);
        stringBuffer.append(COMMA);

        stringBuffer.append(CommonStop.ROUTESTOP);
        stringBuffer.append(TEXT_NOT_NULL);
        stringBuffer.append(COMMA);

        stringBuffer.append(CommonStop.TYPE);
        stringBuffer.append(TEXT_NOT_NULL);

        return String.format(getCreateTableString(), CommonStop.TABLE, stringBuffer.toString());
    }


}
