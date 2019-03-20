package com.fanwe.auction.model;

/**
 * Created by Administrator on 2016/8/21 0021.
 */
public class AuctionCreateSuccessInfoModel
{
    private String name;
    private int goods_id;
    private int bz_diamonds;
    private int qp_diamonds;
    private int jj_diamonds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getBz_diamonds() {
        return bz_diamonds;
    }

    public void setBz_diamonds(int bz_diamonds) {
        this.bz_diamonds = bz_diamonds;
    }

    public int getQp_diamonds() {
        return qp_diamonds;
    }

    public void setQp_diamonds(int qp_diamonds) {
        this.qp_diamonds = qp_diamonds;
    }

    public int getJj_diamonds() {
        return jj_diamonds;
    }

    public void setJj_diamonds(int jj_diamonds) {
        this.jj_diamonds = jj_diamonds;
    }
}
