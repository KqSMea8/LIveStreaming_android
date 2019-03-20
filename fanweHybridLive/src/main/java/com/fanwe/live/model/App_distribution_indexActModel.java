package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActModel;

import java.util.ArrayList;

/**
 * Created by yhz on 2017/1/3.
 */

public class App_distribution_indexActModel extends BaseActModel
{
    private PageModel page;
    private ArrayList<DistributionItemModel> data;

    public PageModel getPage()
    {
        return page;
    }

    public void setPage(PageModel page)
    {
        this.page = page;
    }

    public ArrayList<DistributionItemModel> getData()
    {
        return data;
    }

    public void setData(ArrayList<DistributionItemModel> data)
    {
        this.data = data;
    }
}
