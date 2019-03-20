package com.fanwe.pay.model;

/**
 * Created by Administrator on 2016/11/24.
 */

public class AppGetVideoLiveModel
{
    private int is_live_pay;//直播是否收费 0指不收费，1指收费
    private int on_live_pay;//是否收费中 0指未开始收费，1指收费中
    private int live_fee;//倒计时 （秒）
    private int count_down;//	付费直播 收费多少 （在付费模式下有效）
    private int live_pay_time;//开始收费时间

    public int getIs_live_pay()
    {
        return is_live_pay;
    }

    public void setIs_live_pay(int is_live_pay)
    {
        this.is_live_pay = is_live_pay;
    }

    public int getOn_live_pay()
    {
        return on_live_pay;
    }

    public void setOn_live_pay(int on_live_pay)
    {
        this.on_live_pay = on_live_pay;
    }

    public int getLive_fee()
    {
        return live_fee;
    }

    public void setLive_fee(int live_fee)
    {
        this.live_fee = live_fee;
    }

    public int getCount_down()
    {
        return count_down;
    }

    public void setCount_down(int count_down)
    {
        this.count_down = count_down;
    }

    public int getLive_pay_time()
    {
        return live_pay_time;
    }

    public void setLive_pay_time(int live_pay_time)
    {
        this.live_pay_time = live_pay_time;
    }
}
