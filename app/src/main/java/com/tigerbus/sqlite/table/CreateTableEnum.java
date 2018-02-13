package com.tigerbus.sqlite.table;

/**
 * Created by sinopac on 2018/2/13.
 */

public enum CreateTableEnum {
    createCommonStopType(new CreateCommodStopTypeTable().getCreateTableString()),
    createCommonStop(new CreateCommonStopTable().getCreateTableString()),
    createRouteStop(new CreateRouteStopTable().getCreateTableString()),
    createWeekStatus(new CreateWeekStatusTable().getCreateTableString()),
    createRemindStop(new CreateRemindStopTable().getCreateTableString());

    private String createTableString;

    CreateTableEnum(String createTableString) {
        this.createTableString = createTableString;
    }

    public String getCreateTableString() {
        return this.createTableString;
    }

}
