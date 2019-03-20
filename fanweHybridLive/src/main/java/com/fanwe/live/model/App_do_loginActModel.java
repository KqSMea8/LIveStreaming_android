package com.fanwe.live.model;


/**
 * Created by Administrator on 2016/7/6.
 */
public class App_do_loginActModel extends UserInfoActModel
{
    private int is_lack;//是否缺失信息

    private int first_login;
    private int new_level;

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

    public int getIs_lack()
    {
        return is_lack;
    }

    public void setIs_lack(int is_lack)
    {
        this.is_lack = is_lack;
    }

}
