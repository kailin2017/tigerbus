package com.tigerbus.sqlite.table;

import android.arch.persistence.db.SupportSQLiteDatabase;

import com.tigerbus.sqlite.data.CommonStop;

public final class CreateCommonStopTable extends CreateTableObj {

    @Override
    protected String getTableString() {
        return CommonStop.TABLE;
    }

    @Override
    protected String getColumnString() {
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
        return stringBuffer.toString();
    }

    @Override
    public void initDefaultData(SupportSQLiteDatabase database) {

    }
}
