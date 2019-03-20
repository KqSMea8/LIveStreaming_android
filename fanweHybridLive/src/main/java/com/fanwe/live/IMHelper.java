package com.fanwe.live;

import android.text.TextUtils;
import android.util.Log;

import com.fanwe.hybrid.app.App;
import com.fanwe.hybrid.dao.InitActModelDao;
import com.fanwe.hybrid.model.InitActModel;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDTypeParseUtil;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.event.EIMLoginError;
import com.fanwe.live.event.EIMLoginSuccess;
import com.fanwe.live.event.EImOnNewMessages;
import com.fanwe.live.event.ERefreshMsgUnReaded;
import com.fanwe.live.model.ConversationUnreadMessageModel;
import com.fanwe.live.model.TotalConversationUnreadMessageModel;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.model.custommsg.CustomMsg;
import com.fanwe.live.model.custommsg.MsgModel;
import com.fanwe.live.model.custommsg.TIMMsgModel;
import com.fanwe.live.utils.LiveUtils;
import com.fanwe.live.utils.ReporterUtil;
import com.sunday.eventbus.SDEventManager;
import com.tencent.TIMCallBack;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMGroupManager;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMUser;
import com.tencent.TIMValueCallBack;

import java.util.ArrayList;
import java.util.List;

import me.leolin.shortcutbadger.ShortcutBadger;

public class IMHelper
{
    private static final String TAG = "IMHelper";

    private static boolean isInLogin = false;

    /**
     * IM登录
     *
     * @param userId
     * @param userSig
     */
    public static void loginIM(String userId, String userSig)
    {
        if (isInLogin)
        {
            return;
        }
        String loginUser = TIMManager.getInstance().getLoginUser();
        if (!TextUtils.isEmpty(loginUser))
        {
            LogUtil.i("login IM canceled already login user:" + loginUser);
            return;
        }
        InitActModel initActModel = InitActModelDao.query();
        if (initActModel == null)
        {
            postIMLoginError(-1, "login IM error because of null InitActModel");
            return;
        }
        String sdkAppid = initActModel.getSdkappid();
        String accountType = initActModel.getAccountType();
        if (TextUtils.isEmpty(sdkAppid) || TextUtils.isEmpty(accountType))
        {
            postIMLoginError(-1, "login IM error because of empty sdkAppid or accountType");
            return;
        }

        TIMUser user = new TIMUser();
        user.setIdentifier(userId);

        isInLogin = true;
        TIMManager.getInstance().login(SDTypeParseUtil.getInt(sdkAppid), user, userSig, new TIMCallBack()
        {
            @Override
            public void onError(int errCode, String errMsg)
            {
                isInLogin = false;
                postIMLoginError(errCode, "login IM error " + errMsg);
                App.getApplication().logout(true);
            }

            @Override
            public void onSuccess()
            {
                isInLogin = false;
                Log.i(TAG, "login IM success");

                IMHelper.joinAppGroup();
                IMHelper.joinOnlineGroup();

                EIMLoginSuccess event = new EIMLoginSuccess();
                SDEventManager.post(event);
            }
        });
    }

    private static void postIMLoginError(int errCode, String errMsg)
    {
        String info = "errCode:" + errCode + " errMsg:" + errMsg;
        Log.e(TAG, info);
        ReporterUtil.reportError(info);

        EIMLoginError event = new EIMLoginError();
        event.errCode = errCode;
        event.errMsg = errMsg;
        SDEventManager.post(event);
    }

    /**
     * IM退出登录
     *
     * @param callBack
     */
    public static void logoutIM(final TIMCallBack callBack)
    {
        quitOnlineGroup(new TIMCallBack()
        {
            @Override
            public void onError(int i, String s)
            {
                TIMManager.getInstance().logout(callBack);
            }

            @Override
            public void onSuccess()
            {
                TIMManager.getInstance().logout(callBack);
            }
        });
    }

    public static TIMConversation getConversationC2C(String id)
    {
        TIMConversation conversation = null;
        if (!TextUtils.isEmpty(id))
        {
            conversation = TIMManager.getInstance().getConversation(TIMConversationType.C2C, id);
        }
        return conversation;
    }

    public static TIMConversation getConversationGroup(String id)
    {
        TIMConversation conversation = null;
        if (!TextUtils.isEmpty(id))
        {
            conversation = TIMManager.getInstance().getConversation(TIMConversationType.Group, id);
        }
        return conversation;
    }

    public static void applyJoinGroup(final String groupId, String reason, final TIMCallBack callback)
    {
        if (TextUtils.isEmpty(groupId))
        {
            return;
        }

        TIMGroupManager.getInstance().applyJoinGroup(groupId, reason, new TIMCallBack()
        {
            @Override
            public void onSuccess()
            {
                LogUtil.i("applyJoinGroup success:" + groupId);
                if (callback != null)
                {
                    callback.onSuccess();
                }
            }

            @Override
            public void onError(int code, String desc)
            {
                LogUtil.i("applyJoinGroup error:" + groupId + "," + code + "," + desc);
                if (callback != null)
                {
                    callback.onError(code, desc);
                }
            }
        });
    }

    public static void quitGroup(final String groupId, final TIMCallBack callback)
    {
        if (TextUtils.isEmpty(groupId))
        {
            return;
        }
        TIMGroupManager.getInstance().quitGroup(groupId, new TIMCallBack()
        {

            @Override
            public void onSuccess()
            {
                LogUtil.i("quitGroup success:" + groupId);
                if (callback != null)
                {
                    callback.onSuccess();
                }
            }

            @Override
            public void onError(int code, String desc)
            {
                LogUtil.i("quitGroup error:" + groupId + "," + code + "," + desc);
                if (callback != null)
                {
                    callback.onError(code, desc);
                }
            }
        });
    }

    public static void deleteConversationAndLocalMsgsC2C(String peer)
    {
        if (TextUtils.isEmpty(peer))
        {
            return;
        }
        TIMManager.getInstance().deleteConversationAndLocalMsgs(TIMConversationType.C2C, peer);
    }

    public static void postMsgLocal(CustomMsg customMsg, String conversationId)
    {
        MsgModel msg = customMsg.parseToMsgModel();

        postMsgLocal(msg, conversationId);
    }

    private static void postMsgLocal(MsgModel msg, String conversationId)
    {
        if (msg != null)
        {
            msg.setLocalPost(true);
            msg.setSelf(true);
            msg.setConversationPeer(conversationId);

            EImOnNewMessages event = new EImOnNewMessages();
            event.msg = msg;
            SDEventManager.post(event);
        }
    }

    public static void sendMsgGroup(String id, CustomMsg customMsg, final TIMValueCallBack<TIMMessage> callback)
    {
        if (TextUtils.isEmpty(id))
        {
            return;
        }
        TIMConversation conversation = getConversationGroup(id);
        TIMMessage tMsg = customMsg.parseToTIMMessage();
        conversation.sendMessage(tMsg, new TIMValueCallBack<TIMMessage>()
        {

            @Override
            public void onSuccess(TIMMessage timMessage)
            {
                if (callback != null)
                {
                    callback.onSuccess(timMessage);
                }
            }

            @Override
            public void onError(int code, String desc)
            {
                LogUtil.i("sendMsgGroup error:" + code + "," + desc);
                if (code == 10017 || code == 20012)
                {
                    SDToast.showToast("你已被禁言");
                }
                if (callback != null)
                {
                    callback.onError(code, desc);
                }
            }
        });
    }

    public static TIMMessage sendMsgC2C(String id, CustomMsg customMsg, final TIMValueCallBack<TIMMessage> callback)
    {
        if (TextUtils.isEmpty(id))
        {
            return null;
        }
        TIMConversation conversation = getConversationC2C(id);
        TIMMessage tMsg = customMsg.parseToTIMMessage();
        conversation.sendMessage(tMsg, new TIMValueCallBack<TIMMessage>()
        {

            @Override
            public void onSuccess(TIMMessage timMessage)
            {
                if (callback != null)
                {
                    callback.onSuccess(timMessage);
                }
            }

            @Override
            public void onError(int code, String desc)
            {
                LogUtil.i("sendMsgC2C error:" + code + "," + desc);
                if (code == 10017 || code == 20012)
                {
                    SDToast.showToast("你已被禁言");
                }
                if (callback != null)
                {
                    callback.onError(code, desc);
                }
            }
        });
        return tMsg;
    }

    public static TotalConversationUnreadMessageModel getC2CTotalUnreadMessageModel()
    {
        TotalConversationUnreadMessageModel totalUnreadMessageModel = new TotalConversationUnreadMessageModel();

        UserModel user = UserModelDao.query();
        if (user == null)
        {
            return totalUnreadMessageModel;
        }

        long totalUnreadNum = 0;
        long cnt = TIMManager.getInstance().getConversationCount();
        for (long i = 0; i < cnt; ++i)
        {
            TIMConversation conversation = TIMManager.getInstance().getConversationByIndex(i);
            TIMConversationType type = conversation.getType();
            if (type == TIMConversationType.C2C)
            {
                // 自己对自己发的消息过滤
                if (!conversation.getPeer().equals(user.getUser_id()))
                {
                    long unreadnum = conversation.getUnreadMessageNum();
                    if (unreadnum > 0)
                    {
                        List<TIMMessage> list = conversation.getLastMsgs(1);
                        if (list != null && list.size() > 0)
                        {
                            TIMMessage msg = list.get(0);
                            MsgModel msgModel = new TIMMsgModel(msg);
                            if (msgModel.isPrivateMsg())
                            {
                                ConversationUnreadMessageModel unreadMessageModel = new ConversationUnreadMessageModel();
                                unreadMessageModel.setPeer(conversation.getPeer());
                                unreadMessageModel.setUnreadnum(unreadnum);
                                totalUnreadMessageModel.hashConver.put(conversation.getPeer(), unreadMessageModel);
                                totalUnreadNum = totalUnreadNum + unreadnum;
                            }
                        }
                    }
                }
            }
        }

        totalUnreadMessageModel.setTotalUnreadNum(totalUnreadNum);
        return totalUnreadMessageModel;
    }

    public static void setSingleC2CReadMessage(String peer)
    {
        setSingleC2CReadMessage(peer, true);
    }

    public static void setSingleC2CReadMessage(String peer, boolean isSendRefreshEvent)
    {
        TIMConversation conversation = TIMManager.getInstance().getConversation(TIMConversationType.C2C, peer);
        conversation.setReadMessage();
        if (isSendRefreshEvent)
        {
            postERefreshMsgUnReaded(true);
        }
    }

    public static void setAllC2CReadMessage()
    {
        setAllC2CReadMessage(true);
    }

    public static void setAllC2CReadMessage(boolean isSendRefreshEvent)
    {
        long cnt = TIMManager.getInstance().getConversationCount();
        for (long i = 0; i < cnt; ++i)
        {
            TIMConversation conversation = TIMManager.getInstance().getConversationByIndex(i);
            TIMConversationType type = conversation.getType();
            if (type == TIMConversationType.C2C)
            {
                conversation.setReadMessage();
            }
        }
        if (isSendRefreshEvent)
        {
            postERefreshMsgUnReaded(true);
        }
    }

    public static void postERefreshMsgUnReaded()
    {
        postERefreshMsgUnReaded(false);
    }

    public static void postERefreshMsgUnReaded(boolean isFromSetLocalReade)
    {
        ERefreshMsgUnReaded event = new ERefreshMsgUnReaded();
        event.model = IMHelper.getC2CTotalUnreadMessageModel();
        event.isFromSetLocalReaded = isFromSetLocalReade;
        SDEventManager.post(event);
    }

    public static long getC2CUnreadNumber(String peer)
    {
        TIMConversation conversation = getConversationC2C(peer);
        if (conversation != null)
        {
            return conversation.getUnreadMessageNum();
        } else
        {
            return 0;
        }
    }

    public static List<MsgModel> getC2CMsgList()
    {
        List<MsgModel> listMsg = new ArrayList<>();
        UserModel user = UserModelDao.query();
        if (user != null)
        {
            long count = TIMManager.getInstance().getConversationCount();
            for (long i = 0; i < count; ++i)
            {
                TIMConversation conversation = TIMManager.getInstance().getConversationByIndex(i);
                if (TIMConversationType.C2C == conversation.getType())
                {
                    // 自己对自己发的消息过滤
                    if (!conversation.getPeer().equals(user.getUser_id()))
                    {
                        List<TIMMessage> list = conversation.getLastMsgs(1);
                        if (list != null && list.size() > 0)
                        {
                            TIMMessage timMessage = list.get(0);
                            MsgModel msg = new TIMMsgModel(timMessage);
                            if (msg.isPrivateMsg())
                            {
                                listMsg.add(msg);
                            }
                        }
                    }
                }
            }
        }
        return listMsg;
    }

    /**
     * 加入全员广播大群
     */
    public static void joinAppGroup()
    {
        InitActModel actModel = InitActModelDao.query();
        if (actModel == null)
        {
            return;
        }

        String groupId = actModel.getFull_group_id();
        if (TextUtils.isEmpty(groupId))
        {
            return;
        }
        applyJoinGroup(groupId, "apply join android", new TIMCallBack()
        {
            @Override
            public void onError(int i, String s)
            {
            }

            @Override
            public void onSuccess()
            {
                LiveUtils.updateAeskey(false, null);
            }
        });
    }

    /**
     * 加入在线用户大群
     */
    public static void joinOnlineGroup()
    {
        InitActModel actModel = InitActModelDao.query();
        if (actModel == null)
        {
            return;
        }

        String groupId = actModel.getOn_line_group_id();
        if (TextUtils.isEmpty(groupId))
        {
            return;
        }
        applyJoinGroup(groupId, "apply join android", null);
    }

    /**
     * 退出在线用户大群
     */
    public static void quitOnlineGroup(TIMCallBack callback)
    {
        InitActModel actModel = InitActModelDao.query();
        if (actModel == null)
        {
            if (callback != null)
            {
                callback.onSuccess();
            }
            return;
        }

        String groupId = actModel.getOn_line_group_id();
        if (TextUtils.isEmpty(groupId))
        {
            if (callback != null)
            {
                callback.onSuccess();
            }
            return;
        }
        quitGroup(groupId, callback);
    }

}
