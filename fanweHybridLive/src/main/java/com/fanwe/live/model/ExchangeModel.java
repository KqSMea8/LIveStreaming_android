package com.fanwe.live.model;

/**
 * Created by shibx on 2016/7/19.
 */
public class ExchangeModel {
    private int id;
    private int diamonds;//需要的秀豆
    private int ticket;//兑换的钱票
    private int is_effect;//状态 1为可用，0为不可用
    private int is_delete;//是否删除 0为未删除，1为删除

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDiamonds() {
        return diamonds;
    }

    public void setDiamonds(int diamonds) {
        this.diamonds = diamonds;
    }

    public int getTicket() {
        return ticket;
    }

    public void setTicket(int ticket) {
        this.ticket = ticket;
    }

    public int getIs_effect() {
        return is_effect;
    }

    public void setIs_effect(int is_effect) {
        this.is_effect = is_effect;
    }

    public int getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(int is_delete) {
        this.is_delete = is_delete;
    }
}
