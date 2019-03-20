package com.fanwe.live;

import com.fanwe.library.utils.LogUtil;
import com.fanwe.live.model.custommsg.CustomMsgCreaterComeback;
import com.fanwe.live.model.custommsg.CustomMsgCreaterLeave;
import com.fanwe.live.model.custommsg.CustomMsgCreaterQuit;
import com.fanwe.live.utils.ReporterUtil;
import com.tencent.TIMCallBack;
import com.tencent.TIMGroupManager;
import com.tencent.TIMMessage;
import com.tencent.TIMValueCallBack;

/**
 * 直播聊天组主播操作类
 */
public class LiveCreaterIM
{

    private String strGroupId = "";

    /**
     * 是否加入聊天组成功
     */
    private boolean isJoinGroupSuccess = false;
    private boolean canSendCreaterQuitMsg = false;

    public void setJoinGroupSuccess(String groupId)
    {
        if (groupId == null)
        {
            return;
        }
        strGroupId = groupId;
        isJoinGroupSuccess = true;
        canSendCreaterQuitMsg = true;
    }

    /**
     * 加入聊天组
     *
     * @param groupId  聊天组id
     * @param listener
     */
    public void joinGroup(final String groupId, final TIMCallBack listener)
    {
        if (groupId == null)
        {
            if (listener != null)
            {
                listener.onError(-1, "groupId is null");
            }
            return;
        }
        if (groupId.equals(strGroupId))
        {
            if (listener != null)
            {
                listener.onSuccess();
            }
            return;
        }
        if (isJoinGroupSuccess)
        {
            if (listener != null)
            {
                listener.onSuccess();
            }
            return;
        }

        IMHelper.applyJoinGroup(groupId, "apply join", new TIMCallBack()
        {

            @Override
            public void onSuccess()
            {
                LogUtil.i("creater join im(" + groupId + ") success");
                setJoinGroupSuccess(groupId);
                IMHelper.getConversationGroup(groupId).disableStorage();
                if (listener != null)
                {
                    listener.onSuccess();
                }
            }

            @Override
            public void onError(int code, String desc)
            {
                String info = "creater join im(" + groupId + ") error " + code + "," + desc;

                LogUtil.i(info);
                ReporterUtil.reportError(info);
                isJoinGroupSuccess = false;
                canSendCreaterQuitMsg = false;
                if (listener != null)
                {
                    listener.onError(code, desc);
                }
            }
        });
    }

    /**
     * 创建聊天组
     *
     * @param groupName
     * @param listener
     */
    public void createGroup(String groupName, final TIMValueCallBack<String> listener)
    {
        if (isJoinGroupSuccess)
        {
            if (listener != null)
            {
                listener.onSuccess(strGroupId);
            }
            return;
        }

        TIMGroupManager.getInstance().createAVChatroomGroup(groupName, new TIMValueCallBack<String>()
        {

            @Override
            public void onSuccess(String groupId)
            {
                LogUtil.i("create im success " + groupId);
                setJoinGroupSuccess(groupId);
                IMHelper.getConversationGroup(groupId).disableStorage();
                if (listener != null)
                {
                    listener.onSuccess(groupId);
                }
            }

            @Override
            public void onError(int code, String desc)
            {
                String info = "creater create im error " + code + "," + desc;
                LogUtil.e(info);
                ReporterUtil.reportError(info);
                isJoinGroupSuccess = false;
                canSendCreaterQuitMsg = false;
                if (listener != null)
                {
                    listener.onError(code, desc);
                }
            }
        });
    }

    /**
     * 发送主播离开消息
     *
     * @param listener
     */
    public void sendCreaterLeaveMsg(final TIMValueCallBack<TIMMessage> listener)
    {
        if (isJoinGroupSuccess)
        {
            final CustomMsgCreaterLeave msg = new CustomMsgCreaterLeave();
            IMHelper.sendMsgGroup(strGroupId, msg, new TIMValueCallBack<TIMMessage>()
            {

                @Override
                public void onSuccess(TIMMessage timMessage)
                {
                    LogUtil.i("send creater leave success");
                    IMHelper.postMsgLocal(msg, strGroupId);
                    if (listener != null)
                    {
                        listener.onSuccess(timMessage);
                    }
                }

                @Override
                public void onError(int code, String desc)
                {
                    LogUtil.i("send creater leave error");
                    if (listener != null)
                    {
                        listener.onError(code, desc);
                    }
                }
            });
        } else
        {
            if (listener != null)
            {
                listener.onError(-1, "cant send creater leave msg");
            }
        }
    }

    /**
     * 发送主播回来消息
     *
     * @param listener
     */
    public void sendCreaterComebackMsg(final TIMValueCallBack<TIMMessage> listener)
    {
        if (isJoinGroupSuccess)
        {
            final CustomMsgCreaterComeback msg = new CustomMsgCreaterComeback();
            IMHelper.sendMsgGroup(strGroupId, msg, new TIMValueCallBack<TIMMessage>()
            {

                @Override
                public void onSuccess(TIMMessage timMessage)
                {
                    LogUtil.i("send creater comeback success");
                    IMHelper.postMsgLocal(msg, strGroupId);
                    if (listener != null)
                    {
                        listener.onSuccess(timMessage);
                    }
                }

                @Override
                public void onError(int code, String desc)
                {
                    LogUtil.i("send creater comeback error");
                    if (listener != null)
                    {
                        listener.onError(code, desc);
                    }
                }
            });
        } else
        {
            if (listener != null)
            {
                listener.onError(-1, "cant send creater comeback msg");
            }
        }
    }

    /**
     * 发送主播退出消息
     *
     * @param listener
     */
    public void sendCreaterQuitMsg(final TIMValueCallBack<TIMMessage> listener)
    {
        if (canSendCreaterQuitMsg)
        {
            canSendCreaterQuitMsg = false;
            IMHelper.sendMsgGroup(strGroupId, new CustomMsgCreaterQuit(), new TIMValueCallBack<TIMMessage>()
            {

                @Override
                public void onSuccess(TIMMessage timMessage)
                {
                    LogUtil.e("send creater quit success");
                    if (listener != null)
                    {
                        listener.onSuccess(timMessage);
                    }
                }

                @Override
                public void onError(int code, String desc)
                {
                    LogUtil.e("send creater quit error");
                    if (listener != null)
                    {
                        listener.onError(code, desc);
                    }
                }
            });
        } else
        {
            if (listener != null)
            {
                listener.onError(-1, "cant send creater quit msg");
            }
        }
    }

    /**
     * 销毁IM
     */
    public void destroyIM()
    {

    }

}
