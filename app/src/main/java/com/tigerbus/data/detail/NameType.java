package com.tigerbus.data.detail;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * NameType {
 * Zh_tw (string, optional): 中文繁體名稱 ,
 * En (string, optional): 英文名稱
 * }
 */

public final class NameType implements Parcelable{

    private String Zh_tw;
    private String En;

    public NameType(String Zh_tw,String En){
        setZh_tw(Zh_tw);
        setEn(En);
    }

    protected NameType(Parcel in) {
        Zh_tw = in.readString();
        En = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Zh_tw);
        dest.writeString(En);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NameType> CREATOR = new Creator<NameType>() {
        @Override
        public NameType createFromParcel(Parcel in) {
            return new NameType(in);
        }

        @Override
        public NameType[] newArray(int size) {
            return new NameType[size];
        }
    };

    public String getZh_tw() {
        return Zh_tw;
    }

    public void setZh_tw(String zh_tw) {
        Zh_tw = zh_tw;
    }

    public String getEn() {
        return En;
    }

    public void setEn(String en) {
        En = en;
    }
}
