package com.fanwe.live.model;

/**
 * Created by Administrator on 2016/11/21.
 */

public class App_monitorLiveModel
{
    private int allow_live_pay;//允许切换付费模式 1允许 0不允许
    private int allow_mention;//	允许付费模式提档 1允许 0不允许（在付费模式下有效）
    private String live_fee;//	付费直播 收费多少 （在付费模式下有效）

    public int getAllow_live_pay()
    {
        return allow_live_pay;
    }

    public void setAllow_live_pay(int allow_live_pay)
    {
        this.allow_live_pay = allow_live_pay;
    }

    public int getAllow_mention()
    {
        return allow_mention;
    }

    public void setAllow_mention(int allow_mention)
    {
        this.allow_mention = allow_mention;
    }

    public String getLive_fee()
    {
        return live_fee;
    }

    public void setLive_fee(String live_fee)
    {
        this.live_fee = live_fee;
    }
}
