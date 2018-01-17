package com.tigerbus.data.bus;

import android.os.Parcel;
import android.os.Parcelable;

import com.tigerbus.data.detail.NameType;

import java.util.Arrays;

/**
 * BusSubRoute {
 * SubRouteUID (string): 附屬路線唯一識別代碼，規則為 {業管機關代碼} + {SubRouteID}，其中 {業管機關代碼} 可於Authority API中的AuthorityCode欄位查詢 ,
 * SubRouteID (string): 地區既用中之附屬路線代碼(為原資料內碼) ,
 * OperatorIDs (Array[string]): 營運業者代碼 ,
 * SubRouteName (NameType): 附屬路線名稱 ,
 * Headsign (string, optional): 車頭描述 ,
 * Direction (string): 去返程 = ['0: 去程', '1: 返程'],
 * FirstBusTime (string, optional): 平日第一班發車時間 ,
 * LastBusTime (string, optional): 平日返程第一班發車時間 ,
 * HolidayFirstBusTime (string, optional): 假日去程第一班發車時間 ,
 * HolidayLastBusTime (string, optional): 假日返程第一班發車時間
 * }
 */

public final class BusSubRoute implements Parcelable, BusRouteInterface {
    public static final Creator<BusSubRoute> CREATOR = new Creator<BusSubRoute>() {
        @Override
        public BusSubRoute createFromParcel(Parcel in) {
            return new BusSubRoute(in);
        }

        @Override
        public BusSubRoute[] newArray(int size) {
            return new BusSubRoute[size];
        }
    };
    private String SubRouteUID;
    private String SubRouteID;
    private String[] OperatorIDs;
    private NameType SubRouteName;
    private String Headsign;
    private String Direction;
    private String FirstBusTime;
    private String LastBusTime;
    private String HolidayFirstBusTime;
    private String HolidayLastBusTime;

    protected BusSubRoute(Parcel in) {
        SubRouteUID = in.readString();
        SubRouteID = in.readString();
        OperatorIDs = in.createStringArray();
        SubRouteName = in.readParcelable(NameType.class.getClassLoader());
        Headsign = in.readString();
        Direction = in.readString();
        FirstBusTime = in.readString();
        LastBusTime = in.readString();
        HolidayFirstBusTime = in.readString();
        HolidayLastBusTime = in.readString();
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

    public String[] getOperatorIDs() {
        return OperatorIDs;
    }

    public void setOperatorIDs(String[] operatorIDs) {
        OperatorIDs = operatorIDs;
    }

    @Override
    public NameType getSubRouteName() {
        return SubRouteName;
    }

    public void setSubRouteName(NameType subRouteName) {
        SubRouteName = subRouteName;
    }

    public String getHeadsign() {
        return Headsign;
    }

    public void setHeadsign(String headsign) {
        Headsign = headsign;
    }

    @Override
    public String getDirection() {
        return Direction;
    }

    public void setDirection(String direction) {
        Direction = direction;
    }

    public String getFirstBusTime() {
        return FirstBusTime;
    }

    public void setFirstBusTime(String firstBusTime) {
        FirstBusTime = firstBusTime;
    }

    public String getLastBusTime() {
        return LastBusTime;
    }

    public void setLastBusTime(String lastBusTime) {
        LastBusTime = lastBusTime;
    }

    public String getHolidayFirstBusTime() {
        return HolidayFirstBusTime;
    }

    public void setHolidayFirstBusTime(String holidayFirstBusTime) {
        HolidayFirstBusTime = holidayFirstBusTime;
    }

    public String getHolidayLastBusTime() {
        return HolidayLastBusTime;
    }

    public void setHolidayLastBusTime(String holidayLastBusTime) {
        HolidayLastBusTime = holidayLastBusTime;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(SubRouteUID);
        dest.writeString(SubRouteID);
        dest.writeStringArray(OperatorIDs);
        dest.writeParcelable(SubRouteName, flags);
        dest.writeString(Headsign);
        dest.writeString(Direction);
        dest.writeString(FirstBusTime);
        dest.writeString(LastBusTime);
        dest.writeString(HolidayFirstBusTime);
        dest.writeString(HolidayLastBusTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }


}
