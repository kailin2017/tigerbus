package com.tigerbus.data.bus;

import android.os.Parcel;
import android.os.Parcelable;

import com.tigerbus.data.detail.NameType;
import com.tigerbus.data.detail.PointType;

/**
 * BusA1Data {
 * PlateNumb (string): 車牌號碼 ,
 * OperatorID (string, optional): 營運業者代碼 ,
 * RouteUID (string, optional): 路線唯一識別代碼，規則為 {業管機關代碼} + {RouteID}，其中 {業管機關代碼} 可於Authority API中的AuthorityCode欄位查詢 ,
 * RouteID (string, optional): 地區既用中之路線代碼(為原資料內碼) ,
 * RouteName (NameType, optional): 路線名稱 ,
 * SubRouteUID (string, optional): 子路線唯一識別代碼，規則為 {業管機關代碼} + {SubRouteID}，其中 {業管機關代碼} 可於Authority API中的AuthorityCode欄位查詢 ,
 * SubRouteID (string, optional): 地區既用中之子路線代碼(為原資料內碼) ,
 * SubRouteName (NameType, optional): 子路線名稱 ,
 * Direction (string, optional): 去返程 = ['0: 去程', '1: 返程'],
 * BusPosition (PointType, optional): 車輛位置經度 ,
 * Speed (number, optional): 行駛速度(kph) ,
 * Azimuth (number, optional): 方位角 ,
 * DutyStatus (string, optional): 勤務狀態 = ['0: 正常', '1: 開始', '2: 結束'],
 * BusStatus (string, optional): 行車狀況 = ['0: 正常', '1: 車禍', '2: 故障', '3: 塞車', '4: 緊急求援', '5: 加油', '90: 不明', '91: 去回不明', '98: 偏移路線', '99: 非營運狀態', '100: 客滿', '101: 包車出租', '255: 未知'],
 * MessageType (string, optional): 資料型態種類 = ['0: 未知', '1: 定期', '2: 非定期'],
 * GPSTime (DateTime): 車機時間(ISO8601格式:yyyy-MM-ddTHH:mm:sszzz) ,
 * TransTime (DateTime, optional): 車機資料傳輸時間(ISO8601格式:yyyy-MM-ddTHH:mm:sszzz)[多數單位沒有提供此欄位資訊] ,
 * SrcRecTime (DateTime, optional): 來源端平台接收時間(ISO8601格式:yyyy-MM-ddTHH:mm:sszzz) ,
 * SrcTransTime (DateTime, optional): 來源端平台資料傳出時間(ISO8601格式:yyyy-MM-ddTHH:mm:sszzz)[公總使用TCP動態即時推播故有提供此欄位, 而非公總系統因使用整包資料更新, 故沒有提供此欄位] ,
 * SrcUpdateTime (DateTime, optional): 來源端平台資料更新時間(ISO8601格式:yyyy-MM-ddTHH:mm:sszzz)[公總使用TCP動態即時推播故沒有提供此欄位, 而非公總系統因提供整包資料更新, 故有提供此欄] ,
 * UpdateTime (DateTime): 本平台資料更新時間(ISO8601格式:yyyy-MM-ddTHH:mm:sszzz)
 * }
 * NameType {
 * Zh_tw (string, optional): 中文繁體名稱 ,
 * En (string, optional): 英文名稱
 * }
 * PointType {
 * PositionLat (number, optional): 位置緯度(WGS84) ,
 * PositionLon (number, optional): 位置經度(WGS84)
 * }
 */

public final class BusA1Data implements Parcelable, BusRouteInterface {

    public static final Creator<BusA1Data> CREATOR = new Creator<BusA1Data>() {
        @Override
        public BusA1Data createFromParcel(Parcel in) {
            return new BusA1Data(in);
        }

        @Override
        public BusA1Data[] newArray(int size) {
            return new BusA1Data[size];
        }
    };
    private String PlateNumb;
    private String OperatorID;
    private String RouteUID;
    private String RouteID;
    private NameType RouteName;
    private String SubRouteUID;
    private String SubRouteID;
    private NameType SubRouteName;
    private String Direction;
    private PointType BusPosition;
    private int Speed;
    private int Azimuth;
    private String DutyStatus;
    private String BusStatus;
    private String MessageType;
    private String GPSTime;
    private String TransTime;
    private String SrcRecTime;
    private String SrcTransTime;
    private String SrcUpdateTime;
    private String UpdateTime;

    protected BusA1Data(Parcel in) {
        PlateNumb = in.readString();
        OperatorID = in.readString();
        RouteUID = in.readString();
        RouteID = in.readString();
        RouteName = in.readParcelable(NameType.class.getClassLoader());
        SubRouteUID = in.readString();
        SubRouteID = in.readString();
        SubRouteName = in.readParcelable(NameType.class.getClassLoader());
        Direction = in.readString();
        BusPosition = in.readParcelable(PointType.class.getClassLoader());
        Speed = in.readInt();
        Azimuth = in.readInt();
        DutyStatus = in.readString();
        BusStatus = in.readString();
        MessageType = in.readString();
        GPSTime = in.readString();
        TransTime = in.readString();
        SrcRecTime = in.readString();
        SrcTransTime = in.readString();
        SrcUpdateTime = in.readString();
        UpdateTime = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(PlateNumb);
        dest.writeString(OperatorID);
        dest.writeString(RouteUID);
        dest.writeString(RouteID);
        dest.writeParcelable(RouteName, flags);
        dest.writeString(SubRouteUID);
        dest.writeString(SubRouteID);
        dest.writeParcelable(SubRouteName, flags);
        dest.writeString(Direction);
        dest.writeParcelable(BusPosition, flags);
        dest.writeInt(Speed);
        dest.writeInt(Azimuth);
        dest.writeString(DutyStatus);
        dest.writeString(BusStatus);
        dest.writeString(MessageType);
        dest.writeString(GPSTime);
        dest.writeString(TransTime);
        dest.writeString(SrcRecTime);
        dest.writeString(SrcTransTime);
        dest.writeString(SrcUpdateTime);
        dest.writeString(UpdateTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getPlateNumb() {
        return PlateNumb;
    }

    public void setPlateNumb(String plateNumb) {
        PlateNumb = plateNumb;
    }

    public String getOperatorID() {
        return OperatorID;
    }

    public void setOperatorID(String operatorID) {
        OperatorID = operatorID;
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

    public PointType getBusPosition() {
        return BusPosition;
    }

    public void setBusPosition(PointType busPosition) {
        BusPosition = busPosition;
    }

    public int getSpeed() {
        return Speed;
    }

    public void setSpeed(int speed) {
        Speed = speed;
    }

    public int getAzimuth() {
        return Azimuth;
    }

    public void setAzimuth(int azimuth) {
        Azimuth = azimuth;
    }

    public String getDutyStatus() {
        return DutyStatus;
    }

    public void setDutyStatus(String dutyStatus) {
        DutyStatus = dutyStatus;
    }

    public String getBusStatus() {
        return BusStatus;
    }

    public void setBusStatus(String busStatus) {
        BusStatus = busStatus;
    }

    public String getMessageType() {
        return MessageType;
    }

    public void setMessageType(String messageType) {
        MessageType = messageType;
    }

    public String getGPSTime() {
        return GPSTime;
    }

    public void setGPSTime(String GPSTime) {
        this.GPSTime = GPSTime;
    }

    public String getTransTime() {
        return TransTime;
    }

    public void setTransTime(String transTime) {
        TransTime = transTime;
    }

    public String getSrcRecTime() {
        return SrcRecTime;
    }

    public void setSrcRecTime(String srcRecTime) {
        SrcRecTime = srcRecTime;
    }

    public String getSrcTransTime() {
        return SrcTransTime;
    }

    public void setSrcTransTime(String srcTransTime) {
        SrcTransTime = srcTransTime;
    }

    public String getSrcUpdateTime() {
        return SrcUpdateTime;
    }

    public void setSrcUpdateTime(String srcUpdateTime) {
        SrcUpdateTime = srcUpdateTime;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }
}
