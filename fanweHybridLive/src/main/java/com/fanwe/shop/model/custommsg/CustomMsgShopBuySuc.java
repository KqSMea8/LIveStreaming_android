package com.fanwe.shop.model.custommsg;

import com.fanwe.live.LiveConstant;
import com.fanwe.shop.model.ShopGoodsBuySucModel;

/**
 * Created by Administrator on 2016/12/5.
 */

public class CustomMsgShopBuySuc extends CustomMsgBaseShop
{
    private int is_self;//是否卖给自己，0表示送主播、1表示送自己
    private int score;//经验
    private ShopGoodsBuySucModel goods;

    public CustomMsgShopBuySuc() {
        super();
        setType(LiveConstant.CustomMsgType.MSG_SHOP_GOODS_BUY_SUCCESS);
    }

    public int getIs_self() {
        return is_self;
    }

    public void setIs_self(int is_self) {
        this.is_self = is_self;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ShopGoodsBuySucModel getGoods() {
        return goods;
    }

    public void setGoods(ShopGoodsBuySucModel goods) {
        this.goods = goods;
    }
}
