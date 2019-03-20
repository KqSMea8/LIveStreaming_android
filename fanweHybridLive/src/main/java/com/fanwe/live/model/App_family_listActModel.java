package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActModel;

import java.util.List;

/**
 * 申请加入家族列表
 * Created by Administrator on 2016/9/27.
 */

public class App_family_listActModel extends BaseActModel
{
    private int rs_count;
    private List<App_family_listItemModel> list;
    private PageModel page;

    public int getRs_count() {
        return rs_count;
    }

    public void setRs_count(int rs_count) {
        this.rs_count = rs_count;
    }

    public List<App_family_listItemModel> getList() {
        return list;
    }

    public void setList(List<App_family_listItemModel> list) {
        this.list = list;
    }

    public PageModel getPage() {
        return page;
    }

    public void setPage(PageModel page) {
        this.page = page;
    }
}
