package com.fanwe.auction.model;

import java.util.List;

/**
 * Created by Administrator on 2016/8/23.
 */
public class PaiUserGetVideoDataModel
{
    private PaiUserGoodsDetailDataInfoModel info;
    private List<PaiBuyerModel> buyer;
    private int has_join;

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

    public List<PaiBuyerModel> getBuyer()
    {
        return buyer;
    }

    public void setBuyer(List<PaiBuyerModel> buyer)
    {
        this.buyer = buyer;
    }
}
