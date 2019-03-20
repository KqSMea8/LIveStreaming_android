package com.fanwe.live.activity.room;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.fanwe.auction.event.EBigToSmallScreen;
import com.fanwe.baimei.model.custommsg.BMCustomMsgPushStatus;
import com.fanwe.hybrid.event.EUnLogin;
import com.fanwe.library.common.SDWindowManager;
import com.fanwe.library.dialog.SDDialogConfirm;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.model.SDDelayRunnable;
import com.fanwe.library.receiver.SDNetworkReceiver;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.appview.LiveLinkMicGroupView;
import com.fanwe.live.appview.LiveLinkMicView;
import com.fanwe.live.appview.LivePushFloatViewerView;
import com.fanwe.live.appview.LiveVideoView;
import com.fanwe.live.appview.room.RoomCloseView;
import com.fanwe.live.common.AppRuntimeData;
import com.fanwe.live.control.LivePlayerSDK;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.event.EImOnForceOffline;
import com.fanwe.live.event.ELiveFloatViewClose;
import com.fanwe.live.event.EOnCallStateChanged;
import com.fanwe.live.event.EPushViewerOnDestroy;
import com.fanwe.live.model.App_get_videoActModel;
import com.fanwe.live.model.JoinLiveData;
import com.fanwe.live.model.LiveQualityData;
import com.fanwe.live.model.UpdataPackgeGift;
import com.fanwe.live.model.custommsg.CustomMsgEndVideo;
import com.fanwe.live.model.custommsg.CustomMsgHeatRank;
import com.fanwe.live.model.custommsg.CustomMsgStopLinkMic;
import com.fanwe.live.model.custommsg.CustomMsgStopLive;
import com.fanwe.live.model.custommsg.data.DataLinkMicInfoModel;
import com.fanwe.live.utils.NetTimeUtil;
import com.sunday.eventbus.SDEventManager;
import com.tencent.TIMCallBack;
import com.tencent.rtmp.TXLivePlayer;

/**
 * 推流直播间观众界面
 */
public class LivePushViewerActivity extends LiveLayoutViewerExtendActivity implements LivePlayerSDK.PlayerListener, View.OnClickListener {
    private View fl_view_video;
    private LiveVideoView mPlayView;
    private LiveLinkMicGroupView mLinkMicGroupView;
    /**
     * 是否正在播放主播的加速拉流地址
     */
    private boolean mIsPlayACC = false;
    private String mAccUrl;
    private boolean mIsBigToSmallScreen = false;//是否大屏变小屏
    private RoomCloseView closeView;
    private RelativeLayout rl_parent;

    @Override
    protected int onCreateContentView() {
        return R.layout.act_live_push_viewer;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        if (mIsBigToSmallScreen) {
            smallToBigScreen();
            return;
        }
        fl_view_video = find(R.id.fl_view_video);
        rl_parent = find(R.id.rl_small_large);
        NetTimeUtil.getInstance().caulateDiffTime();
        initPlayer();
        initLinkMicGroupView();
        if (validateParams(getRoomId(), getGroupId(), getCreaterId())) {
            requestRoomInfo();
        }
        //获取令牌
        getViewerBusiness().initspeech();
        ApplicationInfo appInfo = null;
        try {
            appInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            getViewerBusiness().requestSpeechToken(appInfo);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化连麦view
     */
    private void initLinkMicGroupView() {
        mLinkMicGroupView = find(R.id.view_link_mic_group);
        mLinkMicGroupView.mCallBack.set(new LiveLinkMicGroupView.LiveLinkMicGroupViewCallback() {
            @Override
            public void onPlayDisconnect(String userId) {
            }

            @Override
            public void onPlayRecvFirstFrame(String userId) {
            }

            @Override
            public void onClickView(LiveLinkMicView view) {
            }

            @Override
            public void onPushStart(LiveLinkMicView view) {
                if (!TextUtils.isEmpty(mAccUrl)) {
                    if (!mIsPlayACC) {
                        getViewerBusiness().requestMixStream(UserModelDao.getUserId());
                        getPlayer().stopPlay();
                        getPlayer().setPlayType(TXLivePlayer.PLAY_TYPE_LIVE_RTMP_ACC);
                        getPlayer().setUrl(mAccUrl);
                        getPlayer().startPlay();
                        if (!mPlayView.getPusher().isBackCamera()) {
                            mPlayView.getPusher().setMirror(true);
                        }
                        mIsPlayACC = true;
                        LogUtil.i("play acc:" + mAccUrl);
                    }
                } else {
                    LogUtil.e("大主播acc流地址为空");
                }
            }

        });
    }

    /**
     * 初始化拉流对象
     */
    private void initPlayer() {
        mPlayView = find(R.id.view_video);
        mPlayView.setPlayerListener(this);
    }

    public LivePlayerSDK getPlayer() {
        return mPlayView.getPlayer();
    }

    @Override
    public LiveQualityData onBsGetLiveQualityData() {
        return getPlayer().getLiveQualityData();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent != null) {
            int oldRoomId = getRoomId();
            int newRoomId = intent.getIntExtra(EXTRA_ROOM_ID, 0);
            if (newRoomId != oldRoomId) {
                setIntent(intent);
                getViewerBusiness().exitRoom(false);
                init(null);
            } else {
                SDToast.showToast("已经在直播间中");
            }
        }
        super.onNewIntent(intent);
    }

    protected boolean validateParams(int roomId, String groupId, String createrId) {
        if (roomId <= 0) {
            SDToast.showToast("房间id为空");
            finish();
            return false;
        }

        if (isEmpty(groupId)) {
            SDToast.showToast("聊天室id为空");
            finish();
            return false;
        }

        if (isEmpty(createrId)) {
            SDToast.showToast("主播id为空");
            finish();
            return false;
        }

        return true;
    }

    @Override
    protected void initIM() {
        super.initIM();

        final String groupId = getGroupId();
        getViewerIM().joinGroup(groupId, new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                onErrorJoinGroup(code, desc);
            }

            @Override
            public void onSuccess() {
                onSuccessJoinGroup(groupId);
            }
        });
    }

    /**
     * 加入聊天组失败回调
     *
     * @param code
     * @param desc
     */
    public void onErrorJoinGroup(int code, String desc) {
        SDDialogConfirm dialog = new SDDialogConfirm(this);
        dialog.setTextContent("加入聊天组失败:" + code + "，是否重试").setTextCancel("退出").setTextConfirm("重试");
        dialog.setCancelable(false);
        dialog.setCallback(new SDDialogCustom.SDDialogCustomCallback() {
            @Override
            public void onClickConfirm(View v, SDDialogCustom dialog) {
                initIM();
            }

            @Override
            public void onClickCancel(View v, SDDialogCustom dialog) {
                getViewerBusiness().exitRoom(true);
            }
        });
        dialog.show();
    }

    @Override
    protected void onSuccessJoinGroup(String groupId) {
        super.onSuccessJoinGroup(groupId);
        sendViewerJoinMsg();
    }

    @Override
    public void onMsgDataLinkMicInfo(DataLinkMicInfoModel model) {
        super.onMsgDataLinkMicInfo(model);

        boolean isLocalUserLinkMic = model.isLocalUserLinkMic();
        if (isLocalUserLinkMic) {
            if (getViewerBusiness().isInLinkMic()) {
                mAccUrl = model.getPlay_rtmp_acc();
                mLinkMicGroupView.setLinkMicInfo(model);
            }
        } else {
            stopLinkMic(true, false);
        }
    }

    @Override
    public void onMsgStopLinkMic(CustomMsgStopLinkMic msg) {
        super.onMsgStopLinkMic(msg);
        stopLinkMic(true, false);
        SDToast.showToast("主播关闭了连麦");
    }

    @Override
    protected void onClickStopLinkMic() {
        super.onClickStopLinkMic();
        stopLinkMic(true, true);
    }

    /**
     * 暂停连麦
     */
    private void pauseLinkMic() {
        if (getViewerBusiness().isInLinkMic()) {
            mLinkMicGroupView.onPause();
        }
    }

    /**
     * 恢复连麦
     */
    private void resumeLinkMic() {
        if (getViewerBusiness().isInLinkMic()) {
            mLinkMicGroupView.onResume();
        }
    }

    /**
     * 停止连麦
     *
     * @param needPlayOriginal 停止连麦后是否需要拉连麦之前的主播视频流
     * @param sendStopMsg      是否发送结束连麦的消息
     */
    private void stopLinkMic(boolean needPlayOriginal, boolean sendStopMsg) {
        if (getViewerBusiness().isInLinkMic()) {
            getViewerBusiness().stopLinkMic(sendStopMsg);

            mLinkMicGroupView.resetAllView();
            if (needPlayOriginal) {
                if (mIsPlayACC) {
                    playUrlByRoomInfo();
                    mIsPlayACC = false;
                }
            }
        }
    }

    @Override
    public void onMsgEndVideo(CustomMsgEndVideo msg) {
        super.onMsgEndVideo(msg);
        getViewerBusiness().exitRoom(false);
    }

    @Override
    public void onMsgStopLive(CustomMsgStopLive msg) {
        super.onMsgStopLive(msg);
        SDToast.showToast(msg.getDesc());
        getViewerBusiness().exitRoom(true);
    }

    @Override
    public void onBsViewerExitRoom(boolean needFinishActivity) {
        super.onBsViewerExitRoom(needFinishActivity);
        mIsPlayACC = false;
        mAccUrl = null;
        mIsBigToSmallScreen = false;

        destroyIM();
        stopLinkMic(false, true);
        getPlayer().stopPlay();
        if (mIsNeedShowFinish) {
            addLiveFinish();
            close_fouce_dialog();
//            return;
        }

        if (needFinishActivity) {
            finish();
        }
    }

    @Override
    public void onBsHeatRank(CustomMsgHeatRank msg) {
        updateHeatRank(msg.getHeat_rank());
    }

    @Override
    protected void destroyIM() {
        super.destroyIM();
        getViewerIM().destroyIM();
    }

    @Override
    public void onBsRequestRoomInfoSuccess(App_get_videoActModel actModel) {
        int rId = actModel.getRoom_id();
        String gId = actModel.getGroup_id();
        String cId = actModel.getUser_id();
        if (!validateParams(rId, gId, cId)) {
            return;
        }
        this.actModel = actModel;
        super.onBsRequestRoomInfoSuccess(actModel);
        switchVideoViewMode();
        getViewerBusiness().startJoinRoom();
        onResume();
    }

    App_get_videoActModel actModel;

    private void switchVideoViewMode() {
        if (mPlayView == null) {
            return;
        }
        if (getViewerBusiness().isPCCreate()) {
            float height = 0.618f * SDViewUtil.getScreenWidth();

            SDViewUtil.setHeight(mPlayView, (int) height);
            SDViewUtil.setMarginTop(mPlayView, SDViewUtil.dp2px(80));
        } else {
            SDViewUtil.setHeight(mPlayView, ViewGroup.LayoutParams.MATCH_PARENT);
            SDViewUtil.setMarginTop(mPlayView, 0);
        }
    }

    @Override
    public void onBsRequestRoomInfoError(App_get_videoActModel actModel) {
        if (!actModel.canJoinRoom()) {
            super.onBsRequestRoomInfoError(actModel);
            return;
        }
        super.onBsRequestRoomInfoError(actModel);
        if (actModel.isVideoStoped()) {
            addLiveFinish();
        } else {
            getViewerBusiness().exitRoom(true);
        }
    }

    @Override
    public void onBsRequestRoomInfoException(String msg) {
        super.onBsRequestRoomInfoException(msg);

        SDDialogConfirm dialog = new SDDialogConfirm(this);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTextContent("请求直播间信息失败")
                .setTextCancel("退出").setTextConfirm("重试")
                .setCallback(new SDDialogCustom.SDDialogCustomCallback() {
                    @Override
                    public void onClickCancel(View v, SDDialogCustom dialog) {
                        getViewerBusiness().exitRoom(true);
                    }

                    @Override
                    public void onClickConfirm(View v, SDDialogCustom dialog) {
                        requestRoomInfo();
                    }
                }).show();
    }


    @Override
    public void onBsViewerStartJoinRoom() {
        super.onBsViewerStartJoinRoom();
        if (getRoomInfo() == null) {
            return;
        }
        String playUrl = getRoomInfo().getPlay_url();
        if (TextUtils.isEmpty(playUrl)) {
            requestRoomInfo();
        } else {
            startJoinRoomRunnable();
        }
    }

    private void startJoinRoomRunnable() {
        mJoinRoomRunnable.run();
    }

    /**
     * 加入房间runnable
     */
    private SDDelayRunnable mJoinRoomRunnable = new SDDelayRunnable() {

        @Override
        public void run() {
            initIM();
            playUrlByRoomInfo();
        }
    };

    /**
     * 根据接口返回的拉流地址开始拉流
     */
    protected void playUrlByRoomInfo() {
        if (getRoomInfo() == null) {
            return;
        }
        String url = getRoomInfo().getPlay_url();
        if (validatePlayUrl(url)) {
            getPlayer().stopPlay();
            getPlayer().setUrl(url);
            if (getRoomInfo().getIs_push() == 1) {
                getPlayer().startPlay();
                hideLoadingVideo();
            } else if (getRoomInfo().getIs_push() == 0) {
                hideLoadingVideo();
            }
            LogUtil.i("play normal:" + url);
        }
    }

    @Override
    public void onPlayEvent(int event, Bundle param) {

    }

    @Override
    public void onPlayBegin(int event, Bundle param) {

    }

    @Override
    public void onPlayRecvFirstFrame(int event, Bundle param) {
        hideLoadingVideo();
    }

    @Override
    public void onPlayProgress(int event, Bundle param, int total, int progress) {

    }

    @Override
    public void onPlayEnd(int event, Bundle param) {

    }

    @Override
    public void onPlayLoading(int event, Bundle param) {

    }

    @Override
    public void onNetStatus(Bundle param) {

    }

    protected boolean validatePlayUrl(String playUrl) {
        if (TextUtils.isEmpty(playUrl)) {
            SDToast.showToast("未找到直播地址");
            return false;
        }
        if (playUrl.startsWith("rtmp://")) {
            getPlayer().setPlayType(TXLivePlayer.PLAY_TYPE_LIVE_RTMP);
        } else if ((playUrl.startsWith("http://") || playUrl.startsWith("https://")) && playUrl.contains(".flv")) {
            getPlayer().setPlayType(TXLivePlayer.PLAY_TYPE_LIVE_FLV);
        } else {
            SDToast.showToast("播放地址不合法");
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        showExitDialog();
    }

    @Override
    protected void onClickCloseRoom(View v) {
        getViewerBusiness().exitRoom(true);
    }

    private void showExitDialog() {
        SDDialogConfirm dialog = new SDDialogConfirm(this);
        dialog.setTextContent("确定要退出吗？");
        dialog.setCallback(new SDDialogCustom.SDDialogCustomCallback() {
            @Override
            public void onClickConfirm(View v, SDDialogCustom dialog) {
                getViewerBusiness().exitRoom(true);
            }

            @Override
            public void onClickCancel(View v, SDDialogCustom dialog) {
            }
        });
        dialog.show();
    }

    public void onEventMainThread(EUnLogin event) {
        getViewerBusiness().exitRoom(true);
    }

    public void onEventMainThread(EImOnForceOffline event) {
        getViewerBusiness().exitRoom(true);
    }

    public void onEventMainThread(EOnCallStateChanged event) {
        switch (event.state) {
            case TelephonyManager.CALL_STATE_RINGING:
                sdkEnableAudio(false);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                sdkEnableAudio(false);
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                sdkEnableAudio(true);
                break;
            default:
                break;
        }
    }

    @Override
    public void onNetworkChanged(SDNetworkReceiver.NetworkType type) {
        if (type == SDNetworkReceiver.NetworkType.Mobile) {
            SDDialogConfirm dialog = new SDDialogConfirm(this);
            dialog.setTextContent("当前处于数据网络下，会耗费较多流量，是否继续？").setTextCancel("否").setTextConfirm("是")
                    .setCallback(new SDDialogCustom.SDDialogCustomCallback() {
                        @Override
                        public void onClickConfirm(View v, SDDialogCustom dialog) {
                        }

                        @Override
                        public void onClickCancel(View v, SDDialogCustom dialog) {
                            getViewerBusiness().exitRoom(true);
                        }
                    }).show();
        }
        super.onNetworkChanged(type);
    }

    @Override
    protected void sdkEnableAudio(boolean enable) {
        getPlayer().setMute(!enable);
    }

    @Override
    protected void sdkPauseVideo() {
        super.sdkPauseVideo();
        getPlayer().stopPlay();
    }

    @Override
    protected void sdkResumeVideo() {
        super.sdkResumeVideo();
        getPlayer().startPlay();
    }

    @Override
    protected void sdkStopLinkMic() {
        super.sdkStopLinkMic();
        stopLinkMic(false, true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mIsBigToSmallScreen) {
            //如果大屏到小视屏不暂停
        } else {
            getPlayer().stopPlay();
            pauseLinkMic();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mJoinRoomRunnable.removeDelay();
        AppRuntimeData.getInstance().viewerSmallView = null;
        getPlayer().onDestroy();
        mLinkMicGroupView.onDestroy();
        SDEventManager.post(new EPushViewerOnDestroy());
    }

    //小视屏==================================
    @Override
    protected void onResume() {
        super.onResume();
        resumeLinkMic();
        getPlayer().startPlay();
        smallToBigScreen();
        NetTimeUtil.getInstance().caulateDiffTime();
    }

    private void smallToBigScreen() {
        LivePushFloatViewerView view = (LivePushFloatViewerView) SDWindowManager.getInstance().getFirstView(LivePushFloatViewerView.class);
        if (view != null) {
            SDWindowManager.getInstance().removeView(LivePushFloatViewerView.class);
            View videoView = AppRuntimeData.getInstance().viewerSmallView;
            if (videoView != null) {
                mIsBigToSmallScreen = false;
                SDViewUtil.removeView(videoView);
                replaceView(R.id.rl_small_large, videoView);
            }
        }
    }
    public void onEventMainThread(UpdataPackgeGift event) {
        mRoomSendGiftView.updataPackgeGift();
    }
    public void onEventMainThread(EBigToSmallScreen event) {
        switchToSmallVideo();
    }

    public void onEventMainThread(ELiveFloatViewClose event) {
        getViewerBusiness().exitRoom(true);
    }

    private void switchToSmallVideo() {
        resumeLinkMic();
        getPlayer().startPlay();

        mIsBigToSmallScreen = true;

        AppRuntimeData.getInstance().viewerSmallView = fl_view_video;

        JoinLiveData data = new JoinLiveData();
        data.setRoomId(getRoomId());
        data.setGroupId(getGroupId());
        data.setCreaterId(getCreaterId());

        LivePushFloatViewerView.addToFloatView(data);
    }

    //百媚直播======================================start
    @Override
    public void onMsgPushStatus(BMCustomMsgPushStatus bmCustomMsgPushStatus) {
        super.onMsgPushStatus(bmCustomMsgPushStatus);
        if (bmCustomMsgPushStatus.getIs_push() == 1) {
            getPlayer().startPlay();
        } else {
            getPlayer().stopPlay(true);
        }
    }

}
