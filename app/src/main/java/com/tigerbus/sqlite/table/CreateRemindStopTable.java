package com.tigerbus.sqlite.table;

import android.arch.persistence.db.SupportSQLiteDatabase;

import com.tigerbus.sqlite.data.RemindStop;

public final class CreateRemindStopTable extends CreateTableObj {

    @Override
    protected String getTableString() {
        return RemindStop.TABLE;
    }

    @Override
    protected String getColumnString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(RemindStop.ID);
        stringBuffer.append(TEXT_NOT_NULL);
        stringBuffer.append(PRIMARY_KEY);
        stringBuffer.append(COMMA);

        stringBuffer.append(RemindStop.ISONE);
        stringBuffer.append(INTEGER_DEFAULT_0);
        stringBuffer.append(COMMA);

        stringBuffer.append(RemindStop.ISRUN);
        stringBuffer.append(INTEGER_DEFAULT_0);
        stringBuffer.append(COMMA);

        stringBuffer.append(RemindStop.REMIND_MINUTE);
        stringBuffer.append(INTEGER_NOT_NULL);
        stringBuffer.append(COMMA);

        stringBuffer.append(RemindStop.DURATION_START);
        stringBuffer.append(BIGINT_DEFAULT_0);
        stringBuffer.append(COMMA);

        stringBuffer.append(RemindStop.DURATION_END);
        stringBuffer.append(BIGINT_DEFAULT_0);
        stringBuffer.append(COMMA);

        stringBuffer.append(RemindStop.ROUTESTOP);
        stringBuffer.append(TEXT_NOT_NULL);
        stringBuffer.append(COMMA);

        stringBuffer.append(RemindStop.RUNWEEK);
        stringBuffer.append(TEXT_NOT_NULL);
        return stringBuffer.toString();
    }

    @Override
    public void initDefaultData(SupportSQLiteDatabase database) {

    }
}
