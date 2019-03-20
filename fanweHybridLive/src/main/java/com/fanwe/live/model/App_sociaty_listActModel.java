package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActModel;

import java.util.List;

/**
 * Created by Administrator on 2017/2/3.
 */

public class App_sociaty_listActModel extends BaseActModel
{
    private int rs_count;
    private List<App_sociaty_listItemModel> list;
    private PageModel page;

    public List<App_sociaty_listItemModel> getList() {
        return list;
    }

    public void setList(List<App_sociaty_listItemModel> list) {
        this.list = list;
    }

    public PageModel getPage() {
        return page;
    }

    public void setPage(PageModel page) {
        this.page = page;
    }

    public int getRs_count() {
        return rs_count;
    }

    public void setRs_count(int rs_count) {
        this.rs_count = rs_count;
    }
}
