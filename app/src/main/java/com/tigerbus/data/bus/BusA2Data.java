package com.tigerbus.data.bus;

import android.os.Parcel;
import android.os.Parcelable;

import com.tigerbus.data.detail.NameType;

/***
 BusA2Data {
 PlateNumb (string): 車牌號碼 ,
 OperatorID (string, optional): 營運業者代碼 ,
 RouteUID (string, optional): 路線唯一識別代碼，規則為 {業管機關代碼} + {RouteID}，其中 {業管機關代碼} 可於Authority API中的AuthorityCode欄位查詢 ,
 RouteID (string, optional): 地區既用中之路線代碼(為原資料內碼) ,
 RouteName (NameType, optional): 路線名 ,
 SubRouteUID (string, optional): 子路線唯一識別代碼，規則為 {業管機關代碼} + {SubRouteID}，其中 {業管機關代碼} 可於Authority API中的AuthorityCode欄位查詢 ,
 SubRouteID (string, optional): 地區既用中之子路線代碼(為原資料內碼) ,
 SubRouteName (NameType, optional): 子路線名稱 ,
 Direction (string): 去返程 = ['0: 去程', '1: 返程'],
 StopUID (string, optional): 站牌唯一識別代碼，規則為 {平台代碼} + {StopID}，其中 {平台代碼} 可於Provider API中的ProviderCode欄位查詢 ,
 StopID (string, optional): 地區既用中之站牌代號(為原資料內碼) ,
 StopName (NameType, optional): 站牌名 ,
 StopSequence (integer, optional): 路線經過站牌之順序 ,
 MessageType (string, optional): 資料型態種類 = ['0: 未知', '1: 定期', '2: 非定期'],
 DutyStatus (string, optional): 勤務狀態 = ['0: 正常', '1: 開始', '2: 結束'],
 BusStatus (string, optional): 行車狀況 = ['0: 正常', '1: 車禍', '2: 故障', '3: 塞車', '4: 緊急求援', '5: 加油', '90: 不明', '91: 去回不明', '98: 偏移路線', '99: 非營運狀態', '100: 客滿', '101: 包車出租', '255: 未知'],
 A2EventType (string, optional): 進站離站 = ['0: 離站', '1: 進站'],
 GPSTime (DateTime): 車機時間(ISO8601格式:yyyy-MM-ddTHH:mm:sszzz) ,
 TransTime (DateTime, optional): 車機資料傳輸時間(ISO8601格式:yyyy-MM-ddTHH:mm:sszzz)[多數單位沒有提供此欄位資訊] ,
 SrcRecTime (DateTime, optional): 來源端平台接收時間(ISO8601格式:yyyy-MM-ddTHH:mm:sszzz) ,
 SrcTransTime (DateTime, optional): 來源端平台資料傳出時間(ISO8601格式:yyyy-MM-ddTHH:mm:sszzz)[公總使用TCP動態即時推播故有提供此欄位, 而非公總系統因使用整包資料更新, 故沒有提供此欄位] ,
 SrcUpdateTime (DateTime, optional): 來源端平台資料更新時間(ISO8601格式:yyyy-MM-ddTHH:mm:sszzz)[公總使用TCP動態即時推播故沒有提供此欄位, 而非公總系統因提供整包資料更新, 故有提供此欄] ,
 UpdateTime (DateTime): 本平台資料更新時間(ISO8601格式:yyyy-MM-ddTHH:mm:sszzz)
 }
 NameType {
 Zh_tw (string, optional): 中文繁體名稱 ,
 En (string, optional): 英文名稱
 }
 */

public final class BusA2Data implements Parcelable {

    private String PlateNumb;
    private String OperatorID;
    private String RouteUID;
    private String RouteID;
    private NameType RouteName;
    private String SubRouteUID;
    private String SubRouteID;
    private NameType SubRouteName;
    private String Direction;
    private String StopUID;
    private String StopID;
    private NameType StopName;
    private int StopSequence;
    private String MessageType;
    private String DutyStatus;
    private String BusStatus;
    private String A2EventType;
    private String GPSTime;
    private String TransTime;
    private String SrcRecTime;
    private String SrcTransTime;
    private String SrcUpdateTime;
    private String UpdateTime;

    protected BusA2Data(Parcel in) {
        PlateNumb = in.readString();
        OperatorID = in.readString();
        RouteUID = in.readString();
        RouteID = in.readString();
        RouteName = in.readParcelable(NameType.class.getClassLoader());
        SubRouteUID = in.readString();
        SubRouteID = in.readString();
        SubRouteName = in.readParcelable(NameType.class.getClassLoader());
        Direction = in.readString();
        StopUID = in.readString();
        StopID = in.readString();
        StopName = in.readParcelable(NameType.class.getClassLoader());
        StopSequence = in.readInt();
        MessageType = in.readString();
        DutyStatus = in.readString();
        BusStatus = in.readString();
        A2EventType = in.readString();
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
        dest.writeString(StopUID);
        dest.writeString(StopID);
        dest.writeParcelable(StopName, flags);
        dest.writeInt(StopSequence);
        dest.writeString(MessageType);
        dest.writeString(DutyStatus);
        dest.writeString(BusStatus);
        dest.writeString(A2EventType);
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

    public static final Creator<BusA2Data> CREATOR = new Creator<BusA2Data>() {
        @Override
        public BusA2Data createFromParcel(Parcel in) {
            return new BusA2Data(in);
        }

        @Override
        public BusA2Data[] newArray(int size) {
            return new BusA2Data[size];
        }
    };

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

    public NameType getRouteName() {
        return RouteName;
    }

    public void setRouteName(NameType routeName) {
        RouteName = routeName;
    }

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

    public NameType getSubRouteName() {
        return SubRouteName;
    }

    public void setSubRouteName(NameType subRouteName) {
        SubRouteName = subRouteName;
    }

    public String getDirection() {
        return Direction;
    }

    public void setDirection(String direction) {
        Direction = direction;
    }

    public String getStopUID() {
        return StopUID;
    }

    public void setStopUID(String stopUID) {
        StopUID = stopUID;
    }

    public String getStopID() {
        return StopID;
    }

    public void setStopID(String stopID) {
        StopID = stopID;
    }

    public NameType getStopName() {
        return StopName;
    }

    public void setStopName(NameType stopName) {
        StopName = stopName;
    }

    public int getStopSequence() {
        return StopSequence;
    }

    public void setStopSequence(int stopSequence) {
        StopSequence = stopSequence;
    }

    public String getMessageType() {
        return MessageType;
    }

    public void setMessageType(String messageType) {
        MessageType = messageType;
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

    public String getA2EventType() {
        return A2EventType;
    }

    public void setA2EventType(String a2EventType) {
        A2EventType = a2EventType;
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
