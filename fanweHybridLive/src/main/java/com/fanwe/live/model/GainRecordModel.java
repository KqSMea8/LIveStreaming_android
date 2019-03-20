package com.fanwe.live.model;

/**
 * Created by shibx on 2016/7/19.
 */
public class GainRecordModel {
    private String money;//金额
    private String pay_time;//提现成功时间
    private String create_time;//申请提现时间
    private int is_pay;//提现状态 ：1 审核通过 3，提现成功

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getIs_pay() {
        return is_pay;
    }

    public void setIs_pay(int is_pay) {
        this.is_pay = is_pay;
    }
}
