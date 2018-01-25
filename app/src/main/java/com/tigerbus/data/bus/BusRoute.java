package com.tigerbus.data.bus;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.tigerbus.data.detail.NameType;

import java.util.ArrayList;

/***
 Inline Model [
 BUS_ROUTE
 ]
 BUS_ROUTE {
 RouteUID (string): 路線唯一識別代碼，規則為 {業管機關代碼} + {RouteID}，其中 {業管機關代碼} 可於Authority API中的AuthorityCode欄位查詢 ,
 RouteID (string): 地區既用中之路線代碼(為原資料內碼) ,
 HasSubRoutes (boolean): 實際上是否有多條附屬路線。(此欄位值與SubRoutes結構並無強烈的絕對關聯。詳細說明請參閱swagger上方的【資料服務使用注意事項】) ,
 OperatorIDs (Array[string]): 營運業者代碼 ,
 AuthorityID (string): 業管單位代碼 ,
 ProviderID (string): 資料提供平台代碼 ,
 SubRoutes (Array[BusSubRoute], optional): 附屬路線資料(如果原始資料並無提供附屬路線ID，而本平台基於跨來源資料之一致性，會以SubRouteID=RouteID產製一份相對應的附屬路線資料(若有去返程，則會有兩筆)) ,
 BusRouteType (string): 公車路線類別 = ['11: 市區公車', '12: 公路客運', '13: 國道客運'],
 RouteName (NameType): 路線名稱 ,
 DepartureStopNameZh (string, optional): 起站中文名稱 ,
 DepartureStopNameEn (string, optional): 起站英文名稱 ,
 DestinationStopNameZh (string, optional): 終點站中文名稱 ,
 DestinationStopNameEn (string, optional): 終點站英文名稱 ,
 TicketPriceDescriptionZh (string, optional): 票價中文敘述 ,
 TicketPriceDescriptionEn (string, optional): 票價英文敘述 ,
 FareBufferZoneDescriptionZh (string, optional): 收費緩衝區中文敘述 ,
 FareBufferZoneDescriptionEn (string, optional): 收費緩衝區英文敘述 ,
 RouteMapImageUrl (string, optional): 路線簡圖網址 ,
 UpdateTime (DateTime): 資料更新日期時間(ISO8601格式:yyyy-MM-ddTHH:mm:sszzz) ,
 VersionID (integer): 資料版本編號
 }
 BusSubRoute {
 SubRouteUID (string): 附屬路線唯一識別代碼，規則為 {業管機關代碼} + {SubRouteID}，其中 {業管機關代碼} 可於Authority API中的AuthorityCode欄位查詢 ,
 SubRouteID (string): 地區既用中之附屬路線代碼(為原資料內碼) ,
 OperatorIDs (Array[string]): 營運業者代碼 ,
 SubRouteName (NameType): 附屬路線名稱 ,
 Headsign (string, optional): 車頭描述 ,
 Direction (string): 去返程 = ['0: 去程', '1: 返程'],
 FirstBusTime (string, optional): 平日第一班發車時間 ,
 LastBusTime (string, optional): 平日返程第一班發車時間 ,
 HolidayFirstBusTime (string, optional): 假日去程第一班發車時間 ,
 HolidayLastBusTime (string, optional): 假日返程第一班發車時間
 }
 NameType {
 Zh_tw (string, optional): 中文繁體名稱 ,
 En (string, optional): 英文名稱
 }
 */

public final class BusRoute implements Parcelable, Comparable<BusRoute>, BusRouteInterface {
    public static final Creator<BusRoute> CREATOR = new Creator<BusRoute>() {
        @Override
        public BusRoute createFromParcel(Parcel in) {
            return new BusRoute(in);
        }

        @Override
        public BusRoute[] newArray(int size) {
            return new BusRoute[size];
        }
    };
    private String RouteUID;
    private String RouteID;
    private boolean HasSubRoutes;
    private String[] OperatorIDs;
    private String AuthorityID;
    private String ProviderID;
    private ArrayList<BusSubRoute> SubRoutes;
    private String BusRouteType;
    private NameType RouteName;
    private String DepartureStopNameZh;
    private String DepartureStopNameEn;
    private String DestinationStopNameZh;
    private String DestinationStopNameEn;
    private String TicketPriceDescriptionZh;
    private String TicketPriceDescriptionEn;
    private String FareBufferZoneDescriptionZh;
    private String FareBufferZoneDescriptionEn;
    private String RouteMapImageUrl;
    private String UpdateTime;
    private int VersionID;
    private NameType CityName;
    private int SearchSocre;

    protected BusRoute(Parcel in) {
        RouteUID = in.readString();
        RouteID = in.readString();
        HasSubRoutes = in.readByte() != 0;
        OperatorIDs = in.createStringArray();
        AuthorityID = in.readString();
        ProviderID = in.readString();
        SubRoutes = in.createTypedArrayList(BusSubRoute.CREATOR);
        BusRouteType = in.readString();
        RouteName = in.readParcelable(NameType.class.getClassLoader());
        DepartureStopNameZh = in.readString();
        DepartureStopNameEn = in.readString();
        DestinationStopNameZh = in.readString();
        DestinationStopNameEn = in.readString();
        TicketPriceDescriptionZh = in.readString();
        TicketPriceDescriptionEn = in.readString();
        FareBufferZoneDescriptionZh = in.readString();
        FareBufferZoneDescriptionEn = in.readString();
        RouteMapImageUrl = in.readString();
        UpdateTime = in.readString();
        VersionID = in.readInt();
        CityName = in.readParcelable(NameType.class.getClassLoader());
        SearchSocre = in.readInt();
    }

    public static Creator<BusRoute> getCREATOR() {
        return CREATOR;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(RouteUID);
        dest.writeString(RouteID);
        dest.writeByte((byte) (HasSubRoutes ? 1 : 0));
        dest.writeStringArray(OperatorIDs);
        dest.writeString(AuthorityID);
        dest.writeString(ProviderID);
        dest.writeTypedList(SubRoutes);
        dest.writeString(BusRouteType);
        dest.writeParcelable(RouteName, flags);
        dest.writeString(DepartureStopNameZh);
        dest.writeString(DepartureStopNameEn);
        dest.writeString(DestinationStopNameZh);
        dest.writeString(DestinationStopNameEn);
        dest.writeString(TicketPriceDescriptionZh);
        dest.writeString(TicketPriceDescriptionEn);
        dest.writeString(FareBufferZoneDescriptionZh);
        dest.writeString(FareBufferZoneDescriptionEn);
        dest.writeString(RouteMapImageUrl);
        dest.writeString(UpdateTime);
        dest.writeInt(VersionID);
        dest.writeParcelable(CityName, flags);
        dest.writeInt(SearchSocre);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public int compareTo(@NonNull BusRoute busRoute) {
        return this.getSearchSocre() - busRoute.getSearchSocre();
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

    public boolean isHasSubRoutes() {
        return HasSubRoutes;
    }

    public void setHasSubRoutes(boolean hasSubRoutes) {
        HasSubRoutes = hasSubRoutes;
    }

    public String[] getOperatorIDs() {
        return OperatorIDs;
    }

    public void setOperatorIDs(String[] operatorIDs) {
        OperatorIDs = operatorIDs;
    }

    public String getAuthorityID() {
        return AuthorityID;
    }

    public void setAuthorityID(String authorityID) {
        AuthorityID = authorityID;
    }

    public String getProviderID() {
        return ProviderID;
    }

    public void setProviderID(String providerID) {
        ProviderID = providerID;
    }

    public ArrayList<BusSubRoute> getSubRoutes() {
        return SubRoutes;
    }

    public void setSubRoutes(ArrayList<BusSubRoute> subRoutes) {
        SubRoutes = subRoutes;
    }

    public String getBusRouteType() {
        return BusRouteType;
    }

    public void setBusRouteType(String busRouteType) {
        BusRouteType = busRouteType;
    }

    @Override
    public NameType getRouteName() {
        return RouteName;
    }

    public void setRouteName(NameType routeName) {
        RouteName = routeName;
    }

    public String getDepartureStopNameZh() {
        return DepartureStopNameZh;
    }

    public void setDepartureStopNameZh(String departureStopNameZh) {
        DepartureStopNameZh = departureStopNameZh;
    }

    public String getDepartureStopNameEn() {
        return DepartureStopNameEn;
    }

    public void setDepartureStopNameEn(String departureStopNameEn) {
        DepartureStopNameEn = departureStopNameEn;
    }

    public String getDestinationStopNameZh() {
        return DestinationStopNameZh;
    }

    public void setDestinationStopNameZh(String destinationStopNameZh) {
        DestinationStopNameZh = destinationStopNameZh;
    }

    public String getDestinationStopNameEn() {
        return DestinationStopNameEn;
    }

    public void setDestinationStopNameEn(String destinationStopNameEn) {
        DestinationStopNameEn = destinationStopNameEn;
    }

    public String getTicketPriceDescriptionZh() {
        return TicketPriceDescriptionZh;
    }

    public void setTicketPriceDescriptionZh(String ticketPriceDescriptionZh) {
        TicketPriceDescriptionZh = ticketPriceDescriptionZh;
    }

    public String getTicketPriceDescriptionEn() {
        return TicketPriceDescriptionEn;
    }

    public void setTicketPriceDescriptionEn(String ticketPriceDescriptionEn) {
        TicketPriceDescriptionEn = ticketPriceDescriptionEn;
    }

    public String getFareBufferZoneDescriptionZh() {
        return FareBufferZoneDescriptionZh;
    }

    public void setFareBufferZoneDescriptionZh(String fareBufferZoneDescriptionZh) {
        FareBufferZoneDescriptionZh = fareBufferZoneDescriptionZh;
    }

    public String getFareBufferZoneDescriptionEn() {
        return FareBufferZoneDescriptionEn;
    }

    public void setFareBufferZoneDescriptionEn(String fareBufferZoneDescriptionEn) {
        FareBufferZoneDescriptionEn = fareBufferZoneDescriptionEn;
    }

    public String getRouteMapImageUrl() {
        return RouteMapImageUrl;
    }

    public void setRouteMapImageUrl(String routeMapImageUrl) {
        RouteMapImageUrl = routeMapImageUrl;
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

    public NameType getCityName() {
        return CityName;
    }

    public void setCityName(NameType cityName) {
        CityName = cityName;
    }

    public int getSearchSocre() {
        return SearchSocre;
    }

    public void setSearchSocre(int searchSocre) {
        SearchSocre = searchSocre;
    }
}
