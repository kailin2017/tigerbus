package com.tigerbus.sqlite.table;

import com.tigerbus.sqlite.data.RouteStop;

public final class CreateRouteStopTable extends CreateTableString {

    @Override
    protected String getTableString() {
        return RouteStop.TABLE;
    }

    @Override
    protected String getColumnString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(RouteStop.ID);
        stringBuffer.append(TEXT_NOT_NULL);
        stringBuffer.append(PRIMARY_KEY);
        stringBuffer.append(COMMA);

        stringBuffer.append(RouteStop.STOP);
        stringBuffer.append(TEXT_NOT_NULL);
        stringBuffer.append(COMMA);

        stringBuffer.append(RouteStop.ROUTE);
        stringBuffer.append(TEXT_NOT_NULL);
        stringBuffer.append(COMMA);

        stringBuffer.append(RouteStop.ROUTESUB);
        stringBuffer.append(TEXT_NOT_NULL);
        return stringBuffer.toString();
    }
}
