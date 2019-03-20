package com.fanwe.shop.model.custommsg;

import com.fanwe.live.LiveConstant;
import com.fanwe.shop.model.ShopGoodsDetailModel;


/**
 * Created by Administrator on 2016/9/20.
 */
public class CustomMsgShopPush extends CustomMsgBaseShop
{
    private ShopGoodsDetailModel goods;

    public CustomMsgShopPush()
    {
        super();
        setType(LiveConstant.CustomMsgType.MSG_SHOP_GOODS_PUSH);
    }

    public ShopGoodsDetailModel getGoods() {
        return goods;
    }

    public void setGoods(ShopGoodsDetailModel goods) {
        this.goods = goods;
    }
}
