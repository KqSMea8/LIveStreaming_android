package com.fanwe.auction.model;

/**
 * Created by Administrator on 2016/8/8.
 */
public class PageModel
{
    private int page;
    private int has_next;

    public int getPage()
    {
        return page;
    }

    public void setPage(int page)
    {
        this.page = page;
    }

    public int getHas_next()
    {
        return has_next;
    }

    public void setHas_next(int has_next)
    {
        this.has_next = has_next;
    }
}
