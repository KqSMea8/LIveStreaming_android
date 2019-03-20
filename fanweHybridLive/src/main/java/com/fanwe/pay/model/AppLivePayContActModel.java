package com.fanwe.pay.model;

import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.live.model.PageModel;

import java.util.List;

/**
 * Created by Administrator on 2016/11/30.
 */

public class AppLivePayContActModel extends BaseActModel
{
    private PageModel page;
    private List<LivePayContDataModel> data;

    public PageModel getPage()
    {
        return page;
    }

    public void setPage(PageModel page)
    {
        this.page = page;
    }

    public List<LivePayContDataModel> getData()
    {
        return data;
    }

    public void setData(List<LivePayContDataModel> data)
    {
        this.data = data;
    }
}
