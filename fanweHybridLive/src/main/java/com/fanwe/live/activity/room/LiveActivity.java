package com.fanwe.live.activity.room;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;

import com.fanwe.baimei.model.custommsg.BMCustomMsgPushStatus;
import com.fanwe.games.model.App_plugin_initActModel;
import com.fanwe.games.model.custommsg.CustomMsgGameBanker;
import com.fanwe.hybrid.activity.BaseActivity;
import com.fanwe.hybrid.event.EUnLogin;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.live.IMHelper;
import com.fanwe.live.LiveConstant;
import com.fanwe.live.LiveCreaterIM;
import com.fanwe.live.LiveInformation;
import com.fanwe.live.LiveViewerIM;
import com.fanwe.live.business.LiveBusiness;
import com.fanwe.live.business.LiveCreaterBusiness;
import com.fanwe.live.business.LiveMsgBusiness;
import com.fanwe.live.business.LiveViewerBusiness;
import com.fanwe.live.control.IPushSDK;
import com.fanwe.live.event.EImOnForceOffline;
import com.fanwe.live.event.EImOnNewMessages;
import com.fanwe.live.event.EOnCallStateChanged;
import com.fanwe.live.model.App_end_videoActModel;
import com.fanwe.live.model.App_get_videoActModel;
import com.fanwe.live.model.App_monitorActModel;
import com.fanwe.live.model.App_viewerActModel;
import com.fanwe.live.model.LiveQualityData;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.model.custommsg.CustomMsgAcceptLinkMic;
import com.fanwe.live.model.custommsg.CustomMsgApplyLinkMic;
import com.fanwe.live.model.custommsg.CustomMsgCreaterComeback;
import com.fanwe.live.model.custommsg.CustomMsgCreaterLeave;
import com.fanwe.live.model.custommsg.CustomMsgData;
import com.fanwe.live.model.custommsg.CustomMsgEndVideo;
import com.fanwe.live.model.custommsg.CustomMsgGift;
import com.fanwe.live.model.custommsg.CustomMsgGreedLinkMic;
import com.fanwe.live.model.custommsg.CustomMsgHeatRank;
import com.fanwe.live.model.custommsg.CustomMsgLargeGift;
import com.fanwe.live.model.custommsg.CustomMsgLight;
import com.fanwe.live.model.custommsg.CustomMsgOpenShouhu;
import com.fanwe.live.model.custommsg.CustomMsgPopMsg;
import com.fanwe.live.model.custommsg.CustomMsgPrize;
import com.fanwe.live.model.custommsg.CustomMsgRedEnvelope;
import com.fanwe.live.model.custommsg.CustomMsgRejectLinkMic;
import com.fanwe.live.model.custommsg.CustomMsgStopLinkMic;
import com.fanwe.live.model.custommsg.CustomMsgStopLive;
import com.fanwe.live.model.custommsg.CustomMsgViewerJoin;
import com.fanwe.live.model.custommsg.CustomMsgViewerQuit;
import com.fanwe.live.model.custommsg.CustomMsgWarning;
import com.fanwe.live.model.custommsg.MsgModel;
import com.fanwe.live.model.custommsg.data.DataLinkMicInfoModel;
import com.umeng.socialize.UMShareListener;

import java.util.List;

/**
 * 直播间基类
 * <p/>
 * Created by Administrator on 2016/8/4.
 */
public class LiveActivity extends BaseActivity implements ILiveActivity,
        LiveMsgBusiness.LiveMsgBusinessCallback,
        LiveViewerBusiness.LiveViewerBusinessCallback,
        LiveCreaterBusiness.LiveCreaterBusinessCallback {
    /**
     * 房间id(int)
     */
    public static final String EXTRA_ROOM_ID = "extra_room_id";
    /**
     * 讨论组id(String)
     */
    public static final String EXTRA_GROUP_ID = "extra_group_id";
    /**
     * 主播identifier(String)
     */
    public static final String EXTRA_CREATER_ID = "extra_creater_id";

    private LiveViewerIM mViewerIM;
    private LiveCreaterIM mCreaterIM;

    private LiveMsgBusiness mMsgBusiness;

    private LiveViewerBusiness mViewerBusiness;
    private LiveCreaterBusiness mCreaterBusiness;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // 不锁屏

        int roomId = getIntent().getIntExtra(EXTRA_ROOM_ID, 0);
        String groupId = getIntent().getStringExtra(EXTRA_GROUP_ID);
        String createrId = getIntent().getStringExtra(EXTRA_CREATER_ID);

        LiveInformation.getInstance().setRoomId(roomId);
        LiveInformation.getInstance().setGroupId(groupId);
        LiveInformation.getInstance().setCreaterId(createrId);
    }

    /**
     * 获得推流sdk
     *
     * @return
     */
    protected IPushSDK getPushSDK() {
        return null;
    }

    /**
     * 获得消息业务类
     *
     * @return
     */
    public LiveMsgBusiness getMsgBusiness() {
        if (mMsgBusiness == null) {
            mMsgBusiness = new LiveMsgBusiness();
            mMsgBusiness.setBusinessCallback(this);
        }
        return mMsgBusiness;
    }

    /**
     * 获得观众业务类
     *
     * @return
     */
    public LiveViewerBusiness getViewerBusiness() {
        if (mViewerBusiness == null) {
            mViewerBusiness = new LiveViewerBusiness(this);
            mViewerBusiness.setBusinessCallback(this);
        }
        return mViewerBusiness;
    }

    /**
     * 获得主播业务类
     *
     * @return
     */
    public LiveCreaterBusiness getCreaterBusiness() {
        if (mCreaterBusiness == null) {
            mCreaterBusiness = new LiveCreaterBusiness(this);
            mCreaterBusiness.setBusinessCallback(this);
        }
        return mCreaterBusiness;
    }
    /**
     * 获得直播业务类
     *
     * @return
     */
    public LiveBusiness getLiveBusiness() {
        if (isCreater()) {
            return getCreaterBusiness();
        } else {
            return getViewerBusiness();
        }
    }

    /**
     * 获得观众的IM操作类
     *
     * @return
     */
    public LiveViewerIM getViewerIM() {
        if (mViewerIM == null) {
            mViewerIM = new LiveViewerIM();
        }
        return mViewerIM;
    }

    /**
     * 获得主播的IM操作类
     *
     * @return
     */
    public LiveCreaterIM getCreaterIM() {
        if (mCreaterIM == null) {
            mCreaterIM = new LiveCreaterIM();
        }
        return mCreaterIM;
    }
    /**
     * 打开分享面板
     */
    public void openShare(UMShareListener listener) {
        getLiveBusiness().openShare(this, listener);
    }

    /**
     * 请求房间信息
     *
     * @return
     */
    protected void requestRoomInfo() {
        //子类实现
    }

    /**
     * 观众加入聊天组成功回调
     *
     * @param groupId
     */
    protected void onSuccessJoinGroup(String groupId) {

    }

    /**
     * 接收im新消息
     *
     * @param event
     */
    public void onEventMainThread(EImOnNewMessages event) {
        String groupId = getGroupId();

        getMsgBusiness().parseMsg(event.msg, groupId);
        getLiveBusiness().getMsgBusiness().parseMsg(event.msg, groupId);

        try {
            if (LiveConstant.CustomMsgType.MSG_DATA == event.msg.getCustomMsgType()) {
                String peer = event.msg.getConversationPeer();
                if (!TextUtils.isEmpty(groupId)) {
                    if (!groupId.equals(peer)) {
                        // 别的直播间消息
                        IMHelper.quitGroup(peer, null);
                        LogUtil.i("quitGroup other room:" + peer);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onMsgApplyLinkMic(CustomMsgApplyLinkMic msg) {
    }

    @Override
    public void onMsgAcceptLinkMic(CustomMsgAcceptLinkMic msg) {
    }

    @Override
    public void onMsgGreedLinkMic(CustomMsgGreedLinkMic msg) {

    }

    @Override
    public void onMsgRejectLinkMic(CustomMsgRejectLinkMic msg) {
    }

    @Override
    public void onMsgStopLinkMic(CustomMsgStopLinkMic msg) {
    }

    @Override
    public void onMsgRedEnvelope(CustomMsgRedEnvelope msg) {
    }

    @Override
    public void onMsgEndVideo(CustomMsgEndVideo msg) {
    }

    @Override
    public void onMsgStopLive(CustomMsgStopLive msg) {
    }

    @Override
    public void onMsgPrivate(MsgModel msg) {
    }

    @Override
    public void onMsgAuction(MsgModel msg) {
    }

    @Override
    public void onMsgShop(MsgModel msg) {
    }

    @Override
    public void onMsgGame(MsgModel msg) {
    }

    @Override
    public void onMsgGameBanker(CustomMsgGameBanker msg) {

    }

    @Override
    public void onMsgGift(CustomMsgGift msg) {
    }

    @Override
    public void onMsgPopMsg(CustomMsgPopMsg msg) {
    }

    @Override
    public void onMsgViewerJoin(CustomMsgViewerJoin msg) {
    }

    @Override
    public void onMsgViewerQuit(CustomMsgViewerQuit msg) {
    }

    @Override
    public void onMsgCreaterLeave(CustomMsgCreaterLeave msg) {
    }

    @Override
    public void onMsgCreaterComeback(CustomMsgCreaterComeback msg) {
    }

    @Override
    public void onMsgLight(CustomMsgLight msg) {
    }

    @Override
    public void onMsgLiveChat(MsgModel msg) {
    }

    @Override
    public void onMsgWarning(CustomMsgWarning msgWarning) {

    }

    @Override
    public void onMsgData(CustomMsgData msg) {

    }

    @Override
    public void onMsgHeatRank(CustomMsgHeatRank msg) {

    }


    @Override
    public void onMsgPrize(CustomMsgPrize msg) {

    }


    @Override
    public void onMsgLiveOpenShouhu(CustomMsgOpenShouhu msg) {

    }

    @Override
    public void onMsgDataViewerList(App_viewerActModel model) {

    }

    @Override
    public void onMsgDataLinkMicInfo(DataLinkMicInfoModel model) {

    }

    @Override
    public void onMsgPushStatus(BMCustomMsgPushStatus bmCustomMsgPushStatus) {

    }

    @Override
    public void onMsgLargeGift(CustomMsgLargeGift customMsgLargeGift) {

    }

    @Override
    public void onMsgPayMode(MsgModel msg) {
    }

    /**
     * 退出登录
     *
     * @param event
     */
    public void onEventMainThread(EUnLogin event) {
        //子类实现
    }

    /**
     * 帐号异地登录
     *
     * @param event
     */
    public void onEventMainThread(EImOnForceOffline event) {
        //子类实现
    }

    /**
     * 电话监听
     *
     * @param event
     */
    public void onEventMainThread(EOnCallStateChanged event) {
        //子类实现
    }

    /**
     * 初始化IM
     */
    protected void initIM() {
        //子类实现
    }

    /**
     * 销毁IM
     */
    protected void destroyIM() {
        //子类实现
    }

    @Override
    protected void onDestroy() {
        mViewerIM = null;
        mCreaterIM = null;
        if (mViewerBusiness != null) {
            mViewerBusiness.onDestroy();
            mViewerBusiness = null;
        }
        if (mCreaterBusiness != null) {
            mCreaterBusiness.onDestroy();
            mCreaterBusiness = null;
        }
        LiveInformation.getInstance().exitRoom();
        super.onDestroy();
    }

    //sdk

    /**
     * 开关声音
     *
     * @param enable true-打开，false-关闭
     */
    protected void sdkEnableAudio(boolean enable) {
        //子类实现
    }

    /**
     * 暂停视频播放
     */
    protected void sdkPauseVideo() {

    }

    /**
     * 恢复视频播放
     */
    protected void sdkResumeVideo() {

    }

    /**
     * 停止连麦
     */
    protected void sdkStopLinkMic() {
        //子类实现
    }

    @Override
    public LiveCreaterBusiness.CreaterMonitorData onBsCreaterGetMonitorData() {
        return null;
    }

    @Override
    public void onBsCreaterRequestMonitorSuccess(App_monitorActModel actModel) {
    }

    @Override
    public void onBsCreaterRequestInitPluginSuccess(App_plugin_initActModel actModel) {
    }


    @Override
    public void onBsCreateApplyLinkMicError(String msg) {

    }

    @Override
    public void onAcceptApplyPK(CustomMsgAcceptLinkMic msg) {

    }

    @Override
    public void onBsCreateApplyLinkMicGreed(CustomMsgGreedLinkMic msg) {

    }

    @Override
    public void onBsCreateApplyLinkMicRejected(CustomMsgRejectLinkMic msg) {

    }

    @Override
    public void onBsCreaterRequestEndVideoSuccess(App_end_videoActModel actModel) {
    }

    @Override
    public void onBsCreaterShowReceiveApplyLinkMic(CustomMsgApplyLinkMic msg) {
    }

    @Override
    public void onReceivedCustomerData(CustomMsgData msg) {

    }

    @Override
    public void onBsCreaterHideReceiveApplyLinkMic() {
    }

    @Override
    public boolean onBsCreaterIsReceiveApplyLinkMicShow() {
        return false;
    }


    @Override
    public void onBsViewerShowCreaterLeave(boolean show) {
    }

    @Override
    public void onBsViewerShowApplyLinkMic(boolean show) {
    }

    @Override
    public void onBsViewerApplyLinkMicError(String msg) {
    }

    @Override
    public void onBsViewerApplyLinkMicRejected(CustomMsgRejectLinkMic msg) {
    }

    @Override
    public void onBsViewerStartJoinRoom() {
    }

    @Override
    public void onBsViewerExitRoom(boolean needFinishActivity) {

    }

    @Override
    public void onBsHeatRank(CustomMsgHeatRank msg) {

    }


    @Override
    public void onBsRequestRoomInfoSuccess(App_get_videoActModel actModel) {
    }

    @Override
    public void onBsRequestRoomInfoError(App_get_videoActModel actModel) {
    }

    @Override
    public void onBsRequestRoomInfoException(String msg) {
    }

    @Override
    public void onBsRefreshViewerList(List<UserModel> listModel) {
    }

    @Override
    public void onBsRemoveViewer(UserModel model) {
    }

    @Override
    public void onBsInsertViewer(int position, UserModel model) {
    }

    @Override
    public void onBsTicketChange(long ticket) {
    }

    @Override
    public void onBsViewerNumberChange(int viewerNumber) {
    }

    @Override
    public void onBsBindCreaterData(UserModel model) {
    }

    @Override
    public void onBsShowOperateViewer(boolean show) {
    }

    @Override
    public LiveQualityData onBsGetLiveQualityData() {
        return null;
    }

    @Override
    public void onBsUpdateLiveQualityData(LiveQualityData data) {
    }

    @Override
    public void onBsViewerShowLianMai(boolean show) {

    }

    @Override
    public void onBsLiveBackgroundChanged(int imageResId) {

    }

    @Override
    public void onBsShowProgress(String msg) {
        showProgressDialog(msg);
    }

    @Override
    public void onBsHideProgress() {
        dismissProgressDialog();
    }

    //----------ILiveActivity implements start----------

    @Override
    public int getRoomId() {
        return LiveInformation.getInstance().getRoomId();
    }

    @Override
    public String getGroupId() {
        return LiveInformation.getInstance().getGroupId();
    }

    @Override
    public String getCreaterId() {
        return LiveInformation.getInstance().getCreaterId();
    }

    @Override
    public App_get_videoActModel getRoomInfo() {
        return LiveInformation.getInstance().getRoomInfo();
    }

    @Override
    public boolean isPrivate() {
        return LiveInformation.getInstance().isPrivate();
    }

    @Override
    public boolean isPlayback() {
        return LiveInformation.getInstance().isPlayback();
    }

    @Override
    public boolean isCreater() {
        return LiveInformation.getInstance().isCreater();
    }

    @Override
    public int getSdkType() {
        return LiveInformation.getInstance().getSdkType();
    }

    @Override
    public boolean isAuctioning() {
        return LiveInformation.getInstance().isAuctioning();
    }

    @Override
    public boolean isPushing() {
        return LiveInformation.getInstance().isPushing();
    }


    @Override
    public void openSendMsg(String content) {

    }

    //----------ILiveActivity implements start----------
//livepk通用方法
    public String mSec2hms(Long mSec) {
        Long diffTime = mSec / 1000L;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 2; i > 0; i--) {
            Long modTime = diffTime % 60;
            stringBuilder.insert(0, modTime + (i % 2 == 1 ? ":" : ""));
            diffTime /= 60;
        }
        stringBuilder.insert(0, diffTime + ":");
        return stringBuilder.toString();
    }

    /**
     * 缩放主画面
     */
    public void ZoomInAnimationLeft(View view) {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        AnimatorSet animatorSetsuofang = new AnimatorSet();//组合动画
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1, 0.5f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1, 0.5f);
        ObjectAnimator TranslationX = ObjectAnimator.ofFloat(view, "translationX", -dm.widthPixels / 4);
        ObjectAnimator TranslationY = ObjectAnimator.ofFloat(view, "translationY", -dm.heightPixels / 4 + dp2px(this, 90));
        animatorSetsuofang.setDuration(1000);
        animatorSetsuofang.setInterpolator(new DecelerateInterpolator());
        animatorSetsuofang.play(scaleX).with(scaleY).with(TranslationX).with(TranslationY);//两个动画同时开始
        animatorSetsuofang.start();
    }

    private int dp2px(Context context, int dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    public void ZoomInAnimationRight(View view) {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        AnimatorSet animatorSetsuofang = new AnimatorSet();//组合动画
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1, 0.5f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1, 0.5f);
        ObjectAnimator TranslationX = ObjectAnimator.ofFloat(view, "translationX", dm.widthPixels / 4);
        ObjectAnimator TranslationY = ObjectAnimator.ofFloat(view, "translationY", -dm.heightPixels / 4 + dp2px(this, 90));
        animatorSetsuofang.setDuration(500);
        animatorSetsuofang.setInterpolator(new DecelerateInterpolator());
        animatorSetsuofang.play(scaleX).with(scaleY).with(TranslationX).with(TranslationY);//两个动画同时开始
        animatorSetsuofang.start();
    }

    public void ZoomOutAnimation(View view) {
        if(view.getTranslationY()==0){
            return;
        }
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        AnimatorSet animatorSetsuofang = new AnimatorSet();//组合动画
        view.setPivotX(view.getWidth() / 2);
        view.setPivotY(view.getHeight() / 2);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.5f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.5f, 1f);
        ObjectAnimator TranslationX = ObjectAnimator.ofFloat(view, "translationX", view.getTranslationX(), view.getTranslationX() + dm.widthPixels / 4);
        ObjectAnimator TranslationY = ObjectAnimator.ofFloat(view, "translationY", view.getTranslationY(), view.getTranslationY() + dm.heightPixels / 4 - dp2px(this, 90));
        animatorSetsuofang.setDuration(500);
        animatorSetsuofang.setInterpolator(new DecelerateInterpolator());
        animatorSetsuofang.play(TranslationX).with(TranslationY).before(scaleY).before(scaleX);//两个动画同时开始
        animatorSetsuofang.start();
    }
    public void ZoomOutAnimationPK(View view) {
        if(view.getTranslationY()==0){
            return;
        }
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        AnimatorSet animatorSetsuofang = new AnimatorSet();//组合动画
        view.setPivotX(view.getWidth() / 2);
        view.setPivotY(view.getHeight() / 2);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.5f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.5f, 1f);
        ObjectAnimator TranslationX = ObjectAnimator.ofFloat(view, "translationX", view.getTranslationX(), view.getTranslationX() - dm.widthPixels / 4);
        ObjectAnimator TranslationY = ObjectAnimator.ofFloat(view, "translationY", view.getTranslationY(), view.getTranslationY() + dm.heightPixels / 4 - dp2px(this, 90));
        animatorSetsuofang.setDuration(1000);
        animatorSetsuofang.setInterpolator(new DecelerateInterpolator());
        animatorSetsuofang.play(TranslationX).with(TranslationY).before(scaleY).before(scaleX);//两个动画同时开始
        animatorSetsuofang.start();
    }
    public void TranslationDownAnimation(View view) {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        ObjectAnimator TranslationY = ObjectAnimator.ofFloat(view, "translationY", view.getTranslationY(), + dp2px(this, 90));
        TranslationY.setDuration(1);
        TranslationY.setInterpolator(new DecelerateInterpolator());
        TranslationY.start();
    }
    public void TranslationUpAnimation(View view) {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        ObjectAnimator TranslationY = ObjectAnimator.ofFloat(view, "translationY", view.getTranslationY(), view.getTranslationY()- dp2px(this, 90));
        TranslationY.setDuration(1);
        TranslationY.setInterpolator(new DecelerateInterpolator());
        TranslationY.start();
    }
    public void scaleAnimation(View view){
        AnimatorSet animatorSetsuofang = new AnimatorSet();//组合动画
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.5f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.5f, 1f);
        animatorSetsuofang.setDuration(1000);
        animatorSetsuofang.setInterpolator(new DecelerateInterpolator());
        animatorSetsuofang.play(scaleX).with(scaleY);//两个动画同时开始
        animatorSetsuofang.start();
    }
}
