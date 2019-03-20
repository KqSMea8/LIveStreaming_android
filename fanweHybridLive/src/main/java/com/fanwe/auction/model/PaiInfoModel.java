package com.fanwe.auction.model;

/**
 * Created by Administrator on 2016/10/12.
 */

public class PaiInfoModel
{
    private String user_id;
    private String user_name;
    private String pai_number;
    private String pai_diamonds;

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }

    public String getUser_name()
    {
        return user_name;
    }

    public void setUser_name(String user_name)
    {
        this.user_name = user_name;
    }

    public String getPai_number()
    {
        return pai_number;
    }

    public void setPai_number(String pai_number)
    {
        this.pai_number = pai_number;
    }

    public String getPai_diamonds()
    {
        return pai_diamonds;
    }

    public void setPai_diamonds(String pai_diamonds)
    {
        this.pai_diamonds = pai_diamonds;
    }
}
