package com.fanwe.auction.model;

import com.fanwe.hybrid.model.BaseActModel;

/**
 * Created by shibx on 2016/8/9.
 */
public class App_auction_goodsTagsModel extends BaseActModel{
    private GoodsTagsDataModel data;

    public GoodsTagsDataModel getData() {
        return data;
    }

    public void setData(GoodsTagsDataModel data) {
        this.data = data;
    }
}
