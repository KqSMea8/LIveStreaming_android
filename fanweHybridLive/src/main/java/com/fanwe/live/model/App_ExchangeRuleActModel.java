package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActModel;

import java.util.List;

/**
 * Created by shibx on 2016/7/19.
 */
public class App_ExchangeRuleActModel extends BaseActModel{

    private List<ExchangeModel> exchange_rules;//兑换规则
    private int ticket;
    private int diamonds;
    private int useable_ticket;
    private int min_ticket;
    private String ratio;

    public int getMin_ticket() {
        return min_ticket;
    }

    public void setMin_ticket(int min_ticket) {
        this.min_ticket = min_ticket;
    }

    public int getUseable_ticket() {
        return useable_ticket;
    }

    public void setUseable_ticket(int useable_ticket) {
        this.useable_ticket = useable_ticket;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public App_ExchangeRuleActModel() {
    }

    public List<ExchangeModel> getExchange_rules() {
        return exchange_rules;
    }

    public void setExchange_rules(List<ExchangeModel> exchange_rules) {
        this.exchange_rules = exchange_rules;
    }

    public int getTicket() {
        return ticket;
    }

    public void setTicket(int ticket) {
        this.ticket = ticket;
    }

    public int getDiamonds() {
        return diamonds;
    }

    public void setDiamonds(int diamonds) {
        this.diamonds = diamonds;
    }
}
