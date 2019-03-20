package com.fanwe.live.activity.room;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.auction.appview.room.RoomAuctionBtnView;
import com.fanwe.auction.dialog.AuctionSucPayDialog;
import com.fanwe.auction.model.App_pai_user_get_videoActModel;
import com.fanwe.auction.model.PaiBuyerModel;
import com.fanwe.auction.model.custommsg.CustomMsgAuctionFail;
import com.fanwe.auction.model.custommsg.CustomMsgAuctionSuccess;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.dialog.LiveRechargeDialog;
import com.fanwe.live.model.App_get_videoActModel;
import com.fanwe.live.model.custommsg.CustomMsgRedEnvelope;
import com.fanwe.live.model.custommsg.MsgModel;
import com.fanwe.pay.LiveScenePayViewerBusiness;
import com.fanwe.pay.LiveTimePayViewerBusiness;
import com.fanwe.pay.appview.PayLiveBlackBgView;
import com.fanwe.pay.dialog.LiveJoinPayDialog;
import com.fanwe.pay.dialog.PayUserBalanceDialog;
import com.fanwe.pay.model.App_live_live_pay_deductActModel;
import com.fanwe.pay.room.RoomLivePayInfoViewerView;
import com.fanwe.pay.room.RoomLiveScenePayInfoView;

/**
 * 观众界面扩展
 */
public class LiveLayoutViewerExtendActivity extends LiveLayoutViewerActivity implements LiveTimePayViewerBusiness.LiveTimePayViewerBusinessListener, LiveScenePayViewerBusiness.LiveScenePayViewerBusinessListener
{
    //---------竞拍----------
    private RoomAuctionBtnView roomAuctionBtnView;
    private ViewGroup fl_auction_btn;

    //---------付费模式----------
    private boolean isShowBgBlack;//是否黑屏遮盖
    //---------按时直播-----------
    private ViewGroup ll_pay_bg_black;
    protected RoomLivePayInfoViewerView roomLivePayInfoViewerView;
    private PayLiveBlackBgView payLiveBlackBgView;
    private LiveJoinPayDialog timePayJoinDialog; // 是否进入付费直播间dialog
    private PayUserBalanceDialog timePayRechargeDialog;//付费弹出Dialog
    protected LiveTimePayViewerBusiness mTimePayViewerBusiness;

    //---------按场直播-----------
    private RoomLiveScenePayInfoView roomLiveScenePayInfoView;
    private LiveScenePayViewerBusiness mScenePayViewerBusiness;
    private LiveJoinPayDialog scenePayJoinDialog;
    private PayUserBalanceDialog scenePayRechargeDialog;

    @Override
    protected void init(Bundle savedInstanceState)
    {
        super.init(savedInstanceState);

        //---------付费模式----------
        ll_pay_bg_black = (ViewGroup) findViewById(R.id.ll_pay_bg_black);
    }

    /**
     * 返回按时付费业务类
     *
     * @return
     */
    public LiveTimePayViewerBusiness getTimePayViewerBusiness()
    {
        if (mTimePayViewerBusiness == null)
        {
            mTimePayViewerBusiness = new LiveTimePayViewerBusiness(this);
            mTimePayViewerBusiness.setBusinessListener(this);
        }
        return mTimePayViewerBusiness;
    }

    /**
     * 返回按场付费业务类
     *
     * @return
     */
    public LiveScenePayViewerBusiness getScenePayViewerBusiness()
    {
        if (mScenePayViewerBusiness == null)
        {
            mScenePayViewerBusiness = new LiveScenePayViewerBusiness(this);
            mScenePayViewerBusiness.setBusinessListener(this);
        }
        return mScenePayViewerBusiness;
    }

    @Override
    protected void initLayout(View view)
    {
        super.initLayout(view);

        //---------竞拍----------
        fl_auction_btn = (ViewGroup) view.findViewById(R.id.fl_auction_btn);
    }

    @Override
    public void onBsRequestRoomInfoSuccess(App_get_videoActModel actModel)
    {
        super.onBsRequestRoomInfoSuccess(actModel);

        //---------付费模式----------
        getTimePayViewerBusiness().dealPayModelRoomInfoSuccess(actModel);
        getScenePayViewerBusiness().dealPayModelRoomInfoSuccess(actModel);
    }

    @Override
    public void onBsRequestRoomInfoError(App_get_videoActModel actModel)
    {
        super.onBsRequestRoomInfoError(actModel);

        //---------付费模式----------
        getTimePayViewerBusiness().dealPayModelRoomInfoSuccess(actModel);
        getScenePayViewerBusiness().dealPayModelRoomInfoSuccess(actModel);
    }

    //---------竞拍----------

    @Override
    protected void onClickMenuAuctionPay(View v)
    {
        super.onClickMenuAuctionPay(v);

        auctionBusiness.clickAuctionPay(v);
    }

    @Override
    public void onAuctionRequestPaiInfoSuccess(App_pai_user_get_videoActModel actModel)
    {
        super.onAuctionRequestPaiInfoSuccess(actModel);

        // 添加竞拍锤子按钮
        addLiveAuctionBtnView(actModel);
    }

    @Override
    public void onAuctionNeedShowPay(boolean show)
    {
        super.onAuctionNeedShowPay(show);
        if (mRoomViewerBottomView != null)
        {
            mRoomViewerBottomView.showMenuAuctionPay(show);
        }
    }

    private AuctionSucPayDialog auctionSucPayDialog;

    @Override
    public void onAuctionPayClick(View v)
    {
        super.onAuctionPayClick(v);
        if (auctionSucPayDialog == null)
        {
            auctionSucPayDialog = new AuctionSucPayDialog(this, auctionBusiness);
        }
        auctionSucPayDialog.showBottom();
    }

    @Override
    public void onAuctionPayRemaining(PaiBuyerModel buyer, long day, long hour, long min, long sec)
    {
        super.onAuctionPayRemaining(buyer, day, hour, min, sec);
        if (auctionSucPayDialog != null)
        {
            auctionSucPayDialog.onAuctionPayRemaining(buyer, day, hour, min, sec);
        }
    }

    @Override
    public void onAuctionMsgSuccess(CustomMsgAuctionSuccess customMsg)
    {
        super.onAuctionMsgSuccess(customMsg);
        //竞拍成功移除锤子
        removeView(roomAuctionBtnView);
    }

    @Override
    public void onAuctionMsgFail(CustomMsgAuctionFail customMsg)
    {
        super.onAuctionMsgFail(customMsg);
        //流拍移除锤子
        removeView(roomAuctionBtnView);
    }

    /**
     * 添加竞拍锤子按钮
     */
    private void addLiveAuctionBtnView(App_pai_user_get_videoActModel app_pai_user_get_videoActModel)
    {
        roomAuctionBtnView = new RoomAuctionBtnView(this);
        replaceView(fl_auction_btn, roomAuctionBtnView);
        roomAuctionBtnView.bindData(app_pai_user_get_videoActModel);
    }

    //---------付费模式----------


    @Override
    public void onMsgRedEnvelope(CustomMsgRedEnvelope msg)
    {
        if (isShowBgBlack)
        {
            //如果黑屏，红包不弹出
        } else
        {
            super.onMsgRedEnvelope(msg);
        }
    }

    /**
     * 显示付费加载背景
     */
    protected void showPayModelBg()
    {
        if (!isShowBgBlack)
        {
            isShowBgBlack = true;
            sdkStopLinkMic();
            sdkPauseVideo();
            if (payLiveBlackBgView == null)
            {
                payLiveBlackBgView = new PayLiveBlackBgView(this);
                replaceView(ll_pay_bg_black, payLiveBlackBgView);
            }
            SDViewUtil.setVisible(ll_pay_bg_black);
        }
    }

    /**
     * 隐藏付费加载背景
     */
    protected void hidePayModelBg()
    {
        if (isShowBgBlack)
        {
            dismissScenePayRechargeDialog();
            dismissTimePayRechargeDialog();

            isShowBgBlack = false;
            sdkResumeVideo();
            SDViewUtil.setGone(ll_pay_bg_black);
        }
    }

    @Override
    public void onMsgPayMode(MsgModel msg)
    {
        super.onMsgPayMode(msg);
        getTimePayViewerBusiness().onMsgPayMode(msg);
        getScenePayViewerBusiness().onMsgPayMode(msg);
    }

    //按时付费start=====================
    private void addRoomLivePayInfoViewerView()
    {
        if (roomLivePayInfoViewerView == null)
        {
            roomLivePayInfoViewerView = new RoomLivePayInfoViewerView(this);
            replaceView(fl_live_pay_mode, roomLivePayInfoViewerView);
        }
    }

    @Override
    public void onTimePayViewerRequestMonitorSuccess(App_live_live_pay_deductActModel model)
    {
        if (roomLivePayInfoViewerView != null)
        {
            roomLivePayInfoViewerView.bindData(model);
        }
        getLiveBusiness().setTicket(model.getTicket());
    }

    @Override
    public void onTimePayViewerShowCovering(boolean show)
    {
        if (show)
        {
            showPayModelBg();
        } else
        {
            hidePayModelBg();
        }
    }

    @Override
    public void onTimePayViewerLowDiamonds(App_live_live_pay_deductActModel model)
    {
        showIsDiamondsLowDialog(model);
    }


    @Override
    public void onTimePayViewerShowRecharge(App_live_live_pay_deductActModel model)
    {
        showIsDiamondsLowDialog(model);
    }

    //观众余额不足时或者为0时弹出提示
    private void showIsDiamondsLowDialog(final App_live_live_pay_deductActModel data)
    {
        if (timePayRechargeDialog == null)
        {
            timePayRechargeDialog = new PayUserBalanceDialog(this);
            timePayRechargeDialog.setCallback(new SDDialogCustom.SDDialogCustomCallback()
            {
                @Override
                public void onClickConfirm(View v, SDDialogCustom dialog)
                {
                    getTimePayViewerBusiness().setRecharging(true);
                    LiveRechargeDialog liveRechargeDialog = new LiveRechargeDialog(LiveLayoutViewerExtendActivity.this);
                    liveRechargeDialog.setOnDismissListener(new DialogInterface.OnDismissListener()
                    {
                        @Override
                        public void onDismiss(DialogInterface dialog)
                        {
                            getTimePayViewerBusiness().setRecharging(false);
                            getViewerBusiness().exitRoom(true);
                        }
                    });

                    liveRechargeDialog.show();
                }

                @Override
                public void onClickCancel(View v, SDDialogCustom dialog)
                {
                    if (timePayRechargeDialog.getIs_recharge() == 1)
                    {
                        //如果处于强制充值，取消就出去房间
                        getViewerBusiness().exitRoom(true);
                    }
                }
            });
        }

        if (timePayRechargeDialog.isShowing())
        {
            timePayRechargeDialog.dismiss();
        }
        timePayRechargeDialog.bindData(data);
        timePayRechargeDialog.show();
    }

    @Override
    public void onTimePayViewerShowWhetherJoin(int live_fee)
    {
        showTimePayJoinDialog(live_fee);
    }

    @Override
    public void onTimePayViewerShowWhetherJoinFuture(App_live_live_pay_deductActModel model)
    {
        showTimePayJoinDialog(model.getLive_fee());
    }

    private void showTimePayJoinDialog(int live_fee)
    {
        if (timePayJoinDialog == null)
        {
            timePayJoinDialog = new LiveJoinPayDialog(this);
            timePayJoinDialog.setmListener(new SDDialogCustom.SDDialogCustomListener()
            {
                @Override
                public void onDismiss(SDDialogCustom dialog)
                {
                }

                @Override
                public void onClickConfirm(View v, SDDialogCustom dialog)
                {
                    if (payLiveBlackBgView != null)
                    {
                        payLiveBlackBgView.destroyVideo();
                    }
                    getTimePayViewerBusiness().agreePay();
                }

                @Override
                public void onClickCancel(View v, SDDialogCustom dialog)
                {
                    getTimePayViewerBusiness().rejectPay();
                    getViewerBusiness().exitRoom(true);
                }
            });
            timePayJoinDialog.joinPaysetTextContent(live_fee, 0);
            timePayJoinDialog.show();
        }
    }

    @Override
    public void onTimePayShowPayInfoView(App_live_live_pay_deductActModel model)
    {
        addRoomLivePayInfoViewerView();
    }

    @Override
    public void onTimePayViewerCountDown(long leftTime)
    {
        if (roomLivePayInfoViewerView != null)
        {
            roomLivePayInfoViewerView.onPayModeCountDown(leftTime);
        }
    }

    @Override
    public void onTimePayViewerCanJoinRoom(boolean canJoinRoom)
    {
        getViewerBusiness().setCanJoinRoom(canJoinRoom);
        if (canJoinRoom)
        {
            if (payLiveBlackBgView != null)
            {
                payLiveBlackBgView.destroyVideo();
            }
            getViewerBusiness().startJoinRoom();
        }
    }

    @Override
    public void onTimePayViewerShowCoveringPlayeVideo(String preview_play_url, int countdown, int is_only_play_voice)
    {
        showPayModelBg();
        payLiveBlackBgView.setIs_only_play_voice(is_only_play_voice);
        payLiveBlackBgView.setProview_play_time(countdown * 1000);
        payLiveBlackBgView.startPlayer(preview_play_url);
    }

    /**
     * 销毁按时付费相关View，定时器
     */
    private void onDestoryTimePayView()
    {
        destroyTimePayJoinDialog();
        dismissTimePayRechargeDialog();
        removeView(roomLivePayInfoViewerView);
        roomLivePayInfoViewerView = null;

        if (mTimePayViewerBusiness != null)
        {
            mTimePayViewerBusiness.onDestroy();
            mTimePayViewerBusiness = null;
        }
    }

    /**
     * 关闭按时直播加入框
     */
    private void destroyTimePayJoinDialog()
    {
        if (timePayJoinDialog != null && timePayJoinDialog.isShowing())
        {
            timePayJoinDialog.dismiss();
        }
        timePayJoinDialog = null;
    }

    /**
     * 关闭按时直播弹出充值框
     */
    private void dismissTimePayRechargeDialog()
    {
        if (timePayRechargeDialog != null && timePayRechargeDialog.isShowing())
        {
            timePayRechargeDialog.dismiss();
        }
    }
    //按时直播 end===================================


    //按场直播 start=================================
    @Override
    public void onScenePayViewerShowCovering(boolean show)
    {
        if (show)
        {
            showPayModelBg();
        } else
        {
            hidePayModelBg();
        }
    }

    @Override
    public void onScenePayViewerShowRecharge(App_live_live_pay_deductActModel model)
    {
        showSceneIsRechargingDialog(model);
    }

    @Override
    public void onScenePayViewerShowPayInfoView(App_live_live_pay_deductActModel model)
    {
        addRoomLiveScenePayInfoView(model.getLive_fee());
    }

    @Override
    public void onScenePayViewerShowWhetherJoin(int live_fee)
    {
        showScenePayJoinDialog(live_fee);
    }

    @Override
    public void onScenePayViewerCanJoinRoom(boolean canJoinRoom)
    {
        getViewerBusiness().setCanJoinRoom(canJoinRoom);
        if (canJoinRoom)
        {
            if (payLiveBlackBgView != null)
            {
                payLiveBlackBgView.destroyVideo();
            }
            getViewerBusiness().startJoinRoom();
        }
    }

    @Override
    public void onScenePayViewerShowCoveringPlayeVideo(String preview_play_url, int countdown, int is_only_play_voice)
    {
        showPayModelBg();
        payLiveBlackBgView.setPay_type(1);
        payLiveBlackBgView.setIs_only_play_voice(is_only_play_voice);
        payLiveBlackBgView.setProview_play_time(countdown * 1000);
        payLiveBlackBgView.startPlayer(preview_play_url);
    }

    private void addRoomLiveScenePayInfoView(int live_fee)
    {
        if (roomLiveScenePayInfoView == null)
        {
            roomLiveScenePayInfoView = new RoomLiveScenePayInfoView(this);
            replaceView(fl_live_pay_mode, roomLiveScenePayInfoView);
        }
        roomLiveScenePayInfoView.bindData(live_fee);
    }

    //观众余额不足时按场弹出框
    private void showSceneIsRechargingDialog(final App_live_live_pay_deductActModel data)
    {
        if (scenePayRechargeDialog == null)
        {
            scenePayRechargeDialog = new PayUserBalanceDialog(this);
            scenePayRechargeDialog.setmListener(new SDDialogCustom.SDDialogCustomListener()
            {

                @Override
                public void onDismiss(SDDialogCustom dialog)
                {

                }

                @Override
                public void onClickConfirm(View v, SDDialogCustom dialog)
                {
                    LiveRechargeDialog liveRechargeDialog = new LiveRechargeDialog(LiveLayoutViewerExtendActivity.this);
                    liveRechargeDialog.setOnDismissListener(new DialogInterface.OnDismissListener()
                    {
                        @Override
                        public void onDismiss(DialogInterface dialog)
                        {
                            getViewerBusiness().exitRoom(true);
                        }
                    });
                    liveRechargeDialog.show();
                }

                @Override
                public void onClickCancel(View v, SDDialogCustom dialog)
                {
                    getViewerBusiness().exitRoom(true);
                }
            });
        }

        if (scenePayRechargeDialog.isShowing())
        {
            scenePayRechargeDialog.dismiss();
        }
        scenePayRechargeDialog.bindData(data);
        scenePayRechargeDialog.show();
    }

    private void showScenePayJoinDialog(int live_fee)
    {
        if (scenePayJoinDialog == null)
        {
            scenePayJoinDialog = new LiveJoinPayDialog(this);
            scenePayJoinDialog.setmListener(new SDDialogCustom.SDDialogCustomListener()
            {
                @Override
                public void onDismiss(SDDialogCustom dialog)
                {
                }

                @Override
                public void onClickConfirm(View v, SDDialogCustom dialog)
                {
                    if (payLiveBlackBgView != null)
                    {
                        payLiveBlackBgView.destroyVideo();
                    }
                    getScenePayViewerBusiness().agreeJoinSceneLive();
                }

                @Override
                public void onClickCancel(View v, SDDialogCustom dialog)
                {
                    getScenePayViewerBusiness().rejectJoinSceneLive();
                    getViewerBusiness().exitRoom(true);
                }
            });
            scenePayJoinDialog.joinPaysetTextContent(live_fee, 1);
            scenePayJoinDialog.show();
        }
    }

    /**
     * 销毁按场付费相关View
     */
    private void onDestoryScenePayView()
    {
        destroyScenePayJoinDialog();
        dismissScenePayRechargeDialog();

        removeView(roomLiveScenePayInfoView);
        roomLiveScenePayInfoView = null;

        if (mScenePayViewerBusiness != null)
        {
            mScenePayViewerBusiness.onDestroy();
            mScenePayViewerBusiness = null;
        }
    }

    /**
     * 关闭按场加入弹出框
     */
    private void destroyScenePayJoinDialog()
    {
        if (scenePayJoinDialog != null && scenePayJoinDialog.isShowing())
        {
            scenePayJoinDialog.dismiss();
        }
        scenePayJoinDialog = null;
    }

    /**
     * 关闭按场充值弹出框
     */
    private void dismissScenePayRechargeDialog()
    {
        if (scenePayRechargeDialog != null && scenePayRechargeDialog.isShowing())
        {
            scenePayRechargeDialog.dismiss();
        }
    }

    //按场直播 end=================================

    //付费直播 end=================================

    @Override
    public void onBsViewerExitRoom(boolean needFinishActivity)
    {
        super.onBsViewerExitRoom(needFinishActivity);
        //---------竞拍----------
        removeView(roomAuctionBtnView); //主播强制关闭竞拍，移除竞拍锤子

        isShowBgBlack = false;
        onDestoryTimePayView();
        onDestoryScenePayView();

        if (payLiveBlackBgView != null)
        {
            payLiveBlackBgView.destroyVideo();
            payLiveBlackBgView = null;
        }
    }

}
