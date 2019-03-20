package com.fanwe.auction;

import android.os.CountDownTimer;
import android.view.View;

import com.fanwe.auction.common.AuctionCommonInterface;
import com.fanwe.auction.model.App_pai_user_get_videoActModel;
import com.fanwe.auction.model.PaiBuyerModel;
import com.fanwe.auction.model.PaiUserGetVideoDataModel;
import com.fanwe.auction.model.PaiUserGoodsDetailDataInfoModel;
import com.fanwe.auction.model.custommsg.CustomMsgAuctionCreateSuccess;
import com.fanwe.auction.model.custommsg.CustomMsgAuctionFail;
import com.fanwe.auction.model.custommsg.CustomMsgAuctionNotifyPay;
import com.fanwe.auction.model.custommsg.CustomMsgAuctionOffer;
import com.fanwe.auction.model.custommsg.CustomMsgAuctionPaySuccess;
import com.fanwe.auction.model.custommsg.CustomMsgAuctionSuccess;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.http.AppRequestCallbackWrapper;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDDateUtil;
import com.fanwe.live.LiveConstant;
import com.fanwe.live.LiveInformation;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.model.custommsg.MsgModel;

import java.util.List;

/**
 * Created by Administrator on 2016/11/30.
 */

public class AuctionBusiness
{

    private CountDownTimer timer;
    private AuctionBusinessListener auctionBusinessListener;
    private boolean isAuctioning;
    private PaiBuyerModel currentBuyer;

    public PaiBuyerModel getCurrentBuyer()
    {
        return currentBuyer;
    }

    public void setAuctionBusinessListener(AuctionBusinessListener auctionBusinessListener)
    {
        this.auctionBusinessListener = auctionBusinessListener;
    }

    private void setAuctioning(boolean auctioning)
    {

    }

    public boolean isAuctioning()
    {
        return isAuctioning;
    }

    public void onAuctionMsg(MsgModel msg)
    {

    }

    protected void onAuctionMsgCreateSuccess(CustomMsgAuctionCreateSuccess customMsg)
    {
    }

    protected void onAuctionMsgOffer(CustomMsgAuctionOffer customMsg)
    {
    }

    protected void onAuctionMsgSuccess(CustomMsgAuctionSuccess customMsg)
    {
    }

    protected void onAuctionMsgNotifyPay(CustomMsgAuctionNotifyPay customMsg)
    {
    }

    protected void onAuctionMsgFail(CustomMsgAuctionFail customMsg)
    {
    }

    protected void onAuctionMsgPaySuccess(CustomMsgAuctionPaySuccess customMsg)
    {
    }

    /**
     * 是否显示付款入口
     */
    private void needShowPay(List<PaiBuyerModel> list)
    {

    }

    /**
     * 付款倒计时
     */
    private void startPayRemaining()
    {

    }

    /**
     * 停止付款倒计时
     */
    private void stopPayRemaining()
    {
        if (timer != null)
        {
            timer.cancel();
        }
    }

    // 接口

    /**
     * 验证创建竞拍权限
     */
    public void requestCreateAuctionAuthority(AppRequestCallback<BaseActModel> listener)
    {

    }

    /**
     * 请求竞拍信息
     */
    public void requestPaiInfo(int paiId, AppRequestCallback<App_pai_user_get_videoActModel> listener)
    {

    }

    public void clickAuctionPay(View v)
    {
        auctionBusinessListener.onAuctionPayClick(v);
    }

    public void onDestroy()
    {
        stopPayRemaining();
    }

    public interface AuctionBusinessListener
    {
        /**
         * 创建竞拍成功
         */
        void onAuctionMsgCreateSuccess(CustomMsgAuctionCreateSuccess customMsg);

        /**
         * 出价
         */
        void onAuctionMsgOffer(CustomMsgAuctionOffer customMsg);

        /**
         * 竞拍成功
         */
        void onAuctionMsgSuccess(CustomMsgAuctionSuccess customMsg);

        /**
         * 竞拍通知付款，比如第一名超时未付款，通知下一名付款
         */
        void onAuctionMsgNotifyPay(CustomMsgAuctionNotifyPay customMsg);

        /**
         * 竞拍失败
         */
        void onAuctionMsgFail(CustomMsgAuctionFail customMsg);

        /**
         * 支付成功 竞拍结束
         */
        void onAuctionMsgPaySuccess(CustomMsgAuctionPaySuccess customMsg);

        /**
         * 通知付款倒计时
         *
         * @param buyer
         * @param day
         * @param hour
         * @param min
         * @param sec
         */
        void onAuctionPayRemaining(PaiBuyerModel buyer, long day, long hour, long min, long sec);

        /**
         * 本地用户是否显示竞拍支付点击入口
         */
        void onAuctionNeedShowPay(boolean show);

        /**
         * 竞拍支付入口被点击
         */
        void onAuctionPayClick(View v);

        /**
         * 竞拍状态发生变化
         */
        void onAuctioningChange(boolean isAuctioning);

        /**
         * 加载竞拍信息成功
         */
        void onAuctionRequestPaiInfoSuccess(App_pai_user_get_videoActModel actModel);

        /**
         * 验证创建竞拍权限成功
         */
        void onAuctionRequestCreateAuthoritySuccess();

        /**
         * 验证创建竞拍权限失败
         */
        void onAuctionRequestCreateAuthorityError(String msg);
    }

}
