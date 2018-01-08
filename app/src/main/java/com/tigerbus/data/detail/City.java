package com.tigerbus.data.detail;

import android.os.Parcel;

public final class City extends NameType {

    public City(String Zh_tw, String En) {
        super(Zh_tw, En);
    }

    protected City(Parcel in) {
        super(in);
    }


}
