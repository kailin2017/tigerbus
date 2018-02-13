package com.tigerbus.sqlite.table;

import com.tigerbus.sqlite.data.WeekStatus;

/**
 * Created by sinopac on 2018/2/13.
 */

public final class CreateWeekStatusTable extends CreateTableString {

    @Override
    protected String getTableString() {
        return WeekStatus.TABLE;
    }

    @Override
    protected String getColumnString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(WeekStatus.ID);
        stringBuffer.append(TEXT_NOT_NULL);
        stringBuffer.append(PRIMARY_KEY);
        stringBuffer.append(COMMA);

        stringBuffer.append(WeekStatus.SUN);
        stringBuffer.append(INTEGER_DEFAULT_0);
        stringBuffer.append(COMMA);

        stringBuffer.append(WeekStatus.MON);
        stringBuffer.append(INTEGER_DEFAULT_0);
        stringBuffer.append(COMMA);

        stringBuffer.append(WeekStatus.THU);
        stringBuffer.append(INTEGER_DEFAULT_0);
        stringBuffer.append(COMMA);

        stringBuffer.append(WeekStatus.WED);
        stringBuffer.append(INTEGER_DEFAULT_0);
        stringBuffer.append(COMMA);

        stringBuffer.append(WeekStatus.TUE);
        stringBuffer.append(INTEGER_DEFAULT_0);
        stringBuffer.append(COMMA);

        stringBuffer.append(WeekStatus.FRI);
        stringBuffer.append(INTEGER_DEFAULT_0);
        stringBuffer.append(COMMA);

        stringBuffer.append(WeekStatus.SAT);
        stringBuffer.append(INTEGER_DEFAULT_0);
        stringBuffer.append(COMMA);

        return stringBuffer.toString();
    }
}
