package com.fanwe.live.activity.room;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fanwe.auction.AuctionBusiness;
import com.fanwe.auction.appview.AuctionUserRanklistView;
import com.fanwe.auction.appview.room.RoomAuctionInfoView;
import com.fanwe.auction.dialog.AuctionResultsFailDialog;
import com.fanwe.auction.dialog.AuctionResultsNotifyPayDialog;
import com.fanwe.auction.dialog.AuctionResultsPaySucDialog;
import com.fanwe.auction.dialog.AuctionResultsSucDialog;
import com.fanwe.auction.model.App_pai_user_get_videoActModel;
import com.fanwe.auction.model.PaiBuyerModel;
import com.fanwe.auction.model.custommsg.CustomMsgAuctionCreateSuccess;
import com.fanwe.auction.model.custommsg.CustomMsgAuctionFail;
import com.fanwe.auction.model.custommsg.CustomMsgAuctionNotifyPay;
import com.fanwe.auction.model.custommsg.CustomMsgAuctionOffer;
import com.fanwe.auction.model.custommsg.CustomMsgAuctionPaySuccess;
import com.fanwe.auction.model.custommsg.CustomMsgAuctionSuccess;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.LiveInformation;
import com.fanwe.live.R;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.model.App_get_videoActModel;
import com.fanwe.live.model.custommsg.MsgModel;
import com.fanwe.o2o.event.O2OEShoppingCartDialogShowing;
import com.fanwe.shop.ShopBusiness;
import com.fanwe.shop.appview.room.RoomShopGoodsPushView;
import com.fanwe.shop.dialog.ShopGoodsPurchaseSucDialog;
import com.fanwe.shop.model.custommsg.CustomMsgShopBuySuc;
import com.fanwe.shop.model.custommsg.CustomMsgShopPush;

import java.util.List;

/**
 * 公共界面扩展
 */
public class LiveLayoutExtendActivity extends LiveLayoutGameExtendActivity implements AuctionBusiness.AuctionBusinessListener, ShopBusiness.ShopBusinessListener
{
    //-----------付费------------
    protected ViewGroup fl_live_pay_mode;
    //-----------竞拍------------
    protected AuctionBusiness auctionBusiness;

    protected RoomAuctionInfoView roomAuctionInfoView;//竞拍信息
    protected AuctionUserRanklistView auctionUserRanklistView;//付款排行榜

    protected ViewGroup fl_live_auction_info;//竞拍信息
    protected ViewGroup fl_live_auction_rank_list;//付款排行榜

    private AuctionResultsNotifyPayDialog dialogResult;
    private AuctionResultsSucDialog dialogResultSuc;
    private AuctionResultsPaySucDialog dialogResultPaySuc;
    private AuctionResultsFailDialog dialogResultFail;

    //-----------购物------------
    protected ShopBusiness liveShopBusiness;

    protected RoomShopGoodsPushView roomShopGoodsPushView;

    protected ViewGroup fl_live_goods_push;//购物推送

    protected ShopGoodsPurchaseSucDialog shopGoodsPurchaseSucDialog;
    @Override
    protected void init(Bundle savedInstanceState)
    {
        super.init(savedInstanceState);

        //-----------竞拍------------
        auctionBusiness = new AuctionBusiness();
        auctionBusiness.setAuctionBusinessListener(this);

        //-----------购物------------
        liveShopBusiness = new ShopBusiness(this);
        liveShopBusiness.setShopBusinessListener(this);
    }

    @Override
    protected void initLayout(View view)
    {
        super.initLayout(view);
        //-----------付费--------
        fl_live_pay_mode = (ViewGroup) view.findViewById(R.id.fl_live_pay_mode);
        //-----------竞拍------------
        fl_live_auction_info = (ViewGroup) view.findViewById(R.id.fl_live_auction_info);
        fl_live_auction_rank_list = (ViewGroup) view.findViewById(R.id.fl_live_auction_rank_list);
        //-----------购物------------
        fl_live_goods_push = (ViewGroup) view.findViewById(R.id.fl_live_goods_push);
    }

    @Override
    public void onBsRequestRoomInfoSuccess(App_get_videoActModel actModel)
    {
        super.onBsRequestRoomInfoSuccess(actModel);
        //在开启竞拍功能的情况下进入直播间请求拍卖信息
        if (AppRuntimeWorker.getShow_hide_pai_view() == 1)
        {
            int paiId = actModel.getPai_id();
            if (paiId > 0)
            {
                auctionBusiness.requestPaiInfo(paiId, null);
            }
        }
    }

    //-----------竞拍------------

    /**
     * 添加竞拍信息
     */
    protected void addLiveAuctionInfo(App_pai_user_get_videoActModel app_pai_user_get_videoActModel)
    {
        roomAuctionInfoView = new RoomAuctionInfoView(this);
        replaceView(fl_live_auction_info, roomAuctionInfoView);
        roomAuctionInfoView.bindAuctionDetailInfo(app_pai_user_get_videoActModel);
    }

    /**
     * 添加竞拍付款排行榜
     */
    protected void addLiveAuctionRankList(List<PaiBuyerModel> listBuyers)
    {
        auctionUserRanklistView = new AuctionUserRanklistView(this);
        replaceView(fl_live_auction_rank_list, auctionUserRanklistView);
        auctionUserRanklistView.setBuyers(listBuyers);
    }

    @Override
    public void onMsgAuction(MsgModel msg)
    {
        super.onMsgAuction(msg);
        auctionBusiness.onAuctionMsg(msg);
    }

    @Override
    public void onAuctionMsgCreateSuccess(CustomMsgAuctionCreateSuccess customMsg)
    {
        //竞拍创建成功
        LogUtil.i("onAuctionMsgCreateSuccess");
    }

    @Override
    public void onAuctionMsgOffer(CustomMsgAuctionOffer customMsg)
    {
        //竞拍出价
        LogUtil.i("onAuctionMsgOffer");
    }

    @Override
    public void onAuctionMsgSuccess(CustomMsgAuctionSuccess customMsg)
    {
        //竞拍成功
        LogUtil.i("onAuctionMsgSuccess");
        if (dialogResultSuc != null)
            dialogResultSuc.dismiss();

        dialogResultSuc = new AuctionResultsSucDialog(this, customMsg, auctionBusiness);
        dialogResultSuc.showCenter();

        addLiveAuctionRankList(customMsg.getBuyer());
    }

    @Override
    public void onAuctionMsgNotifyPay(CustomMsgAuctionNotifyPay customMsg)
    {
        //竞拍通知付款，比如第一名超时未付款，通知下一名付款
        LogUtil.i("onAuctionMsgNotifyPay");
        List<PaiBuyerModel> listBuyers = customMsg.getBuyer();
        if (listBuyers == null || listBuyers.size() == 0)
        {
            return;
        }
        addLiveAuctionRankList(listBuyers);

        if (dialogResultSuc != null)
            dialogResultSuc.dismiss();

        if (dialogResult != null)
            dialogResult.dismiss();

        dialogResult = new AuctionResultsNotifyPayDialog(this, customMsg, auctionBusiness);
        dialogResult.showCenter();
    }

    @Override
    public void onAuctionMsgFail(CustomMsgAuctionFail customMsg)
    {
        // 流拍
        LogUtil.i("onAuctionMsgFail");
        if (dialogResult != null)
        {
            dialogResult.dismiss();
        }

        if (dialogResultSuc != null)
        {
            dialogResultSuc.dismiss();
        }

        if (dialogResultFail != null)
        {
            dialogResultFail.dismiss();
        }

        dialogResultFail = new AuctionResultsFailDialog(this, customMsg);
        dialogResultFail.showCenter();

        removeView(roomAuctionInfoView);
        removeView(auctionUserRanklistView);
    }

    @Override
    public void onAuctionMsgPaySuccess(CustomMsgAuctionPaySuccess customMsg)
    {
        // 支付成功
        LogUtil.i("onAuctionMsgPaySuccess");
        if (dialogResultSuc != null)
            dialogResultSuc.dismiss();

        if (dialogResult != null)
            dialogResult.dismiss();

        if (dialogResultPaySuc != null)
            dialogResultPaySuc.dismiss();

        dialogResultPaySuc = new AuctionResultsPaySucDialog(this, customMsg);
        dialogResultPaySuc.showCenter();

        removeView(roomAuctionInfoView);
        removeView(auctionUserRanklistView);
    }

    @Override
    public void onAuctionPayRemaining(PaiBuyerModel buyer, long day, long hour, long min, long sec)
    {
        //竞拍通知付款倒计时
        LogUtil.i("onAuctionPayRemaining:" + min + ":" + sec);
    }

    @Override
    public void onAuctionNeedShowPay(boolean show)
    {
        //否显示竞拍支付入口
        LogUtil.i("onAuctionNeedShowPay:" + show);
    }

    @Override
    public void onAuctionPayClick(View v)
    {
        // 竞拍支付入口被点击
        LogUtil.i("onAuctionPayClick");
    }

    @Override
    public void onAuctioningChange(boolean isAuctioning)
    {
        //竞拍状态发生变化
        LogUtil.i("onAuctioningChange:" + isAuctioning);
    }

    @Override
    public void onAuctionRequestPaiInfoSuccess(App_pai_user_get_videoActModel actModel)
    {
        //竞拍信息请求成功
        LogUtil.i("onAuctionRequestPaiInfoSuccess");

        addLiveAuctionInfo(actModel);
        addLiveAuctionRankList(actModel.getData().getBuyer());
    }

    @Override
    public void onAuctionRequestCreateAuthoritySuccess()
    {
        //验证创建竞拍权限成功
        LogUtil.i("onAuctionRequestCreateAuthoritySuccess");
    }

    @Override
    public void onAuctionRequestCreateAuthorityError(String msg)
    {
        //验证创建竞拍权限失败
        LogUtil.i("onAuctionRequestCreateAuthorityError:" + msg);
    }

    //------------购物--------------


    @Override
    public void onMsgShop(MsgModel msg)
    {
        super.onMsgShop(msg);
        liveShopBusiness.onMsgShop(msg);
    }

    @Override
    public void onShopMsgShopPush(CustomMsgShopPush customMsgShopPush)
    {
        addLiveShopGoodsPushView(customMsgShopPush);
    }

    @Override
    public void onShopMsgShopBuySuc(CustomMsgShopBuySuc customMsgShopBuySuc)
    {
        if (shopGoodsPurchaseSucDialog == null)
        {
            shopGoodsPurchaseSucDialog = new ShopGoodsPurchaseSucDialog(this);
        }
        if (shopGoodsPurchaseSucDialog.isShowing())
        {
            shopGoodsPurchaseSucDialog.dismiss();
        }
        shopGoodsPurchaseSucDialog.initData(customMsgShopBuySuc);
        shopGoodsPurchaseSucDialog.showCenter();
    }

    @Override
    public void onShopCountdownEnd()
    {
        if (roomShopGoodsPushView != null)
        {
            removeView(roomShopGoodsPushView);
        }
        if (shopGoodsPurchaseSucDialog != null && shopGoodsPurchaseSucDialog.isShowing())
        {
            shopGoodsPurchaseSucDialog.dismiss();
        }
    }

    /**
     * 观众添加购物商品推送
     *
     * @param customMsgShopPush
     */
    private void addLiveShopGoodsPushView(CustomMsgShopPush customMsgShopPush)
    {
        roomShopGoodsPushView = new RoomShopGoodsPushView(this, getCreaterId(), customMsgShopPush.getGoods().getGoods_id());
        replaceView(fl_live_goods_push, roomShopGoodsPushView);
        roomShopGoodsPushView.bindData(customMsgShopPush);
    }
    //-----------游戏-------------

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        auctionBusiness.onDestroy();
    }

    protected void changeAvVideoGlviewLayout()
    {
        SDViewUtil.setGone(mRoomInfoView);
        SDViewUtil.setHeight(find(R.id.view_video), SDViewUtil.getScreenHeight() / 3);
        SDViewUtil.setWidth(find(R.id.view_video), SDViewUtil.getScreenWidth() / 3);
        SDViewUtil.setGone(fl_live_goods_push);
    }

    protected void revertAvVideoGlviewLayout()
    {
        SDViewUtil.setVisible(mRoomInfoView);
        SDViewUtil.setHeight(find(R.id.view_video), SDViewUtil.getScreenHeight());
        SDViewUtil.setWidth(find(R.id.view_video), SDViewUtil.getScreenWidth());
        SDViewUtil.setVisible(fl_live_goods_push);
    }

    //实体产品列表弹出事件
    public void onEventMainThread(O2OEShoppingCartDialogShowing event)
    {
        if (event.isShowing)
        {
            changeAvVideoGlviewLayout();
        } else
        {
            revertAvVideoGlviewLayout();
        }
    }
}
