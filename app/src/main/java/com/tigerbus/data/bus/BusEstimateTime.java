package com.tigerbus.data.bus;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.tigerbus.data.detail.NameType;

/**
 * 市區公車之預估到站資料(N1)
 * Inline Model [
 * BUS_ESTIMATE_TIME
 * ]
 * BUS_ESTIMATE_TIME {
 * PlateNumb (string, optional): 車牌號碼 [値為値為-1時，表示目前該站位無車輛行駛] ,
 * StopUID (string, optional): 站牌唯一識別代碼，規則為 {業管機關代碼} + {StopID}，其中 {業管機關代碼} 可於Authority API中的AuthorityCode欄位查詢 ,
 * StopID (string, optional): 地區既用中之站牌代碼(為原資料內碼) ,
 * StopName (NameType, optional): 站牌名 ,
 * RouteUID (string, optional): 路線唯一識別代碼，規則為 {業管機關代碼} + {RouteID}，其中 {業管機關代碼} 可於Authority API中的AuthorityCode欄位查詢 ,
 * RouteID (string, optional): 地區既用中之路線代碼(為原資料內碼) ,
 * RouteName (NameType, optional): 路線名稱 ,
 * SubRouteUID (string, optional): 子路線唯一識別代碼，規則為 {業管機關代碼} + {SubRouteID}，其中 {業管機關代碼} 可於Authority API中的AuthorityCode欄位查詢 ,
 * SubRouteID (string, optional): 地區既用中之子路線代碼(為原資料內碼) ,
 * SubRouteName (NameType, optional): 子路線名稱 ,
 * Direction (string): 去返程(該方向指的是此車牌車輛目前所在路線的去返程方向，非指站站牌所在路線的去返程方向，使用時請加值業者多加注意) = ['0: 去程', '1: 返程'],
 * EstimateTime (integer, optional): 到站時間預估(秒) [當StopStatus値為1~4或PlateNumb値為-1時，EstimateTime値為空値; 反之，EstimateTime有値] ,
 * StopCountDown (integer, optional): 車輛距離本站站數 ,
 * CurrentStop (string, optional): 車輛目前所在站牌代碼 ,
 * DestinationStop (string, optional): 車輛目的站牌代碼 ,
 * StopSequence (integer, optional): 路線經過站牌之順序 ,
 * StopStatus (string, optional): 車輛狀態備註 = ['1: 尚未發車', '2: 交管不停靠', '3: 末班車已過', '4: 今日未營運'],
 * MessageType (string, optional): 資料型態種類 = ['0: 未知', '1: 定期', '2: 非定期'],
 * NextBusTime (DateTime, optional): 下一班公車到達時間(ISO8601格式:yyyy-MM-ddTHH:mm:sszzz) ,
 * IsLastBus (boolean, optional): 是否為末班車 ,
 * DataTime (DateTime, optional): 系統演算該筆預估到站資料的時間(ISO8601格式:yyyy-MM-ddTHH:mm:sszzz)[目前僅公總提供此欄位資訊, 且公總提醒!!加值應用時請以EstimateTime-(加值業者收到資料時間 – DataTime)(秒)作為實際預估抵達時間] ,
 * TransTime (DateTime, optional): 車機資料傳輸時間(ISO8601格式:yyyy-MM-ddTHH:mm:sszzz))[該欄位在N1資料中無意義] ,
 * SrcRecTime (DateTime, optional): 來源端平台接收時間(ISO8601格式:yyyy-MM-ddTHH:mm:sszzz))[該欄位在N1資料中無意義] ,
 * SrcTransTime (DateTime, optional): 來源端平台資料傳出時間(ISO8601格式:yyyy-MM-ddTHH:mm:sszzz)[公總使用TCP動態即時推播故有提供此欄位, 而非公總系統因使用整包資料更新, 故沒有提供此欄位] ,
 * SrcUpdateTime (DateTime, optional): 來源端平台資料更新時間(ISO8601格式:yyyy-MM-ddTHH:mm:sszzz)[公總使用TCP動態即時推播故沒有提供此欄位, 而非公總系統因提供整包資料更新, 故有提供此欄] ,
 * UpdateTime (DateTime): 本平台資料更新時間(ISO8601格式:yyyy-MM-ddTHH:mm:sszzz)
 * }
 * NameType {
 * Zh_tw (string, optional): 中文繁體名稱 ,
 * En (string, optional): 英文名稱
 * }
 */

public final class BusEstimateTime implements Parcelable, BusRouteInterface, BusSubRouteInterface {
    private String PlateNumb;
    private String StopUID;
    private String StopID;
    private NameType StopName;
    private String RouteUID;
    private String RouteID;
    private NameType RouteName;
    private String SubRouteUID;
    private String SubRouteID;
    private NameType SubRouteName;
    private String Direction;
    private int EstimateTime;
    private int StopCountDown;
    private String CurrentStop;
    private String DestinationStop;
    private int StopSequence;
    private String StopStatus;
    private String MessageType;
    private String NextBusTime;
    private boolean IsLastBus;
    private String DataTime;
    private String TransTime;
    private String SrcRecTime;
    private String SrcTransTime;
    private String SrcUpdateTime;
    private String UpdateTime;

    protected BusEstimateTime(Parcel in) {
        PlateNumb = in.readString();
        StopUID = in.readString();
        StopID = in.readString();
        StopName = in.readParcelable(NameType.class.getClassLoader());
        RouteUID = in.readString();
        RouteID = in.readString();
        RouteName = in.readParcelable(NameType.class.getClassLoader());
        SubRouteUID = in.readString();
        SubRouteID = in.readString();
        SubRouteName = in.readParcelable(NameType.class.getClassLoader());
        Direction = in.readString();
        EstimateTime = in.readInt();
        StopCountDown = in.readInt();
        CurrentStop = in.readString();
        DestinationStop = in.readString();
        StopSequence = in.readInt();
        StopStatus = in.readString();
        MessageType = in.readString();
        NextBusTime = in.readString();
        IsLastBus = in.readByte() != 0;
        DataTime = in.readString();
        TransTime = in.readString();
        SrcRecTime = in.readString();
        SrcTransTime = in.readString();
        SrcUpdateTime = in.readString();
        UpdateTime = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(PlateNumb);
        dest.writeString(StopUID);
        dest.writeString(StopID);
        dest.writeParcelable(StopName, flags);
        dest.writeString(RouteUID);
        dest.writeString(RouteID);
        dest.writeParcelable(RouteName, flags);
        dest.writeString(SubRouteUID);
        dest.writeString(SubRouteID);
        dest.writeParcelable(SubRouteName, flags);
        dest.writeString(Direction);
        dest.writeInt(EstimateTime);
        dest.writeInt(StopCountDown);
        dest.writeString(CurrentStop);
        dest.writeString(DestinationStop);
        dest.writeInt(StopSequence);
        dest.writeString(StopStatus);
        dest.writeString(MessageType);
        dest.writeString(NextBusTime);
        dest.writeByte((byte) (IsLastBus ? 1 : 0));
        dest.writeString(DataTime);
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

    public static final Creator<BusEstimateTime> CREATOR = new Creator<BusEstimateTime>() {
        @Override
        public BusEstimateTime createFromParcel(Parcel in) {
            return new BusEstimateTime(in);
        }

        @Override
        public BusEstimateTime[] newArray(int size) {
            return new BusEstimateTime[size];
        }
    };

    public String getPlateNumb() {
        return PlateNumb;
    }

    public void setPlateNumb(String plateNumb) {
        PlateNumb = plateNumb;
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

    public int getEstimateTime() {
        return EstimateTime;
    }

    public void setEstimateTime(int estimateTime) {
        EstimateTime = estimateTime;
    }

    public int getStopCountDown() {
        return StopCountDown;
    }

    public void setStopCountDown(int stopCountDown) {
        StopCountDown = stopCountDown;
    }

    public String getCurrentStop() {
        return CurrentStop;
    }

    public void setCurrentStop(String currentStop) {
        CurrentStop = currentStop;
    }

    public String getDestinationStop() {
        return DestinationStop;
    }

    public void setDestinationStop(String destinationStop) {
        DestinationStop = destinationStop;
    }

    public int getStopSequence() {
        return StopSequence;
    }

    public void setStopSequence(int stopSequence) {
        StopSequence = stopSequence;
    }

    public String getStopStatus() {
        return StopStatus;
    }

    public void setStopStatus(String stopStatus) {
        StopStatus = stopStatus;
    }

    public String getMessageType() {
        return MessageType;
    }

    public void setMessageType(String messageType) {
        MessageType = messageType;
    }

    public String getNextBusTime() {
        return NextBusTime;
    }

    public void setNextBusTime(String nextBusTime) {
        NextBusTime = nextBusTime;
    }

    public boolean isLastBus() {
        return IsLastBus;
    }

    public void setLastBus(boolean lastBus) {
        IsLastBus = lastBus;
    }

    public String getDataTime() {
        return DataTime;
    }

    public void setDataTime(String dataTime) {
        DataTime = dataTime;
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

    public static Creator<BusEstimateTime> getCREATOR() {
        return CREATOR;
    }
}
