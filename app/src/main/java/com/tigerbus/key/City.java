package com.tigerbus.key;

/**
 * Created by Kailin on 2017/12/31.
 */

public enum City {
    Taipei("台北市"),
    NewTaipei("新北市"),
    Taoyuan("桃園市"),
    Taichung("台中市"),
    Tainan("台南市"),
    Kaohsiung("高雄市"),
    Keelung("基隆市"), //基隆
    Hsinchu("新竹市"), //新竹
    HsinchuCounty("新竹縣"),
    MiaoliCounty("苗栗縣"),//苗栗
    ChanghuaCounty("彰化縣"),//彰化
    NantouCounty("南投縣"),//南投
    YunlinCounty("雲林縣"),//雲林
    ChiayiCounty("嘉義縣"),//嘉義
    Chiayi("嘉義市"),
    PingtungCounty("屏東縣"),
    YilanCounty("宜蘭縣"),//宜蘭
    HualienCounty("花蓮縣"),//花蓮
    TaitungCounty("台東縣"),//台東
    PenghuCounty("澎湖縣"),//澎湖
    KinmenCounty("金門縣");//金門

    private String city;

    City(String city){
        this.city = city;
    }

    public String getCity(){
        return city;
    }
}
