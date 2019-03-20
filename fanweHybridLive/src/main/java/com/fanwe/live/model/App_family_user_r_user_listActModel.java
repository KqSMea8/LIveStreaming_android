package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActModel;

import java.util.List;

/**
 * 家族成员申请列表
 * Created by Administrator on 2016/9/27.
 */

public class App_family_user_r_user_listActModel extends BaseActModel
{
    private int rs_count;
    private int apply_count;
    private List<UserModel> list;
    private PageModel page;

    public int getRs_count() {
        return rs_count;
    }

    public void setRs_count(int rs_count) {
        this.rs_count = rs_count;
    }

    public int getApply_count() {
        return apply_count;
    }

    public void setApply_count(int apply_count) {
        this.apply_count = apply_count;
    }

    public List<UserModel> getList() {
        return list;
    }

    public void setList(List<UserModel> list) {
        this.list = list;
    }

    public PageModel getPage() {
        return page;
    }

    public void setPage(PageModel page) {
        this.page = page;
    }
}
