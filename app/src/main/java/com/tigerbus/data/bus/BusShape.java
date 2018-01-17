package com.tigerbus.data.bus;

import android.os.Parcel;
import android.os.Parcelable;

import com.tigerbus.data.detail.NameType;

public final class BusShape implements Parcelable, BusRouteInterface {

    public static final Creator<BusShape> CREATOR = new Creator<BusShape>() {
        @Override
        public BusShape createFromParcel(Parcel in) {
            return new BusShape(in);
        }

        @Override
        public BusShape[] newArray(int size) {
            return new BusShape[size];
        }
    };
    private String RouteUID;
    private String RouteID;
    private NameType RouteName;
    private String Direction;
    private String Geometry;
    private String UpdateTime;
    private int VersionID;

    protected BusShape(Parcel in) {
        RouteUID = in.readString();
        RouteID = in.readString();
        RouteName = in.readParcelable(NameType.class.getClassLoader());
        Direction = in.readString();
        Geometry = in.readString();
        UpdateTime = in.readString();
        VersionID = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(RouteUID);
        dest.writeString(RouteID);
        dest.writeParcelable(RouteName, flags);
        dest.writeString(Direction);
        dest.writeString(Geometry);
        dest.writeString(UpdateTime);
        dest.writeInt(VersionID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String getRouteUID() {
        return RouteUID;
    }

    public void setRouteUID(String routeUID) {
        RouteUID = routeUID;
    }

    public String getRouteID() {
        return RouteID;
    }

    public void setRouteID(String routeID) {
        RouteID = routeID;
    }

    @Override
    public NameType getRouteName() {
        return RouteName;
    }

    public void setRouteName(NameType routeName) {
        RouteName = routeName;
    }

    @Override
    public String getDirection() {
        return Direction;
    }

    public void setDirection(String direction) {
        Direction = direction;
    }

    public String getGeometry() {
        return Geometry;
    }

    public void setGeometry(String geometry) {
        Geometry = geometry;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }

    public int getVersionID() {
        return VersionID;
    }

    public void setVersionID(int versionID) {
        VersionID = versionID;
    }
}
