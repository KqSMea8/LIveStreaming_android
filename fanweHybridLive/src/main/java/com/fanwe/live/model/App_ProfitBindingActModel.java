package com.fanwe.live.model;

/**
 * Created by shibx on 2016/7/21.
 */
public class App_ProfitBindingActModel extends UserInfoActModel{
    private int subscribe;
    private int mobile_exist;
    private int binding_wx;

    public int getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(int subscribe) {
        this.subscribe = subscribe;
    }

    public int getMobile_exist() {
        return mobile_exist;
    }

    public void setMobile_exist(int mobile_exist) {
        this.mobile_exist = mobile_exist;
    }

    public int getBinding_wx() {
        return binding_wx;
    }

    public void setBinding_wx(int binding_wx) {
        this.binding_wx = binding_wx;
    }
}
