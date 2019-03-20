package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActModel;

import java.util.List;

/**
 * vip页面初始化对象
 * Created by shibx on 2017/1/16.
 */

public class App_UserVipPurchaseActModel extends BaseActModel {

    private int is_vip;//用户是否为vip
    private String vip_expire_time;//会员到期时间
    private List<PayItemModel> pay_list;
    private List<RuleItemModel> rule_list;

    public int getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(int is_vip) {
        this.is_vip = is_vip;
    }

    public String getVip_expire_time() {
        return vip_expire_time;
    }

    public void setVip_expire_time(String vip_expire_time) {
        this.vip_expire_time = vip_expire_time;
    }

    public List<PayItemModel> getPay_list() {
        return pay_list;
    }

    public void setPay_list(List<PayItemModel> pay_list) {
        this.pay_list = pay_list;
    }

    public List<RuleItemModel> getRule_list() {
        return rule_list;
    }

    public void setRule_list(List<RuleItemModel> rule_list) {
        this.rule_list = rule_list;
    }
}
