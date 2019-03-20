package com.fanwe.auction.model;

import java.util.List;

/**
 * Created by Administrator on 2016/11/8.
 */

public class App_pai_podcast_addpaidetailDataModel
{
    private int goods_id;//竞拍商品id，否则为0
    private String name;//竞拍名称
    private List<String> imgs;//图片列表 为空时 输出 []
    private String bz_diamonds;//竞拍保证金
    private String qp_diamonds;//起拍价
    private int jj_diamonds;//每次加价
    private float pai_time;//竞拍时长
    private int pai_yanshi;//每次竞拍延时（单位分）
    private int max_yanshi;//最大延时(次)
    private int shop_id;//[如果为第三方竞拍，此项必选]
    private String shop_name;//[如果为第三方竞拍，此项必选]

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public String getBz_diamonds() {
        return bz_diamonds;
    }

    public void setBz_diamonds(String bz_diamonds) {
        this.bz_diamonds = bz_diamonds;
    }

    public String getQp_diamonds() {
        return qp_diamonds;
    }

    public void setQp_diamonds(String qp_diamonds) {
        this.qp_diamonds = qp_diamonds;
    }

    public int getJj_diamonds() {
        return jj_diamonds;
    }

    public void setJj_diamonds(int jj_diamonds) {
        this.jj_diamonds = jj_diamonds;
    }

    public float getPai_time() {
        return pai_time;
    }

    public void setPai_time(float pai_time) {
        this.pai_time = pai_time;
    }

    public int getPai_yanshi() {
        return pai_yanshi;
    }

    public void setPai_yanshi(int pai_yanshi) {
        this.pai_yanshi = pai_yanshi;
    }

    public int getMax_yanshi() {
        return max_yanshi;
    }

    public void setMax_yanshi(int max_yanshi) {
        this.max_yanshi = max_yanshi;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }
}
