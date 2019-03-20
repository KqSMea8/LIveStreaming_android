package com.fanwe.pay.model;

import com.fanwe.hybrid.model.BaseActModel;

/**
 * Created by Administrator on 2016/11/18.
 */

public class App_live_live_payActModel extends BaseActModel
{
    private int live_viewer;
    private int live_fee;
    private int live_pay_type;
    private int count_down;

    public int getLive_viewer()
    {
        return live_viewer;
    }

    public void setLive_viewer(int live_viewer)
    {
        this.live_viewer = live_viewer;
    }

    public int getLive_fee()
    {
        return live_fee;
    }

    public void setLive_fee(int live_fee)
    {
        this.live_fee = live_fee;
    }

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
}
