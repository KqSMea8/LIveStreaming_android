package com.fanwe.live.model;


import com.fanwe.hybrid.model.BaseActModel;

/**
 * Created by shibx on 2017/02/27.
 * 游戏币兑换对象
 */
public class App_gameCoinsExchangeActModel extends BaseActModel {
    private long account_diamonds;
    private long coin;

    public long getAccount_diamonds() {
        return account_diamonds;
    }

    public void setAccount_diamonds(long account_diamonds) {
        this.account_diamonds = account_diamonds;
    }

    public long getCoin() {
        return coin;
    }

    public void setCoin(long coin) {
        this.coin = coin;
    }
}
