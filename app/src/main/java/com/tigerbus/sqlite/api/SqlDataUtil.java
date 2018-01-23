package com.tigerbus.sqlite.api;

import com.tigerbus.TigerApplication;

public interface SqlDataUtil {

    String COMMODSTOP = "COMMODSTOP";
    String COMMODSTOPTYPE = "COMMODSTOPTYPE";

    static <T> String object2String(T t) {
        return TigerApplication.object2String(t);
    }

    static  <T> T string2Object(Class<T> clazz, String s) {
        return TigerApplication.string2Object(clazz, s);
    }
}
