package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActModel;

import java.util.List;

/**
 * Created by Administrator on 2018/9/19 0019.
 */

public class PackgeModel extends BaseActModel {

    private int status;
    private List<LiveGiftModel> list;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }


    public List<LiveGiftModel> getList() {
        return list;
    }

    public void setList(List<LiveGiftModel> list) {
        this.list = list;
    }
}
