package com.fanwe.live.business;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.live.IMHelper;
import com.fanwe.live.LiveInformation;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.event.EImOnNewMessages;
import com.fanwe.live.model.App_userinfoActModel;
import com.fanwe.live.model.Deal_send_propActModel;
import com.fanwe.live.model.LiveGiftModel;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.model.custommsg.CustomMsgPrivateGift;
import com.fanwe.live.model.custommsg.CustomMsgPrivateImage;
import com.fanwe.live.model.custommsg.CustomMsgPrivateText;
import com.fanwe.live.model.custommsg.CustomMsgPrivateVoice;
import com.fanwe.live.model.custommsg.MsgModel;
import com.fanwe.live.model.custommsg.MsgStatus;
import com.fanwe.live.model.custommsg.TIMMsgModel;
import com.tencent.TIMConversation;
import com.tencent.TIMMessage;
import com.tencent.TIMValueCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 私聊业务类
 */
public class LivePrivateChatBusiness extends BaseBusiness
{
    public LivePrivateChatBusiness(LivePrivateChatBusinessCallback callback)
    {
        mCallback = callback;
    }

    private TIMMessage mLastMsg;
    /**
     * 私聊的用户id
     */
    private String mUserId;
    private LivePrivateChatBusinessCallback mCallback;

    /**
     * 设置私聊用户id
     *
     * @param userId
     */
    public void setUserId(String userId)
    {
        mUserId = userId;
        LiveInformation.getInstance().setCurrentChatPeer(userId);
    }

    public String getUserId()
    {
        return mUserId;
    }

    /**
     * 设置最后一条im消息，加载历史消息的时候会从从最后一条消息往前加载
     *
     * @param lastMsg
     */
    public void setLastMsg(TIMMessage lastMsg)
    {
        mLastMsg = lastMsg;
    }

    /**
     * 是否可以发私信
     *
     * @return
     */
    public boolean canSendPrivateLetter()
    {
        UserModel userModel = UserModelDao.query();
        if (userModel != null)
        {
            return userModel.canSendPrivateLetter();
        } else
        {
            return true;
        }
    }


    /**
     * 请求用户信息
     */
    public void requestUserInfo()
    {
        CommonInterface.requestUserInfo(null, mUserId, new AppRequestCallback<App_userinfoActModel>()
        {
            @Override
            public String getCancelTag()
            {
                return getHttpCancelTag();
            }

            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.isOk())
                {
                    mCallback.onRequestUserInfoSuccess(actModel.getUser());
                } else
                {

                }
            }
        });
    }

    /**
     * 请求发送礼物
     *
     * @param model
     */
    public void requestSendGiftPrivate(final LiveGiftModel model,int num,int is_packge, String json)
    {
        CommonInterface.requestSendGiftPrivate(model.getId(), num, mUserId,is_packge,json, new AppRequestCallback<Deal_send_propActModel>()
        {
            @Override
            public String getCancelTag()
            {
                return getHttpCancelTag();
            }

            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.isOk())
                {
                    mCallback.onRequestSendGiftPrivateSuccess(actModel, model);
                }
            }
        });
    }

    /**
     * 加载历史消息
     *
     * @param count
     */
    public void loadHistoryMessage(int count)
    {
        TIMConversation conversation = IMHelper.getConversationC2C(mUserId);
        if (conversation == null)
        {
            return;
        }
        final List<MsgModel> listLocal = new ArrayList<>();
        conversation.getLocalMessage(count, mLastMsg, new TIMValueCallBack<List<TIMMessage>>()
        {

            @Override
            public void onSuccess(List<TIMMessage> list)
            {
                if (!SDCollectionUtil.isEmpty(list))
                {
                    Collections.reverse(list);
                    setLastMsg(list.get(0));

                    for (TIMMessage msg : list)
                    {
                        MsgModel msgModel = new TIMMsgModel(msg);
                        if (msgModel.isPrivateMsg() && msgModel.getStatus() != MsgStatus.HasDeleted)
                        {
                            listLocal.add(msgModel);
                        }
                    }
                }
                mCallback.onLoadHistoryMessageSuccess(listLocal);
            }

            @Override
            public void onError(int arg0, String str)
            {
                mCallback.onLoadHistoryMessageError();
            }
        });
    }

    /**
     * 接收IM新消息的时候调用
     *
     * @param event
     */
    public void onEventMainThread(EImOnNewMessages event)
    {
        // 判断新消息来源是否是当前用户
        if (event.msg.getConversationPeer().equals(mUserId))
        {
            if (event.msg.isPrivateMsg())
            {
                mCallback.onAdapterAppendData(event.msg);
            }
        }
    }

    /**
     * 发送IM文字
     *
     * @param content
     */
    public void sendIMText(String content)
    {
        CustomMsgPrivateText msg = new CustomMsgPrivateText();
        msg.setText(content);

        MsgModel msgModel = msg.parseToMsgModel();
        mCallback.onAdapterAppendData(msgModel);

        sendIMMsg(msgModel);
    }

    /**
     * 发送IM图片
     *
     * @param file
     */
    public void sendIMImage(File file)
    {
        CustomMsgPrivateImage msg = new CustomMsgPrivateImage();
        msg.setPath(file.getAbsolutePath());

        MsgModel msgModel = msg.parseToMsgModel();
        mCallback.onAdapterAppendData(msgModel);

        sendIMMsg(msgModel);
    }

    /**
     * 发送IM语音
     *
     * @param file
     * @param duration
     */
    public void sendIMVoice(File file, long duration)
    {
        CustomMsgPrivateVoice msg = new CustomMsgPrivateVoice();
        msg.setDuration(duration);
        msg.setPath(file.getAbsolutePath());

        MsgModel msgModel = msg.parseToMsgModel();
        mCallback.onAdapterAppendData(msgModel);

        sendIMMsg(msgModel);
    }

    /**
     * 发送IM礼物
     *
     * @param model
     */
    public void sendIMGift(Deal_send_propActModel model)
    {
        CustomMsgPrivateGift msg = new CustomMsgPrivateGift();

        msg.fillData(model);

        MsgModel msgModel = msg.parseToMsgModel();
        mCallback.onAdapterAppendData(msgModel);

        sendIMMsg(msgModel);
    }

    public TIMMessage sendIMMsg(final MsgModel model)
    {
        final int index = mCallback.onAdapterIndexOf(model);

        TIMMessage timMessageSending = IMHelper.sendMsgC2C(mUserId, model.getCustomMsg(), new TIMValueCallBack<TIMMessage>()
        {

            @Override
            public void onSuccess(TIMMessage timMessage)
            {
                if (mLastMsg == null)
                {
                    mLastMsg = timMessage;
                }

                if (model.getStatus() == MsgStatus.SendFail)
                {
                    model.remove();
                }

                model.setTimMessage(timMessage);

                mCallback.onAdapterUpdateData(index, model);
            }

            @Override
            public void onError(int arg0, String arg1)
            {
                mCallback.onAdapterUpdateData(index);
            }
        });

        model.setTimMessage(timMessageSending);
        mCallback.onAdapterUpdateData(index, model);
        return timMessageSending;
    }

    @Override
    protected BaseBusinessCallback getBaseBusinessCallback()
    {
        return mCallback;
    }

    public void onDestroy()
    {
        IMHelper.setSingleC2CReadMessage(mUserId);
        LiveInformation.getInstance().setCurrentChatPeer("");
        super.onDestroy();
    }

    public interface LivePrivateChatBusinessCallback extends BaseBusinessCallback
    {

        /**
         * 请求用户信息接口成功
         *
         * @param userModel
         */
        void onRequestUserInfoSuccess(UserModel userModel);

        /**
         * 请求私聊发送礼物接口成功
         *
         * @param actModel
         */
        void onRequestSendGiftPrivateSuccess(Deal_send_propActModel actModel, LiveGiftModel giftModel);

        /**
         * 加载历史消息成功
         *
         * @param listMsg
         */
        void onLoadHistoryMessageSuccess(List<MsgModel> listMsg);

        /**
         * 加载历史消息失败
         */
        void onLoadHistoryMessageError();

        void onAdapterAppendData(MsgModel model);

        void onAdapterUpdateData(int position, MsgModel model);

        void onAdapterUpdateData(int position);

        int onAdapterIndexOf(MsgModel model);
    }

}
