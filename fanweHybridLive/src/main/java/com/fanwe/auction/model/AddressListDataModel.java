package com.fanwe.auction.model;

import java.util.List;

/**
 * Created by Administrator on 2016/10/19.
 */

public class AddressListDataModel
{
    private int rs_count;//地址个数
    private List<AddressListItemActModel> list;
    private PageModel page;

    public int getRs_count() {
        return rs_count;
    }

    public void setRs_count(int rs_count) {
        this.rs_count = rs_count;
    }

    public List<AddressListItemActModel> getList() {
        return list;
    }

    public void setList(List<AddressListItemActModel> list) {
        this.list = list;
    }

    public PageModel getPage() {
        return page;
    }

    public void setPage(PageModel page) {
        this.page = page;
    }
}
