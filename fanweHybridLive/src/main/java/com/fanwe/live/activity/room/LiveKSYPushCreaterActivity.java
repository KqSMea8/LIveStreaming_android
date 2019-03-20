package com.fanwe.live.activity.room;

import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;

import com.fanwe.hybrid.event.EUnLogin;
import com.fanwe.library.dialog.SDDialogConfirm;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.receiver.SDNetworkReceiver;
import com.fanwe.library.utils.SDRunnableTryer;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.LiveInformation;
import com.fanwe.live.R;
import com.fanwe.live.appview.room.ARoomMusicView;
import com.fanwe.live.appview.room.RoomKSYPushMusicView;
import com.fanwe.live.control.IPushSDK;
import com.fanwe.live.control.KSYPushSDK;
import com.fanwe.live.dialog.LiveKSYSetBeautyDialog;
import com.fanwe.live.event.EImOnForceOffline;
import com.fanwe.live.event.EOnCallStateChanged;
import com.fanwe.live.model.App_get_videoActModel;
import com.fanwe.live.model.LiveQualityData;
import com.fanwe.live.model.custommsg.CustomMsgStopLive;
import com.fanwe.live.utils.PermissionUtil;
import com.tencent.TIMCallBack;
import com.tencent.TIMValueCallBack;

/**
 * 金山sdk推流直播主播界面
 */
public class LiveKSYPushCreaterActivity extends LiveLayoutCreaterExtendActivity
{
    protected boolean mIsCreaterLeaveByCall = false;
    private SDRunnableTryer mGroupTryer = new SDRunnableTryer();

    @Override
    protected int onCreateContentView()
    {
        return R.layout.act_live_ksy_push_creater;
    }

    @Override
    protected ARoomMusicView createRoomMusicView()
    {
        return new RoomKSYPushMusicView(this);
    }

    @Override
    protected void init(Bundle savedInstanceState)
    {
        super.init(savedInstanceState);
        PermissionUtil.isCameraCanUse();

        if (getRoomId() <= 0)
        {
            SDToast.showToast("房间id为空");
            finish();
            return;
        }

        initPusher();
        initLayout(getWindow().getDecorView());
        requestRoomInfo();
    }

    @Override
    protected IPushSDK getPushSDK()
    {
        return KSYPushSDK.getInstance();
    }

    /**
     * 初始化推流对象
     */
    private void initPusher()
    {
        getPushSDK().init(find(R.id.view_video));
    }

    @Override
    protected void initIM()
    {
        super.initIM();
        if (isClosedBack())
        {
            getGameBusiness().requestGameInfo();
        } else
        {
            App_get_videoActModel actModel = getRoomInfo();
            if (actModel != null && !TextUtils.isEmpty(actModel.getGroup_id()))
            {
                final String groupId = actModel.getGroup_id();
                getCreaterIM().joinGroup(groupId, new TIMCallBack()
                {
                    @Override
                    public void onError(int code, String desc)
                    {
                        dealGroupError(code, desc);
                    }

                    @Override
                    public void onSuccess()
                    {
                        dealGroupSuccess(groupId);
                    }
                });
            } else
            {
                getCreaterIM().createGroup(String.valueOf(getRoomId()), new TIMValueCallBack<String>()
                {
                    @Override
                    public void onError(int code, String desc)
                    {
                        dealGroupError(code, desc);
                    }

                    @Override
                    public void onSuccess(String groupId)
                    {
                        dealGroupSuccess(groupId);
                    }
                });
            }
        }
    }

    /**
     * 加入或者创建聊天组成功处理
     *
     * @param groupId
     */
    private void dealGroupSuccess(String groupId)
    {
        LiveInformation.getInstance().setGroupId(groupId);
        requestUpdateLiveStateSuccess();
    }

    /**
     * 加入或者创建聊天组失败处理
     *
     * @param code
     * @param desc
     */
    protected void dealGroupError(int code, String desc)
    {
        boolean result = mGroupTryer.tryRunDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                initIM();
            }
        }, 3000);

        if (!result)
        {
            showGroupErrorDialog(code, desc);
        }
    }

    /**
     * 显示加入或者创建聊天组失败窗口
     *
     * @param code
     * @param desc
     */
    protected void showGroupErrorDialog(int code, String desc)
    {
        SDDialogConfirm dialog = new SDDialogConfirm(this);
        dialog.setTextContent("创建聊天组失败，请退出重试").setTextCancel(null).setTextConfirm("确定");
        dialog.setCancelable(false);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener()
        {
            @Override
            public void onDismiss(DialogInterface dialog)
            {
                requestUpdateLiveStateFail();
                exitRoom(false);
            }
        });
        dialog.show();
    }

    @Override
    public void onBsRequestRoomInfoSuccess(App_get_videoActModel actModel)
    {
        super.onBsRequestRoomInfoSuccess(actModel);
        if (isClosedBack())
        {
            final String groupId = actModel.getGroup_id();
            requestUpdateLiveStateComeback();
            getCreaterIM().setJoinGroupSuccess(groupId);
            getCreaterIM().sendCreaterComebackMsg(null);
        }

        initIM();
        startPush(actModel.getPush_rtmp());
    }

    @Override
    public void onBsRequestRoomInfoError(App_get_videoActModel actModel)
    {
        super.onBsRequestRoomInfoError(actModel);
//        exitRoom(false);
    }

    /**
     * 开始推流
     */
    protected void startPush(String url)
    {
        if (TextUtils.isEmpty(url))
        {
            SDToast.showToast("推流地址为空");
            return;
        }
        if (!isClosedBack())
        {
            addRoomCountDownView();
        }
        getPushSDK().setUrl(url);
        getPushSDK().startPush();

        getPushSDK().enableBeautyFilter(true);
    }

    @Override
    public LiveQualityData onBsGetLiveQualityData()
    {
        return getPushSDK().getLiveQualityData();
    }

    @Override
    public void onMsgStopLive(CustomMsgStopLive msg)
    {
        super.onMsgStopLive(msg);
        exitRoom(true);
    }

    /**
     * 退出房间
     *
     * @param addLiveFinish true-显示直播结束界面；false-关闭当前Activity
     */
    protected void exitRoom(boolean addLiveFinish)
    {
        getCreaterBusiness().stopMonitor();
        removeRoomCountDownView();
        getPushSDK().stopPush();
        stopMusic();
        destroyIM();

        if (addLiveFinish)
        {
            addLiveFinish();
        } else
        {
            finish();
        }
    }

    @Override
    protected void addLiveFinish()
    {
        super.addLiveFinish();
        getCreaterBusiness().requestEndVideo();
    }

    @Override
    protected void destroyIM()
    {
        super.destroyIM();
        getCreaterIM().destroyIM();
    }

    /**
     * 主播离开
     */
    private void createrLeave()
    {
        if (!mIsCreaterLeave)
        {
            mIsCreaterLeave = true;
            requestUpdateLiveStateLeave();
            getPushSDK().pausePush();
            getCreaterIM().sendCreaterLeaveMsg(null);
            if (getRoomMusicView() != null)
            {
                getRoomMusicView().onStopLifeCircle();
            }
        }
    }

    /**
     * 主播回来
     */
    private void createrComeback()
    {
        if (mIsCreaterLeave)
        {
            mIsCreaterLeave = false;
            requestUpdateLiveStateComeback();
            getPushSDK().resumePush();
            getCreaterIM().sendCreaterComebackMsg(null);
            if (getRoomMusicView() != null)
            {
                getRoomMusicView().onResumeLifeCircle();
            }
        }
    }


    @Override
    public void onNetworkChanged(SDNetworkReceiver.NetworkType type)
    {
        if (type == SDNetworkReceiver.NetworkType.Mobile)
        {
            SDDialogConfirm dialog = new SDDialogConfirm(this);
            dialog.setTextContent("当前处于数据网络下，会耗费较多流量，是否继续？")
                    .setTextCancel("退出")
                    .setTextConfirm("继续")
                    .setCallback(new SDDialogCustom.SDDialogCustomCallback()
                    {
                        @Override
                        public void onClickCancel(View v, SDDialogCustom dialog)
                        {
                            exitRoom(true);
                        }

                        @Override
                        public void onClickConfirm(View v, SDDialogCustom dialog)
                        {

                        }
                    }).show();
        }
        super.onNetworkChanged(type);
    }

    @Override
    public void onBackPressed()
    {
        if (isAuctioning())
        {
            showActionExitDialog();
        } else
        {
            showNormalExitDialog();
        }
    }

    @Override
    protected void onClickCloseRoom(View v)
    {
        super.onClickCloseRoom(v);
        if (isAuctioning())
        {
            showActionExitDialog();
        } else
        {
            showNormalExitDialog();
        }
    }

    private void showActionExitDialog()
    {
        SDDialogConfirm dialog = new SDDialogConfirm(this);
        dialog.setTextContent("您发起的竞拍暂未结束，不能关闭直播");
        dialog.setTextConfirm(null);
        dialog.show();
    }

    private void showNormalExitDialog()
    {
        SDDialogConfirm dialog = new SDDialogConfirm(this);
        dialog.setTextContent("确定要结束直播吗？");
        dialog.setCallback(new SDDialogCustom.SDDialogCustomCallback()
        {
            @Override
            public void onClickCancel(View v, SDDialogCustom dialog)
            {

            }

            @Override
            public void onClickConfirm(View v, SDDialogCustom dialog)
            {
                exitRoom(true);
            }
        });
        dialog.show();
    }

    @Override
    protected void showSetBeautyDialog()
    {
        LiveKSYSetBeautyDialog dialog = new LiveKSYSetBeautyDialog(this);
        dialog.showBottom();
    }

    //----------EventBus start----------

    @Override
    public void onEventMainThread(EUnLogin event)
    {
        exitRoom(false);
    }

    @Override
    public void onEventMainThread(EImOnForceOffline event)
    {
        exitRoom(true);
    }

    @Override
    public void onEventMainThread(EOnCallStateChanged event)
    {
        switch (event.state)
        {
            case TelephonyManager.CALL_STATE_RINGING:
            case TelephonyManager.CALL_STATE_OFFHOOK:
                if (mIsCreaterLeave)
                {
                    mIsCreaterLeaveByCall = false;
                } else
                {
                    mIsCreaterLeaveByCall = true;
                }
                createrLeave();
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                if (mIsCreaterLeaveByCall)
                {
                    createrComeback();
                    mIsCreaterLeaveByCall = false;
                }
                break;
            default:
                break;
        }
    }

    //----------EventBus end----------

    //----------activity lifecycle start----------

    @Override
    protected void onResume()
    {
        super.onResume();
        createrComeback();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        createrLeave();
    }

    @Override
    protected void onDestroy()
    {
        mGroupTryer.onDestroy();
        getPushSDK().onDestroy();
        super.onDestroy();
    }

    //----------activity lifecycle end----------

}