package com.tigerbus.data.bus;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kailin on 2017/12/31.
 */

public final class BusVersion implements Parcelable{
    private int VersionID;
    private String UpdateTime;
    private String UpdateCheckTime;

    protected BusVersion(Parcel in) {
        VersionID = in.readInt();
        UpdateTime = in.readString();
        UpdateCheckTime = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(VersionID);
        dest.writeString(UpdateTime);
        dest.writeString(UpdateCheckTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BusVersion> CREATOR = new Creator<BusVersion>() {
        @Override
        public BusVersion createFromParcel(Parcel in) {
            return new BusVersion(in);
        }

        @Override
        public BusVersion[] newArray(int size) {
            return new BusVersion[size];
        }
    };

    public int getVersionID() {
        return VersionID;
    }

    public void setVersionID(int versionID) {
        VersionID = versionID;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }

    public String getUpdateCheckTime() {
        return UpdateCheckTime;
    }

    public void setUpdateCheckTime(String updateCheckTime) {
        UpdateCheckTime = updateCheckTime;
    }
}
