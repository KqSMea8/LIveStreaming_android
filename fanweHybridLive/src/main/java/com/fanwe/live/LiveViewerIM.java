package com.fanwe.live;

import com.fanwe.library.utils.LogUtil;
import com.fanwe.live.model.custommsg.CustomMsgViewerJoin;
import com.fanwe.live.model.custommsg.CustomMsgViewerQuit;
import com.fanwe.live.utils.ReporterUtil;
import com.tencent.TIMCallBack;
import com.tencent.TIMMessage;
import com.tencent.TIMValueCallBack;

/**
 * 直播聊天组观众操作类
 */
public class LiveViewerIM
{

    private String strGroupId = "";

    /**
     * 是否加入聊天组成功
     */
    private boolean isJoinGroupSuccess = false;
    /**
     * 是否可以发送加入消息
     */
    private boolean canSendViewerJoinMsg = false;
    /**
     * 是否可以发送退出消息
     */
    private boolean canSendViewerQuitMsg = false;

    public boolean isCanSendViewerJoinMsg()
    {
        return canSendViewerJoinMsg;
    }

    public void setJoinGroupSuccess(String groupId)
    {
        if (groupId == null)
        {
            return;
        }
        strGroupId = groupId;
        isJoinGroupSuccess = true;
        canSendViewerJoinMsg = true;
        canSendViewerQuitMsg = true;
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
                LogUtil.i("viewer join im(" + groupId + ") success");
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
                String info = "viewer join im(" + groupId + ") error " + code + "," + desc;
                LogUtil.e(info);
                ReporterUtil.reportError(info);
                isJoinGroupSuccess = false;
                canSendViewerJoinMsg = false;
                canSendViewerQuitMsg = false;
                if (listener != null)
                {
                    listener.onError(code, desc);
                }
            }
        });
    }

    /**
     * 退出聊天组
     *
     * @param listener
     */
    public void quitGroup(final TIMCallBack listener)
    {
        if (isJoinGroupSuccess)
        {
            final String groupId = strGroupId;
            IMHelper.quitGroup(groupId, new TIMCallBack()
            {
                @Override
                public void onSuccess()
                {
                    LogUtil.e("quit im success:" + groupId);
                    if (listener != null)
                    {
                        listener.onSuccess();
                    }
                }

                @Override
                public void onError(int code, String desc)
                {
                    LogUtil.e("quit im error" + groupId);
                    if (listener != null)
                    {
                        listener.onError(code, desc);
                    }
                }
            });
            reset();
        } else
        {
            if (listener != null)
            {
                listener.onSuccess();
            }
        }
    }

    private void reset()
    {
        isJoinGroupSuccess = false;
        canSendViewerJoinMsg = false;
        canSendViewerQuitMsg = false;
        strGroupId = "";
    }

    /**
     * 发送加入消息
     */
    public void sendViewerJoinMsg(CustomMsgViewerJoin joinMsg, final TIMValueCallBack<TIMMessage> listener)
    {
        if (canSendViewerJoinMsg)
        {
            canSendViewerJoinMsg = false;
            final CustomMsgViewerJoin msg = joinMsg == null ? new CustomMsgViewerJoin() : joinMsg;
            IMHelper.sendMsgGroup(strGroupId, msg, new TIMValueCallBack<TIMMessage>()
            {

                @Override
                public void onSuccess(TIMMessage timMessage)
                {
                    LogUtil.i("send viewer join success");
                    IMHelper.postMsgLocal(msg, timMessage.getConversation().getPeer());
                    if (listener != null)
                    {
                        listener.onSuccess(timMessage);
                    }
                }

                @Override
                public void onError(int code, String desc)
                {
                    LogUtil.i("send viewer join error");
                    canSendViewerJoinMsg = true;
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
                listener.onError(-1, "cant Send Viewer Join Msg");
            }
        }
    }

    /**
     * 发送退出消息
     */
    public void sendViewerQuitMsg(final TIMValueCallBack<TIMMessage> listener)
    {
        if (canSendViewerQuitMsg)
        {
            canSendViewerQuitMsg = false;
            CustomMsgViewerQuit msg = new CustomMsgViewerQuit();
            IMHelper.sendMsgGroup(strGroupId, msg, new TIMValueCallBack<TIMMessage>()
            {

                @Override
                public void onSuccess(TIMMessage timMessage)
                {
                    LogUtil.e("send quit group success");
                    if (listener != null)
                    {
                        listener.onSuccess(timMessage);
                    }
                }

                @Override
                public void onError(int code, String desc)
                {
                    LogUtil.e("send quit group error");
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
                listener.onError(-1, "cant Send Viewer Quit Msg");
            }
        }
    }

    /**
     * 销毁IM
     */
    public void destroyIM()
    {
        sendViewerQuitMsg(new TIMValueCallBack<TIMMessage>()
        {
            @Override
            public void onError(int i, String s)
            {
                quitGroup(null);
            }

            @Override
            public void onSuccess(TIMMessage timMessage)
            {
                quitGroup(null);
            }
        });
    }

}
