package com.tigerbus.sqlite.table;

public enum CreateTableEnum {
//    createCommonStopType(new CreateCommodStopTypeTable()),
//    createCommonStop(new CreateCommonStopTable()),
//    createRouteStop(new CreateRouteStopTable()),
//    createWeekStatus(new CreateWeekStatusTable()),
//    createRemindStop(new CreateRemindStopTable());
//
//    private CreateTableObj createTableObj;
//
//    CreateTableEnum(CreateTableObj createTableObj) {
//        this.createTableObj = createTableObj;
//    }
//
//    public CreateTableObj getCreateTableObj() {
//        return this.createTableObj;
//    }

    createCommonStopType(CreateCommodStopTypeTable.class.getName()),
    createCommonStop(CreateCommonStopTable.class.getName()),
    createRouteStop(CreateRouteStopTable.class.getName()),
    createWeekStatus(CreateWeekStatusTable.class.getName()),
    createRemindStop(CreateRemindStopTable.class.getName());

    private String className;

    CreateTableEnum(String className) {
        this.className = className;
    }

    public String getCreateTableObj() {
        return className;
    }
}
