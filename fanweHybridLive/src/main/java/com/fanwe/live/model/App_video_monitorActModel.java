package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActModel;

/**
 * Created by Administrator on 2016/11/18.
 */

public class App_video_monitorActModel extends BaseActModel
{
    private int allow_live_pay;
    private int allow_mention;
    private String live_fee;

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
