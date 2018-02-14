package com.tigerbus.sqlite.table;

public enum CreateTableEnum {
    createCommonStopType(new CreateCommodStopTypeTable()),
    createCommonStop(new CreateCommonStopTable()),
    createRouteStop(new CreateRouteStopTable()),
    createWeekStatus(new CreateWeekStatusTable()),
    createRemindStop(new CreateRemindStopTable());

    private CreateTableObj createTableObj;

    CreateTableEnum(CreateTableObj createTableObj) {
        this.createTableObj = createTableObj;
    }

    public CreateTableObj getCreateTableObj() {
        return this.createTableObj;
    }

}
