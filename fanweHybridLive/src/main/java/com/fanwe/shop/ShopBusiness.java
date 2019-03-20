package com.fanwe.shop;

import com.fanwe.library.common.SDHandlerManager;
import com.fanwe.live.LiveConstant;
import com.fanwe.live.activity.room.ILiveActivity;
import com.fanwe.live.business.LiveBaseBusiness;
import com.fanwe.live.model.custommsg.MsgModel;
import com.fanwe.shop.model.custommsg.CustomMsgShopBuySuc;
import com.fanwe.shop.model.custommsg.CustomMsgShopPush;

/**
 * Created by Administrator on 2016/12/28.
 */

public class ShopBusiness extends LiveBaseBusiness
{
    public static final long delayTime = 10000;

    private ShopBusinessListener shopBusinessListener;

    public ShopBusiness(ILiveActivity liveActivity)
    {
        super(liveActivity);
    }

    public void setShopBusinessListener(ShopBusinessListener shopBusinessListener)
    {
        this.shopBusinessListener = shopBusinessListener;
    }

    public void onMsgShop(MsgModel msg)
    {

    }

    /**
     * 定时器定时移除view
     *
     * @param delay
     */
    public void startShowGoodsViewRunnable(long delay)
    {
    }

    public void stopShowGoodsViewRunnable()
    {
    }

    private Runnable dismissRunnable = new Runnable()
    {
        @Override
        public void run()
        {
        }
    };

    protected void onMsgShopGoodsPush(CustomMsgShopPush customMsgShopPush)
    {
    }

    protected void onMsgShopGoodsBuySuccess(CustomMsgShopBuySuc customMsgShopBuySuc)
    {
    }

    @Override
    protected BaseBusinessCallback getBaseBusinessCallback()
    {
        return shopBusinessListener;
    }

    public interface ShopBusinessListener extends BaseBusinessCallback
    {
        void onShopMsgShopPush(CustomMsgShopPush customMsgShopPush);

        void onShopMsgShopBuySuc(CustomMsgShopBuySuc customMsgShopBuySuc);

        void onShopCountdownEnd();
    }
}
