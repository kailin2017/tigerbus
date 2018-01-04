package com.tigerbus.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 PointType {
 PositionLat (number, optional): 位置緯度(WGS84) ,
 PositionLon (number, optional): 位置經度(WGS84)
 }
 */

public final class PointType implements Parcelable {
    private String PositionLat;
    private String PositionLon;

    protected PointType(Parcel in) {
        PositionLat = in.readString();
        PositionLon = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(PositionLat);
        dest.writeString(PositionLon);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PointType> CREATOR = new Creator<PointType>() {
        @Override
        public PointType createFromParcel(Parcel in) {
            return new PointType(in);
        }

        @Override
        public PointType[] newArray(int size) {
            return new PointType[size];
        }
    };

    public String getPositionLat() {
        return PositionLat;
    }

    public void setPositionLat(String positionLat) {
        PositionLat = positionLat;
    }

    public String getPositionLon() {
        return PositionLon;
    }

    public void setPositionLon(String positionLon) {
        PositionLon = positionLon;
    }
}
