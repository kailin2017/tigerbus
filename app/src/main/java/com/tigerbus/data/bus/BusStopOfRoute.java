package com.tigerbus.data.bus;

import android.os.Parcel;
import android.os.Parcelable;

import com.tigerbus.data.detail.NameType;
import com.tigerbus.data.detail.Stop;

import java.util.ArrayList;

/**
 * BusStopOfRoute {
 * RouteUID (string): 路線唯一識別代碼，規則為 {業管機關代碼} + {RouteID}，其中 {業管機關代碼} 可於Authority API中的AuthorityCode欄位查詢 ,
 * RouteID (string): 地區既用中之路線代碼(為原資料內碼) ,
 * RouteName (NameType): 路線名稱 ,
 * OperatorID (string, optional): 營運業者代碼 ,
 * SubRouteUID (string): 附屬路線唯一識別代碼，規則為 {業管機關代碼} + {SubRouteID}，其中 {業管機關代碼} 可於Authority API中的AuthorityCode欄位查詢 ,
 * SubRouteID (string): 地區既用中之附屬路線代碼(為原資料內碼) ,
 * SubRouteName (NameType): 附屬路線名稱 ,
 * Direction (string, optional): 去返程 = ['0: 去程', '1: 返程'],
 * Stops (Array[Stop]): 所有經過站牌 ,
 * UpdateTime (DateTime): 資料更新日期時間(ISO8601格式:yyyy-MM-ddTHH:mm:sszzz) ,
 * VersionID (integer): 資料版本編號
 * }
 * NameType {
 * Zh_tw (string, optional): 中文繁體名稱 ,
 * En (string, optional): 英文名稱
 * }
 * Stop {
 * StopUID (string): 站牌唯一識別代碼，規則為 {業管機關代碼} + {StopID}，其中 {業管機關代碼} 可於Authority API中的AuthorityCode欄位查詢 ,
 * StopID (string): 地區既用中之站牌代碼(為原資料內碼) ,
 * StopName (NameType): 站牌名稱 ,
 * StopBoarding (string, optional): 上下車站別 = ['0: 可上下車', '1: 可上車', '-1: 可下車'],
 * StopSequence (integer): 路線經過站牌之順序 ,
 * StopPosition (PointType): 站牌位置
 * }
 * PointType {
 * PositionLat (number, optional): 位置緯度(WGS84) ,
 * PositionLon (number, optional): 位置經度(WGS84)
 * }
 */

public final class BusStopOfRoute implements Parcelable, BusRouteInterface {
    public static final Creator<BusStopOfRoute> CREATOR = new Creator<BusStopOfRoute>() {
        @Override
        public BusStopOfRoute createFromParcel(Parcel in) {
            return new BusStopOfRoute(in);
        }

        @Override
        public BusStopOfRoute[] newArray(int size) {
            return new BusStopOfRoute[size];
        }
    };
    private String RouteUID;
    private String RouteID;
    private NameType RouteName;
    private String OperatorID;
    private String SubRouteUID;
    private String SubRouteID;
    private NameType SubRouteName;
    private String Direction;
    private ArrayList<Stop> Stops;
    private String UpdateTime;
    private int VersionID;

    protected BusStopOfRoute(Parcel in) {
        RouteUID = in.readString();
        RouteID = in.readString();
        RouteName = in.readParcelable(NameType.class.getClassLoader());
        OperatorID = in.readString();
        SubRouteUID = in.readString();
        SubRouteID = in.readString();
        SubRouteName = in.readParcelable(NameType.class.getClassLoader());
        Direction = in.readString();
        Stops = in.createTypedArrayList(Stop.CREATOR);
        UpdateTime = in.readString();
        VersionID = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(RouteUID);
        dest.writeString(RouteID);
        dest.writeParcelable(RouteName, flags);
        dest.writeString(OperatorID);
        dest.writeString(SubRouteUID);
        dest.writeString(SubRouteID);
        dest.writeParcelable(SubRouteName, flags);
        dest.writeString(Direction);
        dest.writeTypedList(Stops);
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

    public String getOperatorID() {
        return OperatorID;
    }

    public void setOperatorID(String operatorID) {
        OperatorID = operatorID;
    }

    @Override
    public String getSubRouteUID() {
        return SubRouteUID;
    }

    public void setSubRouteUID(String subRouteUID) {
        SubRouteUID = subRouteUID;
    }

    public String getSubRouteID() {
        return SubRouteID;
    }

    public void setSubRouteID(String subRouteID) {
        SubRouteID = subRouteID;
    }

    @Override
    public NameType getSubRouteName() {
        return SubRouteName;
    }

    public void setSubRouteName(NameType subRouteName) {
        SubRouteName = subRouteName;
    }

    @Override
    public String getDirection() {
        return Direction;
    }

    public void setDirection(String direction) {
        Direction = direction;
    }

    public ArrayList<Stop> getStops() {
        return Stops;
    }

    public void setStops(ArrayList<Stop> stops) {
        Stops = stops;
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
