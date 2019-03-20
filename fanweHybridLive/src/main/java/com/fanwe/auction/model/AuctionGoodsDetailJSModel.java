package com.fanwe.auction.model;

import com.fanwe.live.model.StartAppPageJsonModel;

/**
 * Created by Administrator on 2016/9/7.
 */
public class AuctionGoodsDetailJSModel extends StartAppPageJsonModel
{
    private AuctionGoodsDetailJsDataModel data;

    public AuctionGoodsDetailJsDataModel getData()
    {
        return data;
    }

    public void setData(AuctionGoodsDetailJsDataModel data)
    {
        this.data = data;
    }
}
