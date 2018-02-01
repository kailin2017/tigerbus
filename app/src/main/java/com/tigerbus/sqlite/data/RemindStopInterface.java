package com.tigerbus.sqlite.data;

import com.squareup.sqlbrite3.BriteDatabase;

public interface RemindStopInterface {

    String TAG  = RemindStopInterface.class.getSimpleName();

    default void initRemindStop(BriteDatabase briteDatabase){

    }
}
