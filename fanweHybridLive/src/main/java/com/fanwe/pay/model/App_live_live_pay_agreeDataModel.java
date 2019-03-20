package com.fanwe.pay.model;

/**
 * Created by Administrator on 2016/11/18.
 */

public class App_live_live_pay_agreeDataModel
{
    private int is_agree;
    private int diamonds;
    private int is_diamonds_low;
    private int is_recharge;
    private String msg;
    private int count_down;
    private int live_fee;

    public int getIs_agree()
    {
        return is_agree;
    }

    public void setIs_agree(int is_agree)
    {
        this.is_agree = is_agree;
    }

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

    public int getCount_down()
    {
        return count_down;
    }

    public void setCount_down(int count_down)
    {
        this.count_down = count_down;
    }

    public int getLive_fee()
    {
        return live_fee;
    }

    public void setLive_fee(int live_fee)
    {
        this.live_fee = live_fee;
    }
}
