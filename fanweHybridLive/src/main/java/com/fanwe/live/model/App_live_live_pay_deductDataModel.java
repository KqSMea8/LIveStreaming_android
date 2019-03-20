package com.fanwe.live.model;

/**
 * Created by Administrator on 2016/11/18.
 */

public class App_live_live_pay_deductDataModel
{
    private int diamonds;
    private int is_diamonds_low;
    private int is_recharge;
    private String msg;
    private int cumulative_time;
    private int cumulative_fee;

    public int getDiamonds()
    {
        return diamonds;
    }

    public void setDiamonds(int diamonds)
    {
        this.diamonds = diamonds;
    }

    public int getIs_diamonds_low()
    {
        return is_diamonds_low;
    }

    public void setIs_diamonds_low(int is_diamonds_low)
    {
        this.is_diamonds_low = is_diamonds_low;
    }

    public int getIs_recharge()
    {
        return is_recharge;
    }

    public void setIs_recharge(int is_recharge)
    {
        this.is_recharge = is_recharge;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public int getCumulative_time()
    {
        return cumulative_time;
    }

    public void setCumulative_time(int cumulative_time)
    {
        this.cumulative_time = cumulative_time;
    }

    public int getCumulative_fee()
    {
        return cumulative_fee;
    }

    public void setCumulative_fee(int cumulative_fee)
    {
        this.cumulative_fee = cumulative_fee;
    }
}
