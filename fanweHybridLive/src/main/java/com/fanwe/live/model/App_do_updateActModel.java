package com.fanwe.live.model;

/**
 * Created by Administrator on 2016/7/7.
 */
public class App_do_updateActModel extends UserInfoActModel
{
    private int need_bind_mobile;//1 绑定手机 0 不绑定手机
    private int first_login;
    private int new_level;

    public int getNeed_bind_mobile()
    {
        return need_bind_mobile;
    }

    public void setNeed_bind_mobile(int need_bind_mobile)
    {
        this.need_bind_mobile = need_bind_mobile;
    }

    public int getFirst_login()
    {
        return first_login;
    }

    public void setFirst_login(int first_login)
    {
        this.first_login = first_login;
    }

    public int getNew_level()
    {
        return new_level;
    }

    public void setNew_level(int new_level)
    {
        this.new_level = new_level;
    }
}
