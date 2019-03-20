package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActModel;

import java.util.List;

/**
 * Created by shibx on 2016/7/19.
 */
public class App_GainRecordActModel extends BaseActModel{
    private String total_money;
    private List<GainRecordModel> list;

    public String getTotal_money() {
        return total_money;
    }

    public void setTotal_money(String total_money) {
        this.total_money = total_money;
    }

    public List<GainRecordModel> getList() {
        return list;
    }

    public void setList(List<GainRecordModel> list) {
        this.list = list;
    }
}
