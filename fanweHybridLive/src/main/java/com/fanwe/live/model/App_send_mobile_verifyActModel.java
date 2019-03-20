package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActModel;

/**
 * Created by Administrator on 2016/7/6.
 */
public class App_send_mobile_verifyActModel extends BaseActModel
{
    private UserModel user;
    private int time;

    public int getTime()
    {
        return time;
    }

    public void setTime(int time)
    {
        this.time = time;
    }
}
