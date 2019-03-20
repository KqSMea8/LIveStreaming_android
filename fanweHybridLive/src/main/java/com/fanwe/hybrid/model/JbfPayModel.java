package com.fanwe.hybrid.model;

/**
 * Created by Administrator on 2017/5/12.
 */

public class JbfPayModel
{
    private String partnerid;
    private String playerid;
    private String goodsName;
    private String amount;
    private String payid;
    private int withType;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPayid() {
        return payid;
    }

    public void setPayid(String payid) {
        this.payid = payid;
    }

    public String getPlayerid() {
        return playerid;
    }

    public void setPlayerid(String playerid) {
        this.playerid = playerid;
    }

    public int getWithType() {
        return withType;
    }

    public void setWithType(int withType) {
        this.withType = withType;
    }
}
