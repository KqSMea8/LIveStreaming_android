package com.fanwe.live.activity.room;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fanwe.games.model.PluginModel;
import com.fanwe.hybrid.dao.InitActModelDao;
import com.fanwe.hybrid.event.EUnLogin;
import com.fanwe.library.dialog.SDDialogConfirm;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.listener.SDViewVisibilityCallback;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDReplaceableLayout;
import com.fanwe.live.R;
import com.fanwe.live.appview.room.RoomGiftGifView;
import com.fanwe.live.appview.room.RoomGiftPlayView;
import com.fanwe.live.appview.room.RoomHeartView;
import com.fanwe.live.appview.room.RoomInfoView;
import com.fanwe.live.appview.room.RoomMsgView;
import com.fanwe.live.appview.room.RoomPopMsgView;
import com.fanwe.live.appview.room.RoomPrivateRemoveViewerView;
import com.fanwe.live.appview.room.RoomSendMsgView;
import com.fanwe.live.appview.room.RoomViewerJoinRoomView;
import com.fanwe.live.appview.room.RoomWawaView;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.dialog.LiveAddViewerDialog;
import com.fanwe.live.dialog.LiveChatC2CDialog;
import com.fanwe.live.dialog.LiveRechargeDialog;
import com.fanwe.live.dialog.LiveRedEnvelopeNewDialog;
import com.fanwe.live.event.SwitchRoom;
import com.fanwe.live.model.App_get_videoActModel;
import com.fanwe.live.model.LiveQualityData;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.model.custommsg.CustomMsgGift;
import com.fanwe.live.model.custommsg.CustomMsgRedEnvelope;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 公共界面
 */
public class LiveLayoutActivity extends LiveActivity {
    protected RoomInfoView mRoomInfoView;
    protected RoomGiftPlayView mRoomGiftPlayView;
    protected RoomPopMsgView mRoomPopMsgView;
    protected RoomViewerJoinRoomView mRoomViewerJoinRoomView;
    protected RoomMsgView mRoomMsgView;
    protected RoomSendMsgView mRoomSendMsgView;
    protected RoomHeartView mRoomHeartView;
    protected RoomGiftGifView mRoomGiftGifView,mRoomGiftGifView2,mRoomGiftGifView3;
    protected RoomPrivateRemoveViewerView mRoomPrivateRemoveViewerView;
    protected RoomWawaView mRoomWawaView;
    protected ImageView wawa_line;
    protected ImageView wawa_stub;

    private RelativeLayout rl_root_layout; // UI表现层根部局
    private SDReplaceableLayout fl_bottom_extend; // 底部扩展

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        // 关闭房间监听
        findViewById(R.id.view_close_room).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickCloseRoom(v);
            }
        });
    }

    @Override
    public void onKeyboardVisibilityChange(boolean visible, int height) {
        if (rl_root_layout != null) {
            if (visible) {
                rl_root_layout.scrollBy(0, height);
            } else {
                rl_root_layout.scrollTo(0, 0);
                rl_root_layout.requestLayout();
                showSendMsgView(false);
            }
        }
        super.onKeyboardVisibilityChange(visible, height);
    }
    public void playShouhu(CustomMsgGift customMsgGift){
        mRoomGiftGifView2.addShouhuAnimator(customMsgGift);
    }
    public void showOpenShouhu(boolean show) {
        mRoomInfoView.showOpenShouhu(show);
    }

    protected void initLayout(View view) {
        rl_root_layout = (RelativeLayout) view.findViewById(R.id.rl_root_layout);
        initBottomExtend();
        addRoomInfoView();
        addRoomGiftPlayView();
        addRoomGiftGifView();
        addRoomPopMsgView();
        addRoomViewerJoinRoomView();
        addRoomMsgView();
        addRoomSendMsgView();
        addRoomHeartView();
        addRoomBottomView();
        addRoomWawaView();
    }

    /**
     * 初始化底部扩展
     */
    private void initBottomExtend() {
        fl_bottom_extend = (SDReplaceableLayout) findViewById(R.id.fl_bottom_extend);
        if (fl_bottom_extend != null) {
            fl_bottom_extend.addCallback(new SDReplaceableLayout.SDReplaceableLayoutCallback() {
                @Override
                public void onContentReplaced(View view) {
                    showBottomExtendSwitch(true);
                    onContentVisibilityChanged(view, view.getVisibility());
                }

                @Override
                public void onContentRemoved(View view) {
                    showBottomExtendSwitch(false);
                }

                @Override
                public void onContentVisibilityChanged(View view, int visibility) {
                    if (View.VISIBLE == visibility) {
                        onShowBottomExtend();
                    } else {
                        onHideBottomExtend();
                    }
                }
            });
        }
    }

    /**
     * 替换底部扩展
     *
     * @param view
     */
    protected void replaceBottomExtend(View view) {
        fl_bottom_extend.replaceContent(view);
    }

    /**
     * 切换显示隐藏底部扩展
     */
    protected void toggleBottomExtend() {
        fl_bottom_extend.toggleContentVisibleOrGone();
    }

    /**
     * 底部扩展显示回调
     */
    protected void onShowBottomExtend() {
        LogUtil.i("onShowBottomExtend");
    }

    /**
     * 底部扩展隐藏回调
     */
    protected void onHideBottomExtend() {
        LogUtil.i("onHideBottomExtend");
    }

    /**
     * 显示隐藏底部扩展开关
     *
     * @param show
     */
    protected void showBottomExtendSwitch(boolean show) {
        // 子类实现
        LogUtil.i("showBottomExtendSwitch:" + show);
    }

    /**
     * 发送礼物的view显示
     *
     * @param view
     */
    protected void onShowSendGiftView(View view) {
        showBottomView(false);
//        showMsgView(false);
    }


    /**
     * 发送礼物的view隐藏
     *
     * @param view
     */
    protected void onHideSendGiftView(View view) {
        showBottomView(true);
        showMsgView(true);
    }

    /**
     * 发送消息的view显示
     *
     * @param view
     */
    protected void onShowSendMsgView(View view) {
        showBottomView(false);
    }

    /**
     * 发送消息的view隐藏
     *
     * @param view
     */
    protected void onHideSendMsgView(View view) {
        showBottomView(true);
    }

    /**
     * 房间信息
     */
    protected void addRoomInfoView() {
        if (mRoomInfoView == null) {
            mRoomInfoView = new RoomInfoView(this);
            mRoomInfoView.setClickListener(roomInfoClickListener);
            replaceView(R.id.fl_live_room_info, mRoomInfoView);
            mRoomInfoView.setLiveViewerBusiness(getViewerBusiness());
        }
    }

    /**
     * 礼物播放
     */
    protected void addRoomGiftPlayView() {
        if (mRoomGiftPlayView == null) {
            mRoomGiftPlayView = new RoomGiftPlayView(this);
            replaceView(R.id.fl_live_gift_play, mRoomGiftPlayView);
        }
    }

    /**
     * gif动画
     */
    protected void addRoomGiftGifView() {
        //礼物
        if (mRoomGiftGifView == null) {
            mRoomGiftGifView = new RoomGiftGifView(this);
            replaceView(R.id.fl_live_gift_gif, mRoomGiftGifView);
        }
        //坐骑  入场
        if (mRoomGiftGifView2 == null) {
            mRoomGiftGifView2 = new RoomGiftGifView(this);
            mRoomGiftGifView2.setType(2);
            replaceView(R.id.fl_live_gift_gif2, mRoomGiftGifView2);
        }
        //中奖
        if (mRoomGiftGifView3 == null) {
            mRoomGiftGifView3 = new RoomGiftGifView(this);
            mRoomGiftGifView3.setType(3);
            replaceView(R.id.fl_live_gift_gif3, mRoomGiftGifView3);
        }
    }
    public void onEventMainThread(final SwitchRoom event) {
        if (event == null) {
            return;
        }
        //当前直播间不能跳转
        if (event.getRoom_id() == getRoomInfo().getRoom_id()) {
            return;
        }
        //主播不能跳转
        if (UserModelDao.query().getUser_id().equals(getRoomInfo().getUser_id())) {
            return;
        }
        //后台是否设置喇叭跳转
        if (1!= InitActModelDao.query().getFull_msg_jump()) {
            return;
        }
        SDDialogConfirm dialog = new SDDialogConfirm(getActivity());
        dialog.setTextContent("您确定需要前往该直播间吗？").setTextCancel("取消").setTextConfirm("确定")
                .setCallback(new SDDialogCustom.SDDialogCustomCallback() {
                    @Override
                    public void onClickCancel(View v, SDDialogCustom dialog) {
                    }

                    @Override
                    public void onClickConfirm(View v, SDDialogCustom dialog) {
                        mRoomInfoView.switchRoom(event.getRoom_id());
                    }
                });
        dialog.show();
    }
    /**
     * 弹幕
     */
    protected void addRoomPopMsgView() {
        if (mRoomPopMsgView == null) {
            mRoomPopMsgView = new RoomPopMsgView(this);
            replaceView(R.id.fl_live_pop_msg, mRoomPopMsgView);
        }
    }

    /**
     * 进入提示
     */
    protected void addRoomViewerJoinRoomView() {
        if (mRoomViewerJoinRoomView == null) {
            mRoomViewerJoinRoomView = new RoomViewerJoinRoomView(this);
            replaceView(R.id.fl_live_viewer_join_room, mRoomViewerJoinRoomView);
        }
    }

    /**
     * 聊天列表
     */
    protected void addRoomMsgView() {
        if (mRoomMsgView == null) {
            mRoomMsgView = new RoomMsgView(this);
            mRoomMsgView.setLayoutParams(new ViewGroup.LayoutParams(
                    SDViewUtil.getScreenWidthPercent(0.9f),
                    SDViewUtil.getScreenHeightPercent(0.26f)));
            replaceView(R.id.fl_live_msg, mRoomMsgView);
        }
    }

    /**
     * 发送消息
     */
    protected void addRoomSendMsgView() {
        if (mRoomSendMsgView == null) {
            mRoomSendMsgView = new RoomSendMsgView(this);
            mRoomSendMsgView.addVisibilityCallback(new SDViewVisibilityCallback() {
                @Override
                public void onViewVisibilityChanged(View view, int visibility) {
                    if (View.VISIBLE == visibility) {
                        onShowSendMsgView(view);
                    } else {
                        onHideSendMsgView(view);
                    }
                }
            });
            replaceView(R.id.fl_live_send_msg, mRoomSendMsgView);
        }
    }

    /**
     * 娃娃
     */
    protected void addRoomWawaView() {
        if (mRoomWawaView == null) {
            mRoomWawaView = new RoomWawaView(this);
            replaceView(R.id.fl_live_wawa, mRoomWawaView);
            mRoomWawaView.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 点亮
     */
    protected void addRoomHeartView() {
        if (mRoomHeartView == null) {
            mRoomHeartView = new RoomHeartView(this);
            mRoomHeartView.setLayoutParams(new ViewGroup.LayoutParams(
                    SDResourcesUtil.getDimensionPixelSize(R.dimen.width_live_bottom_menu) * 3,
                    SDViewUtil.getScreenWidthPercent(0.5f)));
            replaceView(R.id.fl_live_heart, mRoomHeartView);
        }
    }

    /**
     * 底部菜单
     */
    protected void addRoomBottomView() {
        //子类实现
    }

    /**
     * 私密直播踢人
     */
    protected void addRoomPrivateRemoveViewerView() {
        removeView(mRoomPrivateRemoveViewerView);
        mRoomPrivateRemoveViewerView = new RoomPrivateRemoveViewerView(this);
        addView(mRoomPrivateRemoveViewerView);
    }

    /**
     * 结束界面
     */
    protected void addLiveFinish() {
        // 子类实现
    }

    /**
     * 房间信息view点击监听
     */
    private RoomInfoView.ClickListener roomInfoClickListener = new RoomInfoView.ClickListener() {
        @Override
        public void onClickAddViewer(View v) {
            LiveLayoutActivity.this.onClickAddViewer(v);
        }

        @Override
        public void onClickMinusViewer(View v) {
            LiveLayoutActivity.this.onClickMinusViewer(v);
        }
    };

    /**
     * 私密直播点击加号加人
     *
     * @param v
     */
    protected void onClickAddViewer(View v) {
        LiveAddViewerDialog dialog = new LiveAddViewerDialog(this, getRoomInfo().getPrivate_share());
        dialog.showBottom();
    }

    /**
     * 私密直播点击减号踢人
     *
     * @param v
     */
    protected void onClickMinusViewer(View v) {
        addRoomPrivateRemoveViewerView();
    }

    @Override
    public void onBsRefreshViewerList(List<UserModel> listModel) {
        super.onBsRefreshViewerList(listModel);
        mRoomInfoView.onLiveRefreshViewerList(listModel);
    }

    @Override
    public void onBsRemoveViewer(UserModel model) {
        super.onBsRemoveViewer(model);
        mRoomInfoView.onLiveRemoveViewer(model);
    }

    @Override
    public void onBsInsertViewer(int position, UserModel model) {
        super.onBsInsertViewer(position, model);
        mRoomInfoView.onLiveInsertViewer(position, model);
    }

    @Override
    public void onBsTicketChange(long ticket) {
        super.onBsTicketChange(ticket);
        mRoomInfoView.updateTicket(ticket);
    }

    @Override
    public void onBsViewerNumberChange(int viewerNumber) {
        super.onBsViewerNumberChange(viewerNumber);
        mRoomInfoView.updateViewerNumber(viewerNumber);
    }

    @Override
    public void onBsBindCreaterData(UserModel model) {
        super.onBsBindCreaterData(model);
        mRoomInfoView.bindCreaterData(model);
    }

    @Override
    public void onBsShowOperateViewer(boolean show) {
        super.onBsShowOperateViewer(show);
        mRoomInfoView.showOperateViewerView(show);
    }

    @Override
    public void onBsUpdateLiveQualityData(LiveQualityData data) {
        super.onBsUpdateLiveQualityData(data);
        mRoomInfoView.getSdkInfoView().updateLiveQuality(data);
    }

    @Override
    public void onBsRequestRoomInfoSuccess(App_get_videoActModel actModel) {
        super.onBsRequestRoomInfoSuccess(actModel);

        mRoomInfoView.bindData(actModel);
        bindShowShareView();
        getLiveBusiness().startLiveQualityLooper(this);
    }

    /**
     * 显示充值窗口
     * 整合到基类？
     */
    protected void showRechargeDialog() {
        LiveRechargeDialog dialog = new LiveRechargeDialog(this);
        dialog.show();
    }

    /**
     * 点击关闭
     *
     * @param v
     */
    protected void onClickCloseRoom(View v) {
        //子类实现
    }

    /**
     * 点击打开发送窗口
     *
     * @param v
     */
    protected void onClickMenuSendMsg(View v) {
        showSendMsgView(true);
    }

    @Override
    public void openSendMsg(String content) {
        super.openSendMsg(content);
        showSendMsgView(true);
        mRoomSendMsgView.setContent(content);
    }
    public void continueShouhu(){
        mRoomInfoView.updateShouStatus();
    }
    public void updateHeatRank(int rank){
        mRoomInfoView.updateHeatRank(rank);
    }
    /**
     * 点击私聊消息
     *
     * @param v
     */
    protected void onClickMenuPrivateMsg(View v) {
        LiveChatC2CDialog dialog = new LiveChatC2CDialog(this);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
        dialog.showBottom();
    }

    /**
     * 绑定是否显示分享view
     */
    protected void bindShowShareView() {
    }

    /**
     * 主播普通插件点击
     *
     * @param model
     */
    protected void onClickCreaterPluginNormal(PluginModel model) {

    }

    /**
     * 主播游戏插件点击
     *
     * @param model
     */
    protected void onClickCreaterPluginGame(PluginModel model) {

    }

    protected void replaceBankerView(View view) {
        replaceView(R.id.fl_container_banker, view);
    }

    /**
     * 点击分享
     *
     * @param v
     */
    protected void onClickMenuShare(View v) {
        openShare(new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {
                getLiveBusiness().sendShareSuccessMsg();
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
            }
        });
    }

    /**
     * 隐藏发送礼物
     */
    protected void hideSendGiftView() {

    }

    /**
     * 显示隐藏底部菜单view
     */
    protected void showBottomView(boolean show) {
        // 子类实现
    }

    /**
     * 显示隐藏消息列表
     */
    protected void showMsgView(boolean show) {
        if (show) {
            mRoomMsgView.getVisibilityHandler().setVisible(true);
        } else {
            mRoomMsgView.getVisibilityHandler().setInvisible(true);
        }
    }
    public void close_fouce_dialog(){
        mRoomInfoView.close_fouce_dialog();
    }
    /**
     * 显示隐藏发送消息view
     */
    protected void showSendMsgView(boolean show) {
        if (show) {
            SDViewUtil.setVisible(mRoomSendMsgView);
            mRoomSendMsgView.setV_identity(getRoomInfo().getPodcast().getV_identity());
            mRoomSendMsgView.initSendMeaasgeView(getRoomInfo().getPodcast().getV_identity_colour(),getRoomInfo().getPodcast().getV_speak_num());
        } else {
            SDViewUtil.setInvisible(mRoomSendMsgView);
        }
    }

    /**
     * 发送消息view是否可见
     *
     * @return
     */
    protected boolean isSendMsgViewVisible() {
        if (mRoomSendMsgView == null) {
            return false;
        }
        return mRoomSendMsgView.isVisible();
    }

    /**
     * 发送礼物view是否可见
     *
     * @return
     */
    protected boolean isSendGiftViewVisible() {
        return false;
    }

    @Override
    public void onMsgRedEnvelope(CustomMsgRedEnvelope msg) {
        super.onMsgRedEnvelope(msg);
        LiveRedEnvelopeNewDialog dialog = new LiveRedEnvelopeNewDialog(this, msg);
        dialog.show();
    }

    @Override
    public void onBsViewerShowCreaterLeave(boolean show) {
        super.onBsViewerShowCreaterLeave(show);
        if (isAuctioning()) {
            mRoomInfoView.showCreaterLeave(false);
        } else if (getRoomInfo() != null && getRoomInfo().getIs_bm() == 1) {
            mRoomInfoView.showCreaterLeave(false);
        } else {
            mRoomInfoView.showCreaterLeave(show);
        }
    }
}
