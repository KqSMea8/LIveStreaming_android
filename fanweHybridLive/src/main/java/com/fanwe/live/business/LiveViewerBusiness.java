package com.fanwe.live.business;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.text.TextUtils;
import android.util.Base64;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.http.AppRequestCallbackWrapper;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.blocker.SDObjectBlocker;
import com.fanwe.library.utils.SDJsonUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.IMHelper;
import com.fanwe.live.activity.room.ILiveActivity;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.App_check_lianmaiActModel;
import com.fanwe.live.model.App_get_videoActModel;
import com.fanwe.live.model.BodyParams;
import com.fanwe.live.model.SpeechToText;
import com.fanwe.live.model.SpeechToken;
import com.fanwe.live.model.Video_check_statusActModel;
import com.fanwe.live.model.custommsg.CustomMsgAcceptLinkMic;
import com.fanwe.live.model.custommsg.CustomMsgApplyLinkMic;
import com.fanwe.live.model.custommsg.CustomMsgCreaterComeback;
import com.fanwe.live.model.custommsg.CustomMsgCreaterLeave;
import com.fanwe.live.model.custommsg.CustomMsgHeatRank;
import com.fanwe.live.model.custommsg.CustomMsgRejectLinkMic;
import com.fanwe.live.model.custommsg.CustomMsgStopLinkMic;
import com.fanwe.live.model.custommsg.CustomMsgText;
import com.tencent.TIMMessage;
import com.tencent.TIMValueCallBack;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

/**
 * 直播间观众业务类
 */
public class LiveViewerBusiness extends LiveBusiness {
    private LiveViewerBusinessCallback mBusinessCallback;
    /**
     * 是否可以加入房间
     */
    private boolean mCanJoinRoom = true;
    /**
     * 是否正在申请连麦
     */
    private boolean mIsInApplyLinkMic;

    public LiveViewerBusiness(ILiveActivity liveActivity) {
        super(liveActivity);
    }

    public void setBusinessCallback(LiveViewerBusinessCallback businessCallback) {
        this.mBusinessCallback = businessCallback;
        super.setBusinessCallback(businessCallback);
    }

    public void setCanJoinRoom(boolean canJoinRoom) {
        this.mCanJoinRoom = canJoinRoom;
    }

    /**
     * 开始加入房间
     */
    public void startJoinRoom() {
        if (mCanJoinRoom) {
            mBusinessCallback.onBsViewerStartJoinRoom();
        }
    }

    @Override
    protected void onRequestRoomInfoSuccess(App_get_videoActModel actModel) {
        super.onRequestRoomInfoSuccess(actModel);

        if (actModel.getLive_in() == 1) {
            mBusinessCallback.onBsViewerShowCreaterLeave(actModel.getOnline_status() == 0);
        } else {
            mBusinessCallback.onBsViewerShowCreaterLeave(false);
        }
    }

    /**
     * 检查连麦权限
     */
    public void requestCheckLianmai(AppRequestCallback<App_check_lianmaiActModel> listener) {
        CommonInterface.requestCheckLianmai(getLiveActivity().getRoomId(), new AppRequestCallbackWrapper<App_check_lianmaiActModel>(listener) {
            @Override
            public String getCancelTag() {
                return getHttpCancelTag();
            }

            @Override
            protected void onStart() {
                super.onStart();
                showProgress("");
            }

            @Override
            protected void onSuccess(SDResponse sdResponse) {
            }

            @Override
            protected void onFinish(SDResponse resp) {
                super.onFinish(resp);
                hideProgress();
            }
        });
    }

    /**
     * 检查直播间的状态
     *
     * @param roomId
     * @param callback
     */
    public void requestCheckVideoStatus(int roomId, AppRequestCallback<Video_check_statusActModel> callback) {
        CommonInterface.requestCheckVideoStatus(roomId, null, new AppRequestCallbackWrapper<Video_check_statusActModel>(callback) {
            @Override
            public String getCancelTag() {
                return getHttpCancelTag();
            }

            @Override
            protected void onSuccess(SDResponse sdResponse) {

            }
        });
    }

    @Override
    public void onMsgCreaterLeave(CustomMsgCreaterLeave msg) {
        super.onMsgCreaterLeave(msg);
        mBusinessCallback.onBsViewerShowCreaterLeave(true);
    }

    @Override
    public void onMsgCreaterComeback(CustomMsgCreaterComeback msg) {
        super.onMsgCreaterComeback(msg);
        mBusinessCallback.onBsViewerShowCreaterLeave(false);
    }

    /**
     * 申请连麦
     */
    public void applyLinkMic() {
        if (!isInLinkMic()) {
            CommonInterface.requestCheckLianmai(getLiveActivity().getRoomId(), new AppRequestCallback<App_check_lianmaiActModel>() {
                @Override
                public String getCancelTag() {
                    return getHttpCancelTag();
                }

                @Override
                protected void onStart() {
                    super.onStart();
                    showProgress("");
                }

                @Override
                protected void onSuccess(SDResponse sdResponse) {
                    if (actModel.isOk()) {
                        IMHelper.sendMsgC2C(getLiveActivity().getCreaterId(), new CustomMsgApplyLinkMic(), new TIMValueCallBack<TIMMessage>() {
                            @Override
                            public void onError(int code, String msg) {
                                mBusinessCallback.onBsViewerApplyLinkMicError("申请连麦失败:" + code + "," + msg);
                            }

                            @Override
                            public void onSuccess(TIMMessage timMessage) {
                                mBusinessCallback.onBsViewerShowApplyLinkMic(true);
                                mIsInApplyLinkMic = true;
                            }
                        });
                    }
                }

                @Override
                protected void onError(SDResponse resp) {
                    super.onError(resp);
                    mBusinessCallback.onBsViewerApplyLinkMicError("申请连麦失败:" + String.valueOf(resp.getThrowable()));
                }

                @Override
                protected void onFinish(SDResponse resp) {
                    super.onFinish(resp);
                    hideProgress();
                }
            });
        }
    }

    /**
     * 取消申请连麦
     */
    public void cancelApplyLinkMic() {
        if (mIsInApplyLinkMic) {
            IMHelper.sendMsgC2C(getLiveActivity().getCreaterId(), new CustomMsgStopLinkMic(), null);
            mIsInApplyLinkMic = false;
        }
    }

    /**
     * 停止连麦
     *
     * @param sendStopMsg 是否发送结束连麦的消息
     */
    public void stopLinkMic(boolean sendStopMsg) {
        if (isInLinkMic()) {
            requestStopLianmai(UserModelDao.getUserId());
            if (sendStopMsg) {
                IMHelper.sendMsgC2C(getLiveActivity().getCreaterId(), new CustomMsgStopLinkMic(), null);
            }

            setInLinkMic(false);
        }
    }

    @Override
    public void onMsgAcceptLinkMic(CustomMsgAcceptLinkMic msg) {
        super.onMsgAcceptLinkMic(msg);
        if (mIsInApplyLinkMic) {
            mBusinessCallback.onBsViewerShowApplyLinkMic(false);
            setInLinkMic(true);// 设置true，连麦中
            mIsInApplyLinkMic = false;
        }
    }


    @Override
    public void onMsgRejectLinkMic(CustomMsgRejectLinkMic msg) {
        super.onMsgRejectLinkMic(msg);
        if (mIsInApplyLinkMic) {
            mBusinessCallback.onBsViewerApplyLinkMicRejected(msg);

            mIsInApplyLinkMic = false;
        }
    }

    @Override
    public void onMsgHeatRank(CustomMsgHeatRank msg) {
        super.onMsgHeatRank(msg);
        mBusinessCallback.onBsHeatRank(msg);
    }

    /**
     * 退出房间
     *
     * @param needFinishActivity 是否需要结束Activity
     */
    public void exitRoom(boolean needFinishActivity) {
        mBusinessCallback.onBsViewerExitRoom(needFinishActivity);
    }

    public interface LiveViewerBusinessCallback extends LiveBusinessCallback {
        /**
         * 是否显示主播离开
         *
         * @param show true-显示，false-隐藏
         */
        void onBsViewerShowCreaterLeave(boolean show);

        /**
         * 显示申请连麦
         *
         * @param show true-显示，false-隐藏
         */
        void onBsViewerShowApplyLinkMic(boolean show);

        /**
         * 申请连麦失败
         *
         * @param msg
         */
        void onBsViewerApplyLinkMicError(String msg);

        /**
         * 主播拒绝连麦
         *
         * @param msg
         */
        void onBsViewerApplyLinkMicRejected(CustomMsgRejectLinkMic msg);

        /**
         * 加入房间(拉流，加入IM等)
         */
        void onBsViewerStartJoinRoom();

        /**
         * 观众退出房间回调
         *
         * @param needFinishActivity 是否需要结束Activity
         */
        void onBsViewerExitRoom(boolean needFinishActivity);

        void onBsHeatRank(CustomMsgHeatRank msg);
    }

    private static final int MAX_INPUT_LENGTH = 38;
    /**
     * 相同消息拦截间隔
     */
    private static final int DUR_BLOCK_SAME_MSG = 5 * 1000;
    /**
     * 消息拦截间隔
     */
    private static final int DUR_BLOCK_MSG = 2 * 1000;
    private SDObjectBlocker sendBlocker;

    public void initspeech() {
        sendBlocker = new SDObjectBlocker();
        sendBlocker.setMaxEqualsCount(1);
        sendBlocker.setBlockEqualsObjectDuration(DUR_BLOCK_SAME_MSG);
        sendBlocker.setBlockDuration(DUR_BLOCK_MSG);
    }

    public void sendMessage(String strContent) {
        {
            String groupId = getLiveActivity().getGroupId();
            if (TextUtils.isEmpty(groupId)) {
                return;
            }

            if (!getLiveActivity().isCreater()) {
                if (sendBlocker.block()) {
                    SDToast.showToast("发送太频繁");
                    return;
                }
                if (sendBlocker.blockObject(strContent)) {
                    SDToast.showToast("请勿刷屏");
                    return;
                }
            }

            final CustomMsgText msg = new CustomMsgText();
            msg.setText(strContent);
            msg.getSender().setV_identity(getLiveActivity().getRoomInfo().getPodcast().getV_identity());
            IMHelper.sendMsgGroup(groupId, msg, new TIMValueCallBack<TIMMessage>() {

                @Override
                public void onSuccess(TIMMessage timMessage) {
                    IMHelper.postMsgLocal(msg, timMessage.getConversation().getPeer());
                }

                @Override
                public void onError(int code, String desc) {
                    if (code == 80001) {
                        SDToast.showToast("该词已被禁用");
                    }
                }
            });
        }
    }

    public void requestSpeechToken(ApplicationInfo appInfo) {
        try {
            String ADDr = "https://openapi.baidu.com/oauth/2.0/token";
            HashMap<String, Object> params = new HashMap<>();
            params.put("grant_type", "client_credentials");
            params.put("client_id", appInfo.metaData.getString("com.baidu.speech.API_KEY"));
            params.put("client_secret", appInfo.metaData.getString("com.baidu.speech.SECRET_KEY"));
            CommonInterface.doHttpReqeust(0, "POST", ADDr, "", params, new CommonInterface.Json_CallBack() {
                @Override
                public void getJson(String str) {
                    SpeechToken bean = SDJsonUtil.json2Object(str, SpeechToken.class);
                    speech_token = bean.getAccess_token();
                }
            });
        }catch (Exception e){

        }
    }

    //REST API 所需的令牌
    String speech_token;

    private String encodeBase64File(String path) {
        File file = new File(path);
        FileInputStream inputFile = null;
        try {
            inputFile = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
            return Base64.encodeToString(buffer, Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void speechToText(String path, final Context context) {
        try {
            final String base64 = encodeBase64File(path);
            long len = new File(path).length();
            String ADDr = "http://vop.baidu.com/server_api";
            BodyParams bodyParams = new BodyParams("amr", 16000, 1536, 1, speech_token, "qianxiu_speech", len, base64);
            String json = SDJsonUtil.object2Json(bodyParams);
            CommonInterface.doHttpReqeust(1, "POST", ADDr, json, null, new CommonInterface.Json_CallBack() {
                @Override
                public void getJson(String str) {
                    SpeechToText bean = SDJsonUtil.json2Object(str, SpeechToText.class);
                    if(bean.getErr_no()==0){
                        if (null != bean.getResult()) {
                            sendMessage(bean.getResult().get(0));
                        }
                    }else{
                    }
                }
            });
        }catch (Exception e){
        }
    }
}
