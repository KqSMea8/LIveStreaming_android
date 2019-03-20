package com.fanwe.pay.model;

import com.fanwe.hybrid.model.BaseActModel;

/**
 * Created by Administrator on 2016/11/18.
 */

public class App_live_live_pay_deductActModel extends BaseActModel
{
    private int is_live_pay;//是否付费模式
    private int on_live_pay;//是否已经在收费中
    private int is_diamonds_low;//是否余额低
    private int is_recharge;//是否需要充值
    private int live_pay_type;//直播收费类型 0按时，1按场

    private int diamonds;//可用秀豆
    private int count_down;//倒计时（秒）
    private String msg;//	提示信息
    private int total_time;//累计观看时长 （秒）
    private int total_minute;//累计观看分钟
    private int total_diamonds;//累计扣费 （秀豆）
    private int live_fee;//	当前收费价
    private long ticket;//主播映票

    public int getLive_pay_type()
    {
        return live_pay_type;
    }

    public void setLive_pay_type(int live_pay_type)
    {
        this.live_pay_type = live_pay_type;
    }

    public int getCount_down()
    {
        return count_down;
    }

    public void setCount_down(int count_down)
    {
        this.count_down = count_down;
    }

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

    public int getDiamonds()
    {
        return diamonds;
    }

    public void setDiamonds(int diamonds)
    {
        this.diamonds = diamonds;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public int getTotal_time()
    {
        return total_time;
    }

    public void setTotal_time(int total_time)
    {
        this.total_time = total_time;
        this.total_minute = total_time / 60;
    }

    public int getTotal_minute()
    {
        return total_minute;
    }

    public void setTotal_minute(int total_minute)
    {
        this.total_minute = total_minute;
    }

    public int getTotal_diamonds()
    {
        return total_diamonds;
    }

    public void setTotal_diamonds(int total_diamonds)
    {
        this.total_diamonds = total_diamonds;
    }

    public int getLive_fee()
    {
        return live_fee;
    }

    public void setLive_fee(int live_fee)
    {
        this.live_fee = live_fee;
    }

    public long getTicket()
    {
        return ticket;
    }

    public void setTicket(long ticket)
    {
        this.ticket = ticket;
    }
}
