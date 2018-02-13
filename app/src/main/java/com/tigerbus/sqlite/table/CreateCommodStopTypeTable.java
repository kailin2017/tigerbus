package com.tigerbus.sqlite.table;

import com.tigerbus.sqlite.data.CommonStop;
import com.tigerbus.sqlite.data.CommonStopType;

public final class CreateCommodStopTypeTable extends CreateTableString {
    @Override
    protected String getTableString() {
        return CommonStopType.TABLE;
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
}
