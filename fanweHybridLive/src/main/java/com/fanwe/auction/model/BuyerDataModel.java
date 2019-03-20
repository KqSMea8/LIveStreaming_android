package com.fanwe.auction.model;

/**
 * Created by Administrator on 2016/9/1.
 */
public class BuyerDataModel
{
    private String goods_name;
    private String goods_icon;
    private String order_sn;

    public String getGoods_name()
    {
        return goods_name;
    }

    public void setGoods_name(String goods_name)
    {
        this.goods_name = goods_name;
    }

    public String getGoods_icon()
    {
        return goods_icon;
    }

    public void setGoods_icon(String goods_icon)
    {
        this.goods_icon = goods_icon;
    }

    public String getOrder_sn()
    {
        return order_sn;
    }

    public void setOrder_sn(String order_sn)
    {
        this.order_sn = order_sn;
    }
}
