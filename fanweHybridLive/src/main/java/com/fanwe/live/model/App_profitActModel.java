package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActModel;

/**
 * Created by shibx on 2016/7/19.
 */
public class App_profitActModel extends BaseActModel{

    private String ticket;//钱票总数
    private String useable_ticket;//可兑换钱票
    private String money;//可提现人民币
    private String day_cash_max ;//当日可提现金额数量
    private String day_ticket_max;
    private String ticket_catty_ratio;

    private int refund_exist;//是否有未处理的提现订单

    private int withdrawals_wx;//是否开启微信提现
    private int withdrawals_alipay;//是否开启支付宝提现
    private int withdrawals_bankcard;//是否开启支付宝提现
    private int subscribe;//是否关注公众号
    private int binding_bankcard;//是否绑定银行卡
    private int binding_wx;//是否绑定微信
    private int mobile_exist;//是否绑定手机
    private int binding_alipay;//是否绑定支付宝
    private String subscription;//微信公众号名称

    public int getBinding_bankcard() {
        return binding_bankcard;
    }

    public void setBinding_bankcard(int binding_bankcard) {
        this.binding_bankcard = binding_bankcard;
    }

    private int show_pai_ticket;//是否显示拍卖收入
    private String pai_ticket;//拍卖总收入
    private String pai_wait_ticket;//拍卖收入-待结算
    private int show_goods_ticket;//是否显示销售收入
    private String goods_ticket;//销售总收入
    private String goods_wait_ticket;//销售收入-待结算
    private String refund_explain;

    public int getWithdrawals_bankcard() {
        return withdrawals_bankcard;
    }

    public void setWithdrawals_bankcard(int withdrawals_bankcard) {
        this.withdrawals_bankcard = withdrawals_bankcard;
    }

    public String getRefund_explain() {
        return refund_explain;
    }

    public void setRefund_explain(String refund_explain) {
        this.refund_explain = refund_explain;
    }

    public int getRefund_exist() {
        return refund_exist;
    }

    public void setRefund_exist(int refund_exist) {
        this.refund_exist = refund_exist;
    }

    public String getDay_ticket_max() {
        return day_ticket_max;
    }

    public void setDay_ticket_max(String day_ticket_max) {
        this.day_ticket_max = day_ticket_max;
    }

    public String getTicket_catty_ratio() {
        return ticket_catty_ratio;
    }

    public void setTicket_catty_ratio(String ticket_catty_ratio) {
        this.ticket_catty_ratio = ticket_catty_ratio;
    }

    public String getDay_cash_max() {
        return day_cash_max;
    }

    public void setDay_cash_max(String day_cash_max) {
        this.day_cash_max = day_cash_max;
    }

    public int getWithdrawals_wx() {
        return withdrawals_wx;
    }

    public void setWithdrawals_wx(int withdrawals_wx) {
        this.withdrawals_wx = withdrawals_wx;
    }

    public int getWithdrawals_alipay() {
        return withdrawals_alipay;
    }

    public void setWithdrawals_alipay(int withdrawals_alipay) {
        this.withdrawals_alipay = withdrawals_alipay;
    }

    public int getBinding_alipay() {
        return binding_alipay;
    }

    public void setBinding_alipay(int binding_alipay) {
        this.binding_alipay = binding_alipay;
    }

    public int getShow_pai_ticket() {
        return show_pai_ticket;
    }

    public void setShow_pai_ticket(int show_pai_ticket) {
        this.show_pai_ticket = show_pai_ticket;
    }

    public String getPai_ticket() {
        return pai_ticket;
    }

    public void setPai_ticket(String pai_ticket) {
        this.pai_ticket = pai_ticket;
    }

    public String getPai_wait_ticket() {
        return pai_wait_ticket;
    }

    public void setPai_wait_ticket(String pai_wait_ticket) {
        this.pai_wait_ticket = pai_wait_ticket;
    }

    public int getShow_goods_ticket() {
        return show_goods_ticket;
    }

    public void setShow_goods_ticket(int show_goods_ticket) {
        this.show_goods_ticket = show_goods_ticket;
    }

    public String getGoods_ticket() {
        return goods_ticket;
    }

    public void setGoods_ticket(String goods_ticket) {
        this.goods_ticket = goods_ticket;
    }

    public String getGoods_wait_ticket() {
        return goods_wait_ticket;
    }

    public void setGoods_wait_ticket(String goods_wait_ticket) {
        this.goods_wait_ticket = goods_wait_ticket;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public String getUseable_ticket() {
        return useable_ticket;
    }

    public void setUseable_ticket(String useable_ticket) {
        this.useable_ticket = useable_ticket;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(int subscribe) {
        this.subscribe = subscribe;
    }

    public int getBinding_wx() {
        return binding_wx;
    }

    public void setBinding_wx(int binding_wx) {
        this.binding_wx = binding_wx;
    }

    public int getMobile_exist() {
        return mobile_exist;
    }

    public void setMobile_exist(int mobile_exist) {
        this.mobile_exist = mobile_exist;
    }
}
