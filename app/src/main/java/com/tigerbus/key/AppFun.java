package com.tigerbus.key;


import com.tigerbus.ui.MainActivity;

public enum AppFun {
    setting(""),                                                            // 設定
    curr(MainActivity.class.getName()),                                 // 台/外幣
    curracctqry(MainActivity.class.getName()),                   // 查詢帳戶明細
    currtrinput(MainActivity.class.getName()),                         // 轉帳
    currtrinputpretr(MainActivity.class.getName()),                    // 查詢預約
    fund(MainActivity.class.getName()),                                 // 財富管理
    card(MainActivity.class.getName()),                                 // 信用卡
    goldsecurities(MainActivity.class.getName()),                       // 黃金/證卷
    counter(MainActivity.class.getName()),                              // 豐雲櫃檯
    cloudlending(MainActivity.class.getName()),                         // 雲端貸款
    paybill(MainActivity.class.getName()),                        // 繳費網
    rate(MainActivity.class.getName()),                                 // 匯/利率
    place(MainActivity.class.getName()),                                // 分行ATM
    funcashierportal(MainActivity.class.getName()),                     // 豐掌櫃
    videochannel(MainActivity.class.getName()),;                  // 影音頻道

    private String value;

    AppFun(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
