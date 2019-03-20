package com.fanwe.auction.model;

import java.util.List;

/**
 * Created by Administrator on 2016/11/25.
 */

public class PaiUserGoodsDetailDataCommodityDetailModel
{
    private List<PaiCommodityDetailPaiGoodsModel> pai_goods;
    private List<PaiCommodityDetailGoodsDetailModel> goods_detail;

    public List<PaiCommodityDetailPaiGoodsModel> getPai_goods() {
        return pai_goods;
    }

    public void setPai_goods(List<PaiCommodityDetailPaiGoodsModel> pai_goods) {
        this.pai_goods = pai_goods;
    }

    public List<PaiCommodityDetailGoodsDetailModel> getGoods_detail() {
        return goods_detail;
    }

    public void setGoods_detail(List<PaiCommodityDetailGoodsDetailModel> goods_detail) {
        this.goods_detail = goods_detail;
    }
}
