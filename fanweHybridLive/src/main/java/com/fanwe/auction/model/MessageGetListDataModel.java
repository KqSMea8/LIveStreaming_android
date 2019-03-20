package com.fanwe.auction.model;

import java.util.List;

/**
 * Created by Administrator on 2016/8/11.
 */
public class MessageGetListDataModel
{
    private int rs_count;
    private PageModel page;
    private List<MessageGetListDataListItemModel> list;

    public int getRs_count()
    {
        return rs_count;
    }

    public void setRs_count(int rs_count)
    {
        this.rs_count = rs_count;
    }

    public PageModel getPage()
    {
        return page;
    }

    public void setPage(PageModel page)
    {
        this.page = page;
    }

    public List<MessageGetListDataListItemModel> getList()
    {
        return list;
    }

    public void setList(List<MessageGetListDataListItemModel> list)
    {
        this.list = list;
    }
}
