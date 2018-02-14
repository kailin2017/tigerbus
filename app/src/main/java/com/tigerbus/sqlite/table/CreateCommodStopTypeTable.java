package com.tigerbus.sqlite.table;

import android.arch.persistence.db.SupportSQLiteDatabase;

import com.tigerbus.sqlite.data.CommonStopType;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_FAIL;

public final class CreateCommodStopTypeTable extends CreateTableObj {
    @Override
    protected String getTableString() {
        return CommonStopType.TABLE;
    }

    @Override
    protected String getColumnString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(CommonStopType.ID);
        stringBuffer.append(TEXT_NOT_NULL);
        stringBuffer.append(PRIMARY_KEY);
        stringBuffer.append(COMMA);

        stringBuffer.append(CommonStopType.TYPENAME);
        stringBuffer.append(TEXT_NOT_NULL);

        return stringBuffer.toString();
    }

    @Override
    public void initDefaultData(SupportSQLiteDatabase database) {
//        insertData(database,new String[]{"全部","出門","回家","工作","七桃"});
    }

    private void insertData(SupportSQLiteDatabase database ,String strings[]){
        for(String s : strings){
            database.insert(CommonStopType.TABLE, CONFLICT_FAIL,
                    new CommonStopType.SqlBuilder().type(s).build());
        }
    }
}
