package com.tigerbus.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 Stop {
 StopUID (string): 站牌唯一識別代碼，規則為 {業管機關代碼} + {StopID}，其中 {業管機關代碼} 可於Authority API中的AuthorityCode欄位查詢 ,
 StopID (string): 地區既用中之站牌代碼(為原資料內碼) ,
 StopName (NameType): 站牌名稱 ,
 StopBoarding (string, optional): 上下車站別 = ['0: 可上下車', '1: 可上車', '-1: 可下車'],
 StopSequence (integer): 路線經過站牌之順序 ,
 StopPosition (PointType): 站牌位置
 }
 */

public final class Stop implements Parcelable{
    private String StopUID;
    private String StopID;
    private NameType StopName;
    private String StopBoarding;
    private int StopSequence;
    private PointType StopPosition;

    protected Stop(Parcel in) {
        StopUID = in.readString();
        StopID = in.readString();
        StopName = in.readParcelable(NameType.class.getClassLoader());
        StopBoarding = in.readString();
        StopSequence = in.readInt();
        StopPosition = in.readParcelable(PointType.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(StopUID);
        dest.writeString(StopID);
        dest.writeParcelable(StopName, flags);
        dest.writeString(StopBoarding);
        dest.writeInt(StopSequence);
        dest.writeParcelable(StopPosition, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Stop> CREATOR = new Creator<Stop>() {
        @Override
        public Stop createFromParcel(Parcel in) {
            return new Stop(in);
        }

        @Override
        public Stop[] newArray(int size) {
            return new Stop[size];
        }
    };

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

    public String getStopBoarding() {
        return StopBoarding;
    }

    public void setStopBoarding(String stopBoarding) {
        StopBoarding = stopBoarding;
    }

    public int getStopSequence() {
        return StopSequence;
    }

    public void setStopSequence(int stopSequence) {
        StopSequence = stopSequence;
    }

    public PointType getStopPosition() {
        return StopPosition;
    }

    public void setStopPosition(PointType stopPosition) {
        StopPosition = stopPosition;
    }
}
