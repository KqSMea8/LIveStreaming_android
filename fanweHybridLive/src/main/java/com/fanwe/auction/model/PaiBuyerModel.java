package com.fanwe.auction.model;

/**
 * Created by shibx on 2016/8/18.
 */
public class PaiBuyerModel
{
    private String user_id;
    private String head_image;
    private int user_level;
    private int v_type;
    private String v_icon;
    private int type;
    private long left_time;
    private String pai_diamonds;
    private String nick_name;
    private String pay_url;
    private String goods_name;
    private String goods_icon;
    private String order_sn;

    public String getNick_name()
    {
        return nick_name;
    }

    public void setNick_name(String nick_name)
    {
        this.nick_name = nick_name;
    }

    public String getPai_diamonds()
    {
        return pai_diamonds;
    }

    public void setPai_diamonds(String pai_diamonds)
    {
        this.pai_diamonds = pai_diamonds;
    }

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }

    public String getHead_image()
    {
        return head_image;
    }

    public void setHead_image(String head_image)
    {
        this.head_image = head_image;
    }

    public int getUser_level()
    {
        return user_level;
    }

    public void setUser_level(int user_level)
    {
        this.user_level = user_level;
    }

    public int getV_type()
    {
        return v_type;
    }

    public void setV_type(int v_type)
    {
        this.v_type = v_type;
    }

    public String getV_icon()
    {
        return v_icon;
    }

    public void setV_icon(String v_icon)
    {
        this.v_icon = v_icon;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public long getLeft_time()
    {
        return left_time;
    }

    public void setLeft_time(long left_time)
    {
        this.left_time = left_time;
    }

    public String getPay_url()
    {
        return pay_url;
    }

    public void setPay_url(String pay_url)
    {
        this.pay_url = pay_url;
    }

    public String getGoods_name()
    {
        return goods_name;
    }

    public void setGoods_name(String goods_name)
    {
        this.goods_name = goods_name;
    }

    public String getOrder_sn()
    {
        return order_sn;
    }

    public void setOrder_sn(String order_sn)
    {
        this.order_sn = order_sn;
    }

    public String getGoods_icon()
    {
        return goods_icon;
    }

    public void setGoods_icon(String goods_icon)
    {
        this.goods_icon = goods_icon;
    }
}
