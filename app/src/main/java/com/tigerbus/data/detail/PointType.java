package com.tigerbus.data.detail;

import android.os.Parcel;
import android.os.Parcelable;

/**
 PointType {
 PositionLat (number, optional): 位置緯度(WGS84) ,
 PositionLon (number, optional): 位置經度(WGS84)
 }
 */

public final class PointType implements Parcelable {
    private double PositionLat;
    private double PositionLon;

    public PointType(double positionLat, double positionLon) {
        PositionLat = positionLat;
        PositionLon = positionLon;
    }

    public PointType(String positionLat, String positionLon) {
        PositionLat = Double.parseDouble(positionLat);
        PositionLon = Double.parseDouble(positionLon);
    }

    protected PointType(Parcel in) {
        PositionLat = in.readDouble();
        PositionLon = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(PositionLat);
        dest.writeDouble(PositionLon);
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

    public double getPositionLat() {
        return PositionLat;
    }

    public void setPositionLat(double positionLat) {
        PositionLat = positionLat;
    }

    public double getPositionLon() {
        return PositionLon;
    }

    public void setPositionLon(double positionLon) {
        PositionLon = positionLon;
    }
}
