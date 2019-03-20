package com.fanwe.auction.model;

import java.util.List;

/**
 * Created by Administrator on 2016/8/8.
 */
public class PaiUserGoodsDetailDataModel
{
    private int has_join;
    private PaiUserGoodsDetailDataInfoModel info;
    private int rs_count;
    private List<PaiUserGoodsDetailDataPaiListItemModel> pai_list;
    private PageModel page;

    public int getRs_count()
    {
        return rs_count;
    }

    public void setRs_count(int rs_count)
    {
        this.rs_count = rs_count;
    }

    public int getHas_join()
    {
        return has_join;
    }

    public void setHas_join(int has_join)
    {
        this.has_join = has_join;
    }

    public PaiUserGoodsDetailDataInfoModel getInfo()
    {
        return info;
    }

    public void setInfo(PaiUserGoodsDetailDataInfoModel info)
    {
        this.info = info;
    }

    public List<PaiUserGoodsDetailDataPaiListItemModel> getPai_list()
    {
        return pai_list;
    }

    public void setPai_list(List<PaiUserGoodsDetailDataPaiListItemModel> pai_list)
    {
        this.pai_list = pai_list;
    }

    public PageModel getPage()
    {
        return page;
    }

    public void setPage(PageModel page)
    {
        this.page = page;
    }
}
