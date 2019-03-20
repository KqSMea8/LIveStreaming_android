package com.fanwe.live.activity.room;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.fanwe.auction.common.AuctionCommonInterface;
import com.fanwe.auction.event.EBigToSmallScreen;
import com.fanwe.auction.model.App_pai_user_open_goodsActModel;
import com.fanwe.baimei.appview.BMDailyTasksEntranceView;
import com.fanwe.baimei.appview.BMRoomSendGiftView;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.umeng.UmengSocialManager;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.common.SDHandlerManager;
import com.fanwe.library.dialog.SDDialogConfirm;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.dialog.SDDialogMenu;
import com.fanwe.library.dialog.SDDialogProgress;
import com.fanwe.library.listener.SDViewVisibilityCallback;
import com.fanwe.library.utils.SDJsonUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveFloatViewWebViewActivity;
import com.fanwe.live.appview.room.RoomLargeGiftInfoView;
import com.fanwe.live.appview.room.RoomViewerBottomView;
import com.fanwe.live.appview.room.RoomViewerFinishView;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.dialog.LiveApplyLinkMicDialog;
import com.fanwe.live.dialog.LiveViewerPluginDialog;
import com.fanwe.live.model.App_get_videoActModel;
import com.fanwe.live.model.JoinLiveData;
import com.fanwe.live.model.UpdataPackgeGift;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.model.Video_check_statusActModel;
import com.fanwe.live.model.custommsg.CustomMsgEndVideo;
import com.fanwe.live.model.custommsg.CustomMsgRejectLinkMic;
import com.fanwe.live.model.custommsg.CustomMsgViewerJoin;
import com.fanwe.live.runnable.JoinLiveRunnable;
import com.fanwe.live.utils.GlideUtil;
import com.fanwe.live.view.RoomPluginToolView;
import com.fanwe.live.view.SDVerticalScollView;
import com.fanwe.o2o.dialog.O2OShoppingPodCastDialog;
import com.fanwe.shop.dialog.ShopPodcastGoodsDialog;

import java.io.File;
import java.io.FileInputStream;

/**
 * 观众界面
 */
public class LiveLayoutViewerActivity extends LiveLayoutExtendActivity {
    public static final String EXTRA_LOADING_VIDEO_IMAGE_URL = "extra_loading_video_image_url";//加载中的图片链接
    public static final String EXTRA_PRIVATE_KEY = "extra_private_key";//私密直播的key(String)
    protected View view_loading_video;
    protected ImageView iv_loading_video;
    protected BMRoomSendGiftView mRoomSendGiftView;
    protected RoomViewerBottomView mRoomViewerBottomView;
    protected String mStrPrivateKey;//私密直播的key
    protected boolean mIsNeedShowFinish = false;//是否显示直播结束界面
    protected LiveApplyLinkMicDialog mDialogApplyLinkMic;//申请连麦中窗口
    private SDVerticalScollView verticalScollView;
    private int mViewerNumber;
    private RoomViewerFinishView mRoomViewerFinishView;
    private RoomLargeGiftInfoView mRoomLargeGiftInfoView;
    private BMDailyTasksEntranceView mBMDailyTasksEntranceView;//百媚
    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        String loadingVideoImageUrl = getIntent().getStringExtra(EXTRA_LOADING_VIDEO_IMAGE_URL);
        mStrPrivateKey = getIntent().getStringExtra(EXTRA_PRIVATE_KEY);
        view_loading_video = findViewById(R.id.view_loading_video);
        iv_loading_video = (ImageView) findViewById(R.id.iv_loading_video);
        setLoadingVideoImageUrl(loadingVideoImageUrl);
        initLayout(getWindow().getDecorView());
    }

    @Override
    protected void initLayout(View view) {
        super.initLayout(view);
        verticalScollView = (SDVerticalScollView) view.findViewById(R.id.view_vertical_scroll);
        initSDVerticalScollView(verticalScollView);
//        addRoomBMTaskView();
//        addRoomLargeGiftInfoView();
    }

    /**
     * 添加直播间大型礼物动画通知view
     */
//    private void addRoomLargeGiftInfoView() {
//        if (mRoomLargeGiftInfoView == null) {
//            mRoomLargeGiftInfoView = new RoomLargeGiftInfoView(this);
//            mRoomLargeGiftInfoView.setCallback(new RoomLargeGiftInfoView.LargeGiftInfoViewCallback() {
//                @Override
//                public void onClickInfoView(final CustomMsgLargeGift msg) {
//                    if (msg == null) {
//                        return;
//                    }
//                    if (msg.getRoom_id() == getRoomId()) {
//                        return;
//                    }
//                    SDDialogConfirm dialog = new SDDialogConfirm(LiveLayoutViewerActivity.this);
//                    dialog.setTextContent("您确定需要前往该直播间吗？").setTextCancel("取消").setTextConfirm("确定")
//                            .setCallback(new SDDialogCustom.SDDialogCustomCallback() {
//                                @Override
//                                public void onClickCancel(View v, SDDialogCustom dialog) {
//                                }
//
//                                @Override
//                                public void onClickConfirm(View v, SDDialogCustom dialog) {
//                                    switchRoom(msg.getRoom_id());
//                                }
//                            });
//                    dialog.show();
//                }
//            });
//            replaceView(R.id.fl_live_large_gift_info, mRoomLargeGiftInfoView);
//        }
//    }

    /**
     * 切换房间
     *
     * @param roomId
     */
    private void switchRoom(int roomId) {
        getViewerBusiness().requestCheckVideoStatus(roomId, new AppRequestCallback<Video_check_statusActModel>() {
            @Override
            protected void onStart() {
                super.onStart();
                showProgressDialog("");
            }

            @Override
            protected void onSuccess(SDResponse sdResponse) {
                dismissProgressDialog();
                if (actModel.isOk()) {
                    if (actModel.getLive_in() == 1) {
                        getViewerBusiness().exitRoom(true);

                        JoinLiveData data = new JoinLiveData();
                        data.setRoomId(actModel.getRoom_id());
                        data.setGroupId(actModel.getGroup_id());
                        data.setCreaterId(actModel.getUser_id());
                        data.setLoadingVideoImageUrl(actModel.getLive_image());

                        SDHandlerManager.post(new JoinLiveRunnable(data));
                    } else {
                        SDToast.showToast("该直播已结束");
                    }
                }
            }

            @Override
            protected void onFinish(SDResponse resp) {
                super.onFinish(resp);
                dismissProgressDialog();
            }
        });
    }

    /**
     * 重写此方法设置监听
     *
     * @param scollView
     */
    protected void initSDVerticalScollView(SDVerticalScollView scollView) {
        if (scollView == null) {
            return;
        }

        scollView.setLeftView(findViewById(R.id.view_left));
        scollView.setHorizontalView(findViewById(R.id.rl_root_layout));

        scollView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addHeart();
            }
        });
        scollView.setListenerScroll(defaultScrollListener);
    }

    /**
     * 默认滚动监听
     */
    protected SDVerticalScollView.ScrollListener defaultScrollListener = new SDVerticalScollView.ScrollListener() {
        @Override
        public void onFinishTop() {
            verticalScollView.resetVerticalViews();
        }

        @Override
        public void onFinishCenter() {
            verticalScollView.resetVerticalViews();
        }

        @Override
        public void onFinishBottom() {
            verticalScollView.resetVerticalViews();
        }

        @Override
        public void onVerticalScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        }

        @Override
        public void onHorizontalScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        }
    };

    public void addHeart() {
        if (mRoomHeartView != null) {
            mRoomHeartView.addHeart();
        }
    }

    @Override
    public void onMsgEndVideo(CustomMsgEndVideo msg) {
        super.onMsgEndVideo(msg);
        showSendMsgView(false);
        mIsNeedShowFinish = true;
        mViewerNumber = msg.getShow_num();
    }

    @Override
    protected void addLiveFinish() {
        if (getRoomInfo() != null) {
            removeView(mRoomViewerFinishView);
            mRoomViewerFinishView = new RoomViewerFinishView(this);

            int status = getRoomInfo().getStatus();
            if (status == 1) {
                mRoomViewerFinishView.setHasFollow(mRoomInfoView.getHasFollow());
            } else if (status == 2) {
                mViewerNumber = getRoomInfo().getShow_num();
                mRoomViewerFinishView.setHasFollow(getRoomInfo().getHas_focus());
            }
            mRoomViewerFinishView.setViewerNumber(mViewerNumber);
            addView(mRoomViewerFinishView);
        }
    }

    /**
     * 设置直播间的加载背景图片链接
     *
     * @param loadingVideoImageUrl
     */
    public void setLoadingVideoImageUrl(final String loadingVideoImageUrl) {
        if (iv_loading_video != null && !TextUtils.isEmpty(loadingVideoImageUrl)) {
            GlideUtil.load(loadingVideoImageUrl).into(iv_loading_video);
        }
    }

    /**
     * 显示加载背景
     */
    protected void showLoadingVideo() {
        if (view_loading_video != null) {
            SDViewUtil.setVisible(view_loading_video);
        }
    }

    /**
     * 隐藏加载背景
     */
    protected void hideLoadingVideo() {
        if (view_loading_video != null) {
            SDViewUtil.setGone(view_loading_video);
        }
    }

    @Override
    protected void requestRoomInfo() {
        getLiveBusiness().requestRoomInfo(mStrPrivateKey);
    }

    @Override
    public void onBsRequestRoomInfoSuccess(App_get_videoActModel actModel) {
        super.onBsRequestRoomInfoSuccess(actModel);
        updateHeatRank(actModel.getHeat_rank());
        bindShowApplyLinkMic();
        sendViewerJoinMsg();
    }

    /**
     * 发送观众加入消息
     */
    public void sendViewerJoinMsg() {
        if (!getViewerIM().isCanSendViewerJoinMsg()) {
            return;
        }
        App_get_videoActModel actModel = getRoomInfo();
        if (actModel == null) {
            return;
        }
        UserModel user = UserModelDao.query();
        if (user == null) {
            return;
        }

        boolean sendViewerJoinMsg = true;
        if (!user.isProUser() && actModel.getJoin_room_prompt() == 0) {
            sendViewerJoinMsg = false;
        }

        if (sendViewerJoinMsg) {
            CustomMsgViewerJoin joinMsg = new CustomMsgViewerJoin();
            joinMsg.setSortNumber(actModel.getSort_num());
            if(null!=actModel.getGuard()){
                continueShouhu();
                joinMsg.setGuard(actModel.getGuard());
            }else if(null!=actModel.getMount()){
                joinMsg.setMount(actModel.getMount());
            }
            getViewerIM().sendViewerJoinMsg(joinMsg, null);
        }
    }

    /**
     * 是否显示连麦按钮
     */
    protected void bindShowApplyLinkMic() {
        if (mRoomViewerBottomView == null) {
            return;
        }
        if (getRoomInfo() != null) {
            if (getRoomInfo().getHas_lianmai() == 1) {
                mRoomViewerBottomView.showMenuApplyLinkMic(true);
            } else {
                mRoomViewerBottomView.showMenuApplyLinkMic(false);
            }
        } else {
            mRoomViewerBottomView.showMenuApplyLinkMic(false);
        }
    }

    @Override
    protected void bindShowShareView() {
        if (mRoomViewerBottomView == null) {
            return;
        }
        if (getRoomInfo() != null) {
            if (isPrivate() || UmengSocialManager.isAllSocialDisable()) {
                mRoomViewerBottomView.showMenuShare(false);
            } else {
                mRoomViewerBottomView.showMenuShare(true);
            }
        } else {
            mRoomViewerBottomView.showMenuShare(false);
        }
    }

    /**
     * 送礼物
     */
    protected void addRoomSendGiftView() {
        if (mRoomSendGiftView == null) {
            mRoomSendGiftView = new BMRoomSendGiftView(this);
            SDViewUtil.setInvisible(mRoomSendGiftView);
            mRoomSendGiftView.getVisibilityHandler().addVisibilityCallback(new SDViewVisibilityCallback() {
                @Override
                public void onViewVisibilityChanged(View view, int visibility) {
                    if (View.VISIBLE == visibility) {
                        onShowSendGiftView(view);
                    } else {
                        removeView(mRoomSendGiftView);
                        onHideSendGiftView(view);
                    }
                }
            });
        }
        mRoomSendGiftView.bindData();
        replaceView(R.id.fl_live_send_gift, mRoomSendGiftView);
    }

    @Override
    protected void addRoomBottomView() {
        if (mRoomViewerBottomView == null) {
            mRoomViewerBottomView = new RoomViewerBottomView(this);
            mRoomViewerBottomView.setClickListener(mBottomClickListener);
            replaceView(R.id.fl_live_bottom_menu, mRoomViewerBottomView);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1001) {
            //方式二校验
            int result = 0;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    result++;
                }
            }
            if (result != permissions.length) {
                //没有权限
                return;
            }else{
                //有权限
            }
        }
    }
    /**
     * 添加每日任务View
     */
    private void addRoomBMTaskView() {
        if (mBMDailyTasksEntranceView == null) {
            mBMDailyTasksEntranceView = new BMDailyTasksEntranceView(getActivity());
        }
        replaceView(R.id.fl_live_led_task, mBMDailyTasksEntranceView);
    }

    /**
     * 底部菜单点击监听
     */
    protected RoomViewerBottomView.ClickListener mBottomClickListener = new RoomViewerBottomView.ClickListener() {
        @Override
        public void onClickMenuSendMsg(View v) {
            LiveLayoutViewerActivity.this.onClickMenuSendMsg(v);
        }

        @Override
        public void onClickMenuViewerPlugin(View v) {
            LiveLayoutViewerActivity.this.onClickMenuViewerPlugin(v);
        }

        @Override
        public void onClickMenuBottomExtendSwitch(View v) {
            toggleBottomExtend();
        }

        @Override
        public void onClickMenuPrivateMsg(View v) {
            LiveLayoutViewerActivity.this.onClickMenuPrivateMsg(v);
        }

        @Override
        public void onClickMenuAuctionPay(View v) {
            LiveLayoutViewerActivity.this.onClickMenuAuctionPay(v);
        }

        @Override
        public void onClickMenuApplyLinkMic(View v) {
            LiveLayoutViewerActivity.this.onClickMenuApplyLinkMic(v);
        }

        @Override
        public void onClickMenuSendGift(View v) {
            LiveLayoutViewerActivity.this.onClickMenuSendGift(v);
        }

        @Override
        public void onClickMenuShare(View v) {
            LiveLayoutViewerActivity.this.onClickMenuShare(v);
        }

        @Override
        public void onClickMenuApplyBanker(View v) {
            onClickBankerCtrlViewerApplyBanker();
        }

        @Override
        public void onClickSpeech(String filepath) {
            getViewerBusiness().speechToText(filepath,getActivity());
        }
    };

    public void onClickMenuViewerPlugin(View v) {
        final LiveViewerPluginDialog dialog = new LiveViewerPluginDialog(getActivity());
        dialog.setClickListener(new LiveViewerPluginDialog.ClickListener() {
            @Override
            public void onClickStarStore(RoomPluginToolView view) {
                dialog.dismiss();
                onClickMenuPodcastOrder();
            }

            @Override
            public void onClickShopStore(RoomPluginToolView view) {
                dialog.dismiss();
                onClickMenuMyStore();
            }
        });
        dialog.showBottom();
    }

    //星店订单
    protected void onClickMenuPodcastOrder() {
        if (AppRuntimeWorker.getIsOpenWebviewMain()) {
            O2OShoppingPodCastDialog dialog = new O2OShoppingPodCastDialog(this, getCreaterId());
            dialog.showBottom();
            return;
        }

        AuctionCommonInterface.requestPaiUserOpenGoods(getCreaterId(), new AppRequestCallback<App_pai_user_open_goodsActModel>() {
            private SDDialogProgress dialog = new SDDialogProgress(SDActivityManager.getInstance().getLastActivity());

            @Override
            protected void onStart() {
                super.onStart();
                dialog.setTextMsg("");
                dialog.show();
            }

            @Override
            protected void onFinish(SDResponse resp) {
                super.onFinish(resp);
                dialog.dismiss();
            }

            @Override
            protected void onSuccess(SDResponse sdResponse) {
                if (actModel.getStatus() == 1) {
                    clickToWebView(actModel.getUrl());
                }
            }
        });
    }

    private void onClickMenuMyStore() {
        ShopPodcastGoodsDialog dialog = new ShopPodcastGoodsDialog(getActivity(), isCreater(), getCreaterId());
        dialog.showBottom();
    }

    private void clickToWebView(String url) {
        Intent intent = new Intent(this, LiveFloatViewWebViewActivity.class);
        intent.putExtra(LiveFloatViewWebViewActivity.EXTRA_URL, url);
        startActivity(intent);
    }

    /**
     * 观众竞拍付款
     */
    protected void onClickMenuAuctionPay(View v) {

    }

    /**
     * 点击申请连麦
     */
    protected void onClickMenuApplyLinkMic(View v) {
        if (getViewerBusiness().isInLinkMic()) {
            SDDialogMenu dialog = new SDDialogMenu(this);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setItems(new String[]{"关闭连麦"}).setCallback(new SDDialogMenu.SDDialogMenuCallback() {
                @Override
                public void onItemClick(View v, int index, SDDialogMenu dialog) {
                    switch (index) {
                        case 0:
                            onClickStopLinkMic();
                            break;

                        default:
                            break;
                    }
                }

                @Override
                public void onCancelClick(View v, SDDialogMenu dialog) {
                }
            });
            dialog.showBottom();
        } else {
            SDDialogConfirm dialogConfirm = new SDDialogConfirm(this);
            dialogConfirm.setTextContent("是否请求与主播连麦？").setCallback(new SDDialogCustom.SDDialogCustomCallback() {
                @Override
                public void onClickCancel(View v, SDDialogCustom dialog) {
                }

                @Override
                public void onClickConfirm(View v, SDDialogCustom dialog) {
                    getViewerBusiness().applyLinkMic();
                }
            }).show();
        }
    }

    /**
     * 关闭连麦
     */
    protected void onClickStopLinkMic() {
        //子类实现
    }

    @Override
    public void onBsViewerShowApplyLinkMic(boolean show) {
        super.onBsViewerShowApplyLinkMic(show);
        if (show) {
            showApplyLinkMicDialog();
        } else {
            dismissApplyLinkMicDialog();
        }
    }

    @Override
    public void onBsViewerApplyLinkMicError(String msg) {
        super.onBsViewerApplyLinkMicError(msg);
        SDToast.showToast(msg);
    }

    @Override
    public void onBsViewerApplyLinkMicRejected(CustomMsgRejectLinkMic msg) {
        super.onBsViewerApplyLinkMicRejected(msg);
        if (mDialogApplyLinkMic != null && mDialogApplyLinkMic.isShowing()) {
            mDialogApplyLinkMic.setTextContent(msg.getMsg());
            mDialogApplyLinkMic.startDismissRunnable(1000);
        }
    }

    /**
     * 显示申请连麦中窗口
     */
    protected void showApplyLinkMicDialog() {
        dismissApplyLinkMicDialog();
        mDialogApplyLinkMic = new LiveApplyLinkMicDialog(getActivity());
        mDialogApplyLinkMic.setCallback(new SDDialogCustom.SDDialogCustomCallback() {
            @Override
            public void onClickCancel(View v, SDDialogCustom dialog) {
                //取消申请连麦
                getViewerBusiness().cancelApplyLinkMic();
            }

            @Override
            public void onClickConfirm(View v, SDDialogCustom dialog) {
            }
        });
        mDialogApplyLinkMic.show();
    }

    /**
     * 隐藏申请连麦中窗口
     */
    public void dismissApplyLinkMicDialog() {
        if (mDialogApplyLinkMic != null) {
            mDialogApplyLinkMic.dismiss();
        }
    }

    /**
     * 点击送礼物菜单
     *
     * @param v
     */
    protected void onClickMenuSendGift(View v) {
        addRoomSendGiftView();
        mRoomSendGiftView.getVisibilityHandler().setVisible(true);
    }

    @Override
    protected void hideSendGiftView() {
        super.hideSendGiftView();
        SDViewUtil.setInvisible(mRoomSendGiftView);
    }

    @Override
    protected boolean isSendGiftViewVisible() {
        if (mRoomSendGiftView == null) {
            return false;
        }
        return mRoomSendGiftView.isVisible();
    }

    //----------Banker start----------
    @Override
    public void onBankerCtrlViewerShowApplyBanker(boolean show) {
        super.onBankerCtrlViewerShowApplyBanker(show);
        mRoomViewerBottomView.onBankerCtrlViewerShowApplyBanker(show);
    }
    //----------Banker end----------

    @Override
    protected void showBottomExtendSwitch(boolean show) {
        super.showBottomExtendSwitch(show);
        mRoomViewerBottomView.showMenuBottomExtendSwitch(show);
    }

    @Override
    protected void showBottomView(boolean show) {
        super.showBottomView(show);
        if (show) {
            SDViewUtil.setVisible(mRoomViewerBottomView);
        } else {
            SDViewUtil.setInvisible(mRoomViewerBottomView);
        }
    }

    @Override
    protected void onHideBottomExtend() {
        super.onHideBottomExtend();
        mRoomViewerBottomView.setMenuBottomExtendSwitchStateOpen();
    }

    @Override
    protected void onShowBottomExtend() {
        super.onShowBottomExtend();
        mRoomViewerBottomView.setMenuBottomExtendSwitchStateClose();
    }

    //百媚=================================start

    @Override
    public void onBsViewerShowLianMai(boolean show) {
        super.onBsViewerShowLianMai(show);
        if (mRoomViewerBottomView != null) {
            mRoomViewerBottomView.showMenuApplyLinkMic(show);
        }
    }
    //百媚=================================end
}
