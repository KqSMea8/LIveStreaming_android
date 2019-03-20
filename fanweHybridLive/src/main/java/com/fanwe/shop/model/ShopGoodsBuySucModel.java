package com.fanwe.shop.model;

/**
 * Created by Administrator on 2016/12/5.
 */

public class ShopGoodsBuySucModel
{
    private String goods_logo;//图片
    private int quantity;//数量
    private String goods_name;//名称

    public String getGoods_logo() {
        return goods_logo;
    }

    public void setGoods_logo(String goods_logo) {
        this.goods_logo = goods_logo;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }
}
